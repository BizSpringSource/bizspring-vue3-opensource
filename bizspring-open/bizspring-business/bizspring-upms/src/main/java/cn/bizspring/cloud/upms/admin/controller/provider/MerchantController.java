package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.MerchantService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 *Provider Controller - 商家
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "merchant", tags = "provider - 商家")
@RestController("providerMerchantController")
@RequestMapping("/provider/merchant")
public class MerchantController extends BizController {

	private MerchantService merchantService;

	/**
	 * 查找实体对象
	 * @param merchantId
	 * 			ID
	 * @param from
	 * @return
	 */
	@Inside
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	@ApiOperationSupport(order = 9)
	@GetMapping("/find")
	public Merchant find(@RequestParam("merchantId") String merchantId,@RequestHeader(SecurityEnum.FROM) String from){
		return merchantService.find(merchantId);
	}

	/**
	 * 查询商家数量
	 * @param beginDate
	 * @param endDate
	 * @param from
	 * @return
	 */
	@Inside
	@ApiOperation(value = "查询商家数量", notes = "查询商家数量")
	@ApiOperationSupport(order = 9)
	@GetMapping("/count")
	public long count(@RequestParam("beginDate") Date beginDate, @RequestParam("endDate") Date endDate, @RequestHeader(SecurityEnum.FROM) String from){
		return merchantService.count(beginDate,endDate);
	}

}