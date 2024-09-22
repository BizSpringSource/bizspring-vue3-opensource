package cn.bizspring.cloud.order.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.order.admin.service.*;
import cn.bizspring.cloud.order.common.entity.*;
import cn.bizspring.cloud.order.common.entity.Order;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.goods.common.feign.FeignSkuService;
import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignPaymentPatternService;
import cn.bizspring.cloud.store.common.feign.FeignShippingMethodService;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.order.common.modes.Payment;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import cn.bizspring.cloud.upms.common.feign.FeignAddressService;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 订单
 */
@Slf4j
@AllArgsConstructor
@Api(value = "order", tags = "商城 - 订单")
@RestController("memberOrderController")
@RequestMapping("/member/order")
public class OrderController extends BizController {

	private FeignSkuService feignSkuService;
	private FeignAddressService feignAddressService;
	private FeignPaymentPatternService feignPaymentPatternService;
	private FeignShippingMethodService feignShippingMethodService;
	private OrderService orderService;
	private PaymentBizService paymentBizService;
	private FeignMemberService feignMemberService;
	private FeignUserService feignUserService;
	private FeignStoreService feignStoreService;
	private OrderItemService orderItemService;
	private CartService cartService;
	private FeignGoodsService feignGoodsService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public ResponseEntity list(Order.Status status,  Boolean hasExpired, Integer pageNumber) {
		List<User> users = feignUserService.findAll(SecurityEnum.FROM_IN);
		List<Store> stores = feignStoreService.findAll(SecurityEnum.FROM_IN);
		Pageable pageable = new Pageable(pageNumber, Pageable.PAGE_SIZE);
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		data.put("hasExpired", hasExpired);
		Order entity = new Order();
		if (CommonUtils.isNotNull(status)) {
			entity.setStatus(status);
		}
		entity.setMemberId(SecurityUtils.getUserId());
		List<Order> content = new ArrayList<Order>();
		Page page = orderService.findPage(entity, users, stores, null, null, hasExpired, pageable);
		content = page.getContent();
		if (CommonUtils.isNotNull(content) && 0 < content.size()) {
			for (int i = 0; i < content.size(); i++) {
				Order order = content.get(i);
				//orderItems
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(order.getId());
				List<OrderItem> orderItems = orderItemService.findAllByEntity(orderItem);
				if (CollectionUtils.isNotEmpty(orderItems)) {
					Sku sku = null;
					for (OrderItem oi : orderItems) {
						sku = feignSkuService.find(oi.getSkuId(), SecurityEnum.FROM_IN);
						if (CommonUtils.isNotNull(sku)) {
							oi.setGoodsId(sku.getGoodsId());
						}
					}
					(content.get(i)).setOrderItems(orderItems);
				}
			}
		}

		page.setContent(content);
		data.put("data", page);
		return R.ok(data);
	}

	/**
	 * 取消
	 */
	@PostMapping("/cancel")
	public ResponseEntity<?> cancel(String orderId) {
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Order order = orderService.find(orderId);
		if (order == null) {
			return R.INVALID_ENTITY;
		}
		if (order.hasExpired() || (!Order.Status.PENDING_PAYMENT.equals(order.getStatus()) && !Order.Status.PENDING_REVIEW.equals(order.getStatus()))) {
			return R.INVALID_ENTITY;
		}
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.invalidEntity("该订单已被锁定，请稍后再试！");
//		}
		orderService.cancel(order);
		return R.OK;
	}

	/**
	 * 收货
	 */
	@PostMapping("/receive")
	public ResponseEntity<?> receive(String orderId) {
		Order order = orderService.find(orderId);
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		if (order == null) {
			return R.NOT_FOUND;
		}
		if (order.hasExpired() || !Order.Status.SHIPPED.equals(order.getStatus())) {
			return R.NOT_FOUND;
		}
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.invalidEntity("该订单已被锁定，请稍后再试！");
//		}
		orderService.receive(order);
		return R.OK;
	}

