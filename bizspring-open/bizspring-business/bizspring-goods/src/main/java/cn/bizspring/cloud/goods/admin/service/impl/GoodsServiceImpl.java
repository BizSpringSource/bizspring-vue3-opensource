package cn.bizspring.cloud.goods.admin.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.Transient;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.goods.common.entity.*;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import cn.bizspring.cloud.store.common.feign.FeignGoodsCategoryStoreService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.goods.admin.repository.GoodsRepository;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.goods.admin.mapper.*;
import cn.bizspring.cloud.goods.admin.service.GoodsService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import cn.bizspring.cloud.store.common.feign.FeignStoreGoodsCategoryService;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.order.common.feign.FeignSnService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * Service - 商品
 * 
 */
@Service
@AllArgsConstructor
public class GoodsServiceImpl extends BizServiceImpl<Goods> implements GoodsService {

	private GoodsMapper goodsMapper;
	private SkuMapper skuMapper;
	private GoodsCategoryMapper goodsCategoryMapper;
	private BrandMapper brandMapper;
	private StockMapper stockMapper;
	private GoodsRepository goodsRepository;
	private FeignSnService feignSnService;
	private FeignStoreGoodsCategoryService feignStoreGoodsCategoryService;
	private FeignStoreService feignStoreService;
	private FeignGoodsCategoryStoreService feignGoodsCategoryStoreService;

