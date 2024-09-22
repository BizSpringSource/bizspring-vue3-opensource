package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author
 */
@Data
@ApiModel(value="用户")
@TableName("biz_users")
public class User extends BizEntity<User> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@JsonView(BaseView.class)
	@NotEmpty(groups = Save.class)
	private String username;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 随机盐
	 */
	@ApiModelProperty(value = "随机盐")
	@JsonIgnore
	private String salt;

	/**
	 * 锁定日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "锁定日期")
	private Date lockDate;

	@ApiModelProperty(value = "手机")
	private String mobile;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;

	/** ok
	 * 机构ID
	 */
	@ApiModelProperty(value = "机构ID")
	private String organId;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**  可以干掉
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 语言设置
	 */
	public enum Language implements IEnum<String> {

		/**
		 * 中文(简体)
		 */
		zh_CN("zh_CN"),

//		/**
//		 * 中文(繁体)
//		 */
//		zh_TW("zh_TW"),

		/**
		 * 英文(美国)
		 */
		en_US("en_US");

		private String value;

		Language(final String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}
	}

	@TableField(exist = false)
	private Language[] languageValues;

	/**
	 * 类型
	 */
	@Column(name = "language")
	@TableField(exist = false)
	private User.Language language;


	/**
	 * 类型
	 */
	public enum Type implements IEnum<String> {

		/**
		 * 会员
		 */
		MEMBER("Member"),
		/**
		 * 商家
		 */
		MERCHANT("Merchant"),
		/**
		 * 管理员
		 */
		ADMIN("Admin");

		private String value;

		Type(final String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}
	}


	/**
	 * 类型
	 */
	@Column(name = "dtype")
	@TableField(exist = false)
	private User.Type type;

	/**
	 * 最后登录IP
	 */
	private String loginIp;

	/**
	 * 最后登录日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date loginDate;

	/**
	 * 启用标记
	 */
	@ApiModelProperty(value = "是否启用")
	private Boolean isEnabled;

}
