package cn.bizspring.cloud.upms.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.common.entity.RoleMenu;
import cn.bizspring.cloud.upms.admin.mapper.RoleMenuMapper;
import cn.bizspring.cloud.upms.admin.service.RoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
	private final CacheManager cacheManager;

	/**
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheEnum.MENU_CACHE, key = "#roleId + '_menu'")
	public Boolean saveRoleMenus(String role, String roleId, String menuIds) {
		this.remove(Wrappers.<RoleMenu>query().lambda()
				.eq(RoleMenu::getRoleId, roleId));

		if (StrUtil.isBlank(menuIds)) {
			return Boolean.TRUE;
		}
		List<RoleMenu> roleMenuList = Arrays
				.stream(menuIds.split(","))
				.map(menuId -> {
					RoleMenu roleMenu = new RoleMenu();
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(menuId);
					return roleMenu;
				}).collect(Collectors.toList());

		//清空userinfo
		cacheManager.getCache(CacheEnum.USER_CACHE).clear();
		return this.saveBatch(roleMenuList);
	}
}
