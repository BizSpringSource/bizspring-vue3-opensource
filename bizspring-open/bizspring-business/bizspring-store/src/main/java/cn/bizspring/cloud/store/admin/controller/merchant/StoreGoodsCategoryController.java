package cn.bizspring.cloud.store.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.store.admin.service.StoreGoodsCategoryService;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Controller - 店铺商品分类
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "wxapp", tags = "商家 - 店铺商品分类")
@RestController("merchantStoreGoodsCategoryController")
@RequestMapping("/merchant/store_goods_category")
public class StoreGoodsCategoryController extends BizController {

	private StoreGoodsCategoryService storeGoodsCategoryService;
	private StoreService storeService;
	private FeignGoodsService feignGoodsService;

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		return R.ok(storeGoodsCategoryService.findTree(currentStore));
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody StoreGoodsCategory storeGoodsCategoryForm, String parentId) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		StoreGoodsCategory pStoreGoodsCategory = storeGoodsCategoryService.find(parentId);
		if (parentId == null && pStoreGoodsCategory != null && pStoreGoodsCategory.getParentId() != null) {
			if (!currentStore.getId().equals(pStoreGoodsCategory.getStoreId())) {
				return R.INVALID_ENTITY;
			}
		}
		storeGoodsCategoryForm.setParentId(parentId);
		if (!isValid(storeGoodsCategoryForm)) {
			return R.INVALID_ENTITY;
		}
		storeGoodsCategoryForm.setTreePath(null);
		storeGoodsCategoryForm.setLevel(null);

		storeGoodsCategoryForm.setStoreId(currentStore.getId());
		storeGoodsCategoryForm.setChildren(null);
		storeGoodsCategoryForm.setStoreId(currentStore.getId());
		storeGoodsCategoryService.save(storeGoodsCategoryForm);

		return R.OK;
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String storeGoodsCategoryId) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		Map<String, Object> data = new HashMap<>(2);
		if (null==storeGoodsCategoryId) {
			return R.failed();
		}
		StoreGoodsCategory storeGoodsCategory =storeGoodsCategoryService.find(storeGoodsCategoryId);
		StoreGoodsCategory parentStoreGoodsCategory =storeGoodsCategoryService.find(storeGoodsCategory.getParentId());
		data.put("storeGoodsCategoryTree", storeGoodsCategoryService.findTree(currentStore));
		data.put("storeGoodsCategory", storeGoodsCategory);
		data.put("parentStoreGoodsCategory",parentStoreGoodsCategory);
		data.put("children", storeGoodsCategoryService.findChildren(storeGoodsCategory, currentStore, true, null));
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(@RequestBody StoreGoodsCategory storeGoodsCategoryForm) {
		if (!isValid(storeGoodsCategoryForm)) {
			return R.INVALID_ENTITY;
		}
		storeGoodsCategoryService.update(storeGoodsCategoryForm);
		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		return R.ok(storeGoodsCategoryService.findTree(currentStore));
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String storeGoodsCategoryId) {
		if ( null == storeGoodsCategoryId) {
			return R.INVALID_ENTITY;
		}

		StoreGoodsCategory storeGoodsCategory=storeGoodsCategoryService.find(storeGoodsCategoryId);
		StoreGoodsCategory storeGoodsCategoryParent=new StoreGoodsCategory();
		storeGoodsCategoryParent.setParentId(storeGoodsCategoryId);
		List<StoreGoodsCategory> children = storeGoodsCategoryService.findAllByEntity(storeGoodsCategoryParent);
		if (null != children && !children.isEmpty()) {
			return R.invalidEntity("存在下级分类，无法删除");
		}

		Goods goods=new Goods();
		goods.setStoreGoodsCategoryId(storeGoodsCategoryId);
		List<Goods> goodss=feignGoodsService.findAllByEntity(goods, SecurityEnum.FROM_IN);
		if (goodss != null && !goodss.isEmpty()) {
			return R.invalidEntity("存在关联商品,无法删除");
		}
		storeGoodsCategoryService.delete(storeGoodsCategory);
		return R.OK;
	}

}