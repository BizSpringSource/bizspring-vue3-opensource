package cn.bizspring.cloud.order.common.feign;

import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignPaymentConfigService", value = ServiceNameEnum.ORDER_SERVICE)
public interface FeignPaymentConfigService {

}
