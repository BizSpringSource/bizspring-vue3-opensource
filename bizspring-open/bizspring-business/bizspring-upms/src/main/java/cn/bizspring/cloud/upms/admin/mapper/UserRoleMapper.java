package cn.bizspring.cloud.upms.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.bizspring.cloud.upms.common.entity.UserRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
	/**
	 * 根据用户Id删除该用户的角色关系
	 *
	 */
	Boolean deleteByUserId(@Param("userId") String userId);
}
