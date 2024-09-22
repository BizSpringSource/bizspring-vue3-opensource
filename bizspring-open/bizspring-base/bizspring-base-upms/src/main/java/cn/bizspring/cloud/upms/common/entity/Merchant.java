package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.entity.SafeKey;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;
/**
 * Entity - 商家
 * 
 */
@Data
@ApiModel(description = "商家")
@EqualsAndHashCode(callSuper = true)
public class Merchant extends User {

	private static final long serialVersionUID = 1L;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * 用户名
	 */
	@NotEmpty(groups = BizEntity.Save.class)
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(groups = BizEntity.Save.class)
	@TableField(exist = false)
	private String password;

	/**
	 * E-mail
	 */
	private String email;

	/**
	 * 手机
	 */

	private String mobile;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 电话
	 */
	private String phone;

	/**
	 * 安全密匙
	 */
	@TableField(exist = false)
	private SafeKey safeKey;

	/**
	 * 店铺
	 */
	@JsonIgnore
	@TableField(exist = false)
	private String storeId;

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setUsername(StringUtils.lowerCase(getUsername()));
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setEmail(StringUtils.lowerCase(getEmail()));
		setMobile(StringUtils.lowerCase(getMobile()));
	}

}