package cn.bizspring.cloud.goods.common.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang.StringUtils;
import cn.bizspring.cloud.common.core.entity.SortEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Entity - 商品分类
 * 
 */
@Data
@ApiModel(description = "商品分类")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_goods_category")
public class GoodsCategory extends SortEntity<GoodsCategory> {

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
	 * 展示图片
	 */
	private String image;

	/**
	 * 树路径
	 */
	@TableField(exist = false)
	private String treePath;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 上级分类
	 */
	@TableField(exist = false)
	private String parentId;

	/**
	 * 是否有子集
	 */
	@TableField(exist = false)
	private Boolean hasChildren=true;

	/**
	 * 下级分类
	 */
	@TableField(exist = false)
	private List<GoodsCategory> children = new ArrayList<>();

	/**
	 * 关联品牌
	 */
	@TableField(exist = false)
	private Set<String> brands = new HashSet<>();

	/**
	 * 获取所有上级分类ID
	 * 
	 * @return 所有上级分类ID
	 */
	@Transient
	public String[] getParentIds() {
		String treePath=getTreePath();
		if(null!=treePath){
			String[] parentIds = StringUtils.split(getTreePath(), TREE_PATH_SEPARATOR);
			if(null!=parentIds && parentIds.length>0){
				String[] result = new String[parentIds.length];
				for (int i = 0; i < parentIds.length; i++) {
					result[i] = String.valueOf(parentIds[i]);
				}
				return result;
			}
		}
		return null;
	}
}