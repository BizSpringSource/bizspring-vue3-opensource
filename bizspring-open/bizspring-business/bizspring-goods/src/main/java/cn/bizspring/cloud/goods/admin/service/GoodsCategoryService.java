package cn.bizspring.cloud.goods.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import cn.bizspring.cloud.store.common.entity.Store;

import java.util.List;
import java.util.Set;

/**
 * Service - 商品分类
 * 
 */
public interface GoodsCategoryService extends BizService<GoodsCategory> {

	/**
	 * 查找商品分类
	 * 
	 * @param store
	 *            店铺
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @return 商品分类
	 */
	List<GoodsCategory> findList(Store store, Integer count, List<Filter> filters, List<GoodsCategoryStore> goodsCategoryStores, List<Sort> sorts);


	/**
	 * 将商品分类 整合成tree
	 * @param goodsCategories
	 * @return
	 */
	List<GoodsCategory> integrationToTree(List<GoodsCategory> goodsCategories);

	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<GoodsCategory> findRoots();

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 顶级商品分类
	 */
	List<GoodsCategory> findRoots(Integer count, boolean useCache);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<GoodsCategory> findTree();

	/**
	 * 查找下级商品分类
	 * 
	 * @param goodsCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<GoodsCategory> findChildren(GoodsCategory goodsCategory, boolean recursive, Integer count);

	Set<String> findGoodsCategoryBrands(String id);

	/**
	 * 查找下级商品分类
	 * 
	 * @param goodsCategoryId
	 *            商品分类ID
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @param useCache
	 *            是否使用缓存
	 * @return 下级商品分类
	 */
	List<GoodsCategory> findChildren(String goodsCategoryId, boolean recursive, Integer count, boolean useCache);

}