package cn.bizspring.cloud.upms.admin.service;

import cn.bizspring.cloud.upms.common.security.AuthenticationProvider;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.common.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
/**
 * Service - 会员
 * 
 */
public interface MemberService extends IService<Member>, AuthenticationProvider {


	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	Member find(String id);
	
	/**
	 * 查找实体对象集合
	 * 
	 * @param ids
	 *            ID
	 * @return 实体对象集合
	 */
	List<Member> findList(String... ids);
	
	/**
	 * 删除实体对象
	 * 
	 * @param ids
	 *            ID
	 */
	void delete(String... ids);
	
	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param ignoreProperties
	 *            忽略属性
	 * @return 实体对象
	 */
	Member update(Member entity, String... ignoreProperties);
	
	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<Member> findPage(Pageable pageable);
	
	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);

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
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByEmail(String email);

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
	 * 根据手机查找会员
	 * 
	 * @param mobile
	 *            手机(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByMobile(String mobile);

	/**
	 * 根据member实体携带参数 查找会员分页
	 *
	 * @param pageable
	 *            分页信息
	 * @return 会员分页
	 */
	Page<Member> findPageByEntity(Pageable pageable,Member member);

	/**
	 * 查询会员数量
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	long count(Date beginDate, Date endDate);

}