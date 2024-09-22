package cn.bizspring.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.bizspring.cloud.upms.common.entity.OrganRelation;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 */
public interface OrganRelationMapper extends BaseMapper<OrganRelation> {
	/**
	 * 删除机构关系表数据
	 *
	 * @param id 机构ID
	 */
	void deleteOrganRelationsById(String id);

	/**
	 * 更改部分关系表数据
	 *
	 * @param organRelation
	 */
	List<OrganRelation> listOrganRelations(OrganRelation organRelation);

}
