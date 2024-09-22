package cn.bizspring.cloud.order.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.order.admin.service.SnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *Provider Controller - 序列号
 * 
 */
@AllArgsConstructor
@Api(value = "sn", tags = "provider - 序列号")
@RestController("providerSnController")
@RequestMapping("/provider/sn")
public class SnController extends BizController {

	private SnService snService;

	/**
	 * 生成序列号
	 *
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	@Inside
	@PostMapping("/generate")
	@ApiOperation(value = "生成序列号", notes = "生成序列号")
	@ApiOperationSupport(order = 1)
	public String generate(@RequestParam(value = "type",required = false) Sn.Type type, @RequestHeader(SecurityEnum.FROM) String from){
		return	snService.generate(type);
	};

}