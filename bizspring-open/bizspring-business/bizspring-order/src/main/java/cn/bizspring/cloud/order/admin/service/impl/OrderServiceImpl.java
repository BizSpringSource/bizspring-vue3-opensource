package cn.bizspring.cloud.order.admin.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.goods.common.entity.Stock;
import cn.bizspring.cloud.order.common.entity.*;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.order.admin.mapper.*;
import cn.bizspring.cloud.order.admin.service.*;
import cn.bizspring.cloud.order.common.entity.Order;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.goods.common.feign.FeignSkuService;
import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignPaymentPatternService;
import cn.bizspring.cloud.upms.common.feign.FeignRegionService;
import cn.bizspring.cloud.upms.common.feign.FeignMerchantService;
import cn.bizspring.cloud.store.common.feign.FeignShippingMethodService;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.common.entity.*;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Service - 订单
 */
@Service
@AllArgsConstructor
public class OrderServiceImpl extends BizServiceImpl<Order> implements OrderService {

//	private RedissonClient redissonClient;
	private RedisTemplate redisTemplate;
	private OrderMapper orderMapper;
	private OrderItemMapper orderItemMapper;
	private OrderLogService orderLogService;
	private CartMapper cartMapper;
	private SnService snService;
	private OrderPaymentService orderPaymentService;
	private OrderShippingItemMapper orderShippingItemMapper;
	private OrderShippingService orderShippingService;
	private FeignMemberService feignMemberService;
	private FeignGoodsService feignGoodsService;
	private FeignSkuService feignSkuService;
	private FeignShippingMethodService feignShippingMethodService;
	private FeignPaymentPatternService feignPaymentPatternService;
	private FeignRegionService feignRegionService;
	private CartService cartService;

	@Override
	@Transactional(readOnly = true)
	public Order findBySn(String sn) {
		return orderMapper.findByProperty("sn", StringUtils.lowerCase(sn));
	}

