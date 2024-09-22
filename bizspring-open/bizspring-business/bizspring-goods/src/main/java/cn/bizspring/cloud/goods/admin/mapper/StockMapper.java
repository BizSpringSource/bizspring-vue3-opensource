package cn.bizspring.cloud.goods.admin.mapper;

import java.util.Date;
import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.goods.common.entity.Stock;
import cn.bizspring.cloud.store.common.entity.Store;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 库存记录
 * 
 */
public interface StockMapper extends BizMapper<Stock> {

	/**
	 * 入库出库统计
	 * @param type
	 * @param store
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	Long count( @Param("type") Stock.Type type,  @Param("store") Store store, @Param("beginDate")  Date beginDate, @Param("endDate")  Date endDate);
}