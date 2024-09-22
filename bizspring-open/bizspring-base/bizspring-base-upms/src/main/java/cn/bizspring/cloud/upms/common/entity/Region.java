package cn.bizspring.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

import cn.bizspring.cloud.common.core.entity.SortEntity;
/**
 * Entity - 地区
 * 
 */
@Data
@ApiModel(description = "地区")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_region")
public class Region extends SortEntity<Region> {

	private static final long serialVersionUID = 1L;

	/**
	 * 树路径分隔符
	 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 全称
	 */
	private String fullName;

	/**
	 * 树路径
	 */
	private String treePath;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 上级地区
	 */
	@TableField(exist = false)
	private String parentId;

	/**
	 * 上级地区名称
	 */
	@TableField(exist = false)
	private String parentName;

	/**
	 * 是否有子集
	 */
	@TableField(exist = false)
	private Boolean hasChildren=true;

	/**
	 * 下级地区
	 */
	@TableField(exist = false)
	private List<Region> children = new ArrayList<>();

	/**
	 * 获取所有上级地区ID
	 * 
	 * @return 所有上级地区ID
	 */
	@Transient
	public String[] getParentIds() {
		String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
		String[] result = new String[parentIds.length];
		for (int i = 0; i < parentIds.length; i++) {
			result[i] = String.valueOf(parentIds[i]);
		}
		return result;
	}

}