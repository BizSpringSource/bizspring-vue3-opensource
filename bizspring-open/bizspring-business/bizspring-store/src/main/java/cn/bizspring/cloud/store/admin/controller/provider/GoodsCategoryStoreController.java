package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.GoodsCategoryStoreService;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 优惠码
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods_category_store", tags = "provider - 优惠码")
@RestController("providerGoodsCategoryStoreController")
@RequestMapping("/provider/goods_category_store")
public class GoodsCategoryStoreController extends BizController {

	private GoodsCategoryStoreService goodsCategoryStoreService;

	/**
	 * 查找所有实体对象
	 *
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找店铺实体对象", notes = "查找店铺实体对象")
	@ApiOperationSupport(order = 1)
	public List<GoodsCategoryStore> findAll(@RequestHeader(SecurityEnum.FROM) String from){
		return 	goodsCategoryStoreService.findAll();
	};

	/**
	 * 通过实体查找左右对象
	 *
	 * @param goodsCategoryStore
	 *            实体
	 * @return 实体对象集合，若不存在则返回null
	 */
	@Inside
	@PostMapping("/find_all_by_entity")
	@ApiOperation(value = "通过实体查找左右对象", notes = "通过实体查找左右对象")
	@ApiOperationSupport(order = 1)
	public List<GoodsCategoryStore> findAllByEntity(@RequestBody GoodsCategoryStore goodsCategoryStore, @RequestHeader(SecurityEnum.FROM) String from){
		return 	goodsCategoryStoreService.findAllByEntity(goodsCategoryStore);
	}


}