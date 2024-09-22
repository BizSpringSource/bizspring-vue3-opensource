package cn.bizspring.cloud.order.common.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.bizspring.cloud.goods.common.entity.Goods;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

/**
 * Entity - 订单项
 * 
 */
@Data
@ApiModel(description = "订单项")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_order_item")
public class OrderItem extends BizEntity<OrderItem> {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String sn;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	private String name;

	/**
	 * 价格
	 */
	@JsonView(BaseView.class)
	private BigDecimal price;

	/**
	 * 重量
	 */
	private Integer weight;

	/**
	 * 是否需要物流
	 */
	private Boolean isDelivery;

	/**
	 * 商品Id
	 */
	@TableField(exist = false)
	private String goodsId;

	/**
	 * 缩略图
	 */
	@JsonView(BaseView.class)
	private String thumbnail;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * 已发货数量
	 */
	private Integer shippedQuantity;

	/**
	 * SKU
	 */
	private String skuId;

	/**
	 * 订单
	 */
	private String orderId;

	/**
	 * 规格
	 */
	@JsonView(BaseView.class)
	@TableField(exist = false)
	private List<String> specifications = new ArrayList<>();

	/**
	 * 获取总重量
	 * 
	 * @return 总重量
	 */
	@JsonIgnore
	@Transient
	public int getTotalWeight() {
		if (getWeight() != null && getQuantity() != null) {
			return getWeight() * getQuantity();
		} else {
			return 0;
		}
	}

	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@JsonIgnore
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * 获取可发货数
	 * 
	 * @return 可发货数
	 */
	@JsonIgnore
	@Transient
	public int getShippableQuantity() {
		int shippableQuantity = getQuantity() - getShippedQuantity();
		return shippableQuantity >= 0 ? shippableQuantity : 0;
	}
}