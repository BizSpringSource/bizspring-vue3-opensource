package cn.bizspring.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.admin.service.DictValueService;
import cn.bizspring.cloud.upms.admin.service.DictService;
import cn.bizspring.cloud.upms.common.entity.Dict;
import cn.bizspring.cloud.upms.common.entity.DictValue;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author www.bizspring.cn
 * Copyright (C) 2023-2050
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "dict", tags = "字典管理模块")
public class DictController {
	private final DictService dictService;
	private final DictValueService dictValueService;

	/**
	 * 通过ID查询字典信息
	 *
	 * @param id ID
	 * @return 字典信息
	 */
	@ApiOperation(value = "通过ID查询字典信息")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:dict:get')")
	public ResponseEntity getById(@PathVariable String id) {
		return R.ok(dictService.getById(id));
	}

	/**
	 * 分页查询字典信息
	 *
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@ApiOperation(value = "分页列表")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:dict:index')")
	public ResponseEntity<IPage> getDictPage(Page page, Dict dict) {
		return R.ok(dictService.page(page, Wrappers.query(dict)));
	}

	/**
	 * 查询所有字典信息
	 * @param dict 对象
	 * @return list对象
	 */
	@ApiOperation(value = "查询所有字典信息")
	@GetMapping("/all")
	@PreAuthorize("@ato.hasAuthority('sys:dict:index')")
	public ResponseEntity getAll(Dict dict) {
		return R.ok(dictService.list(Wrappers.query(dict)));
	}

	/**
	 * 通过字典类型查找字典
	 *
	 * @param type 类型
	 * @return 同类型字典
	 */
	@ApiOperation(value = "通过字典类型查找")
	@GetMapping("/type/{type}")
	public ResponseEntity getDictByType(@PathVariable String type) {
		return R.ok(dictValueService.list(Wrappers
				.<DictValue>query().lambda()
				.eq(DictValue::getType, type)));
	}

	/**
	 * 添加字典
	 *
	 * @param dict 字典信息
	 * @return ok、false
	 */
	@ApiOperation(value = "添加字典")
	@SysLog("添加字典")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:dict:add')")
	public ResponseEntity save(@Valid @RequestBody Dict dict) {
		dict.setCreatedTime(new Date());
		return R.ok(dictService.save(dict));
	}

	/**
	 * 删除字典，并且清除字典缓存
	 *
	 * @param id ID
	 * @return R
	 */
	@ApiOperation(value = "删除字典，并且清除字典缓存")
	@SysLog("删除字典")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:dict:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		return dictService.removeDict(id);
	}

	/**
	 * 修改字典
	 *
	 * @param dict 字典信息
	 * @return ok/false
	 */
	@ApiOperation(value = "修改字典")
	@PutMapping
	@SysLog("修改字典")
	@PreAuthorize("@ato.hasAuthority('sys:dict:edit')")
	public ResponseEntity updateById(@Valid @RequestBody Dict dict) {
		dict.setUpdatedTime(new Date());
		return dictService.updateDict(dict);
	}

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param dictValue 字典项
	 * @return
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/item/page")
	public ResponseEntity getDictItemPage(Page page, DictValue dictValue) {
		return R.ok(dictValueService.page(page, Wrappers.query(dictValue)));
	}


	/**
	 * 通过id查询字典项
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询字典项")
	@GetMapping("/item/{id}")
	public ResponseEntity getDictItemById(@PathVariable("id") String id) {
		return R.ok(dictValueService.getById(id));
	}

	/**
	 * 新增字典项
	 *
	 * @param dictValue 字典项
	 * @return R
	 */
	@ApiOperation(value = "新增字典项")
	@SysLog("新增字典项")
	@PostMapping("/item")
	@CacheEvict(value = CacheEnum.DICT_CACHE, allEntries = true)
	public ResponseEntity save(@RequestBody DictValue dictValue) {
		dictValue.setCreatedTime(new Date());
		return R.ok(dictValueService.save(dictValue));
	}

	/**
	 * 修改字典项
	 *
	 * @param dictValue 字典项
	 * @return R
	 */
	@ApiOperation(value = "修改字典项")
	@SysLog("修改字典项")
	@PutMapping("/item")
	public ResponseEntity updateById(@RequestBody DictValue dictValue) {
		dictValue.setUpdatedTime(new Date());
		return dictValueService.updateDictItem(dictValue);
	}

	/**
	 * 通过id删除字典项
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除字典项")
	@SysLog("删除字典项")
	@DeleteMapping("/item/{id}")
	public ResponseEntity removeDictItemById(@PathVariable String id) {
		return dictValueService.removeDictItem(id);
	}
}
