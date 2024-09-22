package cn.bizspring.cloud.upms.common.entity;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "字典")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_dict")
public class Dict extends BizEntity<Dict> {
	private static final long serialVersionUID = 1L;

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
	 * 备注信息
	 */
	@ApiModelProperty(value = "备注信息")
	private String remark;

}
