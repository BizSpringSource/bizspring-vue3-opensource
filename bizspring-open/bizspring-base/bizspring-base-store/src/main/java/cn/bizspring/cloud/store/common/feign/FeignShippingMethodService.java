package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignShippingMethodService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignShippingMethodService {

	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@GetMapping("/provider/shipping_method/find_all")
	List<ShippingMethod> findAll(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/shipping_method/find/{id}")
	ShippingMethod find(@PathVariable(value = "id") String id, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 计算运费
	 *
	 * @param params
	 *            参数
	 * @return 运费
	 */
	@PostMapping("/provider/shipping_method/calculate_freight")
	BigDecimal calculateFreight(@RequestBody Map<String,Object> params, @RequestHeader(SecurityEnum.FROM) String from);

}
