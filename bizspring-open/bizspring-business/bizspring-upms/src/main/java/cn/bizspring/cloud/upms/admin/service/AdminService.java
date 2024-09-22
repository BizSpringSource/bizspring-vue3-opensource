package cn.bizspring.cloud.upms.admin.service;

import cn.bizspring.cloud.upms.common.security.AuthenticationProvider;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.common.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Service - 管理员
 * 
 */
public interface AdminService extends IService<Admin>, AuthenticationProvider {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	Admin find(String id);
	
	/**
	 * 查找实体对象集合
	 * 
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	List<Admin> findList(String... ids);
	
	/**
	 * 更新实体对象
	 * 
	 * @param admin
	 *            实体对象
	 * @return 实体对象
	 */
	Admin update(Admin admin);
	
	/**
	 * 更新实体对象
	 * 
	 * @param admin
	 *            实体对象
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	Admin update(Admin admin, String... ignoreProperties);
	
	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<Admin> findPage(Pageable pageable);
	
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
	 * @param admin
	 *            实体对象
	 */
	void delete(Admin admin);
	

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	Admin findByUsername(String username);

}