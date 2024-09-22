package cn.bizspring.cloud.order.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.dto.ReportDto;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.order.admin.service.*;
import cn.bizspring.cloud.order.common.entity.*;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.feign.FeignSkuService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.order.common.entity.Order;
import cn.bizspring.cloud.store.common.feign.FeignExpressCompanyService;
import cn.bizspring.cloud.store.common.feign.FeignPaymentPatternService;
import cn.bizspring.cloud.store.common.feign.FeignShippingMethodService;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.common.entity.Region;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.feign.FeignRegionService;
import cn.bizspring.cloud.upms.common.feign.FeignMerchantService;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import cn.bizspring.cloud.common.core.util.R;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 订单
 */
@Slf4j
@AllArgsConstructor
@Api(value = "order", tags = "商家 - 订单")
@RestController("merchantOrderController")
@RequestMapping("/merchant/order")
public class OrderController extends BizController {

	private FeignRegionService feignRegionService;
	private OrderService orderService;
	private OrderItemService orderItemService;
	private FeignSkuService feignSkuService;
	private OrderShippingService orderShippingService;
	private FeignShippingMethodService feignShippingMethodService;
	private FeignPaymentPatternService feignPaymentPatternService;
	private FeignExpressCompanyService feignExpressCompanyService;
	private FeignMemberService feignMemberService;
	private FeignUserService feignUserService;
	private FeignStoreService feignStoreService;
	private FeignMerchantService feignMerchantService;
	private OrderLogService orderLogService;
	private PaymentLogService paymentLogService;
	private CartService cartService;
	private CartItemService cartItemService;



	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(@RequestBody Order order, String regionId, String paymentPatternId, String shippingMethodId, BigDecimal freight, BigDecimal tax, BigDecimal offsetAmount, Long rewardPoint, String consignee, String address, String zipCode, String phone,String remark) {
		Region region = feignRegionService.find(regionId, SecurityEnum.FROM_IN);
//		if (order == null || !orderService.acquireLock(order)) {
//			return R.INVALID_ENTITY;
//		}
		if (order.hasExpired() || (!Order.Status.PENDING_PAYMENT.equals(order.getStatus()) && !Order.Status.PENDING_REVIEW.equals(order.getStatus()))) {
			return R.INVALID_ENTITY;
		}
		if (order.getStatus().equals(Order.Status.PENDING_REVIEW) || order.getAmountPaid().compareTo(BigDecimal.ZERO) > 0) {
			offsetAmount = BigDecimal.ZERO;
			freight = order.getFreight();
		}

		order.setOffsetAmount(offsetAmount != null ? offsetAmount : BigDecimal.ZERO);
		order.setRemark(remark);
		order.setPaymentPatternId(paymentPatternId);

		if (order.getIsDelivery()) {
			order.setFreight(freight.compareTo(BigDecimal.ZERO) > 0 ? freight : BigDecimal.ZERO);
			order.setConsignee(consignee);
			order.setAddress(address);
			order.setZipCode(zipCode);
			order.setPhone(phone);
			if (CommonUtils.isNull(region)) {
				return R.INVALID_ENTITY;
			}
			order.setRegionId(region.getId());
			order.setRegionName(region.getName());
			order.setShippingMethodId(shippingMethodId);
		} else {
			order.setFreight(BigDecimal.ZERO);
			order.setConsignee(null);
			order.setRegionName(null);
			order.setAddress(null);
			order.setZipCode(null);
			order.setPhone(null);
			order.setShippingMethodName(null);
			order.setRegionId(null);
			order.setRegionName(null);
			order.setShippingMethodId(null);
			if (!isValid(order)) {
				return R.INVALID_ENTITY;
			}
		}
		orderService.modify(order);
		return R.OK;
	}

	/**
	 * 查看
	 */
	@ApiOperation(value = "查看", notes = "查看")
	@ApiOperationSupport(order = 2)
	@GetMapping("/view")
	public ResponseEntity view(String id) {
		Map<String, Object> data = new HashMap<>(2);
		Order order = orderService.find(id);

		if (order == null) {
			return R.failed();
		}
		User member = feignUserService.findByUserId(order.getMemberId(), SecurityEnum.FROM_IN);
		if (CommonUtils.isNotNull(member)) {
			order.setMemberName(member.getUsername());
		}
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(order.getId());
		List<OrderItem> orderItems = orderItemService.findAllByEntity(orderItem);
		if (CommonUtils.isNotNull(orderItems)) {
			order.setOrderItems(orderItems);
		}
		data.put("methods", OrderPayment.Method.values());
		data.put("paymentPatterns", feignPaymentPatternService.findAll(SecurityEnum.FROM_IN));
		data.put("shippingMethods", feignShippingMethodService.findAll(SecurityEnum.FROM_IN));
		data.put("expressCompanys", feignExpressCompanyService.findAll(SecurityEnum.FROM_IN));
		Map paramsMap = new HashMap();
		paramsMap.put("order_id", order.getId());
		data.put("orderLogs", orderLogService.listByMap(paramsMap));
		Map paramsMap1 = new HashMap();
		paramsMap1.put("order_id", order.getId());
		QueryWrapper<OrderShipping> QR = new QueryWrapper();
		data.put("orderShipping", orderShippingService.getOne(QR.eq("order_id", order.getId())));
		data.put("order", order);
		data.put("regions", feignRegionService.findChildren(null, true, null, SecurityEnum.FROM_IN));
		return R.ok(data);
	}

