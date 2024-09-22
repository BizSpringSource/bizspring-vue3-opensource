package cn.bizspring.cloud.store.admin.service.impl;

import java.util.List;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.store.admin.mapper.StoreCategoryMapper;
import cn.bizspring.cloud.store.admin.service.StoreCategoryService;
import cn.bizspring.cloud.store.common.entity.StoreCategory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 店铺分类
 * 
 */
@Service
@AllArgsConstructor
public class StoreCategoryServiceImpl extends BizServiceImpl<StoreCategory> implements StoreCategoryService {

	private StoreCategoryMapper storeCategoryMapper;

	@Override
	@Transactional(readOnly = true)
	public List<StoreCategory> findAll() {
		return storeCategoryMapper.findAll();
	}

}