	@Override
	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return goodsMapper.exists("sn", sn);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "goods", condition = "#useCache")
	public List<Goods> findList(String storeId, String goodsCategoryId, String storeGoodsCategoryId, String brandId, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
								  Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache) {
		Store store = null;
		if (CommonUtils.isNotEmpty(storeId)) {
			store = feignStoreService.find(storeId, SecurityEnum.FROM_IN);
			if (storeId != null && store == null) {
				return Collections.emptyList();
			}
		}
		GoodsCategory goodsCategory = null;
		if (CommonUtils.isNotEmpty(goodsCategoryId)) {
			goodsCategory = goodsCategoryMapper.find(goodsCategoryId);
			if (goodsCategoryId != null && goodsCategory == null) {
				return Collections.emptyList();
			}
		}
		StoreGoodsCategory storeGoodsCategory = null;
		if (CommonUtils.isNotEmpty(storeGoodsCategoryId)) {
			storeGoodsCategory = feignStoreGoodsCategoryService.find(storeGoodsCategoryId, SecurityEnum.FROM_IN);
			if (storeGoodsCategoryId != null && storeGoodsCategory == null) {
				return Collections.emptyList();
			}
		}
		Brand brand = null;
		if (CommonUtils.isNotEmpty(brandId)) {
			brand = brandMapper.find(brandId);
			if (brandId != null && brand == null) {
				return Collections.emptyList();
			}
		}
		QueryWrapper<Goods> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return goodsMapper.findByWrapperList(queryWrapper, store, goodsCategory, storeGoodsCategory, brand,  startPrice, endPrice, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, orderType);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Goods> findSelectionList(Store store, int count){
		return goodsMapper.findSelectionList(store, count);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Goods> findPage(String sn,String name,String keyword, Store.Type storeType, Store store, GoodsCategory goodsCategory, StoreGoodsCategory storeGoodsCategory, Brand brand,
			BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType,List<Store> stores,Pageable pageable) {
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		IPage<Goods> iPage = getPluginsPage(pageable);
		iPage.setRecords(goodsMapper.findPage(iPage, getPageable(pageable),sn,name,keyword,storeType, store, goodsCategory, storeGoodsCategory, brand, startPrice, endPrice, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, orderType,stores));
		return super.findPage(iPage, pageable);
	}

	@Override
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public Goods create(Goods goods, Sku sku) {
		if (sku.getMarketPrice() == null) {
			sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
		}
		sku.setAllocatedStock(0);
		sku.setIsDefault(true);
		sku.setSpecificationValues(null);
		goods.setPrice(sku.getPrice());
		goods.setCost(sku.getCost());
		goods.setMarketPrice(sku.getMarketPrice());
		goods.setIsActive(true);
		goods.setSpecificationItems(null);
		setValue(goods);
		goods.setSn(StringUtils.lowerCase(goods.getSn()));
		if (CollectionUtils.isNotEmpty(goods.getGoodsImages())) {
			Collections.sort(goods.getGoodsImages());
		}
		super.save(goods);
		sku.setGoodsId(goods.getId());
		setValue(goods,sku);
		skuMapper.save(sku);
		stockIn(sku);

		return goods;
	}

	@Override
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public Goods modify(Goods goods, Sku sku) {
		Goods pGoods = goodsMapper.find(goods.getId());
		if (sku.getMarketPrice() == null) {
			sku.setMarketPrice(calculateDefaultMarketPrice(sku.getPrice()));
		}
		sku.setAllocatedStock(0);
		sku.setIsDefault(true);
		sku.setGoodsId(goods.getId());
		sku.setSpecificationValues(null);
		if (pGoods.hasSpecification()&&CommonUtils.isNull(sku)) {
			if (sku.getStock() == null) {
				throw new IllegalArgumentException();
			}
			setValue(goods,sku);
			skuMapper.save(sku);
			stockIn(sku);
		} else {
			Sku defaultSku = getDefaultSku(pGoods);
			defaultSku.setPrice(sku.getPrice());
			defaultSku.setCost(sku.getCost());
			defaultSku.setMarketPrice(sku.getMarketPrice());
			defaultSku.setUpdatedTime(new Date());
			defaultSku.setStock(sku.getStock());
			skuMapper.update(defaultSku);
		}

		goods.setPrice(sku.getPrice());
		goods.setCost(sku.getCost());
		goods.setMarketPrice(sku.getMarketPrice());
		setValue(goods);
		BeanUtils.copyProperties(goods, pGoods, "sn", "type", "score", "totalScore", "scoreCount", "hits", "weekHits", "monthHits", "sales", "weekSales", "monthSales", "weekHitsDate", "monthHitsDate", "weekSalesDate", "monthSalesDate", "reviews", "consultations", "goodsFavorites", "skus", "store");
		if (CollectionUtils.isNotEmpty(pGoods.getGoodsImages())) {
			Collections.sort(pGoods.getGoodsImages());
		}
		super.update(pGoods);
		//添加搜索
		goodsRepository.delete(pGoods);
		//添加搜索
		goodsRepository.save(pGoods);
		return pGoods;
	}

	@Override
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void refreshExpiredStoreGoodsActive() {
		goodsMapper.refreshExpiredStoreGoodsActive();
	}

	@Override
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void refreshActive(Store store) {
		List<GoodsCategoryStore> goodsCategoryStores = new ArrayList<>();
		GoodsCategoryStore goodsCategoryStore = new GoodsCategoryStore();
		if(CommonUtils.isNotNull(store)&&!store.hasExpired()){
			goodsCategoryStore.setStoresId(store.getId());
			goodsCategoryStores= feignGoodsCategoryStoreService.findAllByEntity(goodsCategoryStore,SecurityEnum.FROM_IN);
			goodsMapper.refreshActiveValid(goodsCategoryStores,store);
		}
		if(CommonUtils.isNotNull(store)&&(store.hasExpired()||!true==store.getIsEnabled()||Store.Status.SUCCESS!=store.getStatus())){
			goodsCategoryStore.setStoresId(store.getId());
			goodsCategoryStores= feignGoodsCategoryStoreService.findAllByEntity(goodsCategoryStore,SecurityEnum.FROM_IN);
			goodsMapper.refreshActiveInValid(goodsCategoryStores,store);
		}
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public boolean save(Goods goods) {
		return super.save(goods);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public Goods update(Goods goods) {
		return super.update(goods);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public Goods update(Goods goods, String... ignoreProperties) {
		return super.update(goods, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(String id) {
		goodsMapper.delete(Arrays.asList(id));
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(String... ids) {
		goodsMapper.delete(Arrays.asList(ids));
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(Goods goods) {
		goodsMapper.delete(Arrays.asList(goods.getId()));
	}

	/**
	 * 设置商品值
	 * 
	 * @param goods
	 *            商品
	 */
	private void setValue(Goods goods) {
		if (goods == null) {
			return;
		}

		if (goods.isNew()) {
			if (StringUtils.isEmpty(goods.getSn())) {
				String sn;
				do {
					sn = feignSnService.generate(Sn.Type.GOODS, SecurityEnum.FROM_IN);
				} while (snExists(sn));
				goods.setSn(sn);
			}
		}
	}

	/**
	 * 设置SKU值
	 * 
	 * @param sku
	 *            SKU
	 */
	private void setValue(Goods goods,Sku sku) {
		if (sku == null) {
			return;
		}
		if (sku.isNew()) {
			if (goods != null && StringUtils.isNotEmpty(goods.getSn())) {
				String sn;
				int i = sku.hasSpecification() ? 1 : 0;
				do {
					sn = goods.getSn() + (i == 0 ? StringUtils.EMPTY : "_" + i);
					i++;
				} while (skuMapper.exists("sn", sn));
				sku.setGoodsId(goods.getId());
				sku.setSn(sn);
				sku.setId(String.valueOf(IdWorker.getId()));
				sku.setCreatedTime(new Date());
				sku.setVersion(0L);
			}
		}
	}

	/**
	 * 计算默认市场价
	 * 
	 * @param price
	 *            价格
	 * @return 默认市场价
	 */
	private BigDecimal calculateDefaultMarketPrice(BigDecimal price) {
		return price.multiply(new BigDecimal(1.2));
	}

	/**
	 * 入库
	 * 
	 * @param sku
	 *            SKU
	 */
	private void stockIn(Sku sku) {
		if (sku == null || sku.getStock() == null || sku.getStock() <= 0) {
			return;
		}

		Stock stock = new Stock();
		stock.setType(Stock.Type.STOCK_IN);
		stock.setInQuantity(sku.getStock());
		stock.setOutQuantity(0);
		stock.setStock(sku.getStock());
		stock.setRemark(null);
		stock.setSkuId(sku.getId());
		stock.setId(String.valueOf(IdWorker.getId()));
		stock.setCreatedTime(new Date());
		stock.setVersion(0L);
		stockMapper.save(stock);
	}


	/**
	 * 获取默认SKU
	 *
	 * @return 默认SKU
	 */
	@JsonView(BizEntity.BaseView.class)
	@Transient
	public Sku getDefaultSku(Goods goods) {
		Map columnMap=new HashMap();
		columnMap.put("goods_id",goods.getId());
		List<Sku> skus=skuMapper.selectByMap(columnMap);
		return (Sku) CollectionUtils.find(skus, new Predicate() {
			public boolean evaluate(Object object) {
				Sku sku = (Sku) object;
				return sku != null && sku.getIsDefault();
			}
		});
	}
}