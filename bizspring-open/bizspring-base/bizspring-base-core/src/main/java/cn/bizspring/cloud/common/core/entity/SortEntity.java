package cn.bizspring.cloud.common.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import org.apache.commons.lang.builder.CompareToBuilder;

import javax.persistence.MappedSuperclass;


/**
 * Entity - 排序基类
 * 
 */
@MappedSuperclass
public abstract class SortEntity<T extends BizEntity<T>> extends BizEntity<T> implements Comparable<SortEntity<T>> {

	private static final long serialVersionUID = 1L;

	/**
	 * "排序"
	 */
	public static final String SORT = "sort";

	/**
	 * 排序
	 */
	@TableField(value = "sort")
	private Integer sort;

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * 设置排序
	 * 
	 * @param sort
	 *            排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * 实现compareTo方法
	 * 
	 * @param sortEntity
	 *            排序对象
	 * @return 比较结果
	 */
	@Override
	public int compareTo(SortEntity<T> sortEntity) {
		if (sortEntity == null) {
			return 1;
		}
		return new CompareToBuilder().append(getSort(), sortEntity.getSort()).append(getId(), sortEntity.getId()).toComparison();
	}
}