package cn.bizspring.cloud.upms.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Filter;
import cn.bizspring.cloud.common.core.util.Sort;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.admin.mapper.MemberMapper;
import cn.bizspring.cloud.upms.admin.mapper.StoreFavoriteMapper;
import cn.bizspring.cloud.upms.admin.service.StoreFavoriteService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.StoreFavorite;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service - 店铺收藏
 * 
 */
@Service
@AllArgsConstructor
public class StoreFavoriteServiceImpl extends BizServiceImpl<StoreFavorite> implements StoreFavoriteService {

	private StoreFavoriteMapper storeFavoriteMapper;
	private MemberMapper memberMapper;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(String memberId, String storeId) {
		return storeFavoriteMapper.exists(memberId, storeId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<StoreFavorite> findList(Member member, Integer count, List<Filter> filters, List<Sort> sorts) {
		QueryWrapper<StoreFavorite> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return storeFavoriteMapper.findList(queryWrapper, member);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<StoreFavorite> findPage(Member member, Pageable pageable) {
		IPage<StoreFavorite> iPage = getPluginsPage(pageable);
		iPage.setRecords(storeFavoriteMapper.findPage(iPage, getPageable(pageable), member));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Member member) {
		return storeFavoriteMapper.count(member);
	}

	/**
	 * 店铺收藏统计
	 * @param store
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Long countStore(Store store, Date beginDate, Date endDate){
		return storeFavoriteMapper.countStore(store, beginDate, endDate);
	}

	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "storeFavorite", condition = "#useCache")
	public List<StoreFavorite> findList(String memberId, Integer count, List<Filter> filters, List<Sort> sorts, boolean useCache) {
		Member member = memberMapper.find(memberId);
		if (memberId != null && member == null) {
			return Collections.emptyList();
		}
		QueryWrapper<StoreFavorite> queryWrapper = createQueryWrapper(null, count, filters, sorts);
		return storeFavoriteMapper.findList(queryWrapper, member);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public boolean save(StoreFavorite storeFavorite) {
		return super.save(storeFavorite);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public StoreFavorite update(StoreFavorite storeFavorite) {
		return super.update(storeFavorite);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public StoreFavorite update(StoreFavorite storeFavorite, String... ignoreProperties) {
		return super.update(storeFavorite, ignoreProperties);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@CacheEvict(value = "storeFavorite", allEntries = true)
	public void delete(StoreFavorite storeFavorite) {
		super.delete(storeFavorite);
	}

}