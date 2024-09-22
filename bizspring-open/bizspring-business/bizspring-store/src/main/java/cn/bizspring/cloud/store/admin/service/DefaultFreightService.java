package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.common.entity.DefaultFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;

/**
 * Service - 默认运费配置
 * 
 */
public interface DefaultFreightService extends BizService<DefaultFreight> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param region
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(ShippingMethod shippingMethod, Region region);

	/**
	 * 查找运费配置分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 运费配置分页
	 */
	Page<DefaultFreight> findPage(Store store, Pageable pageable);

	/**
	 * 查找默认运费配置
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @return 默认运费配置
	 */
	DefaultFreight find(ShippingMethod shippingMethod, Store store);

	/**
	 * 更新
	 * 
	 * @param defaultFreight
	 *            默认运费配置
	 * @param store
	 *            店铺
	 * @param shippingMethod
	 *            配送方式
	 */
	void update(DefaultFreight defaultFreight, Store store, ShippingMethod shippingMethod);

}