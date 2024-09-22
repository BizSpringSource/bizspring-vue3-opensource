package cn.bizspring.cloud.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 */
@Data
@EqualsAndHashCode
public class ReportDto implements Serializable {
	/**
	 * 报表X轴
	 */
	private String x;
	/**
	 * 报表Y轴
	 */
	private String Y;

	/**
	 * 其他属性1
	 */
	private String column1;
	/**
	 * 其他属性2
	 */
	private String column2;
	/**
	 * 其他属性3
	 */
	private String column3;
	/**
	 * 其他属性4
	 */
	private String column4;
	/**
	 * 其他属性5
	 */
	private String column5;
}
