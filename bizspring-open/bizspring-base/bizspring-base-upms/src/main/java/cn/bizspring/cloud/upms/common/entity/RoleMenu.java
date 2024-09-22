package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_role_menu")
public class RoleMenu extends BizEntity<RoleMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 菜单ID
	 */
	private String menuId;

}
