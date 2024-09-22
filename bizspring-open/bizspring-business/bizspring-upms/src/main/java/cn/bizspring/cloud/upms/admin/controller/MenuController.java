package cn.bizspring.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.upms.admin.service.MenuService;
import cn.bizspring.cloud.upms.admin.service.RoleService;
import cn.bizspring.cloud.upms.common.dto.MenuTree;
import cn.bizspring.cloud.upms.common.entity.Menu;
import cn.bizspring.cloud.upms.common.entity.Role;
import cn.bizspring.cloud.upms.common.vo.MenuVO;
import cn.bizspring.cloud.upms.common.util.TreeUtil;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author www.bizspring.cn
 * @date 2023/06/13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "menu", tags = "菜单管理模块")
public class MenuController {
	private final MenuService menuService;
	private final RoleService roleService;

	/**
	 * 获取所有菜单和权限
	 *
	 * @return 树形菜单
	 */
	@ApiOperation(value = "返回树形菜单集合")
	@GetMapping(value = "/all/tree")
	public ResponseEntity getAllTree() {
		List<MenuTree> menuTreeList = TreeUtil.buildTree(menuService.list(Wrappers.<Menu>lambdaQuery()
				.orderByAsc(Menu::getSort)).stream()
				.collect(Collectors.toList()),CommonEnum.PARENT_ID);
		return R.ok(menuTreeList);
	}

	/**
	 * 获取当前用户的菜单
	 *
	 * @return 当前用户的树形菜单
	 */
	@ApiOperation(value = "返回当前用户的树形菜单集合")
	@GetMapping
	public ResponseEntity getUserMenu() {
		// 获取符合条件的菜单
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
				.forEach(roleId -> all.addAll(menuService.findMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
				.filter(menuVo -> CommonEnum.MENU.equals(menuVo.getType()))
				.map(MenuTree::new)
				.sorted(Comparator.comparingInt(MenuTree::getSort))
				.collect(Collectors.toList());
		return R.ok(TreeUtil.build(menuTreeList, CommonEnum.PARENT_ID));
	}

	/**
	 * 获取当前用户的菜单和权限
	 *
	 * @return 树形菜单
	 */
	@ApiOperation(value = "返回树形菜单集合")
	@GetMapping(value = "/tree")
	public ResponseEntity getTree() {
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
				.forEach(roleId -> all.addAll(menuService.findMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
				.map(MenuTree::new)
				.collect(Collectors.toList());
		return R.ok(TreeUtil.build(menuTreeList, CommonEnum.PARENT_ID));
	}

	/**
	 * 返回角色的菜单集合
	 *
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@ApiOperation(value = "返回角色的菜单集合")
	@GetMapping("/tree/{roleId}")
	public ResponseEntity getRoleTree(@PathVariable String roleId) {
		return R.ok(menuService.findMenuByRoleId(roleId)
				.stream()
				.map(MenuVO::getId)
				.collect(Collectors.toList()));
	}

	/**
	 * 通过ID查询菜单的详细信息
	 *
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@ApiOperation(value = "通过ID查询菜单的详细信息")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:menu:get')")
	public ResponseEntity getById(@PathVariable String id) {
		return R.ok(menuService.getById(id));
	}

	/**
	 * 新增菜单
	 *
	 * @param menu 菜单信息
	 * @return ok/false
	 */
	@ApiOperation(value = "新增菜单")
	@SysLog("新增菜单")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:menu:add')")
	public ResponseEntity save(@Valid @RequestBody Menu menu) {
		menu.setCreatedTime(new Date());
		menu.setTenantId(TenantContextHolder.getTenantId());
		menuService.saveMenu(menu);
		return R.ok();
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 * @return ok/false
	 */
	@ApiOperation(value = "删除菜单")
	@SysLog("删除菜单")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:menu:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		return menuService.removeMenuById(id);
	}

	/**
	 * 更新菜单
	 *
	 * @param menu
	 * @return
	 */
	@ApiOperation(value = "更新菜单")
	@SysLog("更新菜单")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:menu:edit')")
	public ResponseEntity update(@Valid @RequestBody Menu menu) {
		menu.setUpdatedTime(new Date());
		return R.ok(menuService.updateMenuById(menu));
	}

	/**
	 * 返回租户管理员角色的菜单集合
	 *
	 * @param tenantId 租户ID
	 * @return 属性集合
	 */
	@ApiOperation(value = "返回租户管理员角色的菜单集合")
	@GetMapping("/tree/tenant/{tenantId}")
	@PreAuthorize("@ato.hasAuthority('sys:tenant:edit')")
	public ResponseEntity getRoleTreeTenant(@PathVariable String tenantId) {
		TenantContextHolder.setTenantId(tenantId);
		//找出指定租户的管理员角色
		Role role = roleService.getOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleCode,CommonEnum.ROLE_CODE_ADMIN));
		List listMenuVO = menuService.findMenuByRoleId(role.getId())
				.stream()
				.map(MenuVO::getId)
				.collect(Collectors.toList());
		Map<String,Object> map = new HashMap<>();
		map.put("role",role);
		map.put("listMenuVO",listMenuVO);
		//菜单集合
		return R.ok(map);
	}
}
