package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.GoodsFavorite;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Mapper - 商品收藏
 * 
 */
public interface GoodsFavoriteMapper extends BizMapper<GoodsFavorite> {

	/**
	 * 判断商品收藏是否存在
	 * 
	 * @param member
	 *            会员
	 * @param goodsId
	 *            商品
	 * @return 商品收藏是否存在
	 */
	boolean exists(@Param("member") Member member, @Param("goodsId") String goodsId);

	/**
	 * 查找商品收藏
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏
	 */
	List<GoodsFavorite> findList(@Param("queryWrapper")QueryWrapper<GoodsFavorite> queryWrapper, Member member);

	/**
	 * 查找商品收藏分页
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏分页
	 */
	List<GoodsFavorite> findPage(IPage<GoodsFavorite> iPage, @Param("queryWrapper")QueryWrapper<GoodsFavorite> queryWrapper, @Param("member")Member member);

	/**
	 * 查找商品收藏数量
	 * 
	 * @param member
	 *            会员
	 * @return 商品收藏数量
	 */
	Long count(@Param("member")Member member);

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
	Long countStoreGoods(@Param("goodss") List<Goods> goodss, @Param("beginDate") Date beginDate,@Param("endDate")  Date endDate);

	/**
	 * 查找店铺中商品收藏数据
	 * @param goodss
	 * 			店铺商品
	 * @param pageable
	 * 			分页
	 * @return
	 */
    List<GoodsFavorite> findFavoritePageOfStore(IPage<GoodsFavorite> iPage,@Param("queryWrapper") QueryWrapper<GoodsFavorite> pageable, @Param("goodss")List<Goods> goodss);
}