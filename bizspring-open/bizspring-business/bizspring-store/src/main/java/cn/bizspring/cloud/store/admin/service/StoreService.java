package cn.bizspring.cloud.store.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.common.entity.Store;
import java.util.List;

/**
 * Service - 店铺
 */
public interface StoreService extends BizService<Store> {

	/**
	 * 判断名称是否存在
	 *
	 * @param name 名称(忽略大小写)
	 * @return 名称是否存在
	 */
	boolean nameExists(String name);

	/**
	 * 判断名称是否唯一
	 *
	 * @param id   ID
	 * @param name 名称(忽略大小写)
	 * @return 名称是否唯一
	 */
	boolean nameUnique(String id, String name);

	/**
	 * 查找店铺分页
	 *
	 * @param type       类型
	 * @param status     状态
	 * @param isEnabled  是否启用
	 * @param hasExpired 是否过期
	 * @param pageable   分页信息
	 * @return 店铺分页
	 */
	Page<Store> findPage(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找店铺分页
	 *
	 * @param merchantes,     商家
	 * @param storeCategories 店铺分类
	 * @param type            类型
	 * @param status          状态
	 * @param isEnabled       是否启用
	 * @param hasExpired      是否过期
	 * @param pageable        分页信息
	 * @return 店铺分页
	 */
	Page<Store> findPageList(List merchantes,  List storeCategories, Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, String name, String storeCategoryId, Pageable pageable);



	/**
	 * 获取当前登录商家店铺
	 *
	 * @return 当前登录商家店铺，若不存在则返回null
	 */
	Store getCurrent(String userId);

	/**
	 * 增加到期天数
	 *
	 * @param store  店铺
	 * @param amount 值
	 */
	void addEndDays(Store store, int amount);

	/**
	 * 查找店铺数量
	 *
	 * @param type       类型
	 * @param status     状态
	 * @param isEnabled  是否启用
	 * @param hasExpired 是否过期
	 * @return 店铺数量
	 */
	Long count(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired);

	/**
	 * 搜索店铺名称
	 *
	 * @return 店铺关联方式状态
	 */
	List<Store> keywordSearch(String keyword,int count);

}