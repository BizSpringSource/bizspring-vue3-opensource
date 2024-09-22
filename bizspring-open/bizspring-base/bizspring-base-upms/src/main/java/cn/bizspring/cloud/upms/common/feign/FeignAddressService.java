package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignAddressService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignAddressService {

	/**
	 * 查找默认收货地址
	 *
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	@PostMapping("/provider/address/find_default")
	Address findDefault(@RequestBody Member member, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/address/find")
	Address find(@RequestParam(value="addressId") String addressId, @RequestHeader(SecurityEnum.FROM) String from);


}
