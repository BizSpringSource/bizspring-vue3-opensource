package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignMerchantService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignMerchantService {

	@GetMapping("/provider/merchant/find")
	Merchant find(@RequestParam("merchantId") String merchantId,@RequestHeader(SecurityEnum.FROM) String from);

	@GetMapping("/provider/merchant/count")
	char[] count(@RequestParam("beginDate") Date beginDate, @RequestParam("endDate") Date endDate,@RequestHeader(SecurityEnum.FROM) String from);

}
