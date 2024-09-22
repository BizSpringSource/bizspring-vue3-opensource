package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignStoreGoodsCategoryService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignStoreGoodsCategoryService {

	/**
	 * 查找店铺商品分类树
	 *
	 * @param store
	 *            店铺
	 * @return 店铺商品分类树
	 */
	@PostMapping("/provider/store_goods_category/find_tree")
	List<StoreGoodsCategory> findTree(@RequestBody Store store, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/store_goods_category/find")
	StoreGoodsCategory find(@RequestParam(value = "id",required = false) String id, @RequestHeader(SecurityEnum.FROM) String from);


}
