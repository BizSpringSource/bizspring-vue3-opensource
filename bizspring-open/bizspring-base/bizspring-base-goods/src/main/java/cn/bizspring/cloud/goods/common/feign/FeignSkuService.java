package cn.bizspring.cloud.goods.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.entity.Stock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignSkuService", value = ServiceNameEnum.GOODS_SERVICE)
public interface FeignSkuService {

	/**
	 * 查找sku实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/sku/find/{id}")
	public Sku find(@PathVariable(value = "id") String id, @RequestHeader(SecurityEnum.FROM) String from);


	/**
	 * 增加已分配库存
	 *
	 * @param sku
	 *            SKU
	 * @param amount
	 *            值
	 */
	@PostMapping("/provider/sku/add_allocated_stock")
	public void addAllocatedStock(@RequestBody Sku sku,@RequestParam("amount") int amount, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 增加库存
	 *
	 * @param sku
	 *            SKU
	 * @param amount
	 *            值
	 * @param remark
	 *            备注
	 */
	@PostMapping("/provider/sku/add_stock")
	public void addStock(@RequestBody Sku sku, @RequestParam("amount") int amount, @RequestParam("type")Stock.Type type, @RequestParam(value = "remark",required = false) String remark, @RequestHeader(SecurityEnum.FROM) String from);

}
