package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignGoodsCategoryStoreService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignGoodsCategoryStoreService {
	@GetMapping("/provider/goods_category_store/find_all")
	List<GoodsCategoryStore> findAll(@RequestHeader(SecurityEnum.FROM) String from);

	@PostMapping("/provider/goods_category_store/find_all_by_entity")
	List<GoodsCategoryStore> findAllByEntity(@RequestBody GoodsCategoryStore goodsCategoryStore, @RequestHeader(SecurityEnum.FROM) String from);

}
