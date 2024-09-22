package cn.bizspring.cloud.goods.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity - 产品分类品牌关连表
 *
 */
@Data
@ApiModel(description = "产品分类品牌关连表")
@TableName("biz_goods_category_brand")
public class GoodsCategoryBrand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商品分类Id
	 */
	@TableField(value="goods_categories_id")
	private String goodsCategoriesId;

	/**
	 * 品牌Id
	 */
	@TableField(value="brands_id")
	private String brandId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GoodsCategoryBrand that = (GoodsCategoryBrand) o;
		return Objects.equals(goodsCategoriesId, that.goodsCategoriesId) && Objects.equals(brandId, that.brandId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(goodsCategoriesId, brandId);
	}
}