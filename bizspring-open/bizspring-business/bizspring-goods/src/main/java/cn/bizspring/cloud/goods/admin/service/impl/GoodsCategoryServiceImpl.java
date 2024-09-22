package cn.bizspring.cloud.goods.admin.service.impl;

import java.util.*;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.goods.admin.mapper.GoodsCategoryMapper;
import cn.bizspring.cloud.goods.admin.service.GoodsCategoryService;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import cn.bizspring.cloud.store.common.entity.Store;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Service - 商品分类
 * 
 */
@Service
@AllArgsConstructor
public class GoodsCategoryServiceImpl extends BizServiceImpl<GoodsCategory> implements GoodsCategoryService {

	private GoodsCategoryMapper goodsCategoryMapper;

	@Override
	@Transactional(readOnly = true)
	public List<GoodsCategory> findList(Store store, Integer count, List<Filter> filters, List<GoodsCategoryStore> goodsCategoryStores, List<Sort> sorts) {
		QueryWrapper<GoodsCategory> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return goodsCategoryMapper.findList(queryWrapper, store,goodsCategoryStores);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GoodsCategory> integrationToTree(List<GoodsCategory> goodsCategories) {
		List<GoodsCategory> goodsCategoriesN=new ArrayList<>();
		for (GoodsCategory goodsCategory: goodsCategories) {
			if(null==goodsCategory.getParentId()&&0==goodsCategory.getLevel()) {
				goodsCategory.setChildren(getChildPerms(goodsCategories,goodsCategory.getId()));
				goodsCategoriesN.add(goodsCategory);
			}
		}
		return goodsCategoriesN;
	}

	@Override
	@Transactional(readOnly = true)
	public List<GoodsCategory> findRoots() {
		return goodsCategoryMapper.findRoots(null);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "goodsCategory", condition = "#useCache")
	public List<GoodsCategory> findRoots(Integer count, boolean useCache) {
		return goodsCategoryMapper.findRoots(count);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GoodsCategory> findTree() {
		List<GoodsCategory> goodsCategories = goodsCategoryMapper.findChildren(null, true, null);
		List<GoodsCategory> goodsCategoriesN=new ArrayList<>();
		for (GoodsCategory goodsCategory: goodsCategories) {
			if(null==goodsCategory.getParentId()&&0==goodsCategory.getLevel()) {
				goodsCategory.setChildren(getChildPerms(goodsCategories,goodsCategory.getId()));
				goodsCategoriesN.add(goodsCategory);
			}
		}
		return goodsCategoriesN;
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list 分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<GoodsCategory> getChildPerms(List<GoodsCategory> list, String parentId)
	{
		List<GoodsCategory> returnList = new ArrayList<GoodsCategory>();
		for (Iterator<GoodsCategory> iterator = list.iterator(); iterator.hasNext();)
		{
			GoodsCategory t = (GoodsCategory) iterator.next();
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
	private void recursionFn(List<GoodsCategory> list, GoodsCategory t)
	{
		// 得到子节点列表
		List<GoodsCategory> childList = getChildList(list, t);
		t.setChildren(childList);
		for (GoodsCategory tChild : childList)
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
	private List<GoodsCategory> getChildList(List<GoodsCategory> list, GoodsCategory t)
	{
		List<GoodsCategory> tlist = new ArrayList<GoodsCategory>();
		Iterator<GoodsCategory> it = list.iterator();
		while (it.hasNext())
		{
			GoodsCategory n = (GoodsCategory) it.next();
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
	private boolean hasChild(List<GoodsCategory> list, GoodsCategory t)
	{
		return getChildList(list, t).size() > 0 ? true : false;
	}


	@Override
	@Transactional(readOnly = true)
	public List<GoodsCategory> findChildren(GoodsCategory goodsCategory, boolean recursive, Integer count) {
		return goodsCategoryMapper.findChildren(goodsCategory, recursive, count);
	}

	public Set<String> findGoodsCategoryBrands(String id){
		return goodsCategoryMapper.findGoodsCategoryBrands(id);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "goodsCategory", condition = "#useCache")
	public List<GoodsCategory> findChildren(String goodsCategoryId, boolean recursive, Integer count, boolean useCache) {
		GoodsCategory goodsCategory = goodsCategoryMapper.find(goodsCategoryId);
		if (goodsCategoryId != null && goodsCategory == null) {
			return Collections.emptyList();
		}
		return goodsCategoryMapper.findChildren(goodsCategory, recursive, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public boolean save(GoodsCategory goodsCategory) {
		setValue(goodsCategory);
		return super.save(goodsCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public GoodsCategory update(GoodsCategory goodsCategory) {
		setValue(goodsCategory);
		for (GoodsCategory children : goodsCategoryMapper.findChildren(goodsCategory, true, null)) {
			setValue(children);
		}
		return super.update(goodsCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public GoodsCategory update(GoodsCategory goodsCategory, String... ignoreProperties) {
		return super.update(goodsCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "goods", "goodsCategory" }, allEntries = true)
	public void delete(GoodsCategory goodsCategory) {
		super.delete(goodsCategory);
	}

	/**
	 * 设置值
	 * 
	 * @param goodsCategory
	 *            商品分类
	 */
	private void setValue(GoodsCategory goodsCategory) {
		if (goodsCategory == null) {
			return;
		}
		GoodsCategory parent = goodsCategoryMapper.find(goodsCategory.getParentId());
		if (parent != null) {
			goodsCategory.setTreePath(parent.getTreePath() + parent.getId() + GoodsCategory.TREE_PATH_SEPARATOR);
		} else {
			goodsCategory.setTreePath(GoodsCategory.TREE_PATH_SEPARATOR);
		}
		if(CommonUtils.isNotNull(goodsCategory.getParentIds())&&0<goodsCategory.getParentIds().length)
		{
			goodsCategory.setLevel(goodsCategory.getParentIds().length);
		}else{
			goodsCategory.setLevel(0);
		}
	}

}