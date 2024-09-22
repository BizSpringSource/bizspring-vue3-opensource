package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.order.common.entity.PaymentItem;
import cn.bizspring.cloud.order.common.entity.PaymentLog;
import cn.bizspring.cloud.order.common.modes.Payment;
import cn.bizspring.cloud.store.common.entity.Store;

import java.util.Collection;
import java.util.Date;

/**
 * Service - 支付记录
 * 
 */
public interface PaymentLogService extends BizService<PaymentLog> {

	/**
	 * 根据编号查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	PaymentLog findBySn(String sn);

	/**
	 * 生成支付记录
	 * 
	 * @param lineItem
	 *            支付明细
	 * @param payment
	 *            支付方式
	 * @param rePayUrl
	 *            重新支付URL
	 * @return 支付记录
	 */
	PaymentLog generate(PaymentLog.LineItem lineItem, Payment payment, String rePayUrl);

	/**
	 * 生成父支付记录
	 * 
	 * @param lineItems
	 *            支付明细
	 * @param payment
	 *            支付方式
	 * @param rePayUrl
	 *            重新支付URL
	 * @return 父支付记录
	 */
	PaymentLog generateParent(Collection<PaymentLog.LineItem> lineItems, Payment payment, String rePayUrl);

	/**
	 * 支付处理
	 * 
	 * @param paymentLog
	 *            支付记录
	 */
	void handle(PaymentLog paymentLog);

	/**
	 * 生成支付明细
	 * 
	 * @param paymentItem
	 *            支付项
	 * @return 支付明细
	 */
	PaymentLog.LineItem generate(PaymentItem paymentItem);

		/**
		 * 支付统计
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
		 * @param beginDate
		 * 			起始时间
		 * @param endDate
		 * 			结束时间
		 * @return
		 */
	Long count(String countType, Store currentStore,  Boolean isSuccess, Boolean hasExpired, String paymentId, Date beginDate, Date endDate);

}