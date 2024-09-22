package cn.bizspring.cloud.goods.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.goods.common.entity.Stock;

/**
 * Service - SKU
 * 
 */
public interface SkuService extends BizService<Sku> {

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
	void addStock(Sku sku, int amount, Stock.Type type, String remark);

	/**
	 * 增加已分配库存
	 * 
	 * @param sku
	 *            SKU
	 * @param amount
	 *            值
	 */
	void addAllocatedStock(Sku sku, int amount);

}