	/**
	 * 审核
	 */
	@ApiOperation(value = "审核", notes = "审核")
	@ApiOperationSupport(order = 2)
	@PostMapping("/review")
	public ResponseEntity review(String id, Boolean passed) {
		Merchant currentUser = feignMerchantService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Order order = orderService.find(id);
		if (order == null || order.hasExpired() || !Order.Status.PENDING_REVIEW.equals(order.getStatus()) || passed == null) {
			return R.INVALID_ENTITY;
		}
		//判断重复提交
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.INVALID_ENTITY;
//		}
		orderService.review(order, passed);
		return R.OK;
	}


	/**
	 * 发货
	 */
	@ApiOperation(value = "发货", notes = "发货")
	@ApiOperationSupport(order = 2)
	@PostMapping("/shipping")
	public ResponseEntity shipping(@RequestBody OrderShipping orderShippingForm, String orderId, String shippingMethodId, String regionId, String regionName, String consignee, String address, String zipCode, String phone, String remark) {
		Merchant currentUser = feignMerchantService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Order order = orderService.find(orderId);
		OrderItem orderIm = new OrderItem();
		orderIm.setOrderId(order.getId());
		List<OrderItem> orderItems = orderItemService.findAllByEntity(orderIm);
		order.setOrderItems(orderItems);
		if (order == null || order.getShippableQuantity() <= 0) {
			return R.INVALID_ENTITY;
		}
		boolean isDelivery = false;
		for (Iterator<OrderShippingItem> iterator = orderShippingForm.getOrderShippingItems().iterator(); iterator.hasNext(); ) {
			OrderShippingItem orderShippingItem = iterator.next();
			if (orderShippingItem == null || StringUtils.isEmpty(orderShippingItem.getSn()) || orderShippingItem.getQuantity() == null || orderShippingItem.getQuantity() <= 0) {
				iterator.remove();
				continue;
			}
			OrderItem orderItem = order.getOrderItem(orderShippingItem.getSn());
			if (orderItem == null || orderShippingItem.getQuantity() > orderItem.getShippableQuantity()) {
				return R.INVALID_ENTITY;
			}
			Sku sku = feignSkuService.find(orderItem.getSkuId(), SecurityEnum.FROM_IN);
			if (sku != null && orderShippingItem.getQuantity() > sku.getStock()) {
				return R.INVALID_ENTITY;
			}
			orderShippingItem.setName(orderItem.getName());
			orderShippingItem.setIsDelivery(orderItem.getIsDelivery());
			orderShippingItem.setSkuId(orderItem.getSkuId());
			orderShippingItem.setOrderShippingId(orderShippingForm.getId());
			orderShippingItem.setSpecifications(orderItem.getSpecifications());
			orderShippingItem.setId(String.valueOf(IdWorker.getId()));
			orderShippingItem.setCreatedTime(new Date());
			orderShippingItem.setVersion(0L);
			if (orderItem.getIsDelivery()) {
				isDelivery = true;
			}
		}
		orderShippingForm.setOrderId(order.getId());
		orderShippingForm.setRegion(regionName);
		if (isDelivery) {

		} else {
			orderShippingForm.setShippingMethod((String) null);
			orderShippingForm.setExpressCompany((String) null);
			orderShippingForm.setExpressCompanyUrl(null);
			orderShippingForm.setExpressCompanyCode(null);
			orderShippingForm.setTrackingNo(null);
			orderShippingForm.setFreight(null);
			orderShippingForm.setConsignee(null);
			orderShippingForm.setRegion((String) null);
			orderShippingForm.setAddress(null);
			orderShippingForm.setZipCode(null);
			orderShippingForm.setPhone(null);
			if (!isValid(orderShippingForm)) {
				return R.INVALID_ENTITY;
			}
		}
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.INVALID_ENTITY;
//		}
		order.setShippingMethodId(shippingMethodId);
		order.setRegionId(regionId);
		order.setRegionName(regionName);
		order.setConsignee(consignee);
		order.setAddress(address);
		order.setZipCode(zipCode);
		order.setPhone(phone);
		order.setRemark(remark);
		orderService.shipping(order, orderShippingForm);
		return R.OK;
	}

