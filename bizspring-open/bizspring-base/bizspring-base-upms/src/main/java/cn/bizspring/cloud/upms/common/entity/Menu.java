package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "菜单")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_menu")
public class Menu extends BizEntity<Menu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	@NotNull(message = "菜单名称不能为空")
	private String name;
	/**
	 * 菜单权限标识
	 */
	@ApiModelProperty(value = "菜单权限标识")
	private String permission;
	/**
	 * 父菜单ID
	 */
	@ApiModelProperty(value = "父菜单ID")
	@NotNull(message = "菜单父ID不能为空")
	private String parentId;
	/**
	 * 图标
	 */
	@ApiModelProperty(value = "图标")
	private String icon;
	/**
	 * VUE页面
	 */
	@ApiModelProperty(value = "VUE页面")
	private String component;
	/**
	 * 排序值
	 */
	@ApiModelProperty(value = "排序值")
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	@NotNull(message = "菜单类型 （0菜单 1按钮）")
	private String type;
	/**
	 * 路由缓存
	 */
	@ApiModelProperty(value = "路由缓存")
	private String keepAlive;
	/**
	 * 前端URL
	 */
	@ApiModelProperty(value = "前端URL")
	private String path;

	@ApiModelProperty(value = "	角色Id")
	@TableField(exist = false)
	private String roleId;

}
