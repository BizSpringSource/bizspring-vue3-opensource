package cn.bizspring.cloud.order.common.entity;

import cn.bizspring.cloud.common.core.entity.SortEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 支付配置
 * 
 */
@Data
@ApiModel(description = "支付配置")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_payment_config")
public class PaymentConfig extends SortEntity<PaymentConfig> {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付方式ID
	 */
	private String modeId;

	/**
	 * 属性
	 */
	@TableField(exist = false)
	private Map<String, String> propertys = new HashMap<>();

	/**
	 * 获取支付方式ID
	 * 
	 * @return 方式ID
	 */
	public String getModeId() {
		return modeId;
	}

	/**
	 * 设置支付方式ID
	 * 
	 * @param modeId
	 *            支付方式ID
	 */
	public void setModeId(String modeId) {
		this.modeId = modeId;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public Map<String, String> getPropertys() {
		return propertys;
	}

	/**
	 * 设置属性
	 * 
	 * @param propertys
	 *            属性
	 */
	public void setPropertys(Map<String, String> propertys) {
		this.propertys = propertys;
	}

	/**
	 * 获取属性值
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	@Transient
	public String getProperty(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getPropertys() != null ? getPropertys().get(name) : null;
	}


}