package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.order.admin.service.OrderItemService;
import cn.bizspring.cloud.order.common.entity.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * Service - 订单项
 * 
 */
@Service
@AllArgsConstructor
public class OrderItemServiceImpl extends BizServiceImpl<OrderItem> implements OrderItemService {

}