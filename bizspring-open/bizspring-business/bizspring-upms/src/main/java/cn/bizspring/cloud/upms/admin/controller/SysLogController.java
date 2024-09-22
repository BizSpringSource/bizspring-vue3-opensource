package cn.bizspring.cloud.upms.admin.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.admin.service.SysLogService;
import cn.bizspring.cloud.upms.common.entity.SysLog;
import cn.bizspring.cloud.upms.common.vo.PreLogVO;
import cn.bizspring.cloud.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Api(value = "log", tags = "日志管理模块")
public class SysLogController {
	private final SysLogService sysLogService;

	/**
	 * 简单分页查询
	 *
	 * @param page   分页对象
	 * @param sysLog 系统日志
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	public ResponseEntity getLogPage(Page page, SysLog sysLog) {
		return R.ok(sysLogService.page(page, Wrappers.query(sysLog)));
	}

	/**
	 * 删除日志
	 *
	 * @param id ID
	 * @return
	 */
	@ApiOperation(value = "删除日志")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:log:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		return R.ok(sysLogService.removeById(id));
	}

	/**
	 * 批量插入前端异常日志
	 *
	 * @param preLogVOList 日志实体
	 * @return ok/false
	 */
	@ApiOperation(value = "批量插入异常日志")
	@PostMapping("/logs")
	public ResponseEntity saveBatchLogs(@RequestBody List<PreLogVO> preLogVOList) {
		return R.ok(sysLogService.saveBatchLogs(preLogVOList));
	}
}
