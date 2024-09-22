package cn.bizspring.cloud.goods.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.goods.admin.service.SkuService;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.entity.Stock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 *Provider Controller - SKU 最小库存
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "sku", tags = "provider - SKU")
@RestController("providerSkuController")
@RequestMapping("/provider/sku")
public class SkuController extends BizController {

	private SkuService skuService;

	/**
	 * 查找sku实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find/{id}")
	@ApiOperation(value = "查找sku实体对象", notes = "查找sku实体对象")
	@ApiOperationSupport(order = 3)
	public Sku find(@PathVariable("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return	skuService.find(id);
	}

	/**
	 * 增加已分配库存
	 *
	 * @param sku
	 *            SKU
	 * @param amount
	 *            值
	 */
	@Inside
	@PostMapping("/add_allocated_stock")
	@ApiOperation(value = "增加已分配库存", notes = "增加已分配库存")
	@ApiOperationSupport(order = 4)
	public void addAllocatedStock(@RequestBody Sku sku, int amount, @RequestHeader(SecurityEnum.FROM) String from) {
		skuService.addAllocatedStock(sku,amount);
	}

	/**
	 * 增加库存
	 *
	 * @param sku
	 *            SKU
	 * @param amount
	 *            值
	 * @param type
	 *            类型
	 * @param remark
	 *            备注
	 */
	@Inside
	@PostMapping("/add_stock")
	@ApiOperation(value = "增加库存", notes = "增加库存")
	@ApiOperationSupport(order = 5)
	public void addStock(@RequestBody Sku sku,@RequestParam(value="amount") int amount, @RequestParam(value="type")Stock.Type type,@RequestParam(value="remark",required = false)  String remark, @RequestHeader(SecurityEnum.FROM) String from) {
		skuService.addStock(sku,amount,type,remark);
	}

}