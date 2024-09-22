package cn.bizspring.cloud.upms.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.admin.service.RegionService;
import cn.bizspring.cloud.upms.common.entity.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller - 地区
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "region", tags = "管理 - 地区")
@RestController("adminRegionController")
@RequestMapping("/admin/region")
public class RegionController extends BizController {

	private RegionService regionService;

	/**
	 * 添加
	 */
	@SysLog("添加")
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add(String name) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("parent",regionService.findChildren(null,true,null));
		return R.ok(data);
	}


	/**
	 * 添加
	 */
	@SysLog("添加")
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/loadChildsRegion")
	public ResponseEntity loadChildsRegion(String id) {
		if(null!=id){
			Region region=new Region();
			region.setId(id);
			List<Region> regions = regionService.findChildren(region,true,null);
			return  R.ok(regions);
		}
		return null;
	}

	/**
	 * 保存
	 */
	@SysLog("保存")
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody Region region, String parentId) {
		region.setParentId(parentId);
		if (!isValid(region)) {
			return R.invalidEntity();
		}
		region.setFullName(null);
		region.setTreePath(null);
		region.setLevel(null);
		regionService.save(region);
		return R.OK;
	}

	/**
	 * 编辑
	 */
	@SysLog("编辑")
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String id) {
		return R.ok(regionService.find(id));
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody Region region) {
		if (!isValid(region)) {
			return R.INVALID_ENTITY;
		}
		regionService.update(region, "fullName", "treePath", "level", "parent", "children", "members", "addresss", "orders", "shippingAddresss", "regionFreights");
		return R.OK;
	}

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(String name) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("content", regionService.findRoots(name));
		return R.ok(data);
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String id) {
		Region region = regionService.find(id);
		List<Region> children = region.getChildren();
		if (children != null && !children.isEmpty()) {
			return R.invalidEntity("存在下级分类，无法删除");
		}
		regionService.delete(id);
		return R.OK;
	}

}