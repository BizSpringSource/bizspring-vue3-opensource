package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import cn.bizspring.cloud.upms.common.entity.Address;

import java.math.BigDecimal;

/**
 * Service - 配送方式
 * 
 */
public interface ShippingMethodService extends BizService<ShippingMethod> {

	/**
	 * 计算运费
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param region
	 *            地区
	 * @param weight
	 *            重量
	 * @return 运费
	 */
	BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Region region, Integer weight);

	/**
	 * 计算运费
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param address
	 *            收货地址
	 * @param weight
	 *            重量
	 * @return 运费
	 */
	BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Address address, Integer weight);
}