package cn.bizspring.cloud.upms.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.upms.admin.mapper.TenantMapper;
import cn.bizspring.cloud.upms.admin.service.OrganRelationService;
import cn.bizspring.cloud.upms.admin.service.RoleService;
import cn.bizspring.cloud.upms.admin.service.TenantService;
import cn.bizspring.cloud.upms.admin.service.UserRoleService;
import cn.bizspring.cloud.upms.admin.service.UserService;
import cn.bizspring.cloud.upms.common.entity.Organ;
import cn.bizspring.cloud.upms.common.entity.Role;
import cn.bizspring.cloud.upms.common.entity.Tenant;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 租户管理
 *
 * @author
 */
@Service
@AllArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

	private final OrganRelationService organRelationService;
	private final UserService userService;
	private final RoleService roleService;
	private final UserRoleService userRoleService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(Tenant entity) {
		baseMapper.insert(entity);
		//新建机构关联
		Organ organ = new Organ();
		BeanUtil.copyProperties(entity,organ);
		organRelationService.insertOrganRelation(organ);
		//新建用户
		User user = new User();
		user.setOrganId(organ.getId());
		user.setUsername(entity.getUsername());
		user.setPassword(ENCODER.encode(entity.getPassword()));
		userService.save(user);
		//新建角色
		Role role = new Role();
		role.setRoleName("管理员");
		role.setRoleCode(CommonEnum.ROLE_CODE_ADMIN);
		role.setRoleDesc(entity.getName()+"管理员");
		role.setDsType(CommonEnum.DS_TYPE_0);
		roleService.save(role);
		//新建用户角色
		UserRole userRole = new UserRole();
		userRole.setRoleId(role.getId());
		userRole.setUserId(user.getId());
		userRoleService.save(userRole);
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		baseMapper.deleteTenantById(id);
		return Boolean.TRUE;
	}
}
