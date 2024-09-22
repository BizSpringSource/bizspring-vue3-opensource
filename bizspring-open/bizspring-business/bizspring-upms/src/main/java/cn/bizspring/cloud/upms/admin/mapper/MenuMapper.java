package cn.bizspring.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.bizspring.cloud.upms.common.entity.Menu;
import cn.bizspring.cloud.upms.common.vo.MenuVO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param roleId 角色ID
	 * @return
	 */
	List<MenuVO> listMenusByRoleId(String roleId);

}
