package cn.bizspring.cloud.store.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import java.util.List;

/**
 * <p>
 *  Mapper 产品分类与店铺关连
 * </p>
 *
 */
public interface GoodsCategoryStoreMapper extends BizMapper<GoodsCategoryStore> {

	List<GoodsCategoryStore> findAll();
}
