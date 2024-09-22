package cn.bizspring.cloud.goods.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity - 规格项
 * 
 */
@Data
public class SpecificationItem implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@NotEmpty
	private String name;

	/**
	 * 条目
	 */
	@NotEmpty
	private List<SpecificationItem.Entry> entries = new ArrayList<>();

	/**
	 * 判断是否已选
	 * 
	 * @return 是否已选
	 */
	@JsonIgnore
	public boolean isSelected() {
		if (CollectionUtils.isNotEmpty(getEntries())) {
			for (SpecificationItem.Entry entry : getEntries()) {
				if (entry.getIsSelected()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断规格值是否有效
	 * 
	 * @param specificationValue
	 *            规格值
	 * @return 规格值是否有效
	 */
	public boolean isValid(SpecificationValue specificationValue) {
		if (specificationValue != null && specificationValue.getId() != null && StringUtils.isNotEmpty(specificationValue.getValue()) && CollectionUtils.isNotEmpty(getEntries())) {
			for (SpecificationItem.Entry entry : getEntries()) {
				if (entry != null && entry.getIsSelected() && specificationValue.getId().equals(entry.getId()) && StringUtils.equals(entry.getValue(), specificationValue.getValue())) {
					return true;
				}
			}
		}
		return false;
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

	/**
	 * Entity - 条目
	 * 
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * ID
		 */
		@NotNull
		private Integer id;

		/**
		 * 值
		 */
		@NotEmpty
		private String value;

		/**
		 * 是否已选
		 */
		@NotNull
		private Boolean isSelected;

		/**
		 * 获取ID
		 * 
		 * @return ID
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * 设置ID
		 * 
		 * @param id
		 *            ID
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * 获取值
		 * 
		 * @return 值
		 */
		public String getValue() {
			return value;
		}

		/**
		 * 设置值
		 * 
		 * @param value
		 *            值
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * 获取是否已选
		 * 
		 * @return 是否已选
		 */
		public Boolean getIsSelected() {
			return isSelected;
		}

		/**
		 * 设置是否已选
		 *
		 * @param isSelected
		 *            是否已选
		 */
		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
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

}