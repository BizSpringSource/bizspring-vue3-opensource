package cn.bizspring.cloud.store.admin.mapper;

import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper - 店铺商品分类
 * 
 */
public interface StoreGoodsCategoryMapper extends BizMapper<StoreGoodsCategory> {

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
	List<StoreGoodsCategory> findChildren(@Param("storeGoodsCategory")StoreGoodsCategory storeGoodsCategory, @Param("store")Store store, @Param("recursive")boolean recursive, @Param("count")Integer count);

	/**
	 * 查找店铺商品分类
	 * 
	 * @param store
	 *            店铺
	 * @param iPage
	 *            分页
	 * @return 店铺商品分类
	 */
	List<StoreGoodsCategory> findPage(IPage<StoreGoodsCategory> iPage, @Param("queryWrapper")QueryWrapper<StoreGoodsCategory> queryWrapper, @Param("store")Store store);
}