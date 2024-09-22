package cn.bizspring.cloud.goods.admin.service;

import cn.bizspring.cloud.goods.common.entity.SpecificationItem;

import java.util.List;

/**
 * Service - 规格项
 * 
 */
public interface SpecificationItemService {

	/**
	 * 规格项过滤
	 * 
	 * @param specificationItems
	 *            规格项
	 */
	void filter(List<SpecificationItem> specificationItems);

}