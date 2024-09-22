package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.ConfigStorageService;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 *Provider Controller - 存储配置
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "configStorage", tags = "configStorage - 存储配置")
@RestController("providerConfigStorageController")
@RequestMapping("/provider/config_storage")
public class ConfigStorageController extends BizController {

	private ConfigStorageService configStorageService;

	/**
	 * 获取存储配置信息
	 * @return
	 */
	@ApiOperation(value = "获取存储配置信息", notes = "获取存储配置信息")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/find_one")
	public ConfigStorage getOne(@RequestHeader(SecurityEnum.FROM) String from) {
		return configStorageService.getOne(Wrappers.emptyWrapper());
	}

}