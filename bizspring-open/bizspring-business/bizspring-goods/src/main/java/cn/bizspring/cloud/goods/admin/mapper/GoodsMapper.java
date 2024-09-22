package cn.bizspring.cloud.goods.admin.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.*;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper - 商品
 * 
 */
public interface GoodsMapper extends BizMapper<Goods> {

	/**
	 * 查找商品
	 * 
	 * @param store
	 *            店铺
	 * @param goodsCategory
	 *            商品分类
	 * @param storeGoodsCategory
	 *            店铺商品分类
	 * @param brand
	 *            品牌
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isActive
	 *            是否有效
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @param orderType
	 *            排序类型
	 * @return 商品
	 */
	List<Goods> findByWrapperList(@Param("queryWrapper")QueryWrapper<Goods> wrapper, @Param("store") Store store, @Param("goodsCategory") GoodsCategory goodsCategory, @Param("storeGoodsCategory") StoreGoodsCategory storeGoodsCategory, @Param("brand") Brand brand, @Param("startPrice")BigDecimal startPrice,
									@Param("endPrice")BigDecimal endPrice, @Param("isMarketable")Boolean isMarketable, @Param("isList")Boolean isList, @Param("isTop")Boolean isTop, @Param("isActive")Boolean isActive, @Param("isOutOfStock")Boolean isOutOfStock, @Param("isStockAlert")Boolean isStockAlert, @Param("orderType")Goods.OrderType orderType);

	/**
	 * 查找商品
	 * 
	 * @param goodsCategory
	 *            商品分类
	 * @param storeGoodsCategory
	 *            店铺商品分类
	 * @param isMarketable
	 *            是否上架
	 * @param isActive
	 *            是否有效
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @return 商品
	 */
	List<Goods> findList(@Param("goodsCategory")GoodsCategory goodsCategory, @Param("storeGoodsCategory")StoreGoodsCategory storeGoodsCategory, @Param("isMarketable")Boolean isMarketable, @Param("isActive")Boolean isActive, @Param("beginDate")Date beginDate, @Param("endDate")Date endDate, @Param("first")Integer first, @Param("count")Integer count);

	/**
	 * 查找商品分页
	 * @param storeType
	 *            店铺类型
	 * @param store
	 *            店铺
	 * @param goodsCategory
	 *            商品分类
	 * @param storeGoodsCategory
	 *            店铺商品分类
	 * @param brand
	 *            品牌
	 * @param startPrice
	 *            最低价格
	 * @param endPrice
	 *            最高价格
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isActive
	 *            是否有效
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @param orderType
	 *            排序类型
	 * @return 商品分页
	 */
	List<Goods> findPage(IPage<Goods> iPage, @Param("queryWrapper")QueryWrapper<Goods> queryWrapper,@Param("sn")String sn,@Param("name")String name,@Param("keyword")String keyword, @Param("storeType")Store.Type storeType, @Param("store")Store store, @Param("goodsCategory")GoodsCategory goodsCategory, @Param("storeGoodsCategory")StoreGoodsCategory storeGoodsCategory, @Param("brand")Brand brand, @Param("startPrice")BigDecimal startPrice, @Param("endPrice")BigDecimal endPrice, @Param("isMarketable")Boolean isMarketable, @Param("isList")Boolean isList, @Param("isTop")Boolean isTop, @Param("isActive")Boolean isActive, @Param("isOutOfStock")Boolean isOutOfStock, @Param("isStockAlert")Boolean isStockAlert,  @Param("orderType")Goods.OrderType orderType,@Param("stores")List<Store> stores);

	/**
	 * 查询商品数量
	 * 
	 * @param store
	 *            店铺
	 * @param isMarketable
	 *            是否上架
	 * @param isList
	 *            是否列出
	 * @param isTop
	 *            是否置顶
	 * @param isActive
	 *            是否有效
	 * @param isOutOfStock
	 *            是否缺货
	 * @param isStockAlert
	 *            是否库存警告
	 * @return 商品数量
	 */
	Long count( @Param("store")Store store, @Param("isMarketable")Boolean isMarketable, @Param("isList")Boolean isList, @Param("isTop")Boolean isTop, @Param("isActive")Boolean isActive, @Param("isOutOfStock")Boolean isOutOfStock, @Param("isStockAlert")Boolean isStockAlert);

	/**
	 * 刷新过期店铺商品有效状态
	 */
	void refreshExpiredStoreGoodsActive();

	/**
	 * 刷新商品有效状态
	 *
	 * @param goodsCategoryStores
	 * @param store
	 */
	void refreshActiveValid(@Param("goodsCategoryStores") List<GoodsCategoryStore> goodsCategoryStores, @Param("store") Store store);

	/**
	 * 刷新商品无效状态
	 *
	 * @param goodsCategoryStores
	 * @param store
	 */
	void refreshActiveInValid(@Param("goodsCategoryStores") List<GoodsCategoryStore> goodsCategoryStores, @Param("store") Store store);

	/**
	 * 排名类型获取商品
	 * @count 数量
	 */
    List<Goods> findSelectionList(@Param("store")Store store, @Param("count")int count);
}