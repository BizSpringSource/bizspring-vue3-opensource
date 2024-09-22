package cn.bizspring.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("biz_user_role")
public class UserRole extends Model<UserRole> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 租户ID
	 */
	private String tenantId;
}
