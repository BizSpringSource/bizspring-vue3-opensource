package cn.bizspring.cloud.order.common.feign;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author www.bizspring.cn  to order
 */
@FeignClient(contextId = "feignSnService", value = ServiceNameEnum.ORDER_SERVICE)
public interface FeignSnService {

	/**
	 * 生成序列号
	 *
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	@PostMapping("/provider/sn/generate")
	String generate(@RequestParam(value = "type",required = false) Sn.Type type, @RequestHeader(SecurityEnum.FROM) String from);
}
