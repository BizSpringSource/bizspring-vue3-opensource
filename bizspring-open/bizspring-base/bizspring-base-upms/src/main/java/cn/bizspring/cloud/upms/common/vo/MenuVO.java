package cn.bizspring.cloud.upms.common.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author
 */
@Data
public class MenuVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	private String id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单权限标识
	 */
	private String permission;

	/**
	 * 菜单多语言
	 */
	private String i18nCode;
	/**
	 * 父菜单ID
	 */
	private String parentId;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 一个路径
	 */
	private String path;
	/**
	 * VUE页面
	 */
	private String component;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 菜单类型 （0菜单 1按钮）
	 */
	private String type;
	/**
	 * 是否缓冲
	 */
	private String keepAlive;
	/**
	 * 创建时间
	 */
	private LocalDateTime createdTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updatedTime;
	/**
	 * 0--正常 1--删除
	 */
	private String delFlag;
	/**
	 * 锁定标记
	 */
	private Boolean lockFlag;

	/**
	 * 是否启用
	 */
	private Boolean isEnabled;

	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 版本
	 */
	@Version
	@TableField(value = "version", fill = FieldFill.INSERT)
	private Long version;



	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/**
	 * menuId 相同则相同
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MenuVO) {
			String targetMenuId = ((MenuVO) obj).getId();
			return id.equals(targetMenuId);
		}
		return super.equals(obj);
	}
}
