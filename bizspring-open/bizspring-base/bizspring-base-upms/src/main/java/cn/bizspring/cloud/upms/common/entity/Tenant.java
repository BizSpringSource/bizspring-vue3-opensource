package cn.bizspring.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 租户机构管理
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "租户管理")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_organ")
public class Tenant extends Model<Tenant> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "PK")
	@TableId(type = IdType.INPUT)
	private String id;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "租户名称")
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
	@ApiModelProperty(value = "租户编码")
	@NotNull(message = "机构编码不能为空")
	private String code;
	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "租户类型")
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
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createdTime;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updatedTime;

	@ApiModelProperty(value = "parentId")
	private String parentId;

	/**
	 * 状态 0:正常，9:冻结
	 */
	@ApiModelProperty(value = "状态 0:正常，9:冻结")
	private String status;

	@TableField(exist = false)
	private String username;

	@TableField(exist = false)
	private String password;
}
