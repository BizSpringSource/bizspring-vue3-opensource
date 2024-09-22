package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.Menu;
import cn.bizspring.cloud.upms.common.vo.MenuVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author
 */
public interface MenuService extends IService<Menu> {
	/**
	 * 通过角色编号查询URL 权限
	 *
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<MenuVO> findMenuByRoleId(String roleId);

	/**
	 * 级联删除菜单
	 *
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	ResponseEntity removeMenuById(String id);

	/**
	 * 更新菜单信息
	 *
	 * @param menu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(Menu menu);

	/**
	 * 新增菜单
	 * @param menu
	 */
	void saveMenu(Menu menu);
}
