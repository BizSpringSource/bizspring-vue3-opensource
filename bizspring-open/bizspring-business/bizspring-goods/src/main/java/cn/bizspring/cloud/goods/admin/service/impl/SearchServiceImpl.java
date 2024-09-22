package cn.bizspring.cloud.goods.admin.service.impl;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.goods.admin.mapper.GoodsMapper;
import cn.bizspring.cloud.goods.admin.repository.GoodsRepository;
import cn.bizspring.cloud.goods.admin.service.SearchService;
import cn.bizspring.cloud.goods.admin.service.SkuService;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreRepository;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Service - 搜索
 * 
 */
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class SearchServiceImpl implements SearchService {

	private GoodsRepository goodsRepository;
	private GoodsMapper goodsMapper;
	private SkuService skuService;
	private FeignStoreRepository feignStoreRepository;
	private FeignStoreService feignStoreService;

	@Override
	public void index(Class<?> type) {
		index(type, true);
	}

	@Override
	public void index(Class<?> type, boolean purgeAll) {
		// 清除所有
		goodsRepository.deleteAll();
		// 商品
		if (type.isAssignableFrom(Goods.class)) {
			// 清除所有
			goodsRepository.deleteAll();
			// 查询符合条件
			QueryWrapper<Goods> queryGoods = new QueryWrapper<>();
			List<Goods> goodss = goodsMapper.selectList(queryGoods);
			for (Goods goods : goodss) {
				Goods persistant = goodsMapper.find(goods.getId());
				goods.setStoreId(persistant.getStoreId());
				
				QueryWrapper<Sku> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("goods_id", goods.getId());
				Set<Sku> skus = new HashSet<>(skuService.list(queryWrapper));
				Iterator<Sku> it =skus.iterator();
				while (it.hasNext()){
					Sku sku=it.next();
					sku.setGoodsId(goods.getId());
				}
				goods.setGoodsImages(persistant.getGoodsImages());
				goodsRepository.save(goods);
			}
		}
		// 店铺
		if (type.isAssignableFrom(Store.class)) {
			// 清除所有
			goodsRepository.deleteAll();
			QueryWrapper<Store> queryStore = new QueryWrapper<>();
			queryStore.eq("status", Store.Status.SUCCESS.ordinal());
			queryStore.eq("is_enabled", true);
			List<Store> stores = feignStoreService.findAll(SecurityEnum.FROM_IN);
			feignStoreRepository.saveAll(stores, SecurityEnum.FROM_IN);
		}
	}

}