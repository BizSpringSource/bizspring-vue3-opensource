package cn.bizspring.cloud.store.common.dto;

import cn.bizspring.cloud.store.common.entity.Store;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity - 店铺
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StoreDto extends Store {
	/**
	 * 商家
	 */
	private String merchantName;

	/**
	 * 店铺分类
	 */
	private String storeCategoryName;
}