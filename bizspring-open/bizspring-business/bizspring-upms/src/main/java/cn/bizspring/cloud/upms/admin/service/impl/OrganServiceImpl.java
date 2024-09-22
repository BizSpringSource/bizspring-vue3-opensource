package cn.bizspring.cloud.upms.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.OrganMapper;
import cn.bizspring.cloud.upms.admin.service.OrganRelationService;
import cn.bizspring.cloud.upms.admin.service.OrganService;
import cn.bizspring.cloud.upms.common.dto.OrganTree;
import cn.bizspring.cloud.upms.common.entity.Organ;
import cn.bizspring.cloud.upms.common.entity.OrganRelation;
import cn.bizspring.cloud.upms.common.util.TreeUtil;

import java.util.Comparator;

import cn.bizspring.cloud.common.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构管理
 *
 * @author
 */
@Service
@AllArgsConstructor
public class OrganServiceImpl extends ServiceImpl<OrganMapper, Organ> implements OrganService {
	private final OrganRelationService organRelationService;

	/**
	 * 添加信息机构
	 *
	 * @param org 机构
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveOrgan(Organ org) {
		Organ organ = new Organ();
		BeanUtil.copyProperties(org,organ);
		this.save(organ);
		organRelationService.insertOrganRelation(organ);
		return Boolean.TRUE;
	}


	/**
	 * 删除机构
	 *
	 * @param id 机构 ID
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeOrganById(String id) {
		this.removeById(id);
		//删除机构级联关系
		organRelationService.deleteAllOrganRealtion(id);
		return Boolean.TRUE;
	}

	/**
	 * 更新机构
	 *
	 * @param organ 机构信息
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateOrganById(Organ organ) {
		//更新机构状态
		this.updateById(organ);
		//更新机构关系
		OrganRelation relation = new OrganRelation();
		relation.setAncestor(organ.getParentId());
		relation.setDescendant(organ.getId());
		organRelationService.updateOrganRealtion(relation);
		return Boolean.TRUE;
	}

	/**
	 * 查询全部机构树
	 *
	 * @return 树
	 */
	@Override
	public List<OrganTree> selectTree() {
		return getTree(this.list(Wrappers.emptyWrapper()));
	}

	/**
	 * 构建机构树
	 *
	 * @param entitys
	 * @return
	 */
	private List<OrganTree> getTree(List<Organ> entitys) {
		List<OrganTree> treeList = entitys.stream()
				.filter(entity -> !entity.getId().equals(entity.getParentId()))
				.sorted(Comparator.comparingInt(Organ::getSort))
				.map(entity -> {
					OrganTree node = new OrganTree();
					BeanUtil.copyProperties(entity,node);
					return node;
				}).collect(Collectors.toList());
		return TreeUtil.build(treeList, CommonEnum.PARENT_ID);
	}
}
