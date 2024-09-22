package cn.bizspring.cloud.order.admin.service.impl;


import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.order.admin.service.OrderShippingService;
import cn.bizspring.cloud.order.admin.service.SnService;
import cn.bizspring.cloud.order.common.entity.OrderShipping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service - 订单发货
 * 
 */
@Service
@AllArgsConstructor
public class OrderShippingServiceImpl extends BizServiceImpl<OrderShipping> implements OrderShippingService {

	private SnService snService;

	@Override
	@Transactional
	public boolean save(OrderShipping orderShipping) {
		orderShipping.setSn(snService.generate(Sn.Type.ORDER_SHIPPING));
		return super.save(orderShipping);
	}

}