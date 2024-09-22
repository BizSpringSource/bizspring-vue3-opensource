package cn.bizspring.cloud.store.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.store.admin.mapper.DefaultFreightMapper;
import cn.bizspring.cloud.store.admin.service.DefaultFreightService;
import cn.bizspring.cloud.store.common.entity.DefaultFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Service - 默认运费配置
 * 
 */
@Service
@AllArgsConstructor
public class DefaultFreightServiceImpl extends BizServiceImpl<DefaultFreight> implements DefaultFreightService {

	private DefaultFreightMapper defaultFreightMapper;
	
	@Override
	public DefaultFreight find(String id) {
		return defaultFreightMapper.find(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Region region) {
		return defaultFreightMapper.exists(shippingMethod, region);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<DefaultFreight> findPage(Store store, Pageable pageable) {
		IPage<DefaultFreight> iPage = getPluginsPage(pageable);
		iPage.setRecords(defaultFreightMapper.findPage(iPage, getPageable(pageable), store));
		return super.findPage(iPage, pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public DefaultFreight find(ShippingMethod shippingMethod, Store store) {
		return defaultFreightMapper.findDefault(shippingMethod, store);
	}

	@Override
	public void update(DefaultFreight defaultFreight, Store store, ShippingMethod shippingMethod) {
		if (!defaultFreight.isNew()) {
			super.update(defaultFreight);
		} else {
			defaultFreight.setStoreId(store.getId());
			defaultFreight.setShippingMethodId(shippingMethod.getId());
			super.save(defaultFreight);
		}
	}

}