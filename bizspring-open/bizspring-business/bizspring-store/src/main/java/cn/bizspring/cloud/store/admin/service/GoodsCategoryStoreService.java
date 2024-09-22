package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import java.util.List;

/**
 * Service - 店铺
 *
 */
public interface GoodsCategoryStoreService extends BizService<GoodsCategoryStore> {

	/**
	 * 查找实体对象
	 * @param storeId
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	GoodsCategoryStore find(String storeId);

	/**
	 * 查询所有
	 * @return
	 */
    List<GoodsCategoryStore> findAll();


	}