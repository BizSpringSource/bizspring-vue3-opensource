package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.dto.ReportDto;
import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.order.common.entity.OrderLog;
import cn.bizspring.cloud.store.common.entity.Store;

import java.util.Date;
import java.util.List;

/**
 * Service - 订单记录
 * 
 */
public interface OrderLogService extends BizService<OrderLog> {

	/**
	 * 订单统计
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
    Long count(String countType, Store store, OrderLog.Type type, Date beginDate, Date endDate);


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
	List<ReportDto> countPlaceOrderCurveReport(String groupBy, Store store, OrderLog.Type type, Date beginDate, Date endDate);

}