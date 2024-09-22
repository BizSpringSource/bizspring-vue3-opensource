package cn.bizspring.cloud.goods.admin.mapper;

import java.util.List;
import java.util.Set;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.Sku;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - SKU
 * 
 */
public interface SkuMapper extends BizMapper<Sku> {

	/**
	 * 查找SKU
	 * 
	 * @param store
	 *            店铺
	 * @param matchs
	 *            匹配SKU
	 * @param count
	 *            数量
	 * @return SKU
	 */
	List<Sku> findList(@Param("store")Store store, @Param("matchs")Set<Sku> matchs, @Param("count")Integer count);

}