	/**
	 * 完成
	 */
	@ApiOperation(value = "完成", notes = "完成")
	@ApiOperationSupport(order = 2)
	@PostMapping("/complete")
	public ResponseEntity complete(String id) {
		Merchant currentUser = feignMerchantService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Order order = orderService.find(id);
		if (order == null || order.hasExpired() || !Order.Status.RECEIVED.equals(order.getStatus())) {
			return R.INVALID_ENTITY;
		}
		//判断重复提交
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.INVALID_ENTITY;
//		}
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(id);
		List<OrderItem>  orderItems = orderItemService.findAllByEntity(orderItem);
		if(CollectionUtils.isNotEmpty(orderItems)){
			order.setOrderItems(orderItems);
		}
		orderService.complete(order);
		return R.OK;
	}

	/**
	 * 失败
	 */
	@ApiOperation(value = "失败", notes = "失败")
	@ApiOperationSupport(order = 2)
	@PostMapping("/fail")
	public ResponseEntity fail(String id) {
		Merchant currentUser = feignMerchantService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Order order = orderService.find(id);
		if (order == null || order.hasExpired() || (!Order.Status.PENDING_SHIPMENT.equals(order.getStatus()) && !Order.Status.SHIPPED.equals(order.getStatus()) && !Order.Status.RECEIVED.equals(order.getStatus()))) {
			return R.INVALID_ENTITY;
		}
//		if (!orderService.acquireLock(order, currentUser)) {
//			return R.INVALID_ENTITY;
//		}
		orderService.fail(order);
		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Order.Status status, String memberUsername, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		List<Store> stores = new ArrayList<>();
		stores.add(currentStore);
		data.put("statuses", Order.Status.values());
		data.put("status", status);
		data.put("memberUsername", memberUsername);
		data.put("isPendingReceive", isPendingReceive);
		data.put("isPendingRefunds", isPendingRefunds);
		data.put("isAllocatedStock", isAllocatedStock);
		data.put("hasExpired", hasExpired);
		List<User> users = feignUserService.findAll(SecurityEnum.FROM_IN);
		Order entity = new Order();
		if (CommonUtils.isNotNull(status)) {
			entity.setStatus(status);
		}
		Member member = feignMemberService.findByUsername(memberUsername, SecurityEnum.FROM_IN);
		List<Order> content = new ArrayList<Order>();
		Page page = new Page();
		if (StringUtils.isNotEmpty(memberUsername) && member == null) {
			page = Page.emptyPage(pageable);
		} else {
			page = orderService.findPage(entity, users, stores, isPendingReceive, isAllocatedStock, hasExpired, pageable);
		}
		content = page.getContent();
		if (CommonUtils.isNotNull(content) && 0 < content.size()) {
			for (int i = 0; i < content.size(); i++) {
				Order order = content.get(i);
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(order.getId());
				List<OrderItem> orderItems = orderItemService.findAllByEntity(orderItem);
				if (CommonUtils.isNotNull(orderItems)) {
					(content.get(i)).setOrderItems(orderItems);
				}
			}
		}

		page.setContent(content);
		data.put("data", page);
		return R.ok(data);
	}

