package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.AddressService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 *Provider Controller - 收货地址
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "address", tags = "provider - 收货地址")
@RestController("providerAddressController")
@RequestMapping("/provider/address")
public class AddressController extends BizController {

	private AddressService addressService;

	/**
	 * 查找默认收货地址
	 *
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	@Inside
	@PostMapping("/find_default")
	@ApiOperation(value = "查找默认收货地址", notes = "查找默认收货地址")
	@ApiOperationSupport(order = 3)
	public Address findDefault(@RequestBody Member member, @RequestHeader(SecurityEnum.FROM) String from){
		return	addressService.findDefault(member);
	};

	/**
	 * 查找实体对象
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	@ApiOperationSupport(order = 4)
	public Address find(@RequestParam("addressId") String addressId, @RequestHeader(SecurityEnum.FROM) String from){
		return	addressService.find(addressId);
	};

}