package cn.bizspring.cloud.goods.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignGoodsCategoryService", value = ServiceNameEnum.GOODS_SERVICE)
public interface FeignGoodsCategoryService {

	/**
	 * 查找顶级商品分类
	 *
	 * @return 顶级商品分类
	 */
	@GetMapping("/provider/goods_category/find_roots")
	List<GoodsCategory> findRoots(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找商品分类树
	 *
	 * @return 商品分类树
	 */
	@GetMapping("/provider/goods_category/find_tree")
	List<GoodsCategory> findTree(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象集合
	 *
	 * @return 实体对象集合
	 */
	@GetMapping("/provider/goods_category/find_all")
	List<GoodsCategory> findAll(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/goods_category/find/{id}")
	GoodsCategory find(@PathVariable(value = "id") String id, @RequestHeader(SecurityEnum.FROM) String from);

}
