package cn.bizspring.cloud.order.common.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 订单记录
 * 
 */
@Data
@ApiModel(description = "订单记录")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_order_log")
public class OrderLog extends BizEntity<OrderLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 订单创建
		 */
		CREATE(0),

		/**
		 * 订单修改
		 */
		MODIFY(1),

		/**
		 * 订单取消
		 */
		CANCEL(2),

		/**
		 * 订单审核
		 */
		REVIEW(3),

		/**
		 * 订单收款
		 */
		PAYMENT(4),

		/**
		 * 订单发货
		 */
		SHIPPING(5),

		/**
		 * 订单收货
		 */
		RECEIVE(7),

		/**
		 * 订单完成
		 */
		COMPLETE(8),

		/**
		 * 订单失败
		 */
		FAIL(8);
		
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
	 * 类型
	 */
	@EnumValue
	private OrderLog.Type type;

	/**
	 * 详情
	 */
	private String detail;

	/**
	 * 订单
	 */
	@TableField(exist = false)
	private String orderId;

}