package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.upms.common.entity.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper - 地区
 * 
 */
public interface RegionMapper extends BizMapper<Region> {

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Region> findRoots(@Param("count")Integer count,@Param("name")String name);

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
	List<Region> findChildren(@Param("region")Region region, @Param("recursive")boolean recursive, @Param("count")Integer count);

}