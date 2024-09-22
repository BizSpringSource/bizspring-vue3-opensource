package cn.bizspring.cloud.store.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 *  产品分类与店铺关连表
 * </p>
 *
 * @author BizSpring
 */
@Data
@TableName("biz_goods_category_store")
public class GoodsCategoryStore extends BizEntity<GoodsCategoryStore> {

	private static final long serialVersionUID = 1L;

	/**
	 * 店铺
	 */
	@TableField(value="stores_id")
	private String storesId;

	/**
	 * 商品分类
	 */
	@TableField(value="goods_categories_id")
	private String goodsCategoriesId;

}
