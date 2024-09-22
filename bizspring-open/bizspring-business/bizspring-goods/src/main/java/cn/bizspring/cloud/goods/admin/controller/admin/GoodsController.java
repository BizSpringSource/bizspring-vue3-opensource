package cn.bizspring.cloud.goods.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.goods.admin.repository.GoodsRepository;
import cn.bizspring.cloud.goods.admin.service.*;
import cn.bizspring.cloud.goods.common.entity.Brand;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 商品
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods", tags = "管理 - 商品")
@RestController("adminGoodsController")
@RequestMapping("/admin/goods")
public class GoodsController extends BizController {

	private GoodsService goodsService;
	private GoodsCategoryService goodsCategoryService;
	private BrandService brandService;
	private FeignStoreService feignStoreService;
	private GoodsRepository goodsRepository;
	private SearchService searchService;

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(String sn, String name, String storeId, Date createdTime, String keyword, String goodsCategoryId, String brandId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Pageable pageable) {
		GoodsCategory goodsCategory = goodsCategoryService.find(goodsCategoryId);
		List<Store> stores=feignStoreService.findAll(SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		Brand brand = brandService.find(brandId);
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		data.put("brands", brandService.findAll());
		data.put("storeId",storeId);
		data.put("createdTime",createdTime);
		data.put("goodsCategoryId", goodsCategoryId);
		data.put("brandId", brandId);
		data.put("isMarketable", isMarketable);
		data.put("isList", isList);
		data.put("isTop", isTop);
		data.put("isActive", isActive);
		data.put("isOutOfStock", isOutOfStock);
		data.put("isStockAlert", isStockAlert);
		Store currentStore =null;
		if(CommonUtils.isNotNull(storeId)){
			currentStore=feignStoreService.find(storeId,SecurityEnum.FROM_IN);
		}
		data.put("data", goodsService.findPage(CommonUtils.isNotEmpty(sn)?sn:null,name,keyword, null,currentStore , goodsCategory, null, brand,    null,  null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert,null,stores, pageable));
		return R.ok(data);
	}

	/**
	 * 列表
	 */
	@SysLog("初始化搜索信息")
	@ApiOperation(value = "初始化搜索信息", notes = "初始化搜索信息")
	@ApiOperationSupport(order = 2)
	@GetMapping("/init_search_data")
	public ResponseEntity initSearchData() {
		searchService.index(Goods.class);
		return R.ok();
	}



	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		goodsService.delete(ids);
		for (String id: ids) {
			goodsRepository.deleteById(id);
		}
		return R.OK;
	}

}