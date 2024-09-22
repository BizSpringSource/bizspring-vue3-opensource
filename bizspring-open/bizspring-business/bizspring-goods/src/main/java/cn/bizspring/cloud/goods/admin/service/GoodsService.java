package cn.bizspring.cloud.goods.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import cn.bizspring.cloud.goods.common.entity.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Service - 商品
 */
public interface GoodsService extends BizService<Goods> {

	/**
	 * 判断编号是否存在
	 *
	 * @param sn 编号(忽略大小写)
	 * @return 编号是否存在
	 */
	boolean snExists(String sn);

	/**
	 * 查找商品
	 * @param storeId                店铺ID
	 * @param goodsCategoryId      商品分类ID
	 * @param storeGoodsCategoryId 店铺商品分类ID
	 * @param brandId                品牌ID
	 * @param startPrice             最低价格
	 * @param endPrice               最高价格
	 * @param isMarketable           是否上架
	 * @param isList                 是否列出
	 * @param isTop                  是否置顶
	 * @param isActive               是否有效
	 * @param isOutOfStock           是否缺货
	 * @param isStockAlert           是否库存警告
	 * @param orderType              排序类型
	 * @param count                  数量
	 * @param filters                筛选
	 * @param sorts                 排序
	 * @param useCache               是否使用缓存
	 * @return 商品
	 */
	List<Goods> findList(String storeId, String goodsCategoryId, String storeGoodsCategoryId, String brandId,  BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
						   Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache);

	/**
	 * 查找商品分页
	 * @param storeType            店铺类型
	 * @param store                店铺
	 * @param goodsCategory      商品分类
	 * @param storeGoodsCategory 店铺商品分类
	 * @param brand                品牌
	 * @param startPrice           最低价格
	 * @param endPrice             最高价格
	 * @param isMarketable         是否上架
	 * @param isList               是否列出
	 * @param isTop                是否置顶
	 * @param isActive             是否有效
	 * @param isOutOfStock         是否缺货
	 * @param isStockAlert         是否库存警告
	 * @param orderType            排序类型
	 * @param pageable             分页信息
	 * @return 商品分页
	 */
	Page<Goods> findPage(String sn,String name,String keyword,Store.Type storeType, Store store, GoodsCategory goodsCategory, StoreGoodsCategory storeGoodsCategory, Brand brand,BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isActive, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType, List<Store> stores, Pageable pageable);


	/**
	 * 创建
	 *
	 * @param goods 商品
	 * @param sku     SKU
	 * @return 商品
	 */
	Goods create(Goods goods, Sku sku);

	/**
	 * 修改
	 *
	 * @param goods 商品
	 * @param sku     SKU
	 * @return 商品
	 */
	Goods modify(Goods goods, Sku sku);

	/**
	 * 刷新过期店铺商品有效状态
	 */
	void refreshExpiredStoreGoodsActive();

	/**
	 * 刷新商品有效状态
	 *
	 * @param store 店铺
	 */
	void refreshActive(Store store);

	/**
	 * 获取默认SKU
	 *
	 * @return 默认SKU
	 */
	Sku getDefaultSku(Goods goods);

	List<Goods> findSelectionList(Store store, int count);
}