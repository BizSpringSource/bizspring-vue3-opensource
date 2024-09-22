package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.order.admin.mapper.PaymentLogMapper;
import cn.bizspring.cloud.order.admin.service.OrderService;
import cn.bizspring.cloud.order.admin.service.PaymentLogService;
import cn.bizspring.cloud.order.admin.service.SnService;
import cn.bizspring.cloud.order.common.entity.*;
import cn.bizspring.cloud.store.common.entity.*;
import cn.bizspring.cloud.store.common.feign.FeignPaymentPatternService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.order.common.modes.Payment;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * Service - 支付记录
 */
@Service
@AllArgsConstructor
public class PaymentLogServiceImpl extends BizServiceImpl<PaymentLog> implements PaymentLogService {

	private PaymentLogMapper paymentLogMapper;
	private FeignMemberService feignMemberService;
	private SnService snService;
	private OrderService orderService;
	private FeignPaymentPatternService feignPaymentPatternService;


	/**
	 * 根据编号查找支付记录
	 *
	 * @param sn 编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	@Override
	@Transactional(readOnly = true)
	public PaymentLog findBySn(String sn) {
		return paymentLogMapper.findByProperty("sn", StringUtils.lowerCase(sn));
	}

	/**
	 * 生成支付记录
	 *
	 * @param lineItem      支付明细
	 * @param payment 支付方式
	 * @param rePayUrl      重新支付URL
	 * @return 支付记录
	 */
	@Override
	public PaymentLog generate(PaymentLog.LineItem lineItem, Payment payment, String rePayUrl) {
		BigDecimal amount = lineItem.getAmount();
		BigDecimal fee = lineItem.getAmount();
		PaymentLog paymentLog = paymentLogMapper.findAvailable(lineItem, payment, amount, fee);
		if (paymentLog == null) {
			paymentLog = new PaymentLog();
			paymentLog.setSn(snService.generate(Sn.Type.PAYMENT_LOG));
			paymentLog.setAmount(lineItem.getAmount());
			paymentLog.setIsSuccess(false);
			paymentLog.setExpire(DateUtils.addSeconds(new Date(), payment.getTimeout()));
			paymentLog.setParentId(null);
			paymentLog.setTarget(lineItem.getTarget());
			paymentLog.setPayment(payment);
			paymentLog.setRePayUrl(rePayUrl);
			super.save(paymentLog);
		}
		return paymentLog;
	}

	/**
	 * 生成父支付记录
	 *
	 * @param lineItems     支付明细
	 * @param payment 支付方式
	 * @param rePayUrl      重新支付URL
	 * @return 父支付记录
	 */
	@Override
	public PaymentLog generateParent(Collection<PaymentLog.LineItem> lineItems, Payment payment, String rePayUrl) {
		// 构造查询
		QueryWrapper<PaymentLog> queryWrapper = new QueryWrapper<>();
		String existsSql = "";
		PaymentLog.LineItem linei =null;
		for (PaymentLog.LineItem lineItem : lineItems) {
			linei=lineItem;
		}
		queryWrapper.exists(existsSql);
		PaymentLog parentPaymentLog = null;
		if(CommonUtils.isNotNull(linei)){
			parentPaymentLog = paymentLogMapper.findAvailableParent(linei, payment);
		}
		if (parentPaymentLog == null) {
			BigDecimal amount = BigDecimal.ZERO;
			for (PaymentLog.LineItem lineItem : lineItems) {
				amount = amount.add(lineItem.getAmount());
			}
			parentPaymentLog = new PaymentLog();
			parentPaymentLog.setSn(snService.generate(Sn.Type.PAYMENT_LOG));
			parentPaymentLog.setAmount(amount);
			parentPaymentLog.setIsSuccess(false);
			parentPaymentLog.setExpire(DateUtils.addSeconds(new Date(), payment.getTimeout()));
			parentPaymentLog.setParentId(null);
			parentPaymentLog.setPayment(payment);
			parentPaymentLog.setRePayUrl(rePayUrl);
			super.save(parentPaymentLog);
			for (PaymentLog.LineItem lineItem : lineItems) {
				PaymentLog paymentLog = new PaymentLog();
				paymentLog.setSn(snService.generate(Sn.Type.PAYMENT_LOG));
				paymentLog.setAmount(lineItem.getAmount());
				paymentLog.setIsSuccess(null);
				paymentLog.setExpire(null);
				paymentLog.setTarget(lineItem.getTarget());
				paymentLog.setPayment(null);
				paymentLog.setParentId(parentPaymentLog.getId());
				super.save(paymentLog);
			}
		}
		return parentPaymentLog;
	}


