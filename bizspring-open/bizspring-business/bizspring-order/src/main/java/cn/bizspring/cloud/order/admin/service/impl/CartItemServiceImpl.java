package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.order.admin.service.CartItemService;
import cn.bizspring.cloud.order.common.entity.CartItem;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.goods.common.feign.FeignSkuService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * Service - 购物车项
 * 
 */
@Service
@AllArgsConstructor
public class CartItemServiceImpl extends BizServiceImpl<CartItem> implements CartItemService {

	private FeignSkuService feignSkuService;
	private FeignStoreService feignStoreService;
	private FeignGoodsService feignGoodsService;
	/**
	 * 获取店铺
	 *
	 * @return 店铺
	 */
	public Store getStore(CartItem cartItem) {
	    Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods=feignGoodsService.find(sku.getGoodsId(),SecurityEnum.FROM_IN);
	    Store store=feignStoreService.find(goods.getStoreId(),SecurityEnum.FROM_IN);
		return store != null ? store : null;
	}

	/**
	 * 获取重量
	 *
	 * @return 重量
	 */
	public Integer getWeight(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods=feignGoodsService.find(sku.getGoodsId(),SecurityEnum.FROM_IN);
		if (sku != null && goods.getWeight() != null && cartItem.getQuantity() != null) {
			return goods.getWeight() * cartItem.getQuantity();
		} else {
			return null;
		}
	}

	/**
	 * 获取小计
	 *
	 * @return 小计
	 */
	public BigDecimal getSubtotal(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		if (cartItem.getQuantity() != null) {
			return sku.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取是否有效
	 *
	 * @return 是否有效
	 */
	public boolean getIsActive(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(goods)) {
			return goods != null && goods.getIsActive();
		}
		return false;
	}

	/**
	 * 获取是否上架
	 *
	 * @return 是否上架
	 */
	public boolean getIsMarketable(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(goods)) {
			return goods != null && goods.getIsMarketable();
		}
		return false;
	}

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	public boolean getIsDelivery(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(goods)) {
			return goods != null && goods.getIsDelivery();
		}
		return false;
	}

	/**
	 * 获取是否库存不足
	 *
	 * @return 是否库存不足
	 */
	public boolean getIsLowStock(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		return cartItem.getQuantity() != null && sku != null && cartItem.getQuantity() > sku.getAvailableStock();
	}

	/**
	 * 获取是否存在过期店铺商品
	 *
	 * @return 是否存在过期店铺商品
	 */
	public boolean hasExpiredGoods(CartItem cartItem) {
		Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
		Goods goods=feignGoodsService.find(sku.getGoodsId(),SecurityEnum.FROM_IN);
		Store store=feignStoreService.find(goods.getStoreId(),SecurityEnum.FROM_IN);
		return store != null && store.hasExpired();
	}

}