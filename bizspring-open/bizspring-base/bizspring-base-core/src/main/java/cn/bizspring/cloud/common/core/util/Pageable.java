package cn.bizspring.cloud.common.core.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页信息
 * 
 */
public class Pageable implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 默认页码
	 */
	public static final int PAGE_NUMBER = 1;

	/**
	 * 默认每页记录数
	 */
	public static final int PAGE_SIZE = 10;

	/**
	 * 最大每页记录数
	 */
	public static final int MAX_PAGE_SIZE = 1000;

	/**
	 * 页码
	 */
	private int pageNumber = PAGE_NUMBER;

	/**
	 * 每页记录数
	 */
	private int pageSize = PAGE_SIZE;

	/**
	 * 搜索属性
	 */
	private String searchProperty;

	/**
	 * 搜索值
	 */
	private String searchValue;

	/**
	 * 排序属性
	 */
	private String sortProperty;

	/**
	 * 排序方向
	 */
	private Sort.Direction sortDirection;

	/**
	 * 筛选
	 */
	private List<Filter> filters = new ArrayList<>();

	/**
	 * 排序
	 */
	private List<Sort> sorts = new ArrayList<>();

	/**
	 * 构造方法
	 */
	public Pageable() {
	}

	/**
	 * 构造方法
	 * 
	 * @param pageNumber
	 *            页码
	 * @param pageSize
	 *            每页记录数
	 */
	public Pageable(Integer pageNumber, Integer pageSize) {
		if (pageNumber != null && pageNumber >= 1) {
			this.pageNumber = pageNumber;
		}
		if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * 设置页码
	 * 
	 * @param pageNumber
	 *            页码
	 */
	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = PAGE_NUMBER;
		}
		this.pageNumber = pageNumber;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
			pageSize = PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	/**
	 * 获取搜索属性
	 * 
	 * @return 搜索属性
	 */
	public String getSearchProperty() {
		return searchProperty;
	}

	/**
	 * 设置搜索属性
	 * 
	 * @param searchProperty
	 *            搜索属性
	 */
	public void setSearchProperty(String searchProperty) {
		this.searchProperty = searchProperty;
	}

	/**
	 * 获取搜索值
	 * 
	 * @return 搜索值
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * 设置搜索值
	 * 
	 * @param searchValue
	 *            搜索值
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * 获取排序属性
	 * 
	 * @return 排序属性
	 */
	public String getSortProperty() {
		return sortProperty;
	}

	/**
	 * 设置排序属性
	 * 
	 * @param sortProperty
	 *            排序属性
	 */
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	/**
	 * 获取排序方向
	 * 
	 * @return 排序方向
	 */
	public Sort.Direction getSortDirection() {
		return sortDirection;
	}

	/**
	 * 设置排序方向
	 * 
	 * @param sortDirection
	 *            排序方向
	 */
	public void setSortDirection(Sort.Direction sortDirection) {
		this.sortDirection = sortDirection;
	}

	/**
	 * 获取筛选
	 * 
	 * @return 筛选
	 */
	public List<Filter> getFilters() {
		return filters;
	}

	/**
	 * 设置筛选
	 * 
	 * @param filters
	 *            筛选
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public List<Sort> getSorts() {
		return sorts;
	}

	/**
	 * 设置排序
	 * 
	 * @param sorts
	 *            排序
	 */
	public void setSorts(List<Sort> sorts) {
		this.sorts = sorts;
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