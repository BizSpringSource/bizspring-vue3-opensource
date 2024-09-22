package cn.bizspring.cloud.common.core.service;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service - 基类
 * 
 */
public interface BizService<T extends BizEntity> extends IService<T> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(String id);

	/**
	 * 查找所有实体对象集合
	 * 
	 * @return 所有实体对象集合
	 */
	List<T> findAll();

	/**
	 * 查找实体对象集合
	 * 
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	List<T> findList(String... ids);

	/**
	 * 查找实体对象集合
	 *
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer count, List<Filter> filters, List<Sort> sorts);

	/**
	 * 查找实体对象集合
	 * 
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param sorts
	 *            排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer first, Integer count, List<Filter> filters, List<Sort> sorts);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(Pageable pageable);

	/**
	 * 根据实体对象携带参数 查询分页对象
	 * @param pageable
	 * 			分页信息
	 * @param entity
	 * @return 实体对象分页
	 */
	Page<T> findPageByEntity(Pageable pageable,T entity);

	/**
	 * 根据实体对象携带参数 查询所有对象
	 * @param entity
	 * @return 实体对象分页
	 */
	List<T> findAllByEntity(T entity);

	/**
	 * 判断实体对象是否存在
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象是否存在
	 */
	boolean exists(String id);

	/**
	 * 保存实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	boolean save(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T update(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	T update(T entity, String... ignoreProperties);

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            ID
	 */
	void delete(String id);

	/**
	 * 删除实体对象
	 * 
	 * @param ids
	 *            ID
	 */
	void delete(String... ids);

	/**
	 * 删除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void delete(T entity);

}