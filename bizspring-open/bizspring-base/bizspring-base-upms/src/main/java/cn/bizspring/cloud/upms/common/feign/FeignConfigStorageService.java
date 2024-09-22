package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignConfigStorageService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignConfigStorageService {

	@GetMapping("/provider/config_storage/find_one")
	ConfigStorage getOne(@RequestHeader(SecurityEnum.FROM) String from);

}
