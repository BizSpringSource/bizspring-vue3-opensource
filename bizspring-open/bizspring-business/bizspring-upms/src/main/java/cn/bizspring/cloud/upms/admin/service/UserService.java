package cn.bizspring.cloud.upms.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Pageable;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.common.dto.UserDTO;
import cn.bizspring.cloud.upms.common.dto.UserInfo;
import cn.bizspring.cloud.upms.common.dto.UserRegister;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.vo.UserVO;
import org.springframework.http.ResponseEntity;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.List;

/**
 * @author
 */
public interface UserService extends BizService<User> {
	/**
	 * 查询用户信息
	 *
	 * @param user 用户
	 * @return userInfo
	 */
	UserInfo findUserInfo(User user);

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	IPage getUsersWithRolePage(Page page, UserDTO userDTO);

	/**
	 * 删除用户
	 *
	 * @param user 用户
	 * @return boolean
	 */
	Boolean deleteUserById(User user);

	/**
	 * 更新当前用户基本信息
	 *
	 * @param userDto 用户信息
	 * @return Boolean
	 */
	Boolean updateUserInfo(UserDTO userDto);

	/**
	 * 更新指定用户信息
	 *
	 * @param userDto 用户信息
	 * @return
	 */
	Boolean updateUser(UserDTO userDto);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO selectUserVoById(String id);

	/**
	 * 查询上级机构的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	List<User> listAncestorUsers(String username);


	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return ok/fail
	 */
	Boolean saveUser(UserDTO userDto);

	ResponseEntity register(UserRegister userRegister);

	/**
	 * 手机号自动注册
	 * @param mobile
	 * @return
	 */
	User autoRegisterUser(String mobile);

	/**
	 * 无租户查询
	 *
	 * @param user
	 * @return User
	 */
	User getByNoTenant(User user);

	/**
	 * 绑定/解绑手机号
	 * @param userDto
	 */
	void bindPhone(UserDTO userDto);

	/**
	 * 查找用户分页
	 *
	 * @param type
	 *            类型
	 * @param pageable
	 *            分页信息
	 * @return 用户分页
	 */
	cn.bizspring.cloud.common.core.util.Page<User> findPage(Pageable pageable, User.Type type);

	/**
	 * 查找用户分页
	 *
	 * @param type
	 *            类型
	 * @param pageable
	 *            分页信息
	 * @param user
	 *            用户参数
	 * @return 用户分页
	 */
	cn.bizspring.cloud.common.core.util.Page<User> findPageUser(Pageable pageable,User user,User.Type type);

	List<User> findAllBydType(User.Type type);

	List<User> findAllByEntity(User entity);

	List keywordSearchBydType(String keyword,User.Type type,Integer count);

	/**
	 * 获取当前登录用户
	 *
	 * @return 当前登录用户，若不存在则返回null
	 */
	User getCurrent();

	/**
	 * 获取当前登录用户
	 *
	 * @param userClass
	 *            用户类型
	 * @return 当前登录用户，若不存在则返回null
	 */
	<T extends User> T getCurrent(Class<T> userClass);

}
