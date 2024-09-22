package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Entity - 收货地址
 * 
 */
@Data
@ApiModel(description = "收货地址")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_address")
public class Address extends BizEntity<Address> {

	private static final long serialVersionUID = 1L;

	/**
	 * 收货地址最大保存数
	 */
	public static final Integer MAXIMUM_NUMBER_SAVE_ADDRESSES = 10;

	/**
	 * 收货人
	 */
	@JsonView(BaseView.class)
	@NotEmpty
	private String consignee;

	/**
	 * 地区名称
	 */
	@JsonView(BaseView.class)
	private String regionName;

	/**
	 * 地址
	 */
	@JsonView(BaseView.class)
	@NotEmpty
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
	@NotEmpty
	private String phone;

	/**
	 * 是否默认
	 */
	@JsonView(BaseView.class)
	@NotNull
	private Boolean isDefault;

	/**
	 * 地区
	 */
	@TableField(exist = false)
	private String regionId;

	/**
	 * 会员
	 */
	@TableField(exist = false)
	private String memberId;

}