/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.common.entity.ConfigEditor;
import cn.bizspring.cloud.upms.admin.service.ConfigEditorService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 编辑器配置
 * @author www.bizspring.cn
 * @date 2023-03-11
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/configeditor")
@Api(value = "configeditor", tags = "编辑器配置管理")
public class ConfigEditorController {

    private final ConfigEditorService configEditorService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param configEditor 编辑器配置
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:index')")
    public ResponseEntity getPage(Page page, ConfigEditor configEditor) {
        return R.ok(configEditorService.page(page, Wrappers.query(configEditor)));
    }

    /**
     * 编辑器配置查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "编辑器配置查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:get')")
    public ResponseEntity getById(@PathVariable("id") String id) {
        return R.ok(configEditorService.getById(id));
    }

    /**
     * 编辑器配置新增
     * @param configEditor 编辑器配置
     * @return R
     */
	@ApiOperation(value = "编辑器配置新增")
    @SysLog("新增编辑器配置")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:add')")
    public ResponseEntity save(@RequestBody ConfigEditor configEditor) {
        return R.ok(configEditorService.save(configEditor));
    }

    /**
     * 编辑器配置修改
     * @param configEditor 编辑器配置
     * @return R
     */
	@ApiOperation(value = "编辑器配置修改")
    @SysLog("修改编辑器配置")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:edit')")
    public ResponseEntity updateById(@RequestBody ConfigEditor configEditor) {
		if(StrUtil.isNotBlank(configEditor.getId())){
			configEditorService.updateById(configEditor);
			return R.ok(configEditor);
		}else{
			configEditorService.save(configEditor);
			return R.ok(configEditor);
		}
    }

    /**
     * 编辑器配置删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "编辑器配置删除")
    @SysLog("删除编辑器配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configeditor:del')")
    public ResponseEntity removeById(@PathVariable String id) {
        return R.ok(configEditorService.removeById(id));
    }

	/**
	 * 查询编辑器配置
	 * @return R
	 */
	@ApiOperation(value = "查询编辑器配置")
	@GetMapping()
	public ResponseEntity get() {
		return R.ok(configEditorService.getOne(Wrappers.emptyWrapper()));
	}
}
