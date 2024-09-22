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
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import cn.bizspring.cloud.upms.admin.service.ConfigStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;

/**
 * 存储配置
 * @author www.bizspring.cn
 * @date 2023-03-05
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/configstorage")
@Api(value = "configstorage", tags = "存储配置管理")
public class ConfigStorageController {

    private final ConfigStorageService configStorageService;

    /**
     * 分页列表
     * @param page 分页对象
     * @param configStorage 存储配置
     * @return
     */
	@ApiOperation(value = "分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:index')")
    public ResponseEntity getPage(Page page, ConfigStorage configStorage) {
        return R.ok(configStorageService.page(page, Wrappers.query(configStorage)));
    }

    /**
     * 存储配置查询
     * @param id
     * @return R
     */
	@ApiOperation(value = "存储配置查询")
    @GetMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:get')")
    public ResponseEntity getById(@PathVariable("id") String id) {
        return R.ok(configStorageService.getById(id));
    }

    /**
     * 存储配置新增
     * @param configStorage 存储配置
     * @return R
     */
	@ApiOperation(value = "存储配置新增")
    @SysLog("新增存储配置")
    @PostMapping
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:add')")
    public ResponseEntity save(@RequestBody ConfigStorage configStorage) {
        return R.ok(configStorageService.save(configStorage));
    }

    /**
     * 存储配置修改
     * @param configStorage 存储配置
     * @return R
     */
	@ApiOperation(value = "存储配置修改")
    @SysLog("修改存储配置")
    @PutMapping
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:edit')")
    public ResponseEntity updateById(@RequestBody ConfigStorage configStorage) {
		if(StrUtil.isNotBlank(configStorage.getId())){
			configStorageService.updateById(configStorage);
			return R.ok(configStorage);
		}else{
			configStorageService.save(configStorage);
			return R.ok(configStorage);
		}
    }

    /**
     * 存储配置删除
     * @param id
     * @return R
     */
	@ApiOperation(value = "存储配置删除")
    @SysLog("删除存储配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ato.hasAuthority('sys:configstorage:del')")
    public ResponseEntity removeById(@PathVariable String id) {
        return R.ok(configStorageService.removeById(id));
    }

	/**
	 * 查询存储配置
	 * @return R
	 */
	@ApiOperation(value = "查询存储配置")
	@GetMapping()
	@PreAuthorize("@ato.hasAuthority('sys:configstorage:get')")
	public ResponseEntity get() {
		return R.ok(configStorageService.getOne(Wrappers.emptyWrapper()));
	}
}
