package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.dto.OrganTree;
import cn.bizspring.cloud.upms.common.entity.Organ;

import java.util.List;

/**
 * <p>
 * 机构管理 服务类
 * </p>
 *
 * @author
 */
public interface OrganService extends IService<Organ> {

	/**
	 * 查询机构树菜单
	 *
	 * @return 树
	 */
	List<OrganTree> selectTree();

	/**
	 * 添加信息机构
	 *
	 * @param organ
	 * @return
	 */
	Boolean saveOrgan(Organ organ);

	/**
	 * 删除机构
	 *
	 * @param id 机构 ID
	 * @return 成功、失败
	 */
	Boolean removeOrganById(String id);

	/**
	 * 更新机构
	 *
	 * @param organ 机构信息
	 * @return 成功、失败
	 */
	Boolean updateOrganById(Organ organ);

}
