package cn.bizspring.cloud.upms.common.entity;

import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.bizspring.cloud.common.core.entity.SortEntity;

import javax.persistence.Transient;

/**
 * Entity - 方式配置
 * 
 */
@Data
@ApiModel(description = "方式配置")
@EqualsAndHashCode(callSuper = true)
public class Config extends SortEntity<Config> {

	private static final long serialVersionUID = 1L;

	/**
	 * 方式ID
	 */
	private String modeId;

	/**
	 * 是否启用
	 */
	private Boolean isEnabled;

	/**
	 * 属性
	 */
	@TableField(exist = false)
	private Map<String, String> propertys = new HashMap<>();

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