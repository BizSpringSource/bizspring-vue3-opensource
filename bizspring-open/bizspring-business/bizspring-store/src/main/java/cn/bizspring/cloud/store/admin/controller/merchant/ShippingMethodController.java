package cn.bizspring.cloud.store.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.store.admin.service.DefaultFreightService;
import cn.bizspring.cloud.store.admin.service.ShippingMethodService;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.DefaultFreight;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 配送方式
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "shipping_method", tags = "商家 - 配送方式")
@RestController("merchantShippingMethodController")
@RequestMapping("/merchant/shipping_method")
public class ShippingMethodController extends BizController {

	private ShippingMethodService shippingMethodService;
	private DefaultFreightService defaultFreightService;
	private StoreService storeService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		Page<ShippingMethod> page = shippingMethodService.findPage(pageable);
		if(CommonUtils.isNotNull(page)&& CollectionUtils.isNotEmpty(page.getContent())){
			List<ShippingMethod> shippingMethodList = page.getContent();
			for (ShippingMethod shippingMethod: shippingMethodList) {
				DefaultFreight  defaultFreight=	defaultFreightService.find(shippingMethod,currentStore);
				if (CommonUtils.isNotNull(defaultFreight)){
					shippingMethod.setDefaultFreight(defaultFreight);
				}
			}
			page.setContent(shippingMethodList);
		}
		return R.ok(page);
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String id) {
		ShippingMethod shippingMethod = shippingMethodService.find(id);
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		Map<String, Object> data = new HashMap<>(2);
		if (shippingMethod == null) {
			return R.failed();
		}
		DefaultFreight defaultFreight = defaultFreightService.find(shippingMethod, currentStore);
		if (null != defaultFreight) {
			shippingMethod.setDefaultFreight(defaultFreight);
		}
		data.put("obj", shippingMethod);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(@RequestBody DefaultFreight defaultFreightForm) {
		Store currentStore=storeService.getCurrent(SecurityUtils.getUserId());
		ShippingMethod shippingMethod = shippingMethodService.find(defaultFreightForm.getShippingMethodId());
		if (shippingMethod == null) {
			return R.INVALID_ENTITY;
		}
		defaultFreightService.update(defaultFreightForm, currentStore, shippingMethod);
		return R.OK;
	}

}