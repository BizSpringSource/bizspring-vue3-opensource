package cn.bizspring.cloud.order.admin.mapper;


import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.common.core.dto.ReportDto;
import cn.bizspring.cloud.order.common.entity.OrderLog;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Mapper - 订单记录
 * 
 */
public interface OrderLogMapper extends BizMapper<OrderLog> {

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
	Long count(@Param("countType") String countType,@Param("store")  Store store,@Param("type")  OrderLog.Type type,@Param("beginDate")  Date beginDate,@Param("endDate")  Date endDate);

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
    List<ReportDto> countPlaceOrderCurveReport(@Param("groupBy")String groupBy,@Param("store") Store store,@Param("type") OrderLog.Type type,@Param("beginDate") Date beginDate,@Param("endDate") Date endDate);

}