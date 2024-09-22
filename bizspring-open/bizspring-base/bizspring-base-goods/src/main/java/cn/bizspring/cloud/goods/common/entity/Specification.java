package cn.bizspring.cloud.goods.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.bizspring.cloud.common.core.entity.SortEntity;
/**
 * Entity - 规格
 * 
 */
@Data
@ApiModel(description = "规格")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_specification")
public class Specification extends SortEntity<Specification> {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 绑定分类
	 */
	@NotNull(groups = Save.class)
	@TableField(exist = false)
	private String goodsCategoryId;
	@TableField(exist = false)
	private String goodsCategoryName;

	/**
	 * 可选项
	 */
	@TableField(exist = false)
	private List<String> options = new ArrayList<>();

}