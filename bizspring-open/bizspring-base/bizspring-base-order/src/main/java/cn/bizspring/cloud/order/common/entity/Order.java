package cn.bizspring.cloud.order.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import cn.bizspring.cloud.store.common.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
/**
 * Entity - 订单
 * 
 */
@Data
@ApiModel(description = "订单")
@TableName("biz_orders")
public class Order extends BizEntity<Order> {

	private static final long serialVersionUID = 1L;

	public static final String CACHE_CLASS_NAME = "order:";

	/**
	 * "订单锁"缓存名称
	 */
	public static final String ORDER_LOCK_CACHE_NAME = "order_lock:";

	/**
	 * 配送验证组
	 */
	public interface Delivery extends Default {

	}

	/**
	 * 状态
	 */
	public enum Status implements IEnum<Integer> {

		/**
		 * 等待付款
		 */
		PENDING_PAYMENT(0),

		/**
		 * 等待处理
		 */
		PENDING_REVIEW(1),

		/**
		 * 等待发货
		 */
		PENDING_SHIPMENT(2),

		/**
		 * 已发货
		 */
		SHIPPED(3),

		/**
		 * 已收货
		 */
		RECEIVED(4),

		/**
		 * 已完成
		 */
		COMPLETED(5),

		/**
		 * 订单已失败
		 */
		FAILED(6),

		/**
		 * 已取消
		 */
		CANCELED(7),

		/**
		 * 无法发货
		 */
		DENIED(8);
		
		private int value;

		Status(final int value) {
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
	@JsonView(BaseView.class)
	private String sn;

	/**
	 * 状态
	 */
	@JsonView(BaseView.class)
	private Order.Status status;

	/**
	 * 价格
	 */
	@JsonView(BaseView.class)
	private BigDecimal price;

	/**
	 * 运费
	 */
	@NotNull(groups = Delivery.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal freight;

	/**
	 * 调整金额
	 */
	@NotNull
	@Digits(integer = 12, fraction = 3)
	private BigDecimal offsetAmount;

	/**
	 * 订单金额
	 */
	@JsonView(BaseView.class)
	private BigDecimal amount;

	/**
	 * 已付金额
	 */
	private BigDecimal amountPaid;

	/**
	 * 重量
	 */
	private Integer weight;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 已发货数量
	 */
	private Integer shippedQuantity;

	/**
	 * 收货人
	 */
	@NotEmpty(groups = Delivery.class)
	private String consignee;

	/**
	 * 地区名称
	 */
	private String regionName;

	/**
	 * 地址
	 */
	@NotEmpty(groups = Delivery.class)
	private String address;

	/**
	 * 邮编
	 */
	@NotEmpty(groups = Delivery.class)
	private String zipCode;

	/**
	 * 电话
	 */
	@NotEmpty(groups = Delivery.class)
	private String phone;

	/**
	 * 附言
	 */
	private String remark;

	/**
	 * 过期时间
	 */
	private Date expire;

	/**
	 * 是否已分配库存
	 */
	private Boolean isAllocatedStock;
	
	/**
	 * 支付方式名称
	 */
	private String paymentPatternName;

	/**
	 * 支付方式类型
	 */
	private PaymentPattern.Type paymentPatternType;

	/**
	 * 配送方式名称
	 */
	private String shippingMethodName;

	/**
	 * 完成日期
	 */
	private Date completeDate;

	/**
	 * 地区
	 */
	@NotNull(groups = Delivery.class)
	@TableField(exist = false)
	private String regionId;

	/**
	 * 支付方式
	 */
	@TableField(exist = false)
	private String paymentPatternId;

	/**
	 * 配送方式
	 */
	@TableField(exist = false)
	private String shippingMethodId;

	/**
	 * 会员
	 */
	@TableField(exist = false)
	private String memberId;
	@TableField(exist = false)
	private String memberName;

	/**
	 * 店铺
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String storeId;
	@TableField(exist = false)
	private String storeName;

	/**
	 * 订单项
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private List<OrderItem> orderItems = new ArrayList<>();

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@JsonIgnore
	@Transient
	public boolean getIsDelivery() {
		return CollectionUtils.exists(getOrderItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				OrderItem orderItem = (OrderItem) object;
				return orderItem != null && BooleanUtils.isTrue(orderItem.getIsDelivery());
			}
		});
	}

	/**
	 * 获取应付金额
	 * 
	 * @return 应付金额
	 */
	@JsonIgnore
	@Transient
	public BigDecimal getAmountPayable() {
		if (!hasExpired() && !Order.Status.COMPLETED.equals(getStatus()) && !Order.Status.FAILED.equals(getStatus()) && !Order.Status.CANCELED.equals(getStatus()) && !Order.Status.DENIED.equals(getStatus())) {
			BigDecimal amountPayable = getAmount().subtract(getAmountPaid());
			return amountPayable.compareTo(BigDecimal.ZERO) >= 0 ? amountPayable : BigDecimal.ZERO;
		}
		return BigDecimal.ZERO;
	}

	/**
	 * 获取可发货数
	 * 
	 * @return 可发货数
	 */
	@JsonIgnore
	@Transient
	public int getShippableQuantity() {
		if (!hasExpired() && Order.Status.PENDING_SHIPMENT.equals(getStatus())) {
			int shippableQuantity = getQuantity() - getShippedQuantity();
			return shippableQuantity >= 0 ? shippableQuantity : 0;
		}
		return 0;
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@JsonView(BaseView.class)
	@Transient
	public boolean hasExpired() {
		return getExpire() != null && !getExpire().after(new Date());
	}

	/**
	 * 获取订单项
	 * 
	 * @param sn
	 *            SKU编号
	 * @return 订单项
	 */
	@JsonIgnore
	@Transient
	public OrderItem getOrderItem(String sn) {
		if (StringUtils.isEmpty(sn) || CollectionUtils.isEmpty(getOrderItems())) {
			return null;
		}
		for (OrderItem orderItem : getOrderItems()) {
			if (orderItem != null && StringUtils.equalsIgnoreCase(orderItem.getSn(), sn)) {
				return orderItem;
			}
		}
		return null;
	}
}