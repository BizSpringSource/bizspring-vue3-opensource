package cn.bizspring.cloud.goods.admin.service.impl;

import java.util.*;


import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.goods.admin.mapper.GoodsMapper;
import cn.bizspring.cloud.goods.admin.mapper.SkuMapper;
import cn.bizspring.cloud.goods.admin.mapper.StockMapper;
import cn.bizspring.cloud.goods.admin.service.GoodsCategoryService;
import cn.bizspring.cloud.goods.admin.service.SkuService;
import cn.bizspring.cloud.goods.common.entity.*;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

/**
 * Service - SKU
 * 
 */
@Service
@AllArgsConstructor
public class SkuServiceImpl extends BizServiceImpl<Sku> implements SkuService {

	private SkuMapper skuMapper;
	private StockMapper stockMapper;
	private GoodsMapper goodsMapper;
	private FeignStoreService feignStoreService;
	private GoodsCategoryService goodsCategoryService;

	@Override
	public void addStock(Sku sku, int amount, Stock.Type type, String remark) {
		if (amount == 0) {
			return;
		}
		sku.setStock(sku.getStock() + amount);
		super.update(sku);

		Stock stock = new Stock();
		stock.setType(type);
		stock.setInQuantity(amount > 0 ? amount : 0);
		stock.setOutQuantity(amount < 0 ? Math.abs(amount) : 0);
		stock.setStock(sku.getStock());
		stock.setRemark(remark);
		stock.setSkuId(sku.getId());
		stock.setId(String.valueOf(IdWorker.getId()));
		stock.setCreatedTime(new Date());
		stock.setVersion(0L);
		stockMapper.save(stock);
	}

	@Override
	public void addAllocatedStock(Sku sku, int amount) {
		if (amount == 0) {
			return;
		}
		sku.setAllocatedStock(sku.getAllocatedStock() + amount);
		super.update(sku);
	}

	/**
	 * 获取名称
	 *
	 * @return 名称
	 */
	@JsonView(BizEntity.BaseView.class)
	public String getName(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods ? goods.getName() : null;
	}

	/**
	 * 获取单位
	 *
	 * @return 单位
	 */
	public String getUnit(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods ? goods.getUnit() : null;
	}

	/**
	 * 获取重量
	 *
	 * @return 重量
	 */
	public Integer getWeight(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods ? goods.getWeight() : null;
	}

	/**
	 * 获取是否有效
	 *
	 * @return 是否有效
	 */
	public boolean getIsActive(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods && BooleanUtils.isTrue(goods.getIsActive());
	}

	/**
	 * 获取是否上架
	 *
	 * @return 是否上架
	 */
	public boolean getIsMarketable(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods && BooleanUtils.isTrue(goods.getIsMarketable());
	}

	/**
	 * 获取是否列出
	 *
	 * @return 是否列出
	 */
	public boolean getIsList(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods  && BooleanUtils.isTrue(goods.getIsList());
	}

	/**
	 * 获取是否置顶
	 *
	 * @return 是否置顶
	 */
	public boolean getIsTop(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods && BooleanUtils.isTrue(goods.getIsTop());
	}

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	public boolean getIsDelivery(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods && BooleanUtils.isTrue(goods.getIsDelivery());
	}

	/**
	 * 获取缩略图
	 *
	 * @return 缩略图
	 */
	@JsonView(BizEntity.BaseView.class)
	public String getThumbnail(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		return null != goods ? goods.getThumbnail() : null;
	}

	/**
	 * 获取店铺
	 *
	 * @return 店铺
	 */

	public Store getStore(Sku sku) {
		Goods goods = goodsMapper.find(sku.getGoodsId());
		Store store = null;
		if (null != goods) {
			store = feignStoreService.find(goods.getStoreId(), SecurityEnum.FROM_IN);
		}
		return null != store ? store : null;
	}

}