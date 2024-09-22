package cn.bizspring.cloud.order.admin.service.impl;

import java.math.BigDecimal;
import java.util.*;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.order.admin.mapper.CartMapper;
import cn.bizspring.cloud.order.admin.mapper.CartItemMapper;
import cn.bizspring.cloud.order.admin.service.CartItemService;
import cn.bizspring.cloud.order.admin.service.CartService;
import cn.bizspring.cloud.order.common.entity.Cart;
import cn.bizspring.cloud.order.common.entity.CartItem;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.goods.common.feign.FeignSkuService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/**
 * Service - 购物车
 */
@Service
@AllArgsConstructor
public class CartServiceImpl extends BizServiceImpl<Cart> implements CartService {

	private CartMapper cartMapper;
	private CartItemMapper cartItemMapper;
	private CartItemService cartItemService;
	private FeignSkuService feignSkuService;
	private FeignGoodsService feignGoodsService;
	private FeignMemberService feignMemberService;
	private FeignStoreService feignStoreService;

	@Override
	public Cart create() {
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		if (currentUser != null && currentUser.getCartId() != null) {
			return cartMapper.find(currentUser.getCartId());
		}
		Cart cart = new Cart();
		if (currentUser != null) {
			cart.setMemberId(currentUser.getId());
			currentUser.setCartId(cart.getId());
		}
		cart.setKey(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
		if (cart.getMemberId() == null) {
			cart.setExpire(DateUtils.addSeconds(new Date(), Cart.TIMEOUT));
		} else {
			cart.setExpire(null);
		}
		super.save(cart);
		return cart;
	}

	@Override
	public void add(Cart cart, Sku sku, int quantity) {
		addInternal(cart, sku, quantity);

	}

	@Override
	public void modify(Cart cart, Sku sku, int quantity) {
		CartItem cartItem = cart.getCartItem(sku);
		cartItem.setQuantity(quantity);
		cartItem.setUpdatedTime(new Date());
		cartItemMapper.update(cartItem);

	}

	@Override
	public void select(Cart cart, Sku sku, Boolean checked) {
		if (checked == null) {
			return;
		}
		CartItem cartItem = cart.getCartItem(sku);
		cartItem.setChecked(checked);
		cartItem.setUpdatedTime(new Date());
		cartItemMapper.select(cartItem);
	}

	@Override
	public void remove(Cart cart, Sku sku) {
		CartItem cartItem = cart.getCartItem(sku);
		cartItemMapper.remove(cartItem);
		cart.remove(cartItem);
	}

	@Override
	public void clear(Cart cart) {
		for (CartItem cartItem : cart) {
			cartItemMapper.remove(cartItem);
		}
		cart.clear();

	}

	/**
	 * 添加购物车SKU
	 *
	 * @param cart     购物车
	 * @param sku      SKU
	 * @param quantity 数量
	 */
	private void addInternal(Cart cart, Sku sku, int quantity) {
		if (cart.contains(sku)) {
			CartItem cartItem = cart.getCartItem(sku);
			cartItem.add(quantity);
			cartItemMapper.update(cartItem);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setQuantity(quantity);
			cartItem.setChecked(true);
			cartItem.setSkuId(sku.getId());
			cartItem.setCartId(cart.getId());
			cartItem.setId(String.valueOf(IdWorker.getId()));
			cartItem.setCreatedTime(new Date());
			cartItem.setVersion(0L);
			cartItemMapper.save(cartItem);
		}
	}

	/**
	 * 获取购物车项
	 * <p>
	 * //	 * @param store
	 * 店铺
	 *
	 * @return 购物车项
	 */
	@SuppressWarnings("unchecked")
	public Set<CartItem> getCartItems(Cart cart, final Store store) {
		Set<CartItem> cartItems = new HashSet<>();
		if (CommonUtils.isNull(cart.getId())) {
			//在这里组装没有加入购物车的，的cartItem
			cartItems = cart.getCartItems();
		} else {
			cartItems = cartItemMapper.findSet("cart_id", cart.getId());
		}
		if (CollectionUtils.isNotEmpty(cartItems)) {
			return new HashSet<>(CollectionUtils.select(cartItems, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					CartItem cartItem = (CartItem) object;
					Store store1 = cartItemService.getStore(cartItem);
					return cartItem != null && store1 != null && store1.getId().equals(store.getId());
				}
			}));
		}
		return cartItems;
	}

	/**
	 * 获取购物车项
	 * <p>
	 * //	 * @param store
	 * 店铺
	 *
	 * @return 购物车项
	 */
	@SuppressWarnings("unchecked")
	public Set<CartItem> getCartItems(Cart cart) {
		return cartItemMapper.findSet("cart_id", cart.getId());
	}

	/**
	 * 获取购物车项组
	 *
	 * @return 购物车项组
	 */
	public Map<Store, Set<CartItem>> getCartItemGroup(Cart cart, Boolean isDirectBuy) {
		Map<Store, Set<CartItem>> cartItemGroup = new HashMap<>();
		if (isDirectBuy) {
			//在这里组装没有加入购物车的，的cartItem
			Set<CartItem> cartItems = cart.getCartItems();
			if (cartItems.size() > 0) {
				CartItem cartItem = cartItems.iterator().next();
				Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
				Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
				Store store = feignStoreService.getById(goods.getStoreId(), SecurityEnum.FROM_IN);
				cartItemGroup.put(store, cartItems);
			}
		} else {
			for (Store store : getStores(cart)) {
				cartItemGroup.put(store, getCartItems(cart, store));
			}
		}
		return cartItemGroup;
	}

	/**
	 * 获取店铺
	 *
	 * @return 店铺
	 */
	public Set<Store> getStores(Cart cart) {
		Set<Store> stores = new HashSet<>();
		Set<CartItem> cartItems = new HashSet<>();
		if (CommonUtils.isNull(cart.getId())) {
			//在这里组装没有加入购物车的，的cartItem
			cartItems = cart.getCartItems();
		} else {
			cartItems = cartItemMapper.findSet("cart_id", cart.getId());
		}
		if (cartItems != null) {
			for (CartItem cartItem : cartItems) {
				Store store = cartItemService.getStore(cartItem);
				if (cartItem != null && store != null) {
					stores.add(store);
				}
			}
		}
		return stores;
	}


	/**
	 * 判断是否包含店铺类型
	 *
	 * @return 是否包含店铺类型
	 */
	public boolean contains(Cart cart, final Store.Type type) {
		return CollectionUtils.exists(getStores(cart), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Store store = (Store) object;
				return store != null && store.getType() != null && store.getType().equals(type);
			}
		});
	}


	/**
	 * 获取重量
	 * @param store               店铺
	 * @return 重量
	 */
	public int getWeight(Cart cart, Store store) {
		int weight = 0;
		for (CartItem cartItem : getCartItems(cart, store)) {
			Integer weight1 = cartItemService.getWeight(cartItem);
			Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
			if (cartItem == null || weight1 == null) {
				continue;
			}
			weight += weight1;
		}
		return weight;
	}


	/**
	 * 获取数量
	 *
	 * @param store       店铺
	 * @return 数量
	 */
	public int getQuantity(Cart cart, Store store) {
		int quantity = 0;
		for (CartItem cartItem : getCartItems(cart, store)) {
			if (cartItem != null && cartItem.getQuantity() != null) {
				quantity += cartItem.getQuantity();
			}
		}
		return quantity;
	}


	/**
	 * 获取数量
	 *
	 * @return 数量
	 */
	public int getQuantity(Cart cart) {
		int quantity = 0;
		for (Store store : getStores(cart)) {
			quantity += getQuantity(cart, store);
		}
		return quantity;
	}

	/**
	 * 获取价格
	 *
	 * @param store 店铺
	 * @return 价格
	 */
	public BigDecimal getPrice(Cart cart, Store store) {
		BigDecimal price = BigDecimal.ZERO;
		for (CartItem cartItem : getCartItems(cart, store)) {
			price = price.add(cartItemService.getSubtotal(cartItem));
		}
		return price;
	}


	/**
	 * 获取价格
	 *
	 * @return 价格
	 */
	public BigDecimal getPrice(Cart cart) {
		BigDecimal price = BigDecimal.ZERO;
		for (Store store : getStores(cart)) {
			price = price.add(getPrice(cart, store));
		}
		return price;
	}

	/**
	 * 获取有效价格
	 *
	 * @param store 店铺
	 * @return 有效价格
	 */
	public BigDecimal getEffectivePrice(Cart cart, Store store, BigDecimal discount) {
		BigDecimal effectivePrice = getPrice(cart, store).subtract(discount);
		return effectivePrice.compareTo(BigDecimal.ZERO) >= 0 ? effectivePrice : BigDecimal.ZERO;
	}

	/**
	 * 获取有效价格
	 *
	 * @return 有效价格
	 */
	public BigDecimal getEffectivePrice(Cart cart, BigDecimal discount) {
		BigDecimal effectivePrice = BigDecimal.ZERO;
		for (Store store : getStores(cart)) {
			effectivePrice = effectivePrice.add(getEffectivePrice(cart, store, discount));
		}
		return effectivePrice;
	}

	/**
	 * 获取是否需要物流
	 *
	 * @param store 店铺
	 * @return 是否需要物流
	 */
	public boolean getIsDelivery(Cart cart, Store store) {
		return CollectionUtils.exists(getCartItems(cart, store), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				boolean isDelivery = cartItemService.getIsDelivery(cartItem);
				return cartItem != null && BooleanUtils.isTrue(isDelivery);
			}
		});
	}

	/**
	 * 获取是否需要物流
	 *
	 * @return 是否需要物流
	 */
	public boolean getIsDelivery(Cart cart) {
		return CollectionUtils.exists(getStores(cart), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Store store = (Store) object;
				return store != null && getIsDelivery(cart, store);
			}
		});
	}

	/**
	 * 获取标签
	 *
	 * @return 标签
	 */
	public String getTag(Cart cart) {
		Set<Map<String, Object>> items = new HashSet<>();
		for (CartItem cartItem : cart) {
			Map<String, Object> item = new HashMap<>();
			item.put("skuId", cartItem.getSkuId());
			item.put("quantity", cartItem.getQuantity());
			items.add(item);
		}
		return DigestUtils.md5Hex(JsonUtils.toJson(items));
	}

	/**
	 * 判断是否存在无效商品
	 *
	 * @return 是否存在无效商品
	 */
	public boolean hasNotActive(Cart cart) {
		return CollectionUtils.exists(cart.getCartItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				return cartItem != null && !cartItemService.getIsActive(cartItem);
			}
		});
	}

	/**
	 * 判断是否存在已下架商品
	 *
	 * @return 是否存在已下架商品
	 */
	public boolean hasNotMarketable(Cart cart) {
		return CollectionUtils.exists(cart.getCartItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				return cartItem != null && !cartItemService.getIsMarketable(cartItem);
			}
		});
	}

	/**
	 * 判断是否存在库存不足商品
	 *
	 * @return 是否存在库存不足商品
	 */
	public boolean hasLowStock(Cart cart) {
		return CollectionUtils.exists(cart.getCartItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				return cartItem != null && cartItemService.getIsLowStock(cartItem);
			}
		});
	}


	/**
	 * 判断是否存在过期店铺商品
	 *
	 * @return 是否存在过期店铺商品
	 */
	public boolean hasExpiredGoods(Cart cart) {
		return CollectionUtils.exists(cart.getCartItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				CartItem cartItem = (CartItem) object;
				return cartItem != null && cartItemService.hasExpiredGoods(cartItem);
			}
		});
	}

}