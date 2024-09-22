package cn.bizspring.cloud.common.core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import cn.bizspring.cloud.common.core.annotation.CreatedTime;
import cn.bizspring.cloud.common.core.annotation.UpdatedTime;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.groups.Default;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - BizEntity
 *
 */
@JsonIgnoreProperties({ "handler" })
public abstract class BizEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "PK")
	@JsonView(BaseView.class)
	@TableId(value = "id", type = IdType.ID_WORKER)
	private String id;
	/**
	 * 创建日期
	 */
	@JsonView(BaseView.class)
	@CreatedTime
	@TableField(value = "created_time", fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * 最后修改日期
	 */
	@JsonView(BaseView.class)
	@UpdatedTime
	@TableField(value = "updated_time", fill = FieldFill.UPDATE)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatedTime;

	/**
	 * 0-正常，1-删除
	 */
	@ApiModelProperty(value = "0-正常，1-删除")
	private Boolean delFlag;


	/**
	 * 锁定标记
	 */
	@ApiModelProperty(value = "锁定标记")
	private Boolean lockFlag;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	/**
	 * 版本
	 */
	@Version
	@TableField(value = "version", fill = FieldFill.INSERT)
	private Long version;

	/**
	 * 获取ID
	 * @return ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	public String getId() {
		return id;
	}

	/**
	 * 设置ID
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取创建日期
	 * @return 创建日期
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置创建日期
	 * @param createdTime
	 *            创建日期
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取最后修改日期
	 * @return 最后修改日期
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * 设置最后修改日期
	 * @param updatedTime
	 *            最后修改日期
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Boolean delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * 锁定标记,默认不锁定,预留
	 */
	public Boolean getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Boolean lockFlag) {
		this.lockFlag = lockFlag;
	}

	/**
	 * 获取版本
	 * @return 版本
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * 设置版本
	 * @param version
	 *            版本
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * 判断是否为新建对象
	 * @return 是否为新建对象
	 */
	public boolean isNew() {
		return getId() == null;
	}


	/**
	 * 重写toString方法
	 * @return 字符串
	 */
	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
	}

	/**
	 * 重写equals方法
	 * @param obj
	 *            对象
	 * @return 是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!BizEntity.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		BizEntity<?> other = (BizEntity<?>) obj;
		return getId() != null ? getId().equals(other.getId()) : false;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	/**
	 * 重写hashCode方法
	 * @return HashCode
	 */
	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += getId() != null ? getId().hashCode() * 31 : 0;
		return hashCode;
	}


	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}

	/**
	 * 基础视图
	 */
	public interface BaseView {

	}

}