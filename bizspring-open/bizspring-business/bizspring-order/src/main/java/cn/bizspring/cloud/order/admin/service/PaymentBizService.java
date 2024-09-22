package cn.bizspring.cloud.order.admin.service;


import cn.bizspring.cloud.order.common.modes.Payment;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Service - 支付方式
 * 
 */
public interface PaymentBizService {


	/**
	 * 获取支付方式
	 *
	 * @param isEnabled
	 *            是否启用
	 * @return 支付方式
	 */
	List<Payment> getPayments(boolean isEnabled);

	/**
	 * 获取有效支付方式
	 *
	 * @param request
	 *            request
	 * @return 有效支付方式
	 */
	List<Payment> getActivePayments(HttpServletRequest request);

	/**
	 * 获取支付方式
	 * 
	 * @param id
	 *            ID
	 * @return 支付方式
	 */
	Payment getPayment(String id);

}