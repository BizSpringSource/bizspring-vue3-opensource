package cn.bizspring.cloud.upms.admin.service;


import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.StoreFavorite;

import java.util.Date;
import java.util.List;

/**
 * Service - 店铺收藏
 * 
 */
public interface StoreFavoriteService extends BizService<StoreFavorite> {

	/**
	 * 判断店铺收藏是否存在
	 * 
	 * @param memberId
	 *            会员
	 * @param storeId
	 *            店铺
	 * @return 店铺收藏是否存在
	 */
	boolean exists(String memberId, String storeId);

	/**
	 * 查找店铺收藏
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Sort> sorts);

	/**
	 * 查找店铺收藏
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
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(String memberId, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache);

	/**
	 * 查找店铺收藏分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 店铺收藏分页
	 */
	Page<StoreFavorite> findPage(Member member, Pageable pageable);

	/**
	 * 查找店铺收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 店铺收藏数量
	 */
	Long count(Member member);

	/**
	 * 店铺收藏统计
	 * @param store
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Long countStore(Store store, Date beginDate, Date endDate);
}