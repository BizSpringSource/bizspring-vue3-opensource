package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.SafeKey;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Entity - 会员
 * 
 */
@Data
@ApiModel(description = "会员")
@EqualsAndHashCode(callSuper = true)
public class Member extends User {

	private static final long serialVersionUID = 1L;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * 角色
	 */
	public static final String[] ROLES = new String[] { "R_MEMBER" };
	
	/**
	 * 性别
	 */
	public enum Gender implements IEnum<Integer> {

		/**
		 * 男
		 */
		MALE(0),

		/**
		 * 女
		 */
		FEMALE(1);
		
		private int value;

		Gender(final int value) {
			this.value = value;
		}
		
		@Override
		public Integer getValue() {
			return this.value;
		}
	}

	/**
	 * 用户名
	 */
	@JsonView(BaseView.class)
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
	private String email;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 积分
	 */
	private Long point;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private Gender gender;

	/**
	 * 出生日期
	 */
	private Date birth;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 邮编
	 */
	private String zipCode;

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
	 * 地区
	 */
	@TableField(exist = false)
	private String regionId;

	/**
	 * 分销员
	 */
	@TableField(exist = false)
	private String distributorId;

	/**
	 * 购物车
	 */
	@TableField(exist = false)
	private String cartId;

}