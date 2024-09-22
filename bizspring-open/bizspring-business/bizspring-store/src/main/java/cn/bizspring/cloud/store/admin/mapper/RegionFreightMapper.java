package cn.bizspring.cloud.store.admin.mapper;

import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.RegionFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper - 地区运费配置
 * 
 */
public interface RegionFreightMapper extends BizMapper<RegionFreight> {

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
	boolean exists(@Param("shippingMethod")ShippingMethod shippingMethod, @Param("store") Store store, @Param("region") Region region);

	/**
	 * 判断运费配置是否存在
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
	boolean unique(@Param("id")String id, @Param("shippingMethod") ShippingMethod shippingMethod, @Param("store")Store store, @Param("region")Region region);

	/**
	 * 查找运费配置分页
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @param iPage
	 *            分页信息
	 * @return 运费配置分页
	 */
	List<RegionFreight> findPage(IPage<RegionFreight> iPage, @Param("queryWrapper")QueryWrapper<RegionFreight> queryWrapper, @Param("shippingMethod")ShippingMethod shippingMethod, @Param("store")Store store);

	/**
	 * 查找实体对象集合
	 * @param propertyName
	 * 			参数名称
	 * @param propertyValue
	 * 			参数值
	 * @return
	 */
	List<RegionFreight> findList(@Param("propertyName") String propertyName,@Param("propertyValue")String propertyValue);

}