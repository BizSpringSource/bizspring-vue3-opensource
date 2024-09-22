package cn.bizspring.cloud.store.common.entity;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.bizspring.cloud.common.core.entity.SortEntity;

/**
 * Entity - 支付方式
 * 
 */
@Data
@ApiModel(description = "支付方式")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_payment_pattern")
public class PaymentPattern extends SortEntity<PaymentPattern> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 款到发货
		 */
		DELIVERY_AGAINST_PAYMENT(0),

		/**
		 * 货到付款
		 */
		CASH_ON_DELIVERY(1);
		
		private int value;

		Type(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 方式
	 */
	public enum Pattern implements IEnum<Integer> {

		/**
		 * 在线支付
		 */
		ONLINE(0),

		/**
		 * 线下支付
		 */
		OFFLINE(1);
		
		private int value;

		Pattern(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 类型
	 */
	@NotNull
	private PaymentPattern.Type type;

	/**
	 * 方式
	 */
	@NotNull
	private PaymentPattern.Pattern pattern;

	/**
	 * 超时时间
	 */
	@Min(1)
	private Integer timeout;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 介绍
	 */
	private String description;

	/**
	 * 内容
	 */
	@Lob
	private String content;

}