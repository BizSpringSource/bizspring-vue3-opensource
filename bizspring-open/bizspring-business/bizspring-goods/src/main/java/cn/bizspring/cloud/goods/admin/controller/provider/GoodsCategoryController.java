package cn.bizspring.cloud.goods.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.goods.admin.service.GoodsCategoryService;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 商品分类
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods_category", tags = "provider - 商品分类")
@RestController("providerGoodsCategoryController")
@RequestMapping("/provider/goods_category")
public class GoodsCategoryController extends BizController {

	private GoodsCategoryService goodsCategoryService;

	/**
	 * 查找顶级商品分类
	 *
	 * @return 顶级商品分类
	 */
	@Inside
	@GetMapping("/find_roots")
	@ApiOperation(value = "查找顶级商品分类", notes = "查找顶级商品分类")
	@ApiOperationSupport(order = 2)
	public List<GoodsCategory> findRoots(@RequestHeader(SecurityEnum.FROM) String from){
		return goodsCategoryService.findRoots();
	}

	/**
	 * 查找商品分类树
	 *
	 * @return 商品分类树
	 */
	@Inside
	@GetMapping("/find_tree")
	@ApiOperation(value = "查找商品分类树", notes = "查找商品分类树")
	@ApiOperationSupport(order = 3)
	public List<GoodsCategory> findTree( @RequestHeader(SecurityEnum.FROM) String from){
		return goodsCategoryService.findTree();
	}

	/**
	 * 查找实体对象集合
	 *
	 * @return 实体对象集合
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找实体对象集合", notes = "查找实体对象集合")
	@ApiOperationSupport(order = 5)
	public List<GoodsCategory> findAll(@RequestHeader(SecurityEnum.FROM) String from){
		List<GoodsCategory> goodsCategories= goodsCategoryService.findAll();
		return goodsCategories;
	}

	/**
	 * 查找实体对象集合
	 *
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	@Inside
	@PostMapping("/find_list_ids")
	@ApiOperation(value = "查找实体对象集合", notes = "查找实体对象集合")
	@ApiOperationSupport(order = 4)
	public List<GoodsCategory> findList(@RequestBody String[] ids, @RequestHeader(SecurityEnum.FROM) String from){
		return goodsCategoryService.findList(ids);
	}

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find/{id}")
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	@ApiOperationSupport(order = 5)
	public GoodsCategory find(@PathVariable("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return goodsCategoryService.find(id);
	}


}