package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.order.common.entity.Cart;
import cn.bizspring.cloud.order.common.entity.CartItem;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.store.common.entity.Store;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service - 购物车
 * 
 */
public interface CartService extends BizService<Cart> {

	/**
	 * 创建购物车
	 * 
	 * @return 购物车
	 */
	Cart create();

	/**
	 * 添加购物车SKU
	 * 
	 * @param cart
	 *            购物车
	 * @param sku
	 *            SKU
	 * @param quantity
	 *            数量
	 */
	void add(Cart cart, Sku sku, int quantity);

	/**
	 * 修改购物车SKU
	 * 
	 * @param cart
	 *            购物车
	 * @param sku
	 *            SKU
	 * @param quantity
	 *            数量
	 */
	void modify(Cart cart, Sku sku, int quantity);


	/**
	 * 是否选择购物车SKU
	 * @param currentCart 购物车
	 * @param sku SKU
	 * @param checked  是否选择
	 */
	void select(Cart currentCart, Sku sku, Boolean checked);

	/**
	 * 移除购物车SKU
	 * 
	 * @param cart
	 *            购物车
	 * @param sku
	 *            SKU
	 */
	void remove(Cart cart, Sku sku);

	/**
	 * 清空购物车SKU
	 * 
	 * @param cart
	 *            购物车
	 */
	void clear(Cart cart);

	/**
	 * 获取购物车项
	 * <p>
	 * //	 * @param store
	 * 店铺
	 *
	 * @return 购物车项
	 */
	Set<CartItem> getCartItems(Cart cart, final Store store);

	/**
	 * 获取购物车项
	 * <p>
	 * //	 * @param cart
	 * 购物车
	 *
	 * @return 购物车项
	 */
	Set<CartItem> getCartItems(Cart cart);

	/**
	 * 获取购物车项组
	 *
	 * @return 购物车项组
	 */
	Map<Store, Set<CartItem>> getCartItemGroup(Cart cart,Boolean isDirectBuy);

	/**
	 * 获取店铺
	 *
	 * @return 店铺
	 */
	Set<Store> getStores(Cart cart);

	/**
	 * 判断是否包含店铺类型
	 *
	 * @return 是否包含店铺类型
	 */
	boolean contains(Cart cart,final Store.Type type);

	/**
	 * 获取重量
	 *
	 * @param store               店铺
	 * @return 重量
	 */
	int getWeight(Cart cart, Store store);

	/**
	 * 获取数量
	 *
	 * @param store       店铺
	 * @return 数量
	 */
	int getQuantity(Cart cart, Store store);


	/**
	 * 获取数量
	 *
	 * @return 数量
	 */
	int getQuantity(Cart cart);

	/**
	 * 获取价格
	 *
	 * @param store 店铺
	 * @return 价格
	 */
	BigDecimal getPrice(Cart cart,Store store);

	/**
	 * 获取有效价格
	 *
	 * @param store 店铺
	 * @return 有效价格
	 */
	BigDecimal getEffectivePrice(Cart cart, Store store,BigDecimal discount);

	/**
	 * 获取有效价格
	 *
	 * @return 有效价格
	 */
	BigDecimal getEffectivePrice(Cart cart,BigDecimal discount);

	/**
	 * 获取是否需要物流
	 *
	 * @param store 店铺
	 * @return 是否需要物流
	 */
	boolean getIsDelivery(Cart cart, Store store);

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	boolean getIsDelivery(Cart cart);

	/**
	 * 获取标签
	 *
	 * @return 标签
	 */
	String getTag(Cart cart);

	/**
	 * 判断是否存在无效商品
	 *
	 * @return 是否存在无效商品
	 */
	boolean hasNotActive(Cart cart);

	/**
	 * 判断是否存在已下架商品
	 *
	 * @return 是否存在已下架商品
	 */
	boolean hasNotMarketable(Cart cart);

	/**
	 * 判断是否存在库存不足商品
	 *
	 * @return 是否存在库存不足商品
	 */
	boolean hasLowStock(Cart cart);


	/**
	 * 判断是否存在过期店铺商品
	 *
	 * @return 是否存在过期店铺商品
	 */
	boolean hasExpiredGoods(Cart cart);

}