	/**
	 * 查找支付订单
	 * @param currentStore    店铺
	 * @param isSuccess       是否支付成功
	 * @param hasExpired      是否过期
	 * @param paymentId 支付方式
	 * @return
	 */
	@Override
	public Page<Order> findOrderPaymentedPage(Store currentStore, Boolean isSuccess, Boolean hasExpired, String paymentId, Date beginDate, Date endDate, Pageable pageable) {
		IPage<Order> iPage = getPluginsPage(pageable);
		if (pageable.getSortDirection() == null && pageable.getSortProperty() == null) {
			pageable.setSortDirection(Sort.Direction.DESC);
			pageable.setSortProperty("createdTime");
		}
		iPage.setRecords(orderMapper.findOrderPaymentedPage(currentStore, isSuccess, hasExpired, paymentId, beginDate, endDate));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Order> findPage(Order entity, List<User> users, List<Store> stores, Boolean isPendingReceive, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		IPage<Order> iPage = getPluginsPage(pageable);
		if (pageable.getSortDirection() == null && pageable.getSortProperty() == null) {
			pageable.setSortDirection(Sort.Direction.DESC);
			pageable.setSortProperty("createdTime");
		}
		iPage.setRecords(orderMapper.findPage(iPage, getPageable(pageable), entity, stores, users, isPendingReceive, isAllocatedStock, hasExpired));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Order.Status status, Store store, Member member, Goods goods, Boolean isPendingReceive, Boolean isAllocatedStock, Boolean hasExpired) {
		return orderMapper.count(status, store, member, goods, isPendingReceive, isAllocatedStock, hasExpired);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(BigDecimal price, BigDecimal freight, BigDecimal offsetAmount) {
		BigDecimal amount = price;
		if (freight != null) {
			amount = amount.add(freight);
		}
		if (offsetAmount != null) {
			amount = amount.add(offsetAmount);
		}
		return amount.compareTo(BigDecimal.ZERO) >= 0 ? amount : BigDecimal.ZERO;
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateAmount(Order order) {
		return calculateAmount(order.getPrice(),  order.getFreight(), order.getOffsetAmount());
	}

//	@Override
//	@Transactional(readOnly = true)
//	public boolean acquireLock(Order order, User user) {
//		String orderId = order.getId();
//		RLock lock = redissonClient.getLock(orderId);
//		try {
//			lock.lock();
//			Object userId = redisTemplate.opsForValue().get(Order.CACHE_CLASS_NAME + Order.ORDER_LOCK_CACHE_NAME + orderId);
//			if (userId != null && !user.getId().equals(String.valueOf(userId))) {
//				return false;
//			}
//			redisTemplate.opsForValue().set(Order.CACHE_CLASS_NAME + Order.ORDER_LOCK_CACHE_NAME + orderId, user.getId(), 10000, TimeUnit.MILLISECONDS);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			lock.unlock();
//		}
//		return true;
//	}

//	@Override
//	@Transactional(readOnly = true)
//	public boolean acquireLock(Order order) {
//		User currentUser = feignUserService.findByUserId(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
//		return currentUser != null && acquireLock(order, currentUser);
//	}

	@Override
	@Transactional(readOnly = true)
	public List<Order> generate(Boolean isDirectBuy, Boolean notJoinGroup, Cart cart, Address address, PaymentPattern paymentPattern, ShippingMethod shippingMethod, String[] remark) {
		BigDecimal price = BigDecimal.ZERO;
		List<Order> orders = new ArrayList<>();
		int remarkIndex=0;
		//通过店铺，给商品分订单
		for (Map.Entry<Store, Set<CartItem>> entry : cartService.getCartItemGroup(cart, isDirectBuy).entrySet()) {
			Store store = entry.getKey();
			Set<CartItem> cartItems = entry.getValue();
			price = cartService.getPrice(cart, store);
			Order order = new Order();
			order.setPrice(price);
			order.setOffsetAmount(BigDecimal.ZERO);
			order.setWeight(cartService.getWeight(cart,store));

			order.setQuantity(cartService.getQuantity(cart, store));
			order.setShippedQuantity(0);
			order.setRemark(CommonUtils.isNotNull(remark)&&CommonUtils.isNotEmpty(remark[remarkIndex])?remark[remarkIndex]:null);
			remarkIndex++;
			order.setIsAllocatedStock(false);
			if (CommonUtils.isNotNull(paymentPattern)) {
				order.setPaymentPatternId(paymentPattern.getId());
			}
			order.setMemberId(cart.getMemberId());
			order.setStoreId(store.getId());
			order.setStoreName(store.getName());
			if (shippingMethod != null && cartService.getIsDelivery(cart, store)) {
				int weight = cartService.getWeight(cart, store);
				Map<String, Object> params = new HashMap();
				params.put("shippingMethod", shippingMethod);
				params.put("store", store);
				params.put("address", address);
				params.put("weight", weight);
				order.setFreight(feignShippingMethodService.calculateFreight(params,SecurityEnum.FROM_IN));
				order.setShippingMethodId(shippingMethod.getId());
			} else {
				order.setFreight(BigDecimal.ZERO);
				order.setShippingMethodId(null);
			}
			order.setAmount(calculateAmount(order));
			order.setAmountPaid(BigDecimal.ZERO);
			if (cartService.getIsDelivery(cart, store) && address != null) {
				order.setConsignee(address.getConsignee());
				order.setRegionName(address.getRegionName());
				order.setAddress(address.getAddress());
				order.setZipCode(address.getZipCode());
				order.setPhone(address.getPhone());
				order.setRegionId(address.getRegionId());
			}
			List<OrderItem> orderItems = order.getOrderItems();
			for (CartItem cartItem : cartItems) {
				Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
				if (null != sku) {
					OrderItem orderItem = new OrderItem();
					orderItem.setSn(sku.getSn());
					Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
					if (CommonUtils.isNotNull(goods)) {
						orderItem.setName(goods.getName());
						orderItem.setGoodsId(goods.getId());
						orderItem.setPrice(sku.getPrice());
						orderItem.setWeight(goods.getWeight());
						orderItem.setIsDelivery(goods.getIsDelivery());
						orderItem.setThumbnail(goods.getThumbnail());
					}
					orderItem.setQuantity(cartItem.getQuantity());
					orderItem.setShippedQuantity(0);
					orderItem.setSkuId(cartItem.getSkuId());
					orderItem.setOrderId(order.getId());
					orderItem.setSpecifications(sku.getSpecifications());
					orderItems.add(orderItem);
				}
			}
			order.setOrderItems(orderItems);
			orders.add(order);
		}
		return orders;
	}

	@Override
	public List<Order> create(Boolean notJoinGroup, Boolean isDirectBuy,final Cart cart, Address address, PaymentPattern paymentPattern, ShippingMethod shippingMethod, String[] remark) {
		for (CartItem cartItem : cart.getCartItems()) {
			Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
			if (CommonUtils.isNotNull(sku)) {
				Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
				if (CommonUtils.isNull(goods) || !goods.getIsMarketable() || cartItem.getQuantity() > sku.getAvailableStock()) {
					throw new IllegalArgumentException();
				}
			}
		}
		List<Order> orders = new ArrayList<>();
		int remarkIndex=0;
		for (Map.Entry<Store, Set<CartItem>> entry : cartService.getCartItemGroup(cart, isDirectBuy).entrySet()) {
			Store store = entry.getKey();
			Set<CartItem> cartItems = entry.getValue();
			Member member = feignMemberService.find(cart.getMemberId(), SecurityEnum.FROM_IN);
			Order order = new Order();
			order.setSn(snService.generate(Sn.Type.ORDER));
			order.setPrice(cartService.getPrice(cart, store));
			int weight = cartService.getWeight(cart, store);
			Map<String, Object> params = new HashMap();
			params.put("shippingMethod", shippingMethod);
			params.put("store", store);
			params.put("address", address);
			params.put("weight", weight);
			order.setFreight(cartService.getIsDelivery(cart, store) ? feignShippingMethodService.calculateFreight(params, SecurityEnum.FROM_IN) : BigDecimal.ZERO);

			order.setOffsetAmount(BigDecimal.ZERO);
			order.setAmountPaid(BigDecimal.ZERO);
			order.setWeight(cartService.getWeight(cart, store));

			order.setQuantity(cartService.getQuantity(cart, store));
			order.setShippedQuantity(0);
			if (cartService.getIsDelivery(cart, store)) {
				order.setConsignee(address.getConsignee());
				order.setRegionName(address.getRegionName());
				order.setAddress(address.getAddress());
				order.setZipCode(address.getZipCode());
				order.setPhone(address.getPhone());
				order.setRegionId(address.getRegionId());
				order.setShippingMethodId(shippingMethod.getId());
			}
			order.setRemark(null!=remark&&CommonUtils.isNotEmpty(remark[remarkIndex])?remark[remarkIndex]:null);
			remarkIndex++;

			order.setIsAllocatedStock(false);

			order.setMemberId(member.getId());
			order.setStoreId(store.getId());
			order.setAmount(calculateAmount(order));
			BigDecimal amountPayable = order.getAmount();
			if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {
				if (paymentPattern == null) {
					throw new IllegalArgumentException();
				}
				order.setStatus(PaymentPattern.Type.DELIVERY_AGAINST_PAYMENT.equals(paymentPattern.getType()) ? Order.Status.PENDING_PAYMENT : Order.Status.PENDING_REVIEW);
				order.setPaymentPatternId(paymentPattern.getId());
				if (paymentPattern.getTimeout() != null && Order.Status.PENDING_PAYMENT.equals(order.getStatus())) {
					order.setExpire(DateUtils.addMinutes(new Date(), paymentPattern.getTimeout()));
				}
			} else {
				order.setStatus(Order.Status.PENDING_REVIEW);
				order.setPaymentPatternId(null);
			}

			order.setSn(StringUtils.lowerCase(order.getSn()));
			if (CommonUtils.isNotNull(order.getRegionId())) {
				Region region = feignRegionService.find(order.getRegionId(), SecurityEnum.FROM_IN);
				if (CommonUtils.isNotNull(region)) {
					order.setRegionName(region.getFullName());
				}
			}
			if (CommonUtils.isNotNull(paymentPattern)) {
				order.setPaymentPatternName(paymentPattern.getName());
				order.setPaymentPatternType(paymentPattern.getType());
			}
			if (CommonUtils.isNotNull(shippingMethod)) {
				order.setShippingMethodName(shippingMethod.getName());
			}

			List<OrderItem> orderItems = order.getOrderItems();
			for (CartItem cartItem : cartItems) {
				Sku sku = feignSkuService.find(cartItem.getSkuId(), SecurityEnum.FROM_IN);
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(sku.getSn());

				Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
				if (CommonUtils.isNotNull(goods)) {
					orderItem.setName(goods.getName());
					orderItem.setPrice(sku.getPrice());
					orderItem.setWeight(goods.getWeight());
					orderItem.setIsDelivery(goods.getIsDelivery());
					orderItem.setThumbnail(goods.getThumbnail());
				}
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setSkuId(cartItem.getSkuId());
				orderItem.setOrderId(order.getId());
				orderItem.setId(String.valueOf(IdWorker.getId()));
				orderItem.setCreatedTime(new Date());
				orderItem.setVersion(0L);
				orderItem.setSpecifications(sku.getSpecifications());
				orderItems.add(orderItem);
			}
			super.save(order);
			for (OrderItem orderItem : orderItems) {
				orderItem.setOrderId(order.getId());
			}
			orderItemMapper.saveBatch(orderItems);

			OrderLog orderLog = new OrderLog();
			orderLog.setType(OrderLog.Type.CREATE);
			orderLog.setOrderId(order.getId());
			orderLogService.save(orderLog);
			if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
				allocateStock(order);
			}

			orders.add(order);
		}
		//在cartItem加入orderItem ，后将购物车项中加入订单的，购物车项删除
		//这里做个判断，如果购物车中购物车项全部加入订单了，就直接删除，否则不删除。
		if (!cart.isNew()) {
			cartMapper.remove(cart);
		}
		return orders;
	}

	@Override
	public void modify(Order order) {
		order.setAmount(calculateAmount(order));
		PaymentPattern paymentPattern = feignPaymentPatternService.find(order.getPaymentPatternId(), SecurityEnum.FROM_IN);
		if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
			order.setStatus(Order.Status.PENDING_REVIEW);
			order.setExpire(null);
		} else {
			if (CommonUtils.isNotNull(paymentPattern) && PaymentPattern.Type.DELIVERY_AGAINST_PAYMENT.equals(paymentPattern.getType())) {
				order.setStatus(Order.Status.PENDING_PAYMENT);
			} else {
				order.setStatus(Order.Status.PENDING_REVIEW);
				order.setExpire(null);
			}
		}
		Region region = feignRegionService.find(order.getRegionId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(region)) {
			order.setRegionName(region.getFullName());
		}
		if (CommonUtils.isNotNull(paymentPattern)) {
			order.setPaymentPatternName(paymentPattern.getName());
			order.setPaymentPatternType(paymentPattern.getType());
		}
		ShippingMethod shippingMethod = feignShippingMethodService.find(order.getShippingMethodId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(shippingMethod)) {
			order.setShippingMethodName(shippingMethod.getName());
		}
		super.update(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.MODIFY);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	public void cancel(Order order) {
		order.setStatus(Order.Status.CANCELED);
		order.setExpire(null);
		super.update(order);
		releaseAllocatedStock(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.CANCEL);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	public void review(Order order, boolean passed) {
		if (passed) {
			order.setStatus(Order.Status.PENDING_SHIPMENT);
		} else {
			order.setStatus(Order.Status.DENIED);
			releaseAllocatedStock(order);
		}
		super.update(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.REVIEW);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	public void payment(Order order, OrderPayment orderPayment) {
		orderPayment.setSn(snService.generate(Sn.Type.ORDER_PAYMENT));
		orderPayment.setOrderId(order.getId());
		orderPaymentService.save(orderPayment);
		allocateStock(order);

		order.setAmountPaid(order.getAmountPaid().add(orderPayment.getEffectiveAmount()));
		if (!order.hasExpired() && Order.Status.PENDING_PAYMENT.equals(order.getStatus()) && order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
			order.setStatus(Order.Status.PENDING_REVIEW);
			order.setExpire(null);
		}
		super.update(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.PAYMENT);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	public void shipping(Order order, OrderShipping orderShipping) {
		orderShipping.setSn(snService.generate(Sn.Type.ORDER_SHIPPING));
		orderShipping.setOrderId(order.getId());
		orderShippingService.save(orderShipping);
		List<OrderShippingItem> orderShippingItems = orderShipping.getOrderShippingItems();
		if (CommonUtils.isNotNull(orderShippingItems)) {
			for (int i = 0; i < orderShippingItems.size(); i++) {
				orderShippingItems.get(i).setOrderShippingId(orderShipping.getId());
				Sku sku = feignSkuService.find(orderShippingItems.get(i).getSkuId(), SecurityEnum.FROM_IN);
				if (CommonUtils.isNotNull(sku)) {
					Goods goods = feignGoodsService.find(sku.getGoodsId(), SecurityEnum.FROM_IN);
					if (CommonUtils.isNotNull(goods)) {
						orderShippingItems.get(i).setName(goods.getName());
					}
					orderShippingItems.get(i).setSn(sku.getSn());
				}
			}
			orderShippingItemMapper.saveBatch(orderShippingItems);
		}
		allocateStock(order);
		for (OrderShippingItem orderShippingItem : orderShipping.getOrderShippingItems()) {
			OrderItem orderItem = order.getOrderItem(orderShippingItem.getSn());
			if (orderItem == null || orderShippingItem.getQuantity() > orderItem.getShippableQuantity()) {
				throw new IllegalArgumentException();
			}
			orderItem.setShippedQuantity(orderItem.getShippedQuantity() + orderShippingItem.getQuantity());
			orderItemMapper.update(orderItem);
			Sku sku = feignSkuService.find(orderShippingItem.getSkuId(), SecurityEnum.FROM_IN);
			if (sku != null) {
				if (orderShippingItem.getQuantity() > sku.getStock()) {
					throw new IllegalArgumentException();
				}
				feignSkuService.addStock(sku, -orderShippingItem.getQuantity(), Stock.Type.STOCK_OUT,  null, SecurityEnum.FROM_IN);
				if (BooleanUtils.isTrue(order.getIsAllocatedStock())) {
					feignSkuService.addAllocatedStock(sku, -orderShippingItem.getQuantity(), SecurityEnum.FROM_IN);
				}
			}
		}

		order.setShippedQuantity(order.getShippedQuantity() + orderShipping.getQuantity());
		if (order.getShippedQuantity() >= order.getQuantity()) {
			order.setStatus(Order.Status.SHIPPED);
			order.setIsAllocatedStock(false);
		}
		super.update(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.SHIPPING);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);
	}

	@Override
	public void receive(Order order) {
		order.setStatus(Order.Status.RECEIVED);
		super.update(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.RECEIVE);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	@CacheEvict(value = {"goods", "goodsCategory"}, allEntries = true)
	public void complete(Order order) {
		Member member = feignMemberService.find(order.getMemberId(), SecurityEnum.FROM_IN);
		if (order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
			feignMemberService.addAmount(member, order.getAmountPaid(), SecurityEnum.FROM_IN);
		}

		order.setStatus(Order.Status.COMPLETED);
		order.setCompleteDate(new Date());
		super.update(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.COMPLETE);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	public void fail(Order order) {
		order.setStatus(Order.Status.FAILED);
		releaseAllocatedStock(order);
		super.update(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(OrderLog.Type.FAIL);
		orderLog.setOrderId(order.getId());
		orderLogService.save(orderLog);

	}

	@Override
	@Transactional
	public void delete(Order order) {
		if (order != null && !Order.Status.COMPLETED.equals(order.getStatus())) {
			releaseAllocatedStock(order);
		}

		super.delete(order);
	}

	/**
	 * 分配库存
	 *
	 * @param order 订单
	 */
	private void allocateStock(Order order) {
		if (order == null || BooleanUtils.isNotFalse(order.getIsAllocatedStock())) {
			return;
		}
		OrderItem orderEntity = new OrderItem();
		orderEntity.setOrderId(order.getId());
		List<OrderItem> orderItems = orderItemMapper.findAllByEntity(orderEntity);
		if (orderItems != null) {
			for (OrderItem orderItem : orderItems) {
				Sku sku = feignSkuService.find(orderItem.getSkuId(), SecurityEnum.FROM_IN);
				if (sku != null) {
					feignSkuService.addAllocatedStock(sku, orderItem.getQuantity() - orderItem.getShippedQuantity(), SecurityEnum.FROM_IN);
				}
			}
		}
		order.setIsAllocatedStock(true);
	}

	/**
	 * 释放已分配库存
	 *
	 * @param order 订单
	 */
	private void releaseAllocatedStock(Order order) {
		if (order == null || BooleanUtils.isNotTrue(order.getIsAllocatedStock())) {
			return;
		}
		OrderItem orderEntity = new OrderItem();
		orderEntity.setOrderId(order.getId());
		List<OrderItem> orderItems = orderItemMapper.findAllByEntity(orderEntity);
		if (orderItems != null) {
			for (OrderItem orderItem : orderItems) {
				Sku sku = feignSkuService.find(orderItem.getSkuId(), SecurityEnum.FROM_IN);
				if (sku != null) {
					feignSkuService.addAllocatedStock(sku, -(orderItem.getQuantity() - orderItem.getShippedQuantity()), SecurityEnum.FROM_IN);
				}
			}
		}
		order.setIsAllocatedStock(false);
		super.update(order);
	}



}