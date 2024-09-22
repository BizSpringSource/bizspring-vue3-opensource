package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.ExpressCompany;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignExpressCompanyService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignExpressCompanyService {

	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@GetMapping("/provider/express_company/find_all")
	List<ExpressCompany> findAll(@RequestHeader(SecurityEnum.FROM) String from);

}
