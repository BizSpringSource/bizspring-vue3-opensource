package cn.bizspring.cloud.store.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity - 配送方式与支付方式关连
 *
 */
@Data
@ApiModel(description = "配送方式与支付方式关连")
@TableName("biz_shipping_method_payment_pattern")
public class ShippingMethodPaymentPattern implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 配送方式
	 */
	@TableField(value="shipping_methods_id")
	private String shippingMethodsId;

	/**
	 * 支付方式
	 */
	@TableField(value="payment_patterns_id")
	private String paymentPatternsId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ShippingMethodPaymentPattern that = (ShippingMethodPaymentPattern) o;
		return Objects.equals(shippingMethodsId, that.shippingMethodsId) && Objects.equals(paymentPatternsId, that.paymentPatternsId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(shippingMethodsId, paymentPatternsId);
	}
}