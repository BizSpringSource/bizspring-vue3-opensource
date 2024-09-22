package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignStoreRepository", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignStoreRepository {

	/**
	 * 保存所有
	 * @param stores 店铺
	 */
	@PostMapping("/provider/store_repository/save_all")
	void saveAll(@RequestBody List<Store> stores, @RequestHeader(SecurityEnum.FROM) String from);
}
