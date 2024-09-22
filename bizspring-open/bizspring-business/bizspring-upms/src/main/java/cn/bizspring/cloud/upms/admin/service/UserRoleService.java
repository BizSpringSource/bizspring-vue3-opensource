package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.UserRole;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author
 */
public interface UserRoleService extends IService<UserRole> {

	/**
	 * 根据用户Id删除该用户的角色关系
	 */
	Boolean deleteByUserId(String userId);
}
