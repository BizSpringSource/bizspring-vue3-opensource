package cn.bizspring.cloud.goods.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Entity - 商品图片
 * 
 */
@Data
public class GoodsImage implements Serializable, Comparable<GoodsImage> {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件扩展名
	 */
	public static final String FILE_EXTENSION = "jpg";

	/**
	 * 原图片文件名
	 */
	public static final String SOURCE_FILE_NAME = "%s_source.%s";

	/**
	 * 大图片文件名
	 */
	public static final String LARGE_FILE_NAME = "%s_large.%s";

	/**
	 * 中图片文件名
	 */
	public static final String MEDIUM_FILE_NAME = "%s_medium.%s";

	/**
	 * 缩略图文件名
	 */
	public static final String THUMBNAIL_FILE_NAME = "%s_thumbnail.%s";

	/**
	 * 原图片
	 */
	@NotEmpty
	private String source;

	/**
	 * 大图片
	 */
	@NotEmpty
	private String large;

	/**
	 * 中图片
	 */
	@NotEmpty
	private String medium;

	/**
	 * 缩略图
	 */
	@NotEmpty
	private String thumbnail;

	/**
	 * 排序
	 */
	@Min(0)
	private Integer sort;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 图片
		 */
		IMAGE(0),

		/**
		 * 媒体
		 */
		MEDIA(1),

		/**
		 * 文件
		 */
		FILE(2);

		private int value;

		Type(final int value) {
			this.value = value;
		}

		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 类型
	 */
	@JsonView(BizEntity.BaseView.class)
	@TableField(exist = false)
	private GoodsImage.Type type;

	public GoodsImage.Type getType() {
		return type;
	}

	public void setType(GoodsImage.Type type) {
		this.type = type;
	}

	/**
	 * 实现compareTo方法
	 * 
	 * @param goodsImage
	 *            商品图片
	 * @return 比较结果
	 */
	@Override
	public int compareTo(GoodsImage goodsImage) {
		if (goodsImage == null) {
			return 1;
		}
		return new CompareToBuilder().append(getSort(), goodsImage.getSort()).toComparison();
	}

	/**
	 * 重写equals方法
	 * 
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * 重写hashCode方法
	 * 
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}