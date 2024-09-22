package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.store.common.entity.StoreCategory;

import java.util.List;

/**
 * Service - 店铺分类
 * 
 */
public interface StoreCategoryService extends BizService<StoreCategory> {

	/**
	 * 查找所有实体对象集合
	 * 
	 * @return 所有实体对象集合
	 */
	List<StoreCategory> findAll();

}