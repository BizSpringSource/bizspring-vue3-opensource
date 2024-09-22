package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项
 *
 */
@Data
@ApiModel(description = "字典项")
@EqualsAndHashCode
@TableName("biz_dict_value")
public class DictValue extends BizEntity<DictValue> {
	private static final long serialVersionUID = 1L;

	/**
	 *字典ID
	 */
	@ApiModelProperty(value = "字典ID")
	private String dictId;
	/**
	 * 数据值
	 */
	@ApiModelProperty(value = "数据值")
	private String value;
	/**
	 * 标签名
	 */
	@ApiModelProperty(value = "标签名")
	private String label;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String type;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;
	/**
	 * 备注信息
	 */
	@ApiModelProperty(value = "备注信息")
	private String remark;

}
