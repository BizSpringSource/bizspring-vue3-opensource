package cn.bizspring.cloud.order.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 发货项
 * 
 */
@Data
@ApiModel(description = "发货项")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_order_shipping_item")
public class OrderShippingItem extends BizEntity<OrderShippingItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * SKU编号
	 */
	@JsonView(BaseView.class)
	private String sn;

	/**
	 * SKU名称
	 */
	@JsonView(BaseView.class)
	private String name;

	/**
	 * 数量
	 */
	@JsonView(BaseView.class)
	@NotNull
	@Min(1)
	private Integer quantity;

	/**
	 * 是否需要物流
	 */
	private Boolean isDelivery;

	/**
	 * SKU
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private String skuId;

	/**
	 * 订单发货
	 */
	@TableField(exist = false)
	private String orderShippingId;

	/**
	 * 规格
	 */
	@TableField(exist = false)
	private List<String> specifications = new ArrayList<>();

}