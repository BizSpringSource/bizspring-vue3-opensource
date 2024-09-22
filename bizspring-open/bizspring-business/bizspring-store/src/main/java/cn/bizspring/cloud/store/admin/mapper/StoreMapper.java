package cn.bizspring.cloud.store.admin.mapper;

import java.util.List;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mapper - 店铺
 * 
 */
public interface StoreMapper extends BizMapper<Store> {

	/**
	 * 查找店铺分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param isEnabled
	 *            是否启用
	 * @param hasExpired
	 *            是否过期
	 * @param iPage
	 *            分页信息
	 * @return 店铺分页
	 */
	List<Store> findPage(IPage<Store> iPage, @Param("queryWrapper")QueryWrapper<Store> queryWrapper, @Param("type")Store.Type type, @Param("status")Store.Status status, @Param("isEnabled")Boolean isEnabled, @Param("hasExpired")Boolean hasExpired);

	List<Store> findPageList(@Param("merchantes")List merchantes, @Param("storeCategories")List storeCategories,IPage<Store> iPage, @Param("queryWrapper")QueryWrapper<Store> queryWrapper, @Param("type")Store.Type type, @Param("status")Store.Status status, @Param("isEnabled")Boolean isEnabled, @Param("hasExpired")Boolean hasExpired,@Param("name")String name,@Param("storeCategoryId")String storeCategoryId);

	/**
	 * 根据商家id查找Store
	 *
	 * @param merchantId
	 *            商家id
	 * @return 已付保证金总额
	 */
	Store findByMerchant(@Param("id")String merchantId);

	/**
	 * 查找店铺数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param isEnabled
	 *            是否启用
	 * @param hasExpired
	 *            是否过期
	 * @return 店铺数量
	 */
	Long count(@Param("type")Store.Type type, @Param("status")Store.Status status,  @Param("isEnabled")Boolean isEnabled, @Param("hasExpired")Boolean hasExpired);


	/**
	 * 搜索店铺名称
	 * @param keyword
	 * @param count
	 * @return
	 */
	List<Store> keywordSearch(@Param("keyword")String keyword, @Param("count")Integer count);



}