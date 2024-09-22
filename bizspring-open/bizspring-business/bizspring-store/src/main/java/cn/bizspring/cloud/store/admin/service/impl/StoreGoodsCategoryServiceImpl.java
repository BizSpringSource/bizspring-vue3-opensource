package cn.bizspring.cloud.store.admin.service.impl;

import java.util.*;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.store.admin.mapper.StoreMapper;
import cn.bizspring.cloud.store.admin.mapper.StoreGoodsCategoryMapper;
import cn.bizspring.cloud.store.admin.service.StoreGoodsCategoryService;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.entity.StoreGoodsCategory;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Service - 店铺商品分类
 * 
 */
@Service
@AllArgsConstructor
public class StoreGoodsCategoryServiceImpl extends BizServiceImpl<StoreGoodsCategory> implements StoreGoodsCategoryService {

	private StoreGoodsCategoryMapper storeGoodsCategoryMapper;
	private StoreService storeService;

	@Override
	@Transactional(readOnly = true)
	public List<StoreGoodsCategory> findTree(Store store) {
		List<StoreGoodsCategory> result = storeGoodsCategoryMapper.findChildren(null, store, true, null);
		List<StoreGoodsCategory> storeGoodsCategories=new ArrayList<>();
			for (StoreGoodsCategory storeGoodsCategory: result) {
				if(null==storeGoodsCategory.getParentId()&&0==storeGoodsCategory.getLevel()) {
					storeGoodsCategory.setChildren(getChildPerms(result,storeGoodsCategory.getId()));
					storeGoodsCategories.add(storeGoodsCategory);
				}
			}

		return storeGoodsCategories;
	}


	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<StoreGoodsCategory> getChildPerms(List<StoreGoodsCategory> list, String parentId)
	{
		List<StoreGoodsCategory> returnList = new ArrayList<StoreGoodsCategory>();
		for (Iterator<StoreGoodsCategory> iterator = list.iterator(); iterator.hasNext();)
		{
			StoreGoodsCategory t = (StoreGoodsCategory) iterator.next();
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
	private void recursionFn(List<StoreGoodsCategory> list, StoreGoodsCategory t)
	{
		// 得到子节点列表
		List<StoreGoodsCategory> childList = getChildList(list, t);
		t.setChildren(childList);
		for (StoreGoodsCategory tChild : childList)
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
	private List<StoreGoodsCategory> getChildList(List<StoreGoodsCategory> list, StoreGoodsCategory t)
	{
		List<StoreGoodsCategory> tlist = new ArrayList<StoreGoodsCategory>();
		Iterator<StoreGoodsCategory> it = list.iterator();
		while (it.hasNext())
		{
			StoreGoodsCategory n = (StoreGoodsCategory) it.next();
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
	private boolean hasChild(List<StoreGoodsCategory> list, StoreGoodsCategory t)
	{
		return getChildList(list, t).size() > 0 ? true : false;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreGoodsCategory> findChildren(StoreGoodsCategory storeGoodsCategory, Store store, boolean recursive, Integer count) {
		List<StoreGoodsCategory> result = storeGoodsCategoryMapper.findChildren(storeGoodsCategory, store, recursive, count);
		sort(result);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StoreGoodsCategory> findPage(Store store, Pageable pageable) {
		IPage<StoreGoodsCategory> iPage = getPluginsPage(pageable);
		iPage.setRecords(storeGoodsCategoryMapper.findPage(iPage, getPageable(pageable), store));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public boolean save(StoreGoodsCategory storeGoodsCategory) {
		setValue(storeGoodsCategory);
		return super.save(storeGoodsCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public StoreGoodsCategory update(StoreGoodsCategory storeGoodsCategory) {
		setValue(storeGoodsCategory);
		for (StoreGoodsCategory children : storeGoodsCategoryMapper.findChildren(storeGoodsCategory, storeService.getCurrent(SecurityUtils.getUserId()), true, null)) {
			setValue(children);
		}
		return super.update(storeGoodsCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public StoreGoodsCategory update(StoreGoodsCategory storeGoodsCategory, String... ignoreProperties) {
		return super.update(storeGoodsCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "storeGoodsCategory" }, allEntries = true)
	public void delete(StoreGoodsCategory storeGoodsCategory) {
		super.delete(storeGoodsCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param storeGoodsCategory
	 *            店铺商品分类
	 */
	private void setValue(StoreGoodsCategory storeGoodsCategory) {
		if (storeGoodsCategory == null) {
			return;
		}
		StoreGoodsCategory parent = storeGoodsCategoryMapper.find(storeGoodsCategory.getParentId());
		if (parent != null) {
			storeGoodsCategory.setTreePath(parent.getTreePath() + parent.getId() + StoreGoodsCategory.TREE_PATH_SEPARATOR);
		} else {
			storeGoodsCategory.setTreePath(StoreGoodsCategory.TREE_PATH_SEPARATOR);
		}
		storeGoodsCategory.setLevel(storeGoodsCategory.getParentIds().length);
	}

	/**
	 * 排序店铺商品分类
	 * 
	 * @param storeGoodsCategorys
	 *            店铺商品分类
	 */
	private void sort(List<StoreGoodsCategory> storeGoodsCategorys) {
		if (CollectionUtils.isEmpty(storeGoodsCategorys)) {
			return;
		}
		final Map<String, Integer> orderMap = new HashMap<>();
		for (StoreGoodsCategory shopGoodsCategory : storeGoodsCategorys) {
			orderMap.put(shopGoodsCategory.getId(), shopGoodsCategory.getSort());
		}
		Collections.sort(storeGoodsCategorys, new Comparator<StoreGoodsCategory>() {
			@Override
			public int compare(StoreGoodsCategory storeGoodsCategory1, StoreGoodsCategory storeGoodsCategory2) {
				String[] ids1 = (String[]) ArrayUtils.add(storeGoodsCategory1.getParentIds(), storeGoodsCategory1.getId());
				String[] ids2 = (String[]) ArrayUtils.add(storeGoodsCategory2.getParentIds(), storeGoodsCategory2.getId());
				Iterator<String> iterator1 = Arrays.asList(ids1).iterator();
				Iterator<String> iterator2 = Arrays.asList(ids2).iterator();
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				while (iterator1.hasNext() && iterator2.hasNext()) {
					String id1 = iterator1.next();
					String id2 = iterator2.next();
					Integer order1 = orderMap.get(id1);
					Integer order2 = orderMap.get(id2);
					compareToBuilder.append(order1, order2).append(id1, id2);
					if (!iterator1.hasNext() || !iterator2.hasNext()) {
						compareToBuilder.append(storeGoodsCategory1.getLevel(), storeGoodsCategory2.getLevel());
					}
				}
				return compareToBuilder.toComparison();
			}
		});
	}
	
}