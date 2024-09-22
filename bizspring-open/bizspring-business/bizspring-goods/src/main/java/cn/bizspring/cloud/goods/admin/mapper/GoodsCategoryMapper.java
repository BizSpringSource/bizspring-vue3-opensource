package cn.bizspring.cloud.goods.admin.mapper;

import java.util.List;
import java.util.Set;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Mapper - 商品分类
 * 
 */
public interface GoodsCategoryMapper extends BizMapper<GoodsCategory> {

	/**
	 * 查找商品分类
	 * 
	 * @param store
	 *            店铺
	 * @return 商品分类
	 */
	List<GoodsCategory> findList(@Param("queryWrapper")QueryWrapper<GoodsCategory> queryWrapper, @Param("store") Store store,@Param("goodsCategoryStores") List<GoodsCategoryStore> goodsCategoryStores);

	/**
	 * 根据实体携带参数查找所有商品
	 * @param entity
	 * @return
	 */
	List<GoodsCategory> findAllByEntity(@Param("entity") GoodsCategory entity);


	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<GoodsCategory> findRoots(@Param("count")Integer count);

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
	List<GoodsCategory> findChildren(@Param("goodsCategory")GoodsCategory goodsCategory, @Param("recursive")boolean recursive, @Param("count")Integer count);

	Set<String> findGoodsCategoryBrands(@Param("id")String id);
}