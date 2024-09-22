package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.common.entity.RegionFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;

/**
 * Service - 地区运费配置
 * 
 */
public interface RegionFreightService extends BizService<RegionFreight> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param region
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(ShippingMethod shippingMethod, Store store, Region region);

	/**
	 * 判断运费配置是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param shippingMethod
	 *            配送方式
	 * @param region
	 *            地区
	 * @param store
	 *            店铺
	 * @return 运费配置是否唯一
	 */
	boolean unique(String id, ShippingMethod shippingMethod, Store store, Region region);

	/**
	 * 查找运费配置分页
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页信息
	 * @return 运费配置分页
	 */
	Page<RegionFreight> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable);

}