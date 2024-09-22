package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.Organ;
import cn.bizspring.cloud.upms.common.entity.OrganRelation;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface OrganRelationService extends IService<OrganRelation> {

	/**
	 * 新建机构关系
	 *
	 * @param organ 机构
	 */
	void insertOrganRelation(Organ organ);

	/**
	 * 通过ID删除机构关系
	 *
	 * @param id
	 */
	void deleteAllOrganRealtion(String id);

	/**
	 * 更新机构关系
	 *
	 * @param relation
	 */
	void updateOrganRealtion(OrganRelation relation);
}
