package cn.bizspring.cloud.order.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.order.admin.service.OrderService;
import cn.bizspring.cloud.order.admin.service.OrderShippingService;
import cn.bizspring.cloud.order.common.entity.Order;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.*;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import cn.bizspring.cloud.common.core.util.R;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller - 订单
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "order", tags = "管理 - 订单")
@RestController("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BizController {

	private OrderService orderService;
	private FeignMemberService feignMemberService;
	private FeignUserService feignUserService;
	private FeignStoreService feignStoreService;

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(String  sn, String  memberName, String  consignee, String  address, String  zipCode, String  phone, Order.Status status, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		Map data = new HashMap<>(2);
		Order order=new Order();
		if(CommonUtils.isNotEmpty(sn)){
			order.setSn(sn);
		}
		if(CommonUtils.isNotEmpty(zipCode)){
			order.setZipCode(zipCode);
		}
		if(CommonUtils.isNotEmpty(phone)){
			order.setPhone(phone);
		}
		order.setConsignee(consignee);
		order.setAddress(address);
		order.setStatus(status);
		data.put("statuses", Order.Status.values());
		data.put("memberName", memberName);
		data.put("isPendingReceive", isPendingReceive);
		data.put("isPendingRefunds", isPendingRefunds);
		data.put("isAllocatedStock", isAllocatedStock);
		data.put("hasExpired", hasExpired);
		List<User> users= feignUserService.findAll(SecurityEnum.FROM_IN);
		List<Store> stores=feignStoreService.findAll(SecurityEnum.FROM_IN);

		Member member = CommonUtils.isNotEmpty(memberName)?feignMemberService.findByUsername(memberName, SecurityEnum.FROM_IN):null;
		if (StringUtils.isNotEmpty(memberName) && null == member) {
			data.put("data", Page.emptyPage(pageable));
		} else {
			if(null != member){
				order.setMemberId(member.getId());
			}
			data.put("data", orderService.findPage(order ,users,stores,isPendingReceive, isAllocatedStock, hasExpired, pageable));
		}
		return R.ok(data);
	}

}