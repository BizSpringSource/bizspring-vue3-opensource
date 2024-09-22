package cn.bizspring.cloud.upms.common.vo;

import cn.bizspring.cloud.upms.common.entity.Role;
import cn.bizspring.cloud.upms.common.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@Data
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 随机盐
	 */
	private String salt;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 创建时间
	 */
	private LocalDateTime createdTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updatedTime;
	/**
	 * 0-正常，1-删除
	 */
	private String delFlag;

	/**
	 * 是否启用
	 */
	private String isEnabled;

	/**
	 * 锁定标记
	 */
	private String lockFlag;
	/**
	 * 简介
	 */
	private String mobile;
	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 机构ID
	 */
	private String organId;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 机构名称
	 */
	private String organName;

	/**
	 * 最后登录IP
	 */
	private String loginIp;

	/**
	 * 最后登录日期
	 */
	private Date loginDate;

	/**
	 * 版本
	 */
	private Long version;

	/**
	 * 角色列表
	 */
	private List<Role> roleList;

	/**
	 * 角色
	 */
	private List<String> roleIds;

	private User.Type type;

}
