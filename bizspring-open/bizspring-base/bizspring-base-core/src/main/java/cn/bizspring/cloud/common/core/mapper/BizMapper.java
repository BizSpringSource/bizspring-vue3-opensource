package cn.bizspring.cloud.common.core.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * Mappder - 基类
 * 
 */
public interface BizMapper<T> extends BaseMapper<T> {
	
	/**
	 * 查找所有实体对象集合
	 * 
	 * @return 所有实体对象集合
	 */
	List<T> findAll();
	
	/**
	 * 查找实体对象集合
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 实体对象，若不存在则返回null
	 */
	Set<T> findSet(@Param("propertyName")String propertyName, @Param("propertyValue")Object propertyValue);
	
	/**
	 * 判断是否存在
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 是否存在
	 */
	boolean exists(@Param("propertyName")String propertyName, @Param("propertyValue")Object propertyValue);

	/**
	 * 判断是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 是否唯一
	 */
	boolean unique(@Param("id")String id, @Param("propertyName")String propertyName,  @Param("propertyValue")Object propertyValue);

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(@Param("id")String id);

	/**
	 * 根据实体携带参数查找分页对象
	 * @param iPage
	 * @param pageable
	 * @param entity
	 * @return
	 */
	List findPageByEntity(IPage<T> iPage, @Param("queryWrapper")QueryWrapper<T> pageable, @Param("entity")T entity);


	/**
	 * 根据实体携带参数查找所有对象
	 * @param entity
	 * @return
	 */
	List<T> findAllByEntity(@Param("entity")T entity);

	/**
	 * 查找实体对象
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyValue
	 *            属性值
	 * @return 实体对象，若不存在则返回null
	 */
	T findByProperty(@Param("propertyName")String propertyName, @Param("propertyValue")Object propertyValue);

	/**
	 * 持久化实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	boolean save(@Param("entity")T entity);
	
	/**
	 * 批量插入
	 * 
	 * @param entitys
	 * @return
	 */
	int saveBatch(@Param("entitys") List<T> entitys);
	
	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	boolean update(@Param("entity")T entity);

	/**
	 * 删除实体对象
	 * 
	 * @param ids
	 *            ID
	 */
	void delete(@Param("ids")List<String> ids);
	
	/**
	 * 移除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void remove(@Param("entity")T entity);

}