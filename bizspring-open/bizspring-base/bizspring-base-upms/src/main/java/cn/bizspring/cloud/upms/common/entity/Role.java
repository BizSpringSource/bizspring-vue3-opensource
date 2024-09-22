package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "角色")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_roles")
public class Role extends BizEntity<Role> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色名称")
	@NotNull(message = "角色名称 不能为空")
	private String roleName;

	@ApiModelProperty(value = "角色标识")
	@NotNull(message = "角色标识 不能为空")
	private String roleCode;

	@ApiModelProperty(value = "角色描述")
	@NotNull(message = "角色描述 不能为空")
	private String roleDesc;

	@ApiModelProperty(value = "数据权限")
	@NotNull(message = "数据权限类型 不能为空")
	private Integer dsType;

	private String dsScope;

	/**
	 * 启用标记
	 */
	@ApiModelProperty(value = "是否启用")
	private Boolean isEnabled;

	/**
	 * 是否内置
	 */
	private Boolean isSystem=false;

	protected Serializable pkVal() {
		return getId();
	}

}
