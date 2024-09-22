package cn.bizspring.cloud.common.core.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页
 * 
 */
public class Page<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 内容
	 */
	private  List<T> content = new ArrayList<>();

	/**
	 * 总记录数
	 */
	private  long total;

	/**
	 * 分页信息
	 */
	private  Pageable pageable;

	/**
	 * 构造方法
	 */
	public Page() {
		this.total = 0L;
		this.pageable = new Pageable();
	}

	/**
	 * 构造方法
	 * 
	 * @param content
	 *            内容
	 * @param total
	 *            总记录数
	 * @param pageable
	 *            分页信息
	 */
	public Page(List<T> content, long total, Pageable pageable) {
		this.content.addAll(content);
		this.total = total;
		this.pageable = pageable;
	}

	/**
	 * 获取页码
	 * 
	 * @return 页码
	 */
	public int getPageNumber() {
		return pageable.getPageNumber();
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageable.getPageSize();
	}

	/**
	 * 获取搜索属性
	 * 
	 * @return 搜索属性
	 */
	public String getSearchProperty() {
		return pageable.getSearchProperty();
	}

	/**
	 * 获取搜索值
	 * 
	 * @return 搜索值
	 */
	public String getSearchValue() {
		return pageable.getSearchValue();
	}

	/**
	 * 获取排序属性
	 * 
	 * @return 排序属性
	 */
	public String getSortProperty() {
		return pageable.getSortProperty();
	}

	/**
	 * 获取排序方向
	 * 
	 * @return 排序方向
	 */
	public Sort.Direction getSortDirection() {
		return pageable.getSortDirection();
	}

	/**
	 * 获取排序
	 * 
	 * @return 排序
	 */
	public List<Sort> getSorts() {
		return pageable.getSorts();
	}

	/**
	 * 获取筛选
	 * 
	 * @return 筛选
	 */
	public List<Filter> getFilters() {
		return pageable.getFilters();
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	public int getTotalPages() {
		return (int) Math.ceil((double) getTotal() / (double) getPageSize());
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * 添加内容
	 *
	 * @return 内容
	 */
	public void setContent(List<T> content) {
		this.content=content;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return 总记录数
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 获取分页信息
	 * 
	 * @return 分页信息
	 */
	public Pageable getPageable() {
		return pageable;
	}

	/**
	 * 获取空分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 空分页
	 */
	public static final <T> Page<T> emptyPage(Pageable pageable) {
		return new Page<>(Collections.<T>emptyList(), 0L, pageable);
	}


	/**
	 *  是否第一页
	 *
	 * @return boolean
	 */
	public boolean isFirstPage() {
		return getPageNumber() == 1;
	}

	/**
	 *  是否末页
	 *
	 * @return boolean
	 */
	public boolean isLastPage() {
		return getPageNumber() >= getTotalPages();
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