package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.ConfigService;
import cn.bizspring.cloud.upms.common.entity.Config;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *Provider Controller - 配置
 * 
 */
@AllArgsConstructor
@Api(value = "config", tags = "provider - 配置")
@RestController("providerConfigController")
@RequestMapping("/provider/config")
public class ConfigController extends BizController {

	private ConfigService configService;

	/**
	 * 根据方式ID查找配置
	 *
	 * @param modeId
	 *            方式ID
	 * @return 方式配置，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find_by_mode_id/{mode_id}")
	@ApiOperation(value = "根据方式ID查找配置", notes = "根据方式ID查找配置")
	@ApiOperationSupport(order = 2)
	public Config findByModeId(@PathVariable("mode_id") String modeId, @RequestHeader(SecurityEnum.FROM) String from){
		return	configService.findByModeId(modeId);
	};

}