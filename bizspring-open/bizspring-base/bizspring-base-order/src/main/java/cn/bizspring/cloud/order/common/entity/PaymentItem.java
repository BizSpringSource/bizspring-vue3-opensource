package cn.bizspring.cloud.order.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity - 支付项
 * 
 */
@Data
public class PaymentItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付金额
	 */
	private BigDecimal amount;

	/**
	 * 订单编号
	 */
	private String orderSn;

	/**
	 * 服务编号
	 */
	private String svcSn;

}