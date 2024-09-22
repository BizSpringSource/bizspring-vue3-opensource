package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.service.MenuService;
import cn.bizspring.cloud.upms.common.entity.Menu;
import cn.bizspring.cloud.upms.common.entity.RoleMenu;
import cn.bizspring.cloud.upms.common.vo.MenuVO;
import cn.bizspring.cloud.upms.admin.mapper.MenuMapper;
import cn.bizspring.cloud.upms.admin.mapper.RoleMenuMapper;
import cn.bizspring.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
	private final RoleMenuMapper roleMenuMapper;

	@Override
	@Cacheable(value = CacheEnum.MENU_CACHE, key = "#roleId  + '_menu'", unless = "#result == null")
	public List<MenuVO> findMenuByRoleId(String roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheEnum.MENU_CACHE, allEntries = true)
	public ResponseEntity removeMenuById(String id) {
		// 查询父节点为当前节点的节点
		List<Menu> menuList = this.list(Wrappers.<Menu>query()
				.lambda().eq(Menu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return R.failed("菜单含有下级不能删除");
		}
		//删除角色菜单关联
		roleMenuMapper.delete(Wrappers.<RoleMenu>query()
						.lambda().eq(RoleMenu::getMenuId, id));

		//删除当前菜单及其子菜单
		return R.ok(this.removeById(id));
	}

	@Override
	@CacheEvict(value = CacheEnum.MENU_CACHE, allEntries = true)
	public Boolean updateMenuById(Menu menu) {
		return this.updateById(menu);
	}

	@Override
	@CacheEvict(value = CacheEnum.MENU_CACHE, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public void saveMenu(Menu entity) {
		String roleId = entity.getRoleId();
		super.save(entity);
		if(StrUtil.isNotBlank(roleId)){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(entity.getId());
			roleMenu.setTenantId(TenantContextHolder.getTenantId());
			roleMenuMapper.insert(roleMenu);
		}
	}
}
