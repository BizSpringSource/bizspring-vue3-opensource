package cn.bizspring.cloud.upms.admin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.bizspring.cloud.upms.common.security.AuthenticationProvider;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Service - 商家
 * 
 */
public interface MerchantService extends IService<Merchant>, AuthenticationProvider {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	Merchant find(String id);
	
	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<Merchant> findPage(Pageable pageable);
	
	/**
	 * 更新实体对象
	 * 
	 * @return 实体对象
	 */
	Merchant update(Merchant merchant);

	/**
	 * 更新实体对象
	 * 
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	Merchant update(Merchant merchant, String... ignoreProperties);
	
	/**
	 * 保存实体对象
	 * 
	 * @return 实体对象
	 */
	boolean save(Merchant merchant);
	
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找商家
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 商家，若不存在则返回null
	 */
	Merchant findByUsername(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String id, String email);

	/**
	 * 根据E-mail查找商家
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 商家，若不存在则返回null
	 */
	Merchant findByEmail(String email);

	/**
	 * 判断手机是否存在
	 * 
	 * @param mobile
	 *            手机(忽略大小写)
	 * @return 手机是否存在
	 */
	boolean mobileExists(String mobile);

	/**
	 * 判断手机是否唯一
	 * 
	 * @param id
	 *            ID
	 * @param mobile
	 *            手机(忽略大小写)
	 * @return 手机是否唯一
	 */
	boolean mobileUnique(String id, String mobile);

	/**
	 * 根据手机查找商家
	 * 
	 * @param mobile
	 *            手机(忽略大小写)
	 * @return 商家，若不存在则返回null
	 */
	Merchant findByMobile(String mobile);

	/**
	 * 查询商家数量
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 商家数量
	 */
	long count(Date beginDate, Date endDate);
	
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

}