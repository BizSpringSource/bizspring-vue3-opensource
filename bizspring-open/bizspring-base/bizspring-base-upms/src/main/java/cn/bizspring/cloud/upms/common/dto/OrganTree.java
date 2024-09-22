package cn.bizspring.cloud.upms.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author
 * 机构树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrganTree extends TreeNode {
	private String name;
	/**
	 * 机构编码
	 */
	private String code;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 机构类型
	 */
	private String type;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	private LocalDateTime createdTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updatedTime;
}
