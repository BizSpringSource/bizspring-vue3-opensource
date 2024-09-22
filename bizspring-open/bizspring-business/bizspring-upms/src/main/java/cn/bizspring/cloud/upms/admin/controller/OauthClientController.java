package cn.bizspring.cloud.upms.admin.controller;

import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.admin.service.OauthClientService;
import cn.bizspring.cloud.upms.common.entity.OauthClient;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

/**
 * 终端控制器
 *
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "client", tags = "客户端管理模块")
public class OauthClientController {
	private final OauthClientService sysOauthClientService;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return OauthClient
	 */
	@ApiOperation(value = "通过ID查询")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:client:get')")
	public ResponseEntity getById(@PathVariable String id) {
		return R.ok(sysOauthClientService.getById(id));
	}


	/**
	 * 简单分页查询
	 *
	 * @param page                  分页对象
	 * @param sysOauthClient 系统终端
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:client:index')")
	public ResponseEntity getOauthClientDetailsPage(Page page, OauthClient sysOauthClient) {
		return R.ok(sysOauthClientService.page(page, Wrappers.query(sysOauthClient)));
	}

	/**
	 * 添加
	 *
	 * @param sysOauthClient 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "添加")
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:client:add')")
	public ResponseEntity add(@Valid @RequestBody OauthClient sysOauthClient) {
		sysOauthClient.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(sysOauthClientService.save(sysOauthClient));
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@ApiOperation(value = "删除")
	@SysLog("删除终端")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:client:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		return R.ok(sysOauthClientService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 *
	 * @param sysOauthClient 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "编辑")
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:client:edit')")
	public ResponseEntity update(@Valid @RequestBody OauthClient sysOauthClient) {
		return R.ok(sysOauthClientService.updateClientDetailsById(sysOauthClient));
	}
}
