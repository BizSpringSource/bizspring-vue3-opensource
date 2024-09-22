package cn.bizspring.cloud.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Entity;

/**
 * Entity - 序列号
 * 
 */
@Data
@Entity
@TableName("biz_sn")
public class Sn extends BizEntity<Sn> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 商品
		 */
		GOODS(0),

		/**
		 * 订单
		 */
		ORDER(1),

		/**
		 * 订单支付
		 */
		ORDER_PAYMENT(2),

		/**
		 * 订单发货
		 */
		ORDER_SHIPPING(4),

		/**
		 * 支付记录
		 */
		PAYMENT_LOG(6),

		/**
		 * 平台服务
		 */
		PLATFORM_SERVICE(7);
		
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
	@JsonView(BaseView.class)
	private Sn.Type type;

	/**
	 * 末值
	 */
	private Long lastValue;

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	public Sn.Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Sn.Type type) {
		this.type = type;
	}

	/**
	 * 获取末值
	 * 
	 * @return 末值
	 */
	public Long getLastValue() {
		return lastValue;
	}

	/**
	 * 设置末值
	 * 
	 * @param lastValue
	 *            末值
	 */
	public void setLastValue(Long lastValue) {
		this.lastValue = lastValue;
	}

}