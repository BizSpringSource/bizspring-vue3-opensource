package cn.bizspring.cloud.order.common.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * Entity - 购物车项
 * 
 */
@Data
@ApiModel(description = "购物车项")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_cart_item")
public class CartItem extends BizEntity<CartItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 数量
	 */
	private Integer quantity;

	private boolean checked;

	/**
	 * SKU
	 */
	@TableField(exist = false)
	private String skuId;

	/**
	 * 购物车
	 */
	@TableField(exist = false)
	private String cartId;

	/**
	 * 增加SKU数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity < 1) {
			return;
		}
		if (getQuantity() != null) {
			setQuantity(getQuantity() + quantity);
		} else {
			setQuantity(quantity);
		}
	}

}