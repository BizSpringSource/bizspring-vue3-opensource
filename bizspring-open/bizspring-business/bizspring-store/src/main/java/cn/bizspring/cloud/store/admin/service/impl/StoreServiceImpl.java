package cn.bizspring.cloud.store.admin.service.impl;

import java.util.*;


import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.store.admin.service.*;
import cn.bizspring.cloud.upms.common.feign.FeignMerchantService;
import lombok.AllArgsConstructor;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.store.admin.repository.StoreRepository;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.admin.mapper.StoreMapper;
import cn.bizspring.cloud.store.common.entity.*;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Service - 店铺
 */
@Service
@AllArgsConstructor
public class StoreServiceImpl extends BizServiceImpl<Store> implements StoreService {

	private StoreMapper storeMapper;

	@Override
	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		return storeMapper.exists("name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean nameUnique(String id, String name) {
		return storeMapper.unique(id, "name", name);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Store> findPageList(List merchantes, List storeCategories, Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, String name, String storeCategoryId, Pageable pageable) {
		IPage<Store> iPage = getPluginsPage(pageable);
		iPage.setRecords(storeMapper.findPageList(merchantes, storeCategories, iPage, getPageable(pageable), type, status, isEnabled, hasExpired, name, storeCategoryId));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Store> findPage(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, Pageable pageable) {
		IPage<Store> iPage = getPluginsPage(pageable);
		iPage.setRecords(storeMapper.findPage(iPage, getPageable(pageable), type, status, isEnabled, hasExpired));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Store getCurrent(String userId) {
		Store currentStore = null;
		if (CommonUtils.isNotEmpty(userId)) {
			currentStore = storeMapper.findByMerchant(userId);
			return currentStore;
		}
		return currentStore;
	}

	@Override
	@CacheEvict(value = "authorization", allEntries = true)
	public void addEndDays(Store store, int amount) {
		if (amount == 0) {
			return;
		}

		Date now = new Date();
		Date currentEndDate = store.getEndDate();
		if (amount > 0) {
			store.setEndDate(DateUtils.addDays(currentEndDate.after(now) ? currentEndDate : now, amount));
		} else {
			store.setEndDate(DateUtils.addDays(currentEndDate, amount));
		}
		storeMapper.update(store);
	}

	@Override
	@Transactional
	@CacheEvict(value = {"authorization", "goods", "goodsCategory"}, allEntries = true)
	public Store update(Store store) {
		Map params=new HashMap();
		params.put("store",store);
		return super.update(store);
	}

	@Override
	@Transactional
	@CacheEvict(value = {"authorization", "goods", "goodsCategory"}, allEntries = true)
	public Store update(Store store, String... ignoreProperties) {
		return super.update(store, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = {"authorization", "goods", "goodsCategory"}, allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = {"authorization", "goods", "goodsCategory"}, allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = {"authorization", "goods", "goodsCategory"}, allEntries = true)
	public void delete(Store store) {
		super.delete(store);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired) {
		return storeMapper.count(type, status, isEnabled, hasExpired);
	}

	/**
	 * 搜索店铺名称
	 *
	 * @return 店铺
	 */
	@Override
	public List<Store> keywordSearch(String keyword, int count){
 		return storeMapper.keywordSearch(keyword,count);
	}

}