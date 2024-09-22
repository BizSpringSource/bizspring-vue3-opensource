package cn.bizspring.cloud.order.common.modes;

import cn.bizspring.cloud.order.common.entity.PaymentLog;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 支付
 * 
 */
public abstract class Payment implements Comparable<Payment> {



	/**
	 * 超时时间
	 */
	public static final Integer TIMEOUT = 24 * 60 * 60;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public abstract String getName();

	/**
	 * 是否支持
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 是否支持
	 */
	public boolean supports(HttpServletRequest request) {
		return true;
	}

	/**
	 * 支付后处理
	 * 
	 * @param payment
	 *            支付方式
	 * @param paymentLog
	 *            支付记录
	 * @param paymentDescription
	 *            支付描述
	 * @param extra
	 *            附加内容
	 * @param isPaySuccess
	 *            是否支付成功
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */
	public void postPayHandle(Payment payment, PaymentLog paymentLog, String paymentDescription, String extra, boolean isPaySuccess, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
	 * 获取超时时间
	 * 
	 * @return 超时时间
	 */
	public Integer getTimeout() {
		return Payment.TIMEOUT;
	}

	/**
	 * 实现compareTo方法
	 * 
	 * @param payment
	 *            支付方式
	 * @return 比较结果
	 */
	@Override
	public int compareTo(Payment payment) {
		if (payment == null) {
			return 1;
		}
		return 0;
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Payment other = (Payment) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	/**
	 * 重写hashCode方法
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}

}