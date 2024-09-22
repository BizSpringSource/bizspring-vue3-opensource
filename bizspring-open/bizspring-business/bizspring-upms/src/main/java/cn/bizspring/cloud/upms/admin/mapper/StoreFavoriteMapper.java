package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.StoreFavorite;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Mapper - 店铺收藏
 * 
 */
public interface StoreFavoriteMapper extends BizMapper<StoreFavorite> {

	/**
	 * 判断店铺收藏是否存在
	 * 
	 * @param memberId
	 *            会员
	 * @param storeId
	 *            店铺
	 * @return 店铺收藏是否存在
	 */
	boolean exists(@Param("memberId") String memberId, @Param("storeId") String storeId);

	/**
	 * 查找店铺收藏
	 * 
	 * @param member
	 *            会员
	 *            排序
	 * @return 店铺收藏
	 */
	List<StoreFavorite> findList(@Param("queryWrapper")QueryWrapper<StoreFavorite> queryWrapper, @Param("member")Member member);

	/**
	 * 查找店铺收藏分页
	 * 
	 * @param member
	 *            会员
	 * @return 店铺收藏分页
	 */
	List<StoreFavorite> findPage(IPage<StoreFavorite> iPage, @Param("queryWrapper")QueryWrapper<StoreFavorite> queryWrapper, @Param("member")Member member);

	/**
	 * 查找店铺收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 店铺收藏数量
	 */
	Long count(@Param("member")Member member);

	/**
	 * 店铺收藏统计
	 * @param store
	 * 		  店铺
	 * @param beginDate
	 * 			起始时间
	 * @param endDate
	 * 			结束时间
	 * @return
	 */
    Long countStore(@Param("store")Store store, @Param("beginDate")Date beginDate,@Param("endDate") Date endDate);

}