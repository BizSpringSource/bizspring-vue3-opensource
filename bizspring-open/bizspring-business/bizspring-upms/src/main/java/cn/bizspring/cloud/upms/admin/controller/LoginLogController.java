/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.common.entity.LoginLog;
import cn.bizspring.cloud.upms.admin.service.LoginLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

/**
 * 登录日志
 * @author www.bizspring.cn
 * @date 2023-2050
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/loginlog")
@Api(value = "loginlog", tags = "登录日志管理")
public class LoginLogController {

    private final LoginLogService loginLogService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param loginLog 登录日志
     * @return
     */
    @ApiOperation(value = "分页列表")
    @GetMapping("/page")
    public ResponseEntity getPage(Page page, LoginLog loginLog) {
        return R.ok(loginLogService.page(page, Wrappers.query(loginLog)));
    }

    /**
     * 登录日志查询
     * @param id
     * @return R
     */
    @ApiOperation(value = "登录日志查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:loginlog:get')")
    public ResponseEntity getById(@PathVariable("id") String id) {
        return R.ok(loginLogService.getById(id));
    }

    /**
     * 登录日志新增
     * @param loginLog 登录日志
     * @return R
     */
    @ApiOperation(value = "登录日志新增")
	@Inside
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody LoginLog loginLog) {
    	loginLog.setCreatedTime(new Date());
        return R.ok(loginLogService.save(loginLog));
    }

    /**
     * 登录日志修改
     * @param loginLog 登录日志
     * @return R
     */
    @ApiOperation(value = "登录日志修改")
    @SysLog("修改登录日志")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:loginlog:edit')")
    public ResponseEntity updateById(@RequestBody LoginLog loginLog) {
		loginLog.setUpdatedTime(new Date());
        return R.ok(loginLogService.updateById(loginLog));
    }

    /**
     * 登录日志删除
     * @param id
     * @return R
     */
    @ApiOperation(value = "登录日志删除")
    @SysLog("删除登录日志")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:loginlog:del')")
    public ResponseEntity removeById(@PathVariable String id) {
        return R.ok(loginLogService.removeById(id));
    }

}
