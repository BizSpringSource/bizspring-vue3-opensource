package cn.bizspring.cloud.store.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.bizspring.cloud.common.core.entity.SortEntity;
/**
 * Entity - 配送方式
 * 
 */
@Data
@ApiModel(description = "配送方式")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_shipping_method")
public class ShippingMethod extends SortEntity<ShippingMethod> {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 介绍
	 */
	private String description;

	/**
	 * 默认快递公司
	 */
	@TableField(exist = false)
	private String defaultExpressCompanyId;

	@TableField(exist = false)
	private DefaultFreight defaultFreight;

	/**
	 * 支持支付方式
	 */
	@TableField(exist = false)
	private Set<PaymentPattern> paymentPatterns = new HashSet<>();

}