package cn.bizspring.cloud.order.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.R;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 购物车
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "cart", tags = "商城 - 购物车")
@RestController("memberCartController")
@RequestMapping("/member/cart")
public class CartController extends BizController {

	private FeignSkuService feignSkuService;
	private CartService cartService;
	private FeignGoodsService feignGoodsService;
	private FeignStoreService feignStoreService;
	private CartItemService cartItemService;

	/**
	 * 获取当前购物车
	 */
	@ApiOperation(value = "获取当前购物车", notes = "获取当前购物车")
	@ApiOperationSupport(order = 2)
	@GetMapping("/get_current")
	public ResponseEntity getCurrent(String currentCartId) {
		Cart currentCart = cartService.find(currentCartId);
		if(CommonUtils.isNotNull(currentCart)){
			currentCart.setCartItems(cartService.getCartItems(currentCart));
		}
		Map<String, Object> data = new HashMap<>();
		if (currentCart != null && !currentCart.isEmpty()) {
			data.put("quantity", cartService.getQuantity(currentCart));
			data.put("effectivePrice", cartService.getEffectivePrice(currentCart,new BigDecimal(0)));
			List<Map<String, Object>> cartItems = new ArrayList<>();
			for (CartItem cartItem : currentCart) {
				Map<String, Object> item = new HashMap<>();
				Sku sku = feignSkuService.find(cartItem.getSkuId(),SecurityEnum.FROM_IN);
				if(CommonUtils.isNotNull(sku)){
					Goods goods=feignGoodsService.find(sku.getGoodsId(),SecurityEnum.FROM_IN);
					if(CommonUtils.isNotNull(goods)) {
						item.put("skuId", sku.getId());
						item.put("skuName", goods.getName());
						item.put("goodsId", goods.getId());
						item.put("specifications", sku.getSpecifications());
						item.put("skuThumbnail", goods.getThumbnail());
						item.put("price", sku.getPrice());
						item.put("quantity", cartItem.getQuantity());
						item.put("checked", cartItem.isChecked());
						item.put("subtotal", cartItemService.getSubtotal(cartItem));
						cartItems.add(item);
					}
				}
			}
			data.put("cartItems", cartItems);
		}
		return R.ok(data);
	}

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@PostMapping("/add")
	public ResponseEntity add(String skuId, Integer quantity, String currentCartId) {
		Cart currentCart = cartService.find(currentCartId);
		Map<String, Object> data = new HashMap<>();
		if (quantity == null || quantity < 1) {
			return R.INVALID_ENTITY;
		}
		Sku sku = feignSkuService.find(skuId, SecurityEnum.FROM_IN);
		if (sku == null) {
			return R.invalidEntity("该商品不存在");
		}
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(goods)) {
			if (!goods.getIsActive()) {
				return R.invalidEntity("该商品已失效");
			}
			if (!goods.getIsMarketable()) {
				return R.invalidEntity("该商品已下架");
			}
			Store store = feignStoreService.find(goods.getStoreId(), SecurityEnum.FROM_IN);
			if (store.hasExpired()) {
				return R.invalidEntity("过期店铺商品，无法购买");
			}
		}
		int skuQuantity = quantity;
		if (currentCart != null) {
			if (currentCart.contains(sku)) {
				CartItem cartItem = currentCart.getCartItem(sku);
				skuQuantity = cartItem.getQuantity() + quantity;
			} else {
				skuQuantity = quantity;
			}
		}
		if (skuQuantity > sku.getAvailableStock()) {
			return R.invalidEntity("该商品库存不足");
		}
		if (currentCart == null) {
			currentCart = cartService.create();
		}
		cartService.add(currentCart, sku, quantity);
		data.put("quantity", cartService.getQuantity(currentCart));
		data.put("effectivePrice", cartService.getEffectivePrice(currentCart,new BigDecimal(0)));
		data.put("currentCart", currentCart);
		return R.ok(data);
	}

	/**
	 * 选择
	 */
	@ApiOperation(value = "选择", notes = "选择")
	@ApiOperationSupport(order = 2)
	@PostMapping("/select")
	public ResponseEntity select(String skuIds, Boolean checked, String currentCartId) {
		Cart currentCart = cartService.find(currentCartId);
		String[] skuArr = CommonUtils.isNotEmpty(skuIds) ? skuIds.split(",") : null;
		Map<String, Object> data = new HashMap<>();
		if (checked == null) {
			return R.INVALID_ENTITY;
		}
		for(int i = 0; CommonUtils.isNotNull(skuArr)&&i<skuArr.length;i++) {
			Sku sku = feignSkuService.find(skuArr[i], SecurityEnum.FROM_IN);
			if (sku == null) {
				return R.invalidEntity("该商品不存在");
			}
			if (currentCart == null || currentCart.isEmpty()) {
				return R.invalidEntity("购物车不能为空");
			}
			if (!currentCart.contains(sku)) {
				return R.invalidEntity("购物车项不存在");
			}
			Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
			if (CommonUtils.isNotNull(goods)) {
				if (!goods.getIsActive()) {
					return R.invalidEntity("该商品已失效");
				}
				if (!goods.getIsMarketable()) {
					return R.invalidEntity("该商品已下架");
				}
			}
			cartService.select(currentCart, sku,checked);
		}
		return R.ok(data);
	}


	/**
	 * 修改
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@ApiOperationSupport(order = 2)
	@PostMapping("/modify")
	public ResponseEntity modify(String skuId, Integer quantity, String currentCartId) {
		Cart currentCart=cartService.find(currentCartId);
		Map<String, Object> data = new HashMap<>();
		if (quantity == null || quantity < 1) {
			return R.INVALID_ENTITY;
		}
		Sku sku = feignSkuService.find(skuId , SecurityEnum.FROM_IN);
		if (sku == null) {
			return R.invalidEntity("该商品不存在");
		}
		if (currentCart == null || currentCart.isEmpty()) {
			return R.invalidEntity("购物车不能为空");
		}
		if (!currentCart.contains(sku)) {
			return R.invalidEntity("购物车项不存在");
		}
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(goods)) {
			if (!goods.getIsActive()) {
				return R.invalidEntity("该商品已失效");
			}
			if (!goods.getIsMarketable()) {
				return R.invalidEntity("该商品已下架");
			}
		}
		if (quantity > sku.getAvailableStock()) {
			return R.invalidEntity("该商品库存不足");
		}
		cartService.modify(currentCart, sku, quantity);
		data.put("quantity", cartService.getQuantity(currentCart));
		data.put("effectivePrice", cartService.getEffectivePrice(currentCart,new BigDecimal(0)));

		Map<String, Object> cartItemMap = new HashMap<>();
		CartItem cartItem = currentCart.getCartItem(sku);
		data.put("cartItem", cartItemMap);

		Map<String, Object> storeMap = new HashMap<>();
		data.put("store", storeMap);
		return R.ok(data);
	}

	/**
	 * 移除
	 */
	@ApiOperation(value = "移除", notes = "移除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/remove")
	public ResponseEntity remove(String skuId, String currentCartId) {
		Cart currentCart=cartService.find(currentCartId);
		Map<String, Object> data = new HashMap<>();
		Sku sku = feignSkuService.find(skuId , SecurityEnum.FROM_IN);
		if (sku == null) {
			return R.invalidEntity("该商品不存在");
		}
		if (currentCart == null) {
			return R.invalidEntity("购物车不能为空");
		}
		if (!currentCart.contains(sku)) {
			return R.invalidEntity("购物车项不存在");
		}
		cartService.remove(currentCart, sku);

		data.put("quantity", cartService.getQuantity(currentCart));
		data.put("effectivePrice", cartService.getEffectivePrice(currentCart,new BigDecimal(0)));

		Map<String, Object> storeMap = new HashMap<>();
		data.put("store", storeMap);
		return R.ok(data);
	}

	/**
	 * 清空
	 */
	@ApiOperation(value = "清空", notes = "清空")
	@ApiOperationSupport(order = 2)
	@PostMapping("/clear")
	public ResponseEntity clear(String currentCartId) {
		Cart currentCart=cartService.find(currentCartId);
		if (currentCart != null) {
			cartService.clear(currentCart);
		}
		return R.OK;
	}

}