package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.PaymentPatternService;
import cn.bizspring.cloud.store.common.entity.PaymentPattern;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 支付方式
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "payment_pattern", tags = "provider - 支付方式")
@RestController("providerPaymentPatternController")
@RequestMapping("/provider/payment_pattern")
public class PaymentPatternController extends BizController {

	private PaymentPatternService paymentPatternService;


	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找所有实体对象集合", notes = "查找所有实体对象集合")
	@ApiOperationSupport(order = 1)
	public List<PaymentPattern> findAll(@RequestHeader(SecurityEnum.FROM) String from){
		return 	paymentPatternService.findAll();
	};

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	@ApiOperationSupport(order = 2)
	public PaymentPattern find(@RequestParam("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return 	paymentPatternService.find(id);
	};
}