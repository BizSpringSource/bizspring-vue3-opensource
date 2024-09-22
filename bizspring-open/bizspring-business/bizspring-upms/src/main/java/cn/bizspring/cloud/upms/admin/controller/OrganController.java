package cn.bizspring.cloud.upms.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.bizspring.cloud.upms.admin.service.OrganService;
import cn.bizspring.cloud.upms.common.entity.Organ;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author www.bizspring.cn
 * @since 2023-01-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/organ")
@Api(value = "organ", tags = "机构管理模块")
public class OrganController {
	private final OrganService organService;

	/**
	 * 通过ID查询
	 *
	 * @param id ID
	 * @return Organ
	 */
	@ApiOperation(value = "通过ID查询")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:organ:get')")
	public ResponseEntity getById(@PathVariable String id) {
		return R.ok(organService.getById(id));
	}

	/**
	 * list查询
	 * @param organ
	 * @return
	 */
	@ApiOperation(value = "list查询")
	@GetMapping("/list")
	public ResponseEntity getList(Organ organ) {
		return R.ok(organService.list(Wrappers.query(organ)));
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@ApiOperation(value = "返回树形菜单集合")
	@GetMapping(value = "/tree")
	public ResponseEntity getTree() {
		return R.ok(organService.selectTree());
	}

	/**
	 * 添加
	 *
	 * @param organ 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "添加")
	@SysLog("添加机构")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:organ:add')")
	public ResponseEntity save(@Valid @RequestBody Organ organ) {
		try {
			if(CommonEnum.PARENT_ID.equals(organ.getParentId())){
				throw new Exception("父级节点不能为0");
			}
			return R.ok(organService.saveOrgan(organ));
		} catch (DuplicateKeyException e){
			if(e.getMessage().contains("uk_tenant_id_code")){
				return R.failed("机构编码已存在");
			}else{
				return R.failed(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}

	/**
	 * 删除
	 *
	 * @param id ID
	 * @return ok/false
	 */
	@ApiOperation(value = "删除")
	@SysLog("删除机构")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:organ:del')")
	public ResponseEntity removeById(@PathVariable String id) {
		Organ organ2 = organService.getById(id);
		if(CommonEnum.PARENT_ID.equals(organ2.getParentId())){
			return R.failed("总机构（租户机构）不能删除");
		}
		return R.ok(organService.removeOrganById(id));
	}

	/**
	 * 编辑
	 *
	 * @param organ 实体
	 * @return ok/false
	 */
	@ApiOperation(value = "编辑")
	@SysLog("编辑机构")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:organ:edit')")
	public ResponseEntity update(@Valid @RequestBody Organ organ) {
		try {
			Organ organ2 = organService.getById(organ.getId());
			if(CommonEnum.PARENT_ID.equals(organ2.getParentId())){
				organ.setParentId(CommonEnum.PARENT_ID);
			}
			return R.ok(organService.updateOrganById(organ));
		} catch (DuplicateKeyException e){
			if(e.getMessage().contains("uk_tenant_id_code")){
				return R.failed("机构编码已存在");
			}else{
				return R.failed(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return R.failed(e.getMessage());
		}
	}
}
