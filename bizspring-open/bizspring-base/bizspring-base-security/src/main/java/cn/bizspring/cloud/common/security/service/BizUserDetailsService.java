package cn.bizspring.cloud.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author
 */
public interface BizUserDetailsService extends UserDetailsService {

	/**
	 * 手机验证码登录
	 *
	 * @param code TYPE:CODE
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
//	UserDetails loadUserByPhone(String client_id,String grantType,String code) throws UsernameNotFoundException;
}
