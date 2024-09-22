package cn.bizspring.cloud.order.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.bizspring.cloud.order.common.modes.Payment;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.User;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * Entity - 支付记录
 * 
 */
@Data
@ApiModel(description = "支付记录")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_payment_log")
public class PaymentLog extends BizEntity<PaymentLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String sn;

	/**
	 * 支付金额
	 */
	private BigDecimal amount;

	/**
	 * 是否成功
	 */
	private Boolean isSuccess;

	/**
	 * 支付方式ID
	 */
	private String paymentId;

	/**
	 * 支付方式名称
	 */
	private String paymentName;

	/**
	 * 重新支付URL
	 */
	private String rePayUrl;

	/**
	 * 过期时间
	 */
	private Date expire;

	/**
	 * 父支付
	 */
	@TableField(exist = false)
	private String parentId;

	@TableField(exist = false)
	private String orderId;

	/**
	 * 店铺
	 */
	@TableField(exist = false)
	private String storeId;

	/**
	 * 用户
	 */
	@TableField(exist = false)
	private String userId;

	/**
	 * 判断是否已过期
	 *
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getExpire() != null && !getExpire().after(new Date());
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
	 * 设置支付
	 * 
	 * @param payment
	 *            支付
	 */
	@Transient
	public void setPayment(Payment payment) {
		setPaymentId(payment != null ? payment.getId() : null);
		setPaymentName(payment != null ? payment.getName() : null);
	}

	/**
	 * 支付明细
	 * 
	 */
	public abstract static class LineItem {
		/**
		 * 金额
		 */
		private BigDecimal amount;

		/**
		 * 获取金额
		 * 
		 * @return 金额
		 */
		public BigDecimal getAmount() {
			return amount;
		}

		/**
		 * 设置金额
		 * 
		 * @param amount
		 *            金额
		 */
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		/**
		 * 获取支付目标
		 * 
		 * @return 支付目标
		 */
		public abstract Object getTarget();

		/**
		 * 设置支付目标
		 *
		 * @param target
		 *            支付目标
		 */
	}

	@Transient
	public void setTarget(Object target) {
		if (target == null) {
			return;
		}
		if (target instanceof Order) {
			Order order = (Order) target;
			setOrderId(order.getId());
		} else if (target instanceof User) {
			User user = (User) target;
			setUserId(user.getId());
		} else if (target instanceof Store) {
			Store store = (Store) target;
			setStoreId(store.getId());
		}
	}

	/**
	 * 订单支付明细
	 *
	 */
	public static class OrderLineItem extends LineItem {

		/**
		 * 订单
		 */
		private Order order;

		/**
		 * 构造方法
		 *
		 * @param order
		 *            订单
		 */
		public OrderLineItem(Order order) {
			this.order = order;
			super.amount = order.getAmountPayable();
		}

		/**
		 * 获取订单
		 *
		 * @return 订单
		 */
		public Order getOrder() {
			return order;
		}

		/**
		 * 设置订单
		 *
		 * @param order
		 *            订单
		 */
		public void setOrder(Order order) {
			this.order = order;
		}

		@Override
		public Object getTarget() {
			return this.order;
		}
	}
}