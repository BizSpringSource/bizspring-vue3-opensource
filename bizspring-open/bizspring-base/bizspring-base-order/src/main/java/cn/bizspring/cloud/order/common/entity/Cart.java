package cn.bizspring.cloud.order.common.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.bizspring.cloud.goods.common.entity.Sku;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;


/**
 * Entity - 购物车
 * 
 */
@Data
@ApiModel(description = "购物车")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_cart")
public class Cart extends BizEntity<Cart> implements Iterable<CartItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 超时时间
	 */
	public static final int TIMEOUT = 604800;

	/**
	 * 密钥
	 */
	@TableField(value = "cart_key")
	private String key;

	/**
	 * 过期时间
	 */
	private Date expire;

	/**
	 * 会员
	 */
	@JsonIgnore
	@TableField(exist = false)
	private String memberId;

	/**
	 * 购物车项
	 */
	@JsonIgnore
	@TableField(exist = false)
	private Set<CartItem> cartItems = new HashSet<>();

	/**
	 * 获取购物车项
	 *
	 * @param sku
	 *            SKU
	 * @return 购物车项
	 */
	@Transient
	public CartItem getCartItem(final Sku sku) {
		return (CartItem) CollectionUtils.find(getCartItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				return cartItem != null && cartItem.getSkuId() != null && cartItem.getSkuId().equals(sku.getId());
			}
		});
	}

	/**
	 * 判断是否包含SKU
	 *
	 * @param sku
	 *            SKU
	 * @return 是否包含SKU
	 */
	@Transient
	public boolean contains(Sku sku) {
		return getCartItem(sku) != null;
	}

	/**
	 * 购物车项数量
	 *
	 * @return 购物车项数量
	 */
	@Transient
	public int size() {
		return getCartItems() != null ? getCartItems().size() : 0;
	}

	/**
	 * 判断购物车项是否为空
	 *
	 * @return 购物车项是否为空
	 */
	@Transient
	public boolean isEmpty() {
		return CollectionUtils.isEmpty(getCartItems());
	}

	/**
	 * 添加购物车项
	 *
	 * @param cartItem
	 *            购物车项
	 */
	@Transient
	public void add(CartItem cartItem) {
		if (getCartItems() == null) {
			setCartItems(new HashSet<CartItem>());
		}
		getCartItems().add(cartItem);
	}

	/**
	 * 移除购物车项
	 * 
	 * @param cartItem
	 *            购物车项
	 */
	@Transient
	public void remove(CartItem cartItem) {
		if (getCartItems() != null) {
			getCartItems().remove(cartItem);
		}
	}

	/**
	 * 清空购物车项
	 */
	@Transient
	public void clear() {
		if (getCartItems() != null) {
			getCartItems().clear();
		}
	}

	/**
	 * 实现iterator方法
	 * 
	 * @return Iterator
	 */
	@Override
	@Transient
	public Iterator<CartItem> iterator() {
		return getCartItems() != null ? getCartItems().iterator() : Collections.<CartItem>emptyIterator();
	}

}