package cn.bizspring.cloud.order.admin.service.impl;

import cn.bizspring.cloud.common.core.dto.ReportDto;
import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.order.admin.mapper.OrderLogMapper;
import cn.bizspring.cloud.order.admin.service.OrderLogService;
import cn.bizspring.cloud.order.common.entity.OrderLog;
import cn.bizspring.cloud.store.common.entity.Store;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Service - 订单记录
 * 
 */
@Service
@AllArgsConstructor
public class OrderLogServiceImpl extends BizServiceImpl<OrderLog> implements OrderLogService {

	private OrderLogMapper orderLogMapper;

	 /** 订单统计
	 * @param countType
	 * 		  统计方式
	 * @param store
	 * 			店铺
	 * @param type
	 * 			类型
	 * @param beginDate
	 * 			起始时间
	 * @param endDate
	 * 			结束时间
	 * @return
			 */
	 @Override
	public Long count(String countType, Store store, OrderLog.Type type, Date beginDate, Date endDate){
		return 	orderLogMapper.count(countType,  store, type,  beginDate,  endDate);
	}

	/**
	 * 曲线下单报表
	 * @param groupBy
	 * 			分组方式
	 * @param store
	 * 			当前店铺
	 * @param type
	 * 			类型
	 * @param beginDate
	 * 			开始时间
	 * @param endDate
	 * 			结束时间
	 * @return
	 */
	@Override
	public List<ReportDto> countPlaceOrderCurveReport(String groupBy, Store store, OrderLog.Type type, Date beginDate, Date endDate){
		return 	orderLogMapper.countPlaceOrderCurveReport(groupBy, store, type,  beginDate, endDate);
	}

}