package cn.bizspring.cloud.common.security.service.impl;

import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.upms.common.feign.FeignMemberService;
import cn.hutool.core.util.ArrayUtil;
import cn.bizspring.cloud.common.security.entity.BizUser;
import cn.bizspring.cloud.common.security.service.BizUserDetailsService;
import cn.bizspring.cloud.upms.common.dto.UserInfo;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.R;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户详细信息
 *
 * @author
 */
@Slf4j
@Service
@AllArgsConstructor
public class BizUserDetailsServiceImpl implements BizUserDetailsService {
	private final FeignUserService feignUserService;
	private final CacheManager cacheManager;
	private final FeignMemberService feignMemberService;


	/**
	 * 用户密码登录
	 *
	 * @param username 用户名
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		//查询缓存中是否有此用户信息，有则直接返回
		Cache cache = cacheManager.getCache(CacheEnum.USER_CACHE);
		if (cache != null && cache.get(username) != null) {
			BizUser bizUser= (BizUser) cache.get(username).get();
			if(!bizUser.isEnabled()){
				throw new DisabledException("common_auth_error_disabled");
			}
			return bizUser;
		}
		//缓存中无此用户信息，feign查询
		R<UserInfo> result = feignUserService.info(username, SecurityEnum.FROM_IN);
		if(CommonUtils.isNull(result)){
			log.debug("Authentication failed: no credentials provided");
			throw new UsernameNotFoundException("common_auth_error_badCredentials");
		}
		if(!result.getData().getUser().getIsEnabled()){
			throw new DisabledException("common_auth_error_disabled");
		}
		UserDetails userDetails = getUserDetails(result);
		if(userDetails.isAccountNonLocked()){
			//合法用户，放入缓存
			cache.put(username, userDetails);
		}
		return userDetails;
	}

	/**
	 * 构建userdetails
	 *
	 * @param result 用户信息
	 * @return
	 */
	private UserDetails getUserDetails(R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		if(!result.getData().getUser().getIsEnabled()){
			log.debug("Authentication failed: no credentials provided");
			throw new DisabledException("common_auth_error_disabled");
		}

		UserInfo info = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityEnum.ROLE + roleId));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
		}
		Collection<? extends GrantedAuthority> authorities
				= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		User user = info.getUser();
		boolean enabled = BooleanUtils.isTrue(user.getIsEnabled());
		// 构造security用户
		return new BizUser(user.getId(), user.getOrganId(), user.getTenantId(), user.getUsername(), SecurityEnum.BCRYPT + user.getPassword(), enabled,
				true, true, BooleanUtils.isFalse(user.getLockFlag()), authorities);
	}
}
