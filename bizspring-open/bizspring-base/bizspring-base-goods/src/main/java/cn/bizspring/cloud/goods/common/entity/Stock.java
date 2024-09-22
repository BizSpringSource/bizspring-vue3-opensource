package cn.bizspring.cloud.goods.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 库存记录
 * 
 */
@Data
@ApiModel(description = "库存记录")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_stock")
public class Stock extends BizEntity<Stock> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 入库
		 */
		STOCK_IN(0),

		/**
		 * 出库
		 */
		STOCK_OUT(1);
		
		private int value;

		Type(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 类型
	 */
	private Stock.Type type;

	/**
	 * 入库数量
	 */
	private Integer inQuantity;

	/**
	 * 出库数量
	 */
	private Integer outQuantity;

	/**
	 * 当前库存
	 */
	private Integer stock;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * SKU
	 */
	@TableField(exist = false)
	private String skuId;
	@TableField(exist = false)
	private String skuSn;
	@TableField(exist = false)
	private String skuValues;

	/**
	 * 商品
	 */
	@TableField(exist = false)
	private String goodsId;
	@TableField(exist = false)
	private String goodsName;


}