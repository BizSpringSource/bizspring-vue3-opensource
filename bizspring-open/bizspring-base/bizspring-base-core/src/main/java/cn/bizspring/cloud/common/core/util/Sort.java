package cn.bizspring.cloud.common.core.util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * 排序
 * 
 */
public class Sort implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 方向
	 */
	public enum  Direction {

		/**
		 * 递增
		 */
		ASC,

		/**
		 * 递减
		 */
		DESC
	}

	/**
	 * 方向
	 */
	private static final Direction DIRECTION = Direction.DESC;

	/**
	 * 属性
	 */
	private String property;

	/**
	 * 方向
	 */
	private Direction direction = DIRECTION;

	/**
	 * 构造方法
	 */
	public Sort() {
	}

	/**
	 * 构造方法
	 * 
	 * @param property
	 *            属性
	 * @param direction
	 *            方向
	 */
	public Sort(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	/**
	 * 返回递增排序
	 * 
	 * @param property
	 *            属性
	 * @return 递增排序
	 */
	public static Sort asc(String property) {
		return new Sort(property, Direction.ASC);
	}

	/**
	 * 返回递减排序
	 * 
	 * @param property
	 *            属性
	 * @return 递减排序
	 */
	public static Sort desc(String property) {
		return new Sort(property, Direction.DESC);
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property
	 *            属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取方向
	 * 
	 * @return 方向
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * 设置方向
	 * 
	 * @param direction
	 *            方向
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
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