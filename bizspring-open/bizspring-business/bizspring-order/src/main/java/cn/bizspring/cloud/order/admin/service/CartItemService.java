package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.order.common.entity.CartItem;
import cn.bizspring.cloud.store.common.entity.Store;

import java.math.BigDecimal;

/**
 * Service - 购物车项
 * 
 */
public interface CartItemService extends BizService<CartItem> {


	Store getStore(CartItem cartItem);

	/**
	 * 获取重量
	 *
	 * @return 重量
	 */
	Integer getWeight(CartItem cartItem);

	/**
	 * 获取小计
	 *
	 * @return 小计
	 */
	BigDecimal getSubtotal(CartItem cartItem);

	/**
	 * 获取是否有效
	 *
	 * @return 是否有效
	 */
	boolean getIsActive(CartItem cartItem);

	/**
	 * 获取是否上架
	 *
	 * @return 是否上架
	 */
	boolean getIsMarketable(CartItem cartItem);

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	boolean getIsDelivery(CartItem cartItem);

	/**
	 * 获取是否库存不足
	 *
	 * @return 是否库存不足
	 */
	boolean getIsLowStock(CartItem cartItem);

	/**
	 * 获取是否存在过期店铺商品
	 *
	 * @return 是否存在过期店铺商品
	 */
	boolean hasExpiredGoods(CartItem cartItem);

}