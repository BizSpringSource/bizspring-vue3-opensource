package cn.bizspring.cloud.upms.admin.service;


import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.upms.common.entity.Region;

import java.util.List;

/**
 * Service - 地区
 * 
 */
public interface RegionService extends BizService<Region> {

	/**
	 * 查找顶级地区
	 *
	 * @param name
	 * 		  名称
	 * @return
	 */
	List<Region> findRoots(String name);

	/**
	 * 查找下级地区
	 * 
	 * @param region
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级地区
	 */
	List<Region> findChildren(Region region, boolean recursive, Integer count);

}