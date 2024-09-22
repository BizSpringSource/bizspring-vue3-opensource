package cn.bizspring.cloud.goods.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.goods.admin.service.BrandService;
import cn.bizspring.cloud.goods.admin.service.GoodsCategoryService;
import cn.bizspring.cloud.goods.admin.service.GoodsService;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller - 商品分类
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods_category", tags = "管理 - 商品分类")
@RestController("adminGoodsCategoryController")
@RequestMapping("/admin/goods_category")
public class GoodsCategoryController extends BizController {

	private GoodsCategoryService goodsCategoryService;
	private GoodsService goodsService;
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
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		data.put("brands", brandService.findAll());
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@SysLog("保存")
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody GoodsCategory goodsCategory) {
		if (!isValid(goodsCategory)) {
			return R.INVALID_ENTITY;
		}
		goodsCategory.setTreePath(null);
		goodsCategory.setLevel(null);
		goodsCategoryService.save(goodsCategory);
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
		GoodsCategory goodsCategory = goodsCategoryService.find(id);
		if(CommonUtils.isNotNull(goodsCategory)){
			Set<String>  bands = goodsCategoryService.findGoodsCategoryBrands(id);
			if(CommonUtils.isNotNull(bands)){ goodsCategory.setBrands(bands); }
		}
		Map<String, Object> data = new HashMap<>(2);
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		data.put("brands", brandService.findAll());
		data.put("data", goodsCategory);
		data.put("children", goodsCategoryService.findChildren(goodsCategory, true, null));
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody GoodsCategory goodsCategory) {
		if (!isValid(goodsCategory)) {
			return R.INVALID_ENTITY;
		}
		if (null!=goodsCategory.getParentId() ) {
			GoodsCategory parent = goodsCategoryService.find(goodsCategory.getParentId());
			if (parent.equals(goodsCategory)) {
				return R.INVALID_ENTITY;
			}
			List<GoodsCategory> children = goodsCategoryService.findChildren(parent, true, null);
			if (children != null && children.contains(parent)) {
				return R.INVALID_ENTITY;
			}
		}
		goodsCategoryService.update(goodsCategory, "stores", "managementClassifications", "treePath", "level", "children", "goods", "parameters", "propertys", "specifications");
		return R.OK;
	}

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list() {
		return R.ok(goodsCategoryService.findTree());
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		for (String id: ids) {
			GoodsCategory goodsCategory = goodsCategoryService.find(id);
			if (goodsCategory == null) {
				return R.INVALID_ENTITY;
			}
			List<GoodsCategory> childrens= goodsCategoryService.findChildren(goodsCategory.getId(),true,null,false);
			if (childrens != null && !childrens.isEmpty()) {
				return R.invalidEntity("存在下级分类，无法删除");
			}
			Goods goods = new Goods();
			goods.setGoodsCategoryId(goodsCategory.getId());
			List<Goods> goodss = goodsService.findAllByEntity(goods);
			if (goodss != null && !goodss.isEmpty()) {
				return R.invalidEntity("存在下级商品，无法删除");
			}
		}
		goodsCategoryService.delete(ids);
		return R.OK;
	}

}