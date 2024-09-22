package cn.bizspring.cloud.upms.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

/**
 * Entity - 管理员
 * 
 */
@Data
@ApiModel(description = "管理员")
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@NotEmpty(groups = Save.class)
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(groups = Save.class)
	@TableField(exist = false)
	private String password;

	/**
	 * E-mail
	 */
	@NotEmpty
	private String email;

	/**
	 * 手机
	 */
	@NotEmpty
	private String mobile;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 部门
	 */
	private String department;

	@Transient
	public boolean isValidCredentials(Object credentials) {
		String rawPassword =credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials);
		String encodedPassword =getPassword();
		return credentials != null && ENCODER.matches(rawPassword, encodedPassword);
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setUsername(StringUtils.lowerCase(getUsername()));
		setEmail(StringUtils.lowerCase(getEmail()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setEmail(StringUtils.lowerCase(getEmail()));
	}

}