package cn.bizspring.cloud.order.common.entity;

import java.math.BigDecimal;

import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 订单支付
 * 
 */
@Data
@ApiModel(description = "订单支付")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_order_payment")
public class OrderPayment extends BizEntity<OrderPayment> {

	private static final long serialVersionUID = 1L;

	/**
	 * 方式
	 */
	public enum Method implements IEnum<Integer> {

		/**
		 * 在线支付
		 */
		ONLINE(0),

		/**
		 * 线下支付
		 */
		OFFLINE(1);

		private int value;

		Method(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 编号
	 */
	private String sn;

	/**
	 * 方式
	 */
	@NotNull
	private OrderPayment.Method method;

	/**
	 * 支付方式
	 */
	private String paymentPattern;

	/**
	 * 收款银行
	 */
	@Length(max = 200)
	private String bank;

	/**
	 * 收款账号
	 */
	@Length(max = 200)
	private String account;

	/**
	 * 付款金额
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal amount;

	/**
	 * 付款人
	 */
	@Length(max = 200)
	private String payer;

	/**
	 * 备注
	 */
	@Length(max = 200)
	private String remark;

	/**
	 * 订单
	 */
	@TableField(exist = false)
	private String orderId;


	public void setPaymentPattern(String paymentPattern) {
		this.paymentPattern = paymentPattern;
	}

	/**
	 * 获取有效金额
	 * 
	 * @return 有效金额
	 */
	@Transient
	public BigDecimal getEffectiveAmount() {
		BigDecimal effectiveAmount = getAmount();
		return effectiveAmount.compareTo(BigDecimal.ZERO) >= 0 ? effectiveAmount : BigDecimal.ZERO;
	}

	/**
	 * 设置支付方式
	 * 
	 * @param paymentPattern
	 *            支付方式
	 */
	@Transient
	public void setPaymentPattern(PaymentPattern paymentPattern) {
		setPaymentPattern(paymentPattern != null ? paymentPattern.getName() : null);
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setSn(StringUtils.lowerCase(getSn()));
	}

}