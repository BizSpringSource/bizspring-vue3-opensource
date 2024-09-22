package cn.bizspring.cloud.upms.common.dto;

import cn.bizspring.cloud.upms.common.vo.MenuVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {
	private String icon;
	private String name;
	private boolean spread = false;
	private String path;
	private String component;
	private String authority;
	private String redirect;
	private String keepAlive;
	private String code;
	private String type;
	private String label;
	private Integer sort;
	private String permission;
	private String i18nCode;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;

	public MenuTree() {
	}

	public MenuTree(String id, String name, String parentId) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.label = name;
	}

	public MenuTree(String id, String name, MenuTree parent) {
		this.id = id;
		this.parentId = parent.getId();
		this.name = name;
		this.label = name;
	}

	public MenuTree(MenuVO menuVo) {
		this.id = menuVo.getId();
		this.parentId = menuVo.getParentId();
		this.icon = menuVo.getIcon();
		this.name = menuVo.getName();
		this.path = menuVo.getPath();
		this.component = menuVo.getComponent();
		this.type = menuVo.getType();
		this.label = menuVo.getName();
		this.sort = menuVo.getSort();
		this.keepAlive = menuVo.getKeepAlive();
		this.permission = menuVo.getPermission();
		this.i18nCode=menuVo.getI18nCode();
		this.createdTime = menuVo.getCreatedTime();
		this.updatedTime = menuVo.getUpdatedTime();
	}
}
