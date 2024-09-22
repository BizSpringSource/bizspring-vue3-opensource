package cn.bizspring.cloud.upms.common.security;

import cn.bizspring.cloud.upms.common.entity.User;

import java.util.Set;

/**
 * Security - 认证Provider
 * 
 */
public interface AuthenticationProvider {

	/**
	 * 获取用户
	 * 
	 * @param principal
	 *            身份
	 * @return 用户
	 */
	User getUser(Object principal);

	/**
	 * 是否支持
	 * 
	 * @param userClass
	 *            用户类型
	 * @return 是否支持
	 */
	boolean supports(Class<?> userClass);

}