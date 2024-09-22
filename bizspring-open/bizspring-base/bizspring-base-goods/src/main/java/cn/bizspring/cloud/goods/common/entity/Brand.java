package cn.bizspring.cloud.goods.common.entity;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cn.bizspring.cloud.common.core.entity.SortEntity;

/**
 * Entity - 品牌
 * 
 */
@Data
@ApiModel(description = "品牌")
@EqualsAndHashCode(callSuper = true)
@TableName("biz_brand")
public class Brand extends SortEntity<Brand> {

	private static final long serialVersionUID = 1L;

	/**
	 * 类型
	 */
	public enum Type implements IEnum<Integer> {

		/**
		 * 文本
		 */
		TEXT(0),

		/**
		 * 图片
		 */
		IMAGE(1);
		
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
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 类型
	 */
	@NotNull
	private Brand.Type type;

	/**
	 * logo
	 */
	private String logo;

	/**
	 * 网址
	 */
	private String url;

	/**
	 * 介绍
	 */
	@Lob
	private String introduction;

	private String comment;

}