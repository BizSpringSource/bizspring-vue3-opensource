package cn.bizspring.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.admin.service.RoleMenuService;
import cn.bizspring.cloud.upms.admin.service.RoleService;
import cn.bizspring.cloud.upms.common.entity.Role;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.common.entity.RoleMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "role", tags = "角色管理模块")
public class RoleController {
	private final RoleService roleService;
	private final RoleMenuService roleMenuService;

	/**
	 * 通过ID查询角色信息
	 *
	 * @param id ID
	 * @return 角色信息
	 */
	@ApiOperation(value = "通过ID查询角色信息")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:role:get')")
	public ResponseEntity getById(@PathVariable String id) {
		return R.ok(roleService.getById(id));
	}

	/**
	 * 添加角色
	 *
	 * @param role 角色信息
	 * @return ok、false
	 */
	@ApiOperation(value = "添加角色")
	@SysLog("添加角色")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:role:add')")
	public ResponseEntity save(@Valid @RequestBody Role role) {
		role.setIsEnabled(true);
		role.setDelFlag(false);
		role.setLockFlag(false);
		role.setTenantId(TenantContextHolder.getTenantId());
		role.setCreatedTime(new Date());
		return R.ok(roleService.save(role));
	}

	/**
	 * 修改角色
	 *
	 * @param role 角色信息
	 * @return ok/false
	 */
	@ApiOperation(value = "修改角色")
	@SysLog("修改角色")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:role:edit')")
	public ResponseEntity update(@Valid @RequestBody Role role) {
		if(!this.judeAdmin(role.getId())){
			return R.failed("管理员角色不能操作");
		}
		role.setUpdatedTime(new Date());
		return R.ok(roleService.updateById(role));
	}

	/**
	 * 删除角色
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "删除角色")
	@SysLog("删除角色")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:role:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		if(!this.judeAdmin(id)){
			return R.failed("管理员角色不能操作");
		}
		return R.ok(roleService.removeRoleById(id));
	}

	/**
	 * 获取角色列表
	 *
	 * @return 角色列表
	 */
	@ApiOperation(value = "获取角色列表")
	@GetMapping("/list")
	public List<Role> getList(Role role) {
		return roleService.list(Wrappers.query(role));
	}

	/**
	 * 分页查询角色信息
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@ApiOperation(value = "分页查询角色信息")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:role:index')")
	public ResponseEntity getRolePage(Page page) {
		return R.ok(roleService.page(page, Wrappers.emptyWrapper()));
	}

	/**
	 * 更新角色菜单
	 *
	 * @param roleMenu roleId  角色ID
	 * @param roleMenu menuId 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return ok、false
	 */
	@ApiOperation(value = "更新角色菜单")
	@SysLog("更新角色菜单")
	@PutMapping("/menu")
	@PreAuthorize("@ato.hasAuthority('sys:role:perm','sys:tenant:edit')")
	public ResponseEntity saveRoleMenus(@RequestBody RoleMenu roleMenu) {
		String roleId = roleMenu.getRoleId();
		String menuIds = roleMenu.getMenuId();
		if(StrUtil.isBlank(roleId) || StrUtil.isBlank(menuIds)){
			return R.ok();
		}
		if(!this.judeAdmin(roleId)){
			return R.failed("管理员角色不能操作");
		}
		Role role = roleService.getById(roleId);
		return R.ok(roleMenuService.saveRoleMenus(role.getRoleCode(), roleId, menuIds));
	}

	/**
	 * 校验是否是管理员角色
	 * @param roleId
	 * @return
	 */
	boolean judeAdmin(String roleId){
		Role role = roleService.getById(roleId);
		if(CommonEnum.ROLE_CODE_ADMIN.equals(role.getRoleCode())){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 更新租户管理员角色菜单
	 *
	 * @param roleMenu roleId  角色ID
	 * @param roleMenu menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return ok、false
	 */
	@ApiOperation(value = "更新租户管理员角色菜单")
	@SysLog("更新租户管理员角色菜单")
	@PutMapping("/menu/tenant")
	@PreAuthorize("@ato.hasAuthority('sys:tenant:edit')")
	public ResponseEntity saveRoleMenusTenant(@RequestBody RoleMenu roleMenu) {
		String tenantId = roleMenu.getTenantId();
		String roleId = roleMenu.getRoleId();
		String menuIds = roleMenu.getMenuId();
		if(StrUtil.isBlank(tenantId) || StrUtil.isBlank(menuIds) || StrUtil.isBlank(menuIds)){
			return R.ok();
		}
		TenantContextHolder.setTenantId(tenantId);
		Role role = roleService.getById(roleId);
		return R.ok(roleMenuService.saveRoleMenus(role.getRoleCode(), roleId, menuIds));
	}
}
