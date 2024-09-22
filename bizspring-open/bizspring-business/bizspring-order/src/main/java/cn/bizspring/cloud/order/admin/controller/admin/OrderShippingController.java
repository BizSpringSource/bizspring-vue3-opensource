package cn.bizspring.cloud.order.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.order.admin.service.OrderShippingService;
import cn.bizspring.cloud.order.common.entity.OrderShipping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller - 订单发货
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "order_shipping", tags = "管理 - 订单发货")
@RestController("adminOrderShippingController")
@RequestMapping("/admin/order_shipping")
public class OrderShippingController extends BizController {

	private OrderShippingService orderShippingService;

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable,String sn,String trackingNo,String consignee,String phone,String address,String region,String zipCode ) {
			OrderShipping orderShipping = new OrderShipping();
			if(StringUtils.isNotEmpty(sn)){
				orderShipping.setSn(sn);
			}
			if(StringUtils.isNotEmpty(trackingNo)){
				orderShipping.setTrackingNo(trackingNo);
			}
			if(StringUtils.isNotEmpty(consignee)){
				orderShipping.setConsignee(consignee);
			}
			if(StringUtils.isNotEmpty(phone)){
				orderShipping.setPhone(phone);
			}
			if(StringUtils.isNotEmpty(address)){
				orderShipping.setAddress(address);
			}
			if(StringUtils.isNotEmpty(region)){
				orderShipping.setRegion(region);
			}
			if(StringUtils.isNotEmpty(zipCode)){
				orderShipping.setZipCode(zipCode);
			}

			return R.ok(orderShippingService.findPageByEntity(pageable,orderShipping));
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		orderShippingService.delete(ids);
		return R.OK;
	}

}