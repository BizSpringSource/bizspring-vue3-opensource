package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.order.admin.mapper.OrderPaymentMapper;
import cn.bizspring.cloud.order.admin.service.OrderPaymentService;
import cn.bizspring.cloud.order.admin.service.SnService;
import cn.bizspring.cloud.order.common.entity.OrderPayment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 订单支付
 * 
 */
@Service
@AllArgsConstructor
public class OrderPaymentServiceImpl extends BizServiceImpl<OrderPayment> implements OrderPaymentService {

	private SnService snService;

	@Override
	@Transactional
	public boolean save(OrderPayment orderPayment) {
		orderPayment.setSn(snService.generate(Sn.Type.ORDER_PAYMENT));
		return super.save(orderPayment);
	}

}