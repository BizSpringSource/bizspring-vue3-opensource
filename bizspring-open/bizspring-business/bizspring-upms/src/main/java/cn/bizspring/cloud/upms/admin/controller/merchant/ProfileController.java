package cn.bizspring.cloud.upms.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.upms.admin.service.MerchantService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller - 个人资料
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "profile", tags = "商家 - 个人资料")
@RestController("merchantProfileController")
@RequestMapping("/merchant/profile")
public class ProfileController extends BizController {

	private MerchantService merchantService;
	private FeignStoreService feignStoreService;

	/**
	 * 主页
	 */
	@ApiOperation(value = "主页", notes = "主页")
	@ApiOperationSupport(order = 2)
	@GetMapping("/index")
	public ResponseEntity index() {
		Map<String, Object> data = new HashMap<>(2);
		Store currentStore=feignStoreService.getCurrent(SecurityUtils.getUserId(),SecurityEnum.FROM_IN);
		Merchant currentMerchant=merchantService.find(SecurityUtils.getUserId());
		data.put("store",currentStore);
		data.put("merchant",currentMerchant);
		return R.ok(data);
	}

}