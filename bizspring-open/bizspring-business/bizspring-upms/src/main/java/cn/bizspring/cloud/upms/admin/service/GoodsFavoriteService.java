package cn.bizspring.cloud.upms.admin.service;


import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.GoodsFavorite;

import java.util.Date;
import java.util.List;

/**
 * Service - 商品收藏
 * 
 */
public interface GoodsFavoriteService extends BizService<GoodsFavorite> {

	/**
	 * 判断商品收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param goodsId
	 *            商品
	 * @return 商品收藏是否存在
	 */
	boolean exists(Member member, String goodsId);

	/**
	 * 查找商品收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @return 商品收藏
	 */
	List<GoodsFavorite> findList(Member member, Integer count, List<Filter> filters, List<Sort> sorts);

	/**
	 * 查找商品收藏
	 * 
	 * @param memberId
	 *            会员ID
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @param useCache
	 *            是否使用缓存
	 * @return 商品收藏
	 */
	List<GoodsFavorite> findList(String memberId, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache);

	/**
	 * 查找商品收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 商品收藏分页
	 */
	Page<GoodsFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找商品收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏数量
	 */
	Long count(Member member);

	/**
	 * 店铺商品收藏统计
	 * @param goodss
	 * 			店铺商品
	 * @param beginDate
	 * 			起始时间
	 * @param endDate
	 * 			结束时间
	 * @return
	 */
	Long countStoreGoods(List<Goods> goodss, Date beginDate, Date endDate);

	/**
	 * 查找店铺中商品收藏数据
	 * @param goodss
	 * 			店铺商品
	 * @param pageable
	 * 			分页
	 * @return
	 */
	Page findFavoritePageOfStore(List<Goods> goodss, Pageable pageable);
}