package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.upms.admin.mapper.RegionMapper;
import cn.bizspring.cloud.upms.admin.service.RegionService;
import cn.bizspring.cloud.upms.common.entity.Region;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service - 地区
 * 
 */
@Service
@AllArgsConstructor
public class RegionServiceImpl extends BizServiceImpl<Region> implements RegionService {

	private RegionMapper regionMapper;

	@Override
	@Transactional(readOnly = true)
	public List<Region> findRoots(String name) {
		return regionMapper.findRoots(null,name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findChildren(Region are, boolean recursive, Integer count) {
		List<Region> regions= regionMapper.findChildren(are, recursive, count);
		List<Region> regionN=new ArrayList<>();
		for (Region region: regions) {
			region.setChildren(getChildPerms(regions,region.getId()));
			regionN.add(region);
	}
		return regionN;
}

	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<Region> getChildPerms(List<Region> list, String parentId)
	{
		List<Region> returnList = new ArrayList<Region>();
		for (Iterator<Region> iterator = list.iterator(); iterator.hasNext();)
		{
			Region t = (Region) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (parentId.equals(t.getParentId()))
			{
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 *
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<Region> list, Region t)
	{
		// 得到子节点列表
		List<Region> childList = getChildList(list, t);
		t.setChildren(childList);
		for (Region tChild : childList)
		{
			if (hasChild(list, tChild))
			{
				recursionFn(list, tChild);
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<Region> getChildList(List<Region> list, Region t)
	{
		List<Region> tlist = new ArrayList<Region>();
		Iterator<Region> it = list.iterator();
		while (it.hasNext())
		{
			Region n = (Region) it.next();
			if (t.getId().equals(n.getParentId()))
			{
				tlist.add(n);
			}
		}
		return tlist;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<Region> list, Region t)
	{
		return getChildList(list, t).size() > 0 ? true : false;
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public boolean save(Region region) {
		setValue(region);
		return super.save(region);
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public Region update(Region region) {
		setValue(region);
		for (Region children : regionMapper.findChildren(region, true, null)) {
			setValue(children);
		}
		return super.update(region);
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public Region update(Region region, String... ignoreProperties) {
		return super.update(region, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "regionPage", allEntries = true)
	public void delete(Region region) {
		super.delete(region);
	}

	/**
	 * 设置值
	 * 
	 * @param region
	 *            地区
	 */
	private void setValue(Region region) {
		if (region == null) {
			return;
		}
		Region parent = regionMapper.find(region.getParentId());
		if (parent != null) {
			region.setFullName(parent.getFullName() + Region.TREE_PATH_SEPARATOR + region.getName());
			region.setTreePath(parent.getTreePath() + parent.getId() + Region.TREE_PATH_SEPARATOR);
		} else {
			region.setFullName(region.getName());
			region.setTreePath(Region.TREE_PATH_SEPARATOR);
		}
		region.setLevel(region.getParentIds().length);
	}

}