	/**
	 * 结算
	 */
	@ApiOperation(value = "结算", notes = "结算")
	@ApiOperationSupport(order = 2)
	@GetMapping("/checkout")
	public ResponseEntity checkout(String skuId, Boolean notJoinGroup, Integer quantity, String currentCartId) {
		Cart currentCart = cartService.find(currentCartId);
		String currentUserId = SecurityUtils.getUserId();
		Member currentUser = feignMemberService.find(currentUserId, SecurityEnum.FROM_IN);
		if(CommonUtils.isNull(currentUser)){
			return R.unauthorized();
		}
		Map<String, Object> data = new HashMap<>(2);
		Cart cart;
		Boolean isDirectBuy = false;
		//直接购买
		if (skuId != null) {
			Sku sku = feignSkuService.find(skuId, SecurityEnum.FROM_IN);
			if (sku == null) {
				return R.failed();
			}
			isDirectBuy = true;
			Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
			if (quantity == null || quantity < 1) {
				return R.failed();
			}
			cart = generateCart(currentUser, sku, quantity);
			//购物车结算
		} else {
			cart = currentCart;
		}
		if (cart == null || cart.isEmpty()) {
			return R.failed("购物车为空");//购物车项是否为空
		}
		if (cartService.hasNotActive(cart)) {
			return R.failed("购物车存在无效商品");//无效商品
		}
		if (cartService.hasNotMarketable(cart)) {
			return R.failed("购物车存在已下架商品");//商品已下架
		}
		if (cartService.hasLowStock(cart)) {
			return R.failed("购物车存在库存不足商品");//商品库存不足
		}
		if (cartService.hasExpiredGoods(cart)) {
			return R.failed("购物车中存在过期店铺商品");//店铺商品已过期
		}
		ShippingMethod shippingMethod=null;
		List<ShippingMethod> shippingMethods=feignShippingMethodService.findAll(SecurityEnum.FROM_IN);
		if(CollectionUtils.isNotEmpty(shippingMethods)){
			shippingMethod=shippingMethods.get(0);
			data.put("shippingMethods",shippingMethods);
		}
		Address defaultAddress = feignAddressService.findDefault(currentUser, SecurityEnum.FROM_IN);
		List<Order> orders = orderService.generate(isDirectBuy,notJoinGroup,  cart, defaultAddress, null, shippingMethod, null);


		BigDecimal price = BigDecimal.ZERO;
		BigDecimal freight = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountPayable = BigDecimal.ZERO;
		Long rewardPoint = 0L;
		Long exchangePoint = 0L;
		boolean isDelivery = false;
		for (Order order : orders) {
			price = price.add(order.getPrice());
			freight = freight.add(order.getFreight());
			amount = amount.add(order.getAmount());
			amountPayable = amountPayable.add(order.getAmountPayable());
			if (order.getIsDelivery()) {
				isDelivery = true;
			}
		}
		data.put("skuId", skuId);
		data.put("quantity", quantity);
		data.put("cart", cart);
		data.put("defaultAddress", defaultAddress);
		data.put("orders", orders);
		data.put("price", price);
		data.put("freight", freight);
		data.put("amount", amount);
		data.put("amountPayable", amountPayable);
		data.put("rewardPoint", rewardPoint);
		data.put("exchangePoint", exchangePoint);
		data.put("isDelivery", isDelivery);

		List<PaymentPattern> paymentPatterns = new ArrayList<>();
		if (cartService.contains(cart, Store.Type.GENERAL)) {
			CollectionUtils.select(feignPaymentPatternService.findAll(SecurityEnum.FROM_IN), new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					PaymentPattern paymentPattern = (PaymentPattern) object;
					return paymentPattern != null && PaymentPattern.Pattern.ONLINE.equals(paymentPattern.getPattern());
				}
			}, paymentPatterns);
		} else {
			paymentPatterns = feignPaymentPatternService.findAll(SecurityEnum.FROM_IN);
		}
		data.put("paymentPatterns", paymentPatterns);
		return R.ok(data);
	}

	/**
	 * 计算
	 */
	@ApiOperation(value = "计算", notes = "计算")
	@ApiOperationSupport(order = 2)
	@GetMapping("/calculate")
	public ResponseEntity calculate(String skuId, Boolean notJoinGroup, Integer quantity, String addressId, String paymentPatternId, String shippingMethodId,  String[] remark, String currentCartId) {
		Cart currentCart = cartService.find(currentCartId);
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>();
		Cart cart;
		Boolean isDirectBuy = false;
		if (CommonUtils.isNotNull(skuId)) {
			Sku sku = feignSkuService.find(skuId, SecurityEnum.FROM_IN);
			if (sku == null) {
				return R.INVALID_ENTITY;
			}
			isDirectBuy = true;
			Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
			if (quantity == null || quantity < 1) {
				return R.INVALID_ENTITY;
			}
			cart = generateCart(currentUser, sku, quantity);
		} else {
			cart = currentCart;
		}
		if (cart == null || cart.isEmpty()) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasNotActive(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasNotMarketable(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasLowStock(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasExpiredGoods(cart)) {
			return R.INVALID_ENTITY;
		}
		Address address = null;
		if (CommonUtils.isNotNull(addressId)) {
			address = feignAddressService.find(addressId, SecurityEnum.FROM_IN);
			if (address != null && !currentUser.getId().equals(address.getMemberId())) {
				return R.INVALID_ENTITY;
			}
		}
		PaymentPattern paymentPattern = null;
		if (CommonUtils.isNotNull(paymentPatternId)) {
			paymentPattern = feignPaymentPatternService.find(paymentPatternId, SecurityEnum.FROM_IN);
		}
		if (cartService.contains(cart, Store.Type.GENERAL) && paymentPattern != null && PaymentPattern.Pattern.OFFLINE.equals(paymentPattern.getPattern())) {
			return R.INVALID_ENTITY;
		}
		ShippingMethod shippingMethod = null;
		if (CommonUtils.isNotNull(shippingMethodId)) {
			shippingMethod = feignShippingMethodService.find(shippingMethodId, SecurityEnum.FROM_IN);
		}
		List<Order> orders = orderService.generate(isDirectBuy,notJoinGroup,cart, address, paymentPattern, shippingMethod, remark);
		BigDecimal price = BigDecimal.ZERO;
		BigDecimal freight = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal amountPayable = BigDecimal.ZERO;
		Long rewardPoint = 0L;
		Long exchangePoint = 0L;
		boolean isDelivery = false;

		for (Order order : orders) {
			price = price.add(order.getPrice());
			if (CommonUtils.isNotNull(order.getFreight())) {
				freight = freight.add(order.getFreight());
			}
			amount = amount.add(order.getAmount());
			amountPayable = amountPayable.add(order.getAmountPayable());
			if (order.getIsDelivery()) {
				isDelivery = true;
			}
		}

		data.put("price", price);
		data.put("freight", freight);
		data.put("tax", tax);
		data.put("amount", amount);
		data.put("amountPayable", amountPayable);
		data.put("rewardPoint", rewardPoint);
		data.put("exchangePoint", exchangePoint);
		data.put("isDelivery", isDelivery);
		return R.ok(data);
	}

	/**
	 * 创建
	 */
	@ApiOperation(value = "创建", notes = "创建")
	@ApiOperationSupport(order = 2)
	@PostMapping("/create")
	public ResponseEntity create(String skuId,Boolean notJoinGroup, Integer quantity, String cartTag, String addressId, String paymentPatternId, String shippingMethodId, String[] remark,
								 String currentCartId) {
//		对已勾选的cartitem 生成 新的购物车（cart），将老cart中 的cartItem 修改到新的cart中
		Cart currentCart = cartService.find(currentCartId);
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>();
		Cart cart;
		Boolean isDirectBuy = false;
		//直接购买
		if (skuId != null) {
			Sku sku = feignSkuService.find(skuId, SecurityEnum.FROM_IN);
			if (sku == null) {
				return R.INVALID_ENTITY;
			}
			Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
			isDirectBuy = true;
			if (quantity == null || quantity < 1) {
				return R.INVALID_ENTITY;
			}
			cart = generateCart(currentUser, sku, quantity);
			//购物车提交订单
		} else {
			cart = currentCart;
		}
		if (cart == null || cart.isEmpty()) {
			return R.INVALID_ENTITY;
		}
		if (cartTag != null && !StringUtils.equals(cartService.getTag(cart), cartTag)) {
			return R.invalidEntity("购物车商品已修改，请检查购物车");
		}
		if (cartService.hasNotActive(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasNotMarketable(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasLowStock(cart)) {
			return R.INVALID_ENTITY;
		}
		if (cartService.hasExpiredGoods(cart)) {
			return R.INVALID_ENTITY;
		}
		Address address = cartService.getIsDelivery(cart) ? feignAddressService.find(addressId, SecurityEnum.FROM_IN) : null;
		if (cartService.getIsDelivery(cart) && (address == null || !currentUser.getId().equals(address.getMemberId()))) {
			return R.INVALID_ENTITY;
		}
		PaymentPattern paymentPattern = feignPaymentPatternService.find(paymentPatternId, SecurityEnum.FROM_IN);
		if (cartService.contains(cart, Store.Type.GENERAL) && paymentPattern != null && PaymentPattern.Pattern.OFFLINE.equals(paymentPattern.getPattern())) {
			return R.INVALID_ENTITY;
		}
		ShippingMethod shippingMethod = feignShippingMethodService.find(shippingMethodId, SecurityEnum.FROM_IN);
		if (cartService.getIsDelivery(cart) && shippingMethod == null) {
			return R.INVALID_ENTITY;
		}
		List<Order> orders = orderService.create(notJoinGroup,isDirectBuy, cart, address, paymentPattern, shippingMethod, remark);
		List<String> orderSns = new ArrayList<>();
		for (Order order : orders) {
			orderSns.add(order.getSn());
		}
		data.put("orders", orders);
		data.put("orderSns", orderSns);
		return R.ok(data);
	}

	/**
	 * 支付计算页
	 */
	@ApiOperation(value = "支付", notes = "支付")
	@ApiOperationSupport(order = 2)
	@GetMapping("/payment")
	public ResponseEntity payment(String[] orderSns) {
		Member currentUser = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		if (ArrayUtils.isEmpty(orderSns)) {
			return R.failed();
		}
		List<Payment> payments = paymentBizService.getActivePayments(WebUtils.getRequest());
		Payment defaultPayment = null;
		PaymentPattern orderPaymentPattern = null;
		BigDecimal fee = BigDecimal.ZERO;
		BigDecimal amount = BigDecimal.ZERO;
		boolean online = false;
		List<Order> orders = new ArrayList<>();
		for (String orderSn : orderSns) {
			Order order = orderService.findBySn(orderSn);
			if (order == null) {
				return R.failed();
			}
			BigDecimal amountPayable = order.getAmountPayable();
			if (order.getAmount().compareTo(order.getAmountPaid()) <= 0 || amountPayable.compareTo(BigDecimal.ZERO) <= 0) {
				//订单这里不用支付，可以去我的订单中心
			}
			orderPaymentPattern = feignPaymentPatternService.find(order.getPaymentPatternId(), SecurityEnum.FROM_IN);
			if (!currentUser.getId().equals(order.getMemberId()) || orderPaymentPattern == null) {
				return R.invalidEntity();
			}
			if (PaymentPattern.Pattern.ONLINE.equals(orderPaymentPattern.getPattern())) {
//				if (!orderService.acquireLock(order, currentUser)) {
//					//订单已锁定，这里无需支付，可以去我的订单中心
//				}
				if (CollectionUtils.isNotEmpty(payments)) {
					defaultPayment = payments.get(0);
				}
				online = true;
			} else {
				online = false;
			}
			amount = amount.add(amountPayable);
			orders.add(order);
		}
		if (online && defaultPayment != null) {
			data.put("defaultPayment", defaultPayment);
			data.put("payments", payments);
		}
		if (CollectionUtils.isNotEmpty(orders)) {
			Order order = orders.get(0);
			data.put("shippingMethodName", order.getShippingMethodName());
			data.put("paymentPatternName", order.getPaymentPatternName());
			data.put("paymentPattern", orderPaymentPattern);
			data.put("expireDate", order.getExpire());
		}
		data.put("fee", fee);
		data.put("amount", amount);
		data.put("orders", orders);
		data.put("orderSns", Arrays.asList(orderSns));
		return R.ok(data);
	}

	/**
	 * 生成购物车
	 *
	 * @param member   会员
	 * @param sku      SKU
	 * @param quantity 数量
	 * @return 购物车
	 */
	public Cart generateCart(Member member, Sku sku, Integer quantity) {
		Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
		Cart cart = new Cart();
		Set<CartItem> cartItems = new HashSet<>();
		CartItem cartItem = new CartItem();
		cartItem.setSkuId(sku.getId());
		cartItem.setQuantity(quantity);
		cartItems.add(cartItem);
		cartItem.setCartId(cart.getId());
		cart.setMemberId(member.getId());
		cart.setCartItems(cartItems);
		return cart;
	}

}