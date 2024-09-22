package cn.bizspring.cloud.store.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.store.admin.service.RegionFreightService;
import cn.bizspring.cloud.store.admin.service.ShippingMethodService;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.RegionFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Region;
import cn.bizspring.cloud.upms.common.feign.FeignRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 地区运费配置
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "region_freight", tags = "商家 - 地区运费配置")
@RestController("merchantRegionFreightController")
@RequestMapping("/merchant/region_freight")
public class RegionFreightController extends BizController {

	private RegionFreightService regionFreightService;
	private ShippingMethodService shippingMethodService;
	private FeignRegionService feignRegionService;
	private StoreService storeService;

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add(String shippingMethodId) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("regions", feignRegionService.findChildren(null,true,null,SecurityEnum.FROM_IN));
		data.put("shippingMethod",shippingMethodService.find(shippingMethodId));
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody RegionFreight regionFreightForm) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		ShippingMethod shippingMethod = shippingMethodService.find(regionFreightForm.getShippingMethodId());
		Region region = feignRegionService.find(regionFreightForm.getRegionId(),SecurityEnum.FROM_IN);
		regionFreightForm.setStoreId(currentStore.getId());
		if (!isValid(regionFreightForm, BizEntity.Save.class)) {
			return R.INVALID_ENTITY;
		}
		if (regionFreightService.exists(shippingMethod, currentStore, region)) {
			return R.INVALID_ENTITY;
		}
		regionFreightService.save(regionFreightForm);
		return R.OK;
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String id) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("regions", feignRegionService.findChildren(null,true,null,SecurityEnum.FROM_IN));
		RegionFreight regionFreight = regionFreightService.find(id);
		if (regionFreight == null) {
			return R.failed();
		}
		data.put("obj",regionFreight);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(@RequestBody RegionFreight regionFreightForm) {
		RegionFreight	regionFreight = regionFreightService.find(regionFreightForm.getId());
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		ShippingMethod shippingMethod = shippingMethodService.find(regionFreightForm.getRegionId());
		Region region = feignRegionService.find(regionFreightForm.getRegionId(),SecurityEnum.FROM_IN);
		if (regionFreight == null) {
			return R.INVALID_ENTITY;
		}
		if (!isValid(regionFreightForm, BizEntity.Update.class)) {
			return R.INVALID_ENTITY;
		}
		if (regionFreightService.unique(regionFreight.getId(),shippingMethod, currentStore, region)) {
			return R.INVALID_ENTITY;
		}
		BeanUtils.copyProperties(regionFreightForm, regionFreight, "id", "store", "shippingMethod");
		regionFreightService.update(regionFreight);
		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(String shippingMethodId,Pageable pageable) {
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		Map<String, Object> data = new HashMap<>(2);
		data.put("shippingMethod", shippingMethod);
		Page<RegionFreight> page = regionFreightService.findPage(shippingMethod, currentStore, pageable);
		if(CommonUtils.isNotNull(page)&& CollectionUtils.isNotEmpty(page.getContent())){
			List<RegionFreight> regionFreights = page.getContent();
			for (RegionFreight  regionFreight : regionFreights){
				Region region = feignRegionService.find(regionFreight.getRegionId(),SecurityEnum.FROM_IN);
				if(CommonUtils.isNotNull(region)){
					regionFreight.setRegion(region);
				}
			}
			page.setContent(regionFreights);
		}

		data.put("data",page);
		return R.ok(data);
	}

}