package cn.bizspring.cloud.upms.common.dto;

import cn.bizspring.cloud.upms.common.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {
	/**
	 * 角色ID
	 */
	private List<String> roleIds;

	private String organId;

	private Boolean isEnabled;

	private Boolean delFlag;

	private Boolean lockFlag;

	/**
	 * 新密码
	 */
	private String newpassword1;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 操作类型
	 */
	private String doType;

	public Boolean getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = "0".equals(delFlag)?false:true;
	}

	public Boolean getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = "0".equals(lockFlag)?false:true;
	}

	public Boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(Boolean enabled) {
		isEnabled = enabled;
	}
}
