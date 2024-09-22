package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.UserRoleMapper;
import cn.bizspring.cloud.upms.admin.service.UserRoleService;
import cn.bizspring.cloud.upms.common.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	/**
	 * 根据用户Id删除该用户的角色关系
	 */
	@Override
	public Boolean deleteByUserId(String userId) {
		return baseMapper.deleteByUserId(userId);
	}
}
