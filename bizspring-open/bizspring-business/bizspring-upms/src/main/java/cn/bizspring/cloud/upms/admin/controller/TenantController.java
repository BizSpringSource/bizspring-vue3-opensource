package cn.bizspring.cloud.upms.admin.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.TenantService;
import cn.bizspring.cloud.upms.common.entity.Tenant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;

/**
 * <p>
 * 租户管理
 * </p>
 *
 * @author www.bizspring.cn
 * @since 2023-01-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "tenant", tags = "租户管理模块")
public class TenantController {
	private final TenantService tenantService;

	/**
	 * 分页查询
	 *
	 * @param page    参数集
	 * @param tenant 查询参数列表
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:tenant:index')")
	public ResponseEntity getUserPage(Page page, Tenant tenant) {
		tenant.setParentId(CommonEnum.PARENT_ID);
		return R.ok(tenantService.page(page, Wrappers.query(tenant)));
	}

	/**
	 * list查询
	 * @param tenant
	 * @return
	 */
	@ApiOperation(value = "list查询")
	@GetMapping("/list")
	public ResponseEntity getList(Tenant tenant) {
		tenant.setParentId(CommonEnum.PARENT_ID);
		return R.ok(tenantService.list(Wrappers.query(tenant)));
	}

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return Systenant
	 */
	@ApiOperation(value = "通过ID查询")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:tenant:get')")
	public ResponseEntity getById(@PathVariable String id) {
		TenantContextHolder.setTenantId(id);
		return R.ok(tenantService.getById(id));
	}

	/**
	 * 添加
	 *
	 * @param tenant 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "添加")
	@SysLog("添加租户")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:tenant:add')")
	public ResponseEntity save(@Valid @RequestBody Tenant tenant) {
		String id = String.valueOf(IdUtil.getSnowflake(1,2).nextId());
		TenantContextHolder.setTenantId(id);
		tenant.setId(id);
		tenant.setTenantId(id);
		tenant.setParentId(CommonEnum.PARENT_ID);
		return R.ok(tenantService.save(tenant));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@ApiOperation(value = "删除")
	@SysLog("删除租户")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:tenant:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		TenantContextHolder.setTenantId(id);
		return R.ok(tenantService.removeById(id));
	}

	/**
	 * 编辑
	 *
	 * @param tenant 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "编辑")
	@SysLog("编辑租户")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:tenant:edit')")
	public ResponseEntity update(@Valid @RequestBody Tenant tenant) {
		TenantContextHolder.setTenantId(tenant.getId());
		tenant.setParentId(CommonEnum.PARENT_ID);
		return R.ok(tenantService.updateById(tenant));
	}

	/**
	 * list查询服务间调用
	 * @return
	 */
	@ApiOperation(value = "list查询服务间调用")
	@Inside
	@GetMapping("/inside/list")
	public ResponseEntity getListInside() {
		Tenant tenant = new Tenant();
		tenant.setParentId(CommonEnum.PARENT_ID);
		return R.ok(tenantService.list(Wrappers.query(tenant)));
	}
}
