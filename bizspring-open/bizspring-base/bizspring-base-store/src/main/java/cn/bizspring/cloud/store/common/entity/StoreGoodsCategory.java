package cn.bizspring.cloud.store.common.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import cn.bizspring.cloud.common.core.entity.SortEntity;
/**
 * Entity - 店铺内商品分类
 * 
 */
@Data
@ApiModel(description = "店铺商品分类")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_store_goods_category")
public class StoreGoodsCategory extends SortEntity<StoreGoodsCategory> {

	private static final long serialVersionUID = 1L;

	/**
	 * 树路径分隔符
	 */
	public static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 分类名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 树路径
	 */
	private String treePath;

	/**
	 * 店铺
	 */
	@TableField(exist = false)
	private String storeId;

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
	private List<StoreGoodsCategory> children = new ArrayList<>();


	/**
	 * 获取所有上级分类ID
	 *
	 * @return 所有上级分类ID
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