package cn.bizspring.cloud.order.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.order.common.entity.PaymentLog;
import cn.bizspring.cloud.order.common.modes.Payment;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Mapper - 支付记录
 * 
 */
public interface PaymentLogMapper extends BizMapper<PaymentLog> {

	/**
	 * 查找可用支付记录
	 * 
	 * @param lineItem
	 *            支付明细
	 * @param payment
	 *            支付方式
	 * @return 可用支付记录，若不存在则返回null
	 */
	PaymentLog findAvailable(@Param("lineItem")PaymentLog.LineItem lineItem, @Param("payment") Payment payment, @Param("amount")BigDecimal amount, @Param("fee")BigDecimal fee);

	/**
	 * 查找可用父支付记录
	 * 
	 * @param lineItems
	 *            支付明细
	 * @param payment
	 *            支付方式
	 * @return 可用父支付记录，若不存在则返回null
	 */
	PaymentLog findAvailableParent(@Param("lineItem")PaymentLog.LineItem lineItems, @Param("payment")Payment payment);



	/**
	 * 支付计算
	 * @param store
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
	Long count(@Param("countType") String countType, @Param("store") Store store, @Param("isSuccess") Boolean isSuccess, @Param("hasExpired") Boolean hasExpired, @Param("paymentId") String paymentId,@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

}