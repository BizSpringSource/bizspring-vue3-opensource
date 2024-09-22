package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "机构管理")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_organ")
public class Organ extends BizEntity<Organ> {

	private static final long serialVersionUID = 1L;

	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	@NotNull(message = "机构名称不能为空")
	private String name;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	private Integer sort;
	/**
	 * 机构编码
	 */
	@ApiModelProperty(value = "排序")
	@NotNull(message = "机构编码不能为空")
	private String code;
	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "机构类型")
	@NotNull(message = "机构类型不能为空")
	private String type;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String phone;
	/**
	 * 传真
	 */
	@ApiModelProperty(value = "传真")
	private String fax;
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String address;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 父类ID
	 */
	@ApiModelProperty(value = "父类ID")
	private String parentId;

}
