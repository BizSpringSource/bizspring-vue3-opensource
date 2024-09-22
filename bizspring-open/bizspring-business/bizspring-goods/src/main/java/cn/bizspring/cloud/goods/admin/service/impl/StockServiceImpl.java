package cn.bizspring.cloud.goods.admin.service.impl;

import cn.bizspring.cloud.common.core.service.BizServiceImpl;
import cn.bizspring.cloud.goods.admin.mapper.StockMapper;
import cn.bizspring.cloud.goods.admin.service.StockService;
import cn.bizspring.cloud.goods.common.entity.Stock;
import cn.bizspring.cloud.store.common.entity.Store;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service - 库存记录
 * 
 */
@Service
@AllArgsConstructor
public class StockServiceImpl extends BizServiceImpl<Stock> implements StockService {

	private StockMapper stockMapper;

	/**
	 * 入库出库统计
	 * @param type
	 * @param store
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Long count(Stock.Type type, Store store, Date beginDate, Date endDate){
		return stockMapper.count(type,store,beginDate,endDate);
	}

}