	/**
	 * 支付处理
	 *
	 * @param paymentLog 支付记录
	 */
	@Override
	public void handle(PaymentLog paymentLog) {
		if (BooleanUtils.isNotFalse(paymentLog.getIsSuccess())) {
			return;
		}
		Set<PaymentLog> paymentLogs = new HashSet<>();
		Set<PaymentLog> childrenList = paymentLogMapper.findSet("parentId", paymentLog.getId());
		if (CollectionUtils.isNotEmpty(childrenList)) {
			paymentLog.setIsSuccess(true);
			paymentLogs = childrenList;
		} else {
			paymentLogs.add(paymentLog);
		}
		for (PaymentLog log : paymentLogs) {
			String orderId = log.getOrderId();
			Order order = orderService.find(orderId);
			if (order != null) {
				OrderPayment orderPayment = new OrderPayment();
				orderPayment.setMethod(OrderPayment.Method.ONLINE);
				orderPayment.setPaymentPattern(log.getPaymentName());
				orderPayment.setAmount(log.getAmount());
				orderPayment.setOrderId(orderId);
				orderService.payment(order, orderPayment);
			}
			log.setIsSuccess(true);
			super.update(log);
		}
	}

	/**
	 * 生成支付明细
	 *
	 * @param paymentItem 支付项
	 * @return 支付明细
	 */
	@Override
	public PaymentLog.LineItem generate(PaymentItem paymentItem) {
		User user = feignMemberService.find(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
				Member member = (Member) user;
				if (member == null) {
					return null;
				}
				Order order = orderService.findBySn(paymentItem.getOrderSn());
				if (order == null || !member.getId().equals(order.getMemberId())) {
//				if (order == null || !member.getId().equals(order.getMemberId()) || !orderService.acquireLock(order, member)) {
					if (order.getPaymentPatternId() == null) {
						return null;
					}
					if (CommonUtils.isNotNull(order.getPaymentPatternId() == null)) {
						PaymentPattern paymentPattern = feignPaymentPatternService.find(order.getPaymentPatternId(), SecurityEnum.FROM_IN);
						if (CommonUtils.isNotNull(paymentPattern)) {
							if (!PaymentPattern.Pattern.ONLINE.equals(paymentPattern.getPattern())) {
								return null;
							}
						} else {
							return null;
						}
					}
					return null;
				}
				if (order.getAmountPayable().compareTo(BigDecimal.ZERO) <= 0) {
					return null;
				}
				return new PaymentLog.OrderLineItem(order);
	}

	/**
	 * 保存支付记录
	 *
	 * @param paymentLog
	 * @return
	 */
	@Override
	@Transactional
	public boolean save(PaymentLog paymentLog) {
		paymentLog.setSn(snService.generate(Sn.Type.PAYMENT_LOG));
		return super.save(paymentLog);
	}

	/**
	 * 支付金额
	 * @param countType
	 *          计算方式 count(order)、sum(amount)
	 * @param currentStore
	 * 			店铺
	 * @param isSuccess
	 * 			是否支付成功
	 * @param hasExpired
	 * 			是否过期
	 * @param paymentId
	 * 			支付方式
	 * @return
	 */
	@Override
	public Long count(String countType,Store currentStore, Boolean isSuccess, Boolean hasExpired, String paymentId,Date beginDate, Date endDate) {
		return paymentLogMapper.count(countType,currentStore,isSuccess,hasExpired,paymentId,beginDate,endDate);
	}

}