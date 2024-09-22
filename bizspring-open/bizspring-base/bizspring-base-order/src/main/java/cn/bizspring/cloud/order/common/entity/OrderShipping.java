package cn.bizspring.cloud.order.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.upms.common.entity.Region;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonView;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 订单发货
 * 
 */
@Data
@ApiModel(description = "订单发货")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_order_shipping")
public class OrderShipping extends BizEntity<OrderShipping> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@JsonView(BaseView.class)
	private String sn;

	/**
	 * 配送方式
	 */
	@JsonView(BaseView.class)
	private String shippingMethod;

	/**
	 * 快递公司
	 */
	@JsonView(BaseView.class)
	private String expressCompany;

	/**
	 * 快递公司网址
	 */
	@JsonView(BaseView.class)
	private String expressCompanyUrl;

	/**
	 * 快递公司代码
	 */
	@JsonView(BaseView.class)
	private String expressCompanyCode;

	/**
	 * 运单号
	 */
	@JsonView(BaseView.class)
	private String trackingNo;

	/**
	 * 物流费用
	 */
	@JsonView(BaseView.class)
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal freight;

	/**
	 * 收货人
	 */
	@JsonView(BaseView.class)
	private String consignee;

	/**
	 * 地区
	 */
	@JsonView(BaseView.class)
	private String region;

	/**
	 * 地址
	 */
	@JsonView(BaseView.class)
	private String address;

	/**
	 * 邮编
	 */
	@JsonView(BaseView.class)
	private String zipCode;

	/**
	 * 电话
	 */
	@JsonView(BaseView.class)
	private String phone;

	/**
	 * 备注
	 */
	@JsonView(BaseView.class)
	private String remark;

	/**
	 * 订单
	 */
	@TableField(exist = false)
	private String orderId;

	/**
	 * 订单发货项
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private List<OrderShippingItem> orderShippingItems = new ArrayList<>();


	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getOrderShippingItems() != null) {
			for (OrderShippingItem orderShippingItem : getOrderShippingItems()) {
				if (orderShippingItem != null && orderShippingItem.getQuantity() != null) {
					quantity += orderShippingItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取是否需要物流
	 * 
	 * @return 是否需要物流
	 */
	@Transient
	public boolean getIsDelivery() {
		return CollectionUtils.exists(getOrderShippingItems(), new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				OrderShippingItem orderShippingItem = (OrderShippingItem) object;
				return orderShippingItem != null && BooleanUtils.isTrue(orderShippingItem.getIsDelivery());
			}
		});
	}

	/**
	 * 设置配送方式
	 *
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	@Transient
	public void setShippingMethod(ShippingMethod shippingMethod) {
		setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
	}

	/**
	 * 设置快递公司
	 *
	 * @param expressCompany
	 *            快递公司
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	/**
	 * 设置地区
	 *
	 * @param region
	 *            地区
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * 设置地区
	 * 
	 * @param region
	 *            地区
	 */
	@Transient
	public void setRegion(Region region) {
		setRegion(region != null ? region.getFullName() : null);
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setSn(StringUtils.lowerCase(getSn()));
	}

}