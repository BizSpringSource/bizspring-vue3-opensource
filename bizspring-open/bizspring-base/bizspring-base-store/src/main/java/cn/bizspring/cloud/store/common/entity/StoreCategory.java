package cn.bizspring.cloud.store.common.entity;

import javax.validation.constraints.NotEmpty;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.bizspring.cloud.common.core.entity.SortEntity;
/**
 * Entity - 店铺分类
 * 
 */
@Data
@ApiModel(description = "店铺分类")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_store_category")
public class StoreCategory extends SortEntity<StoreCategory> {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

}