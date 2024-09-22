package cn.bizspring.cloud.store.common.entity;

import cn.bizspring.cloud.common.core.entity.SortEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * Entity - 快递公司
 * 
 */
@Data
@ApiModel(description = "快递公司")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_express_company")
public class ExpressCompany extends SortEntity<ExpressCompany> {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 网址
	 */
	private String url;

	/**
	 * 代码
	 */
	private String code;

}