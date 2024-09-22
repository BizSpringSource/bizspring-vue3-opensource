package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bizspring.cloud.upms.admin.mapper.RoleMapper;
import cn.bizspring.cloud.upms.admin.mapper.RoleMenuMapper;
import cn.bizspring.cloud.upms.admin.service.RoleService;
import cn.bizspring.cloud.upms.common.entity.Role;
import cn.bizspring.cloud.upms.common.entity.RoleMenu;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends BizServiceImpl<Role> implements RoleService {
	private RoleMenuMapper roleMenuMapper;
	private RoleMapper roleMapper;

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public List findRoleIdsByUserId(String userId) {
		return roleMapper.listRoleIdsByUserId(userId);
	}

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.OAUTH_CLIENT_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeRoleById(String id) {
		roleMenuMapper.delete(Wrappers
			.<RoleMenu>update().lambda()
			.eq(RoleMenu::getRoleId, id));
		return this.removeById(id);
	}


	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public boolean save(Role role) {
		return super.save(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Role update(Role role) {
		return super.update(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Role update(Role role, String... ignoreProperties) {
		return super.update(role, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Role role) {
		super.delete(role);
	}


}