	/**
	 * 计算订单数据
	 */
	@ApiOperation(value = "计算支付金额", notes = "计算支付金额")
	@ApiOperationSupport(order = 2)
	@GetMapping("/count")
	public ResponseEntity count() {
		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>();
		if (CommonUtils.isNull(currentStore)) {
			return R.failed();
		}
		//待支付  PENDING_PAYMENT
		Long countPendingPayment = orderService.count(Order.Status.PENDING_PAYMENT, currentStore, null, null, false, null, false);
		//待处理  PENDING_REVIEW
		Long countPendingReview = orderService.count(Order.Status.PENDING_REVIEW, currentStore, null, null, false, null, false);
//		//待发货  PENDING_SHIPMENT
		Long countPendingShipment = orderService.count(Order.Status.PENDING_SHIPMENT, currentStore, null, null, false, null, false);

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		//今日起始时间
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date thatdayBeginDate = calendar.getTime();
		//昨日起始时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		Date yesterdayBeginDate = calendar.getTime();
		//昨日结束时间
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date yesterdayEndDate = calendar.getTime();
		//今日结束时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		Date thatdayEndDate = calendar.getTime();

//		//当日--支付金额  PAYMENT
		Long countPaymentAmount = paymentLogService.count("amount", currentStore, true, false, null, thatdayBeginDate, thatdayEndDate);
//		//当日--支付订单 paymentLog
		Long countPaymentOrder = paymentLogService.count("order", currentStore,  true, false, null, thatdayBeginDate, thatdayEndDate);

//		订单日志中
//		今日支付
		Long sumThatdayPaymentTotal = paymentLogService.count("amount", currentStore, true, false, null, thatdayBeginDate, thatdayEndDate);
//		昨日支付
		Long sumYesterdayPaymentTotal = paymentLogService.count("amount", currentStore, true, false, null, yesterdayBeginDate, yesterdayEndDate);

//		今日下单
		Long countThatdayPlaceOrder = orderLogService.count("placeOrder", currentStore, OrderLog.Type.CREATE, thatdayBeginDate, thatdayEndDate);
//		昨日下单
		Long countYesterdayPlaceOrder = orderLogService.count("placeOrder", currentStore, OrderLog.Type.CREATE, yesterdayBeginDate, yesterdayEndDate);

//		今日下单客户
		Long countThatdayPlaceOrderMember = orderLogService.count("placeOrderMember", currentStore, OrderLog.Type.CREATE, thatdayBeginDate, thatdayEndDate);
//		昨日下单客户
		Long countYesterdayPlaceOrderMember = orderLogService.count("placeOrderMember", currentStore, OrderLog.Type.CREATE, yesterdayBeginDate, yesterdayEndDate);


		//都要加时间段 当天、一周 一月
		//都要有完成等状态
		data.put("countPendingPayment", countPendingPayment);
		data.put("countPendingReview", countPendingReview);
		data.put("countPendingShipment", countPendingShipment);
		data.put("countPaymentAmount", countPaymentAmount);
		data.put("countPaymentOrder", countPaymentOrder);
		data.put("sumThatdayPaymentTotal", sumThatdayPaymentTotal);
		data.put("sumYesterdayPaymentTotal", sumYesterdayPaymentTotal);
		data.put("countThatdayPlaceOrder", countThatdayPlaceOrder);
		data.put("countYesterdayPlaceOrder", countYesterdayPlaceOrder);
		data.put("countThatdayPlaceOrderMember", countThatdayPlaceOrderMember);
		data.put("countYesterdayPlaceOrderMember", countYesterdayPlaceOrderMember);

		return R.ok(data);
	}


	/**
	 * 支付订单统计
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list_paymented_order")
	public ResponseEntity findPaymentedOrder(Pageable pageable) {
		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		//支付订单统计
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		//今日起始时间
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date thatdayBeginDate = calendar.getTime();
		//昨日起始时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		//昨日结束时间
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		//今日结束时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		Date thatdayEndDate = calendar.getTime();
		Page page = orderService.findOrderPaymentedPage(currentStore, true, false, null, thatdayBeginDate, thatdayEndDate, pageable);
		List<Order> content = page.getContent();
		if (CommonUtils.isNotNull(content) && 0 < content.size()) {
			for (int i = 0; i < content.size(); i++) {
				Order order = content.get(i);
				//orderItems
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(order.getId());
				List<OrderItem> orderItems = orderItemService.findAllByEntity(orderItem);
				if (CommonUtils.isNotNull(orderItems)) {
					(content.get(i)).setOrderItems(orderItems);
				}

				Member member = feignMemberService.find(order.getMemberId(), SecurityEnum.FROM_IN);
				if (CommonUtils.isNotNull(member)) {
					(content.get(i)).setMemberName(member.getUsername());
				}
			}
		}
		page.setContent(content);
		data.put("data", page);
		return R.ok(data);
	}

	/**
	 * 下单曲线统计
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/place_order_curve_report")
	public ResponseEntity countPlaceOrderCurveReport(String groupBy) {
		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		//今日起始时间
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date thatdayBeginDate = calendar.getTime();
		//昨日起始时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		Date yesterdayBeginDate = calendar.getTime();
		//昨日结束时间
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date yesterdayEndDate = calendar.getTime();
		//今日结束时间
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		Date thatdayEndDate = calendar.getTime();

		List<ReportDto> objsListYesterday = orderLogService.countPlaceOrderCurveReport(groupBy, currentStore, OrderLog.Type.CREATE, yesterdayBeginDate, yesterdayEndDate);
		List<ReportDto> objsListThatday = orderLogService.countPlaceOrderCurveReport(groupBy, currentStore, OrderLog.Type.CREATE, thatdayBeginDate, thatdayEndDate);
		data.put("objsListYesterday", objsListYesterday);
		data.put("objsListThatday", objsListThatday);
		//		订单日志中

		//		今日下单
		Long countThatdayPlaceOrder = orderLogService.count("placeOrder", currentStore, OrderLog.Type.CREATE, thatdayBeginDate, thatdayEndDate);
		//		昨日下单
		Long countYesterdayPlaceOrder = orderLogService.count("placeOrder", currentStore, OrderLog.Type.CREATE, yesterdayBeginDate, yesterdayEndDate);

		data.put("countThatdayPlaceOrder", countThatdayPlaceOrder);
		data.put("countYesterdayPlaceOrder", countYesterdayPlaceOrder);

		return R.ok(data);
	}

}