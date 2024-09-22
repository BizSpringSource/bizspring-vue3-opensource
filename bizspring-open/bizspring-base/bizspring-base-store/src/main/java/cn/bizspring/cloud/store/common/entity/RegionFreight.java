package cn.bizspring.cloud.store.common.entity;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import cn.bizspring.cloud.upms.common.entity.Region;
import com.baomidou.mybatisplus.annotation.TableField;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity - 地区运费配置
 * 
 */
@Data
@ApiModel(description = "地区运费配置")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_region_freight")
public class RegionFreight extends BizEntity<RegionFreight> {

	private static final long serialVersionUID = 1L;

	/**
	 * 首重量
	 */
	@NotNull
	@Min(0)
	private Integer firstWeight;

	/**
	 * 续重量
	 */
	@NotNull
	@Min(1)
	private Integer continueWeight;

	/**
	 * 首重价格
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal firstPrice;

	/**
	 * 续重价格
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	private BigDecimal continuePrice;

	/**
	 * 配送方式
	 */
	@TableField(exist = false)
	private String shippingMethodId;
	@TableField(exist = false)
	private ShippingMethod shippingMethod;

	/**
	 * 店铺
	 */
	@TableField(exist = false)
	private String storeId;
	/**
	 * 地区
	 */
	@TableField(exist = false)
	private String regionId;
	@TableField(exist = false)
	private Region region;

}