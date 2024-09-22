package cn.bizspring.cloud.upms.common.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author
 */
@Data
public class UserRegister implements Serializable {
	@NotNull(message = "邮箱不能为空")
	private String email;

	@NotNull(message = "验证码不能为空")
	private String code;

	@NotNull(message = "用户名不能为空")
	private String username;

	@NotNull(message = "密码不能为空")
	private String password;

	@NotNull(message = "公司名不能为空")
	private String organname;
}
