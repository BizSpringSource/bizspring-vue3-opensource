package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.upms.common.dto.UserDTO;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface UserMapper extends BizMapper<User> {
	/**
	 * 无租户查询
	 *
	 * @param user
	 * @return User
	 */
	@SqlParser(filter=true)
	User getByNoTenant(User user);

	/**
	 * 通过merchant，member 查找用户分页
	 * @param iPage
	 *            分页信息
	 * @return 订单分页
	 */
	List<User> findPage(IPage<User> iPage, @Param("queryWrapper") QueryWrapper<User> queryWrapper,@Param("type")User.Type type);

	/**
	 * 通过merchant，member 查找用户分页
	 * @param iPage
	 *            分页信息
	 * @return 订单分页
	 */
	List<User> findPageUser(IPage<User> iPage, @Param("queryWrapper") QueryWrapper<User> queryWrapper,@Param("user")User user,@Param("type")User.Type type);


	/**
	 * 分页查询用户信息（含角色）
	 *
	 * @param page      分页
	 * @param userDTO   查询参数
	 * @return list
	 */
	IPage<List<UserVO>> getUserVosPage(Page page, @Param("query") UserDTO userDTO);

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return userVo
	 */
	UserVO getUserVoById(String id);

	/**
	 * 查找不通类型用户
	 * @param type
	 *            用户类型
	 * @return 订单分页
	 */
    List<User> findAllBydType(@Param("type")User.Type type);

	/**
	 * 查找不通类型用户
	 * @param entity
	 *        用户类型
	 * @return 订单分页
	 */
	List<User> findAllByEntity(@Param("entity")User entity);

	/**
	 * 查找不通类型用户
	 * @param type
	 *            用户类型
	 * @return 订单分页
	 */
	List<User> keywordSearchBydType(@Param("keyword")String keyword,@Param("type")User.Type type,@Param("count")Integer count);



	/**
	 * 自动注册用户
	 * @param entity
	 *        用户类型
	 * @return 订单分页
	 */
	void autoRegisterUser(@Param("entity")User entity);

	/**
	 * 添加Admin用户
	 * @param entity
	 *        用户类型
	 */
	void saveAdmin(@Param("entity")User entity);
}
