package cn.bizspring.cloud.goods.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.goods.admin.service.BrandService;
import cn.bizspring.cloud.goods.common.entity.Brand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 品牌
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "brand", tags = "管理 - 品牌")
@RestController("adminBrandController")
@RequestMapping("/admin/brand")
public class BrandController extends BizController {

	private BrandService brandService;

	/**
	 * 添加
	 */
	@SysLog("添加")
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Map<String, Object> data = new HashMap<>(2);
		data.put("types", Brand.Type.values());
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@SysLog("保存")
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody Brand brand) {
		if (!isValid(brand)) {
			return R.INVALID_ENTITY;
		}
		if (Brand.Type.TEXT.equals(brand.getType())) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return R.INVALID_ENTITY;
		}
		brandService.save(brand);
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
		Map<String, Object> data = new HashMap<>(2);
		data.put("types", Brand.Type.values());
		data.put("data", brandService.find(id));
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody Brand brand) {
		if (!isValid(brand)) {
			return R.INVALID_ENTITY;
		}
		if (Brand.Type.TEXT.equals(brand.getType())) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return R.INVALID_ENTITY;
		}
		brandService.update(brand, "goodss", "goodsCategories");
		return R.OK;
	}

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable,String name,String url) {
		Brand brand=new Brand();
		if(StringUtils.isNotEmpty(name)){
			brand.setName(name);
		}
		if(StringUtils.isNotEmpty(url)){
			brand.setUrl(url);
		}
		return R.ok(brandService.findPageByEntity(pageable,brand));
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		brandService.delete(ids);
		return R.OK;
	}

}