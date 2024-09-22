package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignPaymentPatternService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignPaymentPatternService {

	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@GetMapping("/provider/payment_pattern/find_all")
	List<PaymentPattern> findAll(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/payment_pattern/find")
	PaymentPattern find(@RequestParam(value = "id",required = false) String id, @RequestHeader(SecurityEnum.FROM) String from);
}
