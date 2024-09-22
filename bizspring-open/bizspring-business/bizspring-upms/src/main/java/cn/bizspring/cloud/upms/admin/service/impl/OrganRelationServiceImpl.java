package cn.bizspring.cloud.upms.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.service.OrganRelationService;
import cn.bizspring.cloud.upms.common.entity.Organ;
import cn.bizspring.cloud.upms.common.entity.OrganRelation;
import cn.bizspring.cloud.upms.admin.mapper.OrganRelationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class OrganRelationServiceImpl extends ServiceImpl<OrganRelationMapper, OrganRelation> implements OrganRelationService {
	private final OrganRelationMapper organRelationMapper;

	/**
	 * 维护机构关系
	 *
	 * @param organ 机构
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertOrganRelation(Organ organ) {
		//增加机构关系表
		OrganRelation condition = new OrganRelation();
		condition.setDescendant(organ.getParentId());
		List<OrganRelation> relationList = organRelationMapper
			.selectList(Wrappers.<OrganRelation>query().lambda()
				.eq(OrganRelation::getDescendant, organ.getParentId()))
			.stream().map(relation -> {
				relation.setTenantId(null);
				relation.setDescendant(organ.getId());
				return relation;
			}).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(relationList)) {
			this.saveBatch(relationList);
		}

		//自己也要维护到关系表中
		OrganRelation own = new OrganRelation();
		own.setDescendant(organ.getId());
		own.setAncestor(organ.getId());
		organRelationMapper.insert(own);
	}

	/**
	 * 通过ID删除机构关系
	 *
	 * @param id
	 */
	@Override
	public void deleteAllOrganRealtion(String id) {
		baseMapper.deleteOrganRelationsById(id);
	}

	/**
	 * 更新机构关系
	 *
	 * @param relation
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateOrganRealtion(OrganRelation relation) {
		List<OrganRelation> relationList = baseMapper.listOrganRelations(relation)
				.stream().map(relation2 -> {
			relation2.setTenantId(null);
			return relation2;
		}).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(relationList)) {
			this.saveBatch(relationList);
		}
	}

}
