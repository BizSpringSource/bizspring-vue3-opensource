package cn.bizspring.cloud.store.admin.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.store.admin.mapper.RegionFreightMapper;
import cn.bizspring.cloud.store.admin.mapper.DefaultFreightMapper;
import cn.bizspring.cloud.store.admin.mapper.ShippingMethodMapper;
import cn.bizspring.cloud.store.admin.service.ShippingMethodService;
import cn.bizspring.cloud.store.common.entity.RegionFreight;
import cn.bizspring.cloud.store.common.entity.DefaultFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import cn.bizspring.cloud.upms.common.entity.Address;
import cn.bizspring.cloud.upms.common.feign.FeignRegionService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 配送方式
 * 
 */
@Service
@AllArgsConstructor
public class ShippingMethodServiceImpl extends BizServiceImpl<ShippingMethod> implements ShippingMethodService {

	private DefaultFreightMapper defaultFreightMapper;
	private ShippingMethodMapper shippingMethodMapper;
	private FeignRegionService feignRegionService;
	private RegionFreightMapper regionFreightMapper;

	
	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Region region, Integer weight) {
		DefaultFreight defaultFreight = defaultFreightMapper.findDefault(shippingMethod, store);
		BigDecimal firstPrice = defaultFreight != null ? defaultFreight.getFirstPrice() : BigDecimal.ZERO;
		BigDecimal continuePrice = defaultFreight != null ? defaultFreight.getContinuePrice() : BigDecimal.ZERO;
		Integer firstWeight = defaultFreight != null ? defaultFreight.getFirstWeight() : 0;
		Integer continueWeight = defaultFreight != null ? defaultFreight.getContinueWeight() : 1;
		if (region != null && CollectionUtils.isNotEmpty(getRegionFreights(shippingMethod))) {
			List<Region> regions = new ArrayList<>();

			String[] parentIds = region.getParentIds();
			for(String string :parentIds){
				Region region1 =feignRegionService.find(string,SecurityEnum.FROM_IN);
				if(CommonUtils.isNotNull(region1))
					regions.add(region1);
			}
			regions.add(region);
			for (int i = regions.size() - 1; i >= 0; i--) {
				RegionFreight regionFreight = getRegionFreight(shippingMethod,store, regions.get(i));
				if (regionFreight != null) {
					firstPrice = regionFreight.getFirstPrice();
					continuePrice = regionFreight.getContinuePrice();
					firstWeight = regionFreight.getFirstWeight();
					continueWeight = regionFreight.getContinueWeight();
					break;
				}
			}
		}
		if (weight == null || weight <= firstWeight || continuePrice.compareTo(BigDecimal.ZERO) == 0) {
			return firstPrice;
		} else {
			double contiuneWeightCount = Math.ceil((weight - firstWeight) / (double) continueWeight);
			return firstPrice.add(continuePrice.multiply(new BigDecimal(String.valueOf(contiuneWeightCount))));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal calculateFreight(ShippingMethod shippingMethod, Store store, Address address, Integer weight) {
		Region region = feignRegionService.find(address.getRegionId(), SecurityEnum.FROM_IN);
		return calculateFreight(shippingMethod, store, address != null ? region : null, weight);
	}

	@Override
	@Transactional
	public boolean save(ShippingMethod shippingMethod) {
		return super.save(shippingMethod);
	}
	
	@Override
	@Transactional
	public ShippingMethod update(ShippingMethod shippingMethod) {
		return super.update(shippingMethod);
	}

	@Override
	@Transactional
	public ShippingMethod update(ShippingMethod shippingMethod, String... ignoreProperties) {
		return super.update(shippingMethod, ignoreProperties);
	}
	
	@Override
	@Transactional
	public void delete(String id) {
		shippingMethodMapper.delete(Arrays.asList(id));
	}

	@Override
	@Transactional
	public void delete(String... ids) {
		shippingMethodMapper.delete(Arrays.asList(ids));
	}

	@Override
	@Transactional
	public void delete(ShippingMethod shippingMethod) {
		shippingMethodMapper.delete(Arrays.asList(shippingMethod.getId()));
	}

	/**
	 * 获取地区运费配置
	 *
	 * @param shippingMethod
	 *            配送方式
	 * @return 地区运费配置
	 */
	public List<RegionFreight> getRegionFreights(ShippingMethod shippingMethod) {
		List<RegionFreight> regionFreights = regionFreightMapper.findList("shipping_method_id",shippingMethod.getId());
		if (CollectionUtils.isEmpty(regionFreights)) {
			return null;
		}
		return regionFreights;
	}

	/**
	 * 获取地区运费配置
	 *
	 * @param store
	 *            店铺
	 * @param region
	 *            地区
	 * @return 地区运费配置
	 */
	public RegionFreight getRegionFreight(ShippingMethod shippingMethod ,Store store, Region region) {
		List<RegionFreight> regionFreights = regionFreightMapper.findList("shipping_method_id",shippingMethod.getId());
		if (region == null || store == null || CollectionUtils.isEmpty(regionFreights)) {
			return null;
		}
		for (RegionFreight regionFreight : regionFreights) {
			if (regionFreight.getRegionId() != null&&regionFreight.getStoreId()!= null && store.getId().equals(regionFreight.getStoreId()) && regionFreight.getRegionId().equals(region.getId())) {
				return regionFreight;
			}
		}
		return null;
	}

}