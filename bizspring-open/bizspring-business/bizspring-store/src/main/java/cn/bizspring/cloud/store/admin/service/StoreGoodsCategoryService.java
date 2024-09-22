package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;

import java.util.List;

/**
 * Service - 店铺商品分类
 * 
 */
public interface StoreGoodsCategoryService extends BizService<StoreGoodsCategory> {

	/**
	 * 查找店铺商品分类树
	 * 
	 * @param store
	 *            店铺
	 * @return 店铺商品分类树
	 */
	List<StoreGoodsCategory> findTree(Store store);

	/**
	 * 查找下级店铺商品分类
	 * 
	 * @param storeGoodsCategory
	 *            店铺商品分类
	 * @param store
	 *            店铺
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级店铺商品分类
	 */
	List<StoreGoodsCategory> findChildren(StoreGoodsCategory storeGoodsCategory, Store store, boolean recursive, Integer count);

	/**
	 * 查找店铺商品分类
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 店铺商品分类
	 */
	Page<StoreGoodsCategory> findPage(Store store, Pageable pageable);

}