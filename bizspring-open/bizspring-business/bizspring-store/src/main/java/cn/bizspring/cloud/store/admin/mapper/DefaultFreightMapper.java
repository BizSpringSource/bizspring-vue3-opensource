package cn.bizspring.cloud.store.admin.mapper;

import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.DefaultFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper - 默认运费配置
 * 
 */
public interface DefaultFreightMapper extends BizMapper<DefaultFreight> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param region
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(@Param("shippingMethod") ShippingMethod shippingMethod, @Param("region") Region region);

	/**
	 * 查找默认运费配置分页
	 * 
	 * @param store
	 *            店铺
	 * @return 运费配置分页
	 */
	List<DefaultFreight> findPage(IPage<DefaultFreight> iPage, @Param("queryWrapper")QueryWrapper<DefaultFreight> queryWrapper, @Param("store") Store store);

	/**
	 * 查找默认运费配置
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param store
	 *            店铺
	 * @return 默认运费配置
	 */
	DefaultFreight findDefault(@Param("shippingMethod")ShippingMethod shippingMethod, @Param("store")Store store);

}