package cn.bizspring.cloud.store.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.store.admin.mapper.GoodsCategoryStoreMapper;
import cn.bizspring.cloud.store.admin.service.GoodsCategoryStoreService;
import cn.bizspring.cloud.store.common.entity.GoodsCategoryStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service - 店铺产品分类
 * 
 */
@Service
@AllArgsConstructor
public class GoodsCategoryStoreServiceImpl extends BizServiceImpl<GoodsCategoryStore> implements  GoodsCategoryStoreService {

	private GoodsCategoryStoreMapper goodsCategoryStoreMapper;


	@Override
	public GoodsCategoryStore find(String id) {
		return goodsCategoryStoreMapper.find(id);
	}

	@Override
	public List<GoodsCategoryStore> findAll() {
		return goodsCategoryStoreMapper.findAll();
	}
}