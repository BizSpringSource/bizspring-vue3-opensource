package cn.bizspring.cloud.store.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.admin.mapper.RegionFreightMapper;
import cn.bizspring.cloud.store.admin.service.RegionFreightService;
import cn.bizspring.cloud.store.common.entity.RegionFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Service - 地区运费配置
 * 
 */
@Service
@AllArgsConstructor
public class RegionFreightServiceImpl extends BizServiceImpl<RegionFreight> implements RegionFreightService {

	private RegionFreightMapper regionFreightMapper;

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Store store, Region region) {
		return regionFreightMapper.exists(shippingMethod, store, region);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean unique(String id, ShippingMethod shippingMethod, Store store, Region region) {
		return regionFreightMapper.unique(id, shippingMethod, store, region);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RegionFreight> findPage(ShippingMethod shippingMethod, Store store, Pageable pageable) {
		IPage<RegionFreight> iPage = getPluginsPage(pageable);
		iPage.setRecords(regionFreightMapper.findPage(iPage, getPageable(pageable), shippingMethod, store));
		return super.findPage(iPage, pageable);
	}

}