package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.ExpressCompanyService;
import cn.bizspring.cloud.store.common.entity.ExpressCompany;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 快递公司
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "express_company", tags = "provider -快递公司")
@RestController("providerExpressCompanyController")
@RequestMapping("/provider/express_company")
public class ExpressCompanyController extends BizController {

	private ExpressCompanyService expressCompanyService;

	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找所有实体对象集合", notes = "查找所有实体对象集合")
	@ApiOperationSupport(order = 1)
	public List<ExpressCompany> findAll(@RequestHeader(SecurityEnum.FROM) String from){
		return 	expressCompanyService.findAll();
	};
}