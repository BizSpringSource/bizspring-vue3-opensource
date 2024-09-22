package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.StoreGoodsCategoryService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 店铺商品分类
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "store_goods_category", tags = "provider - 店铺商品分类")
@RestController("providerStoreGoodsCategoryController")
@RequestMapping("/provider/store_goods_category")
public class StoreGoodsCategoryController extends BizController {

	private StoreGoodsCategoryService storeGoodsCategoryService;

	/**
	 * 查找店铺商品分类树
	 *
	 * @param store
	 *            店铺
	 * @return 店铺商品分类树
	 */
	@Inside
	@PostMapping("/find_tree")
	@ApiOperation(value = "查找店铺商品分类树", notes = "查找店铺商品分类树")
	@ApiOperationSupport(order = 1)
	public List<StoreGoodsCategory> findTree(@RequestBody Store store, @RequestHeader(SecurityEnum.FROM) String from){
		return	storeGoodsCategoryService.findTree(store);
	};

	/**
	 * 查找实体对象
	 *
	 * @param storeGoodsCategoryId
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找店铺商品分类实体对象", notes = "查找店铺商品分类实体对象")
	@ApiOperationSupport(order = 2)
	public StoreGoodsCategory find(@RequestParam(value = "id",required = false) String storeGoodsCategoryId, @RequestHeader(SecurityEnum.FROM) String from){
		return	storeGoodsCategoryService.find(storeGoodsCategoryId);
	};

}