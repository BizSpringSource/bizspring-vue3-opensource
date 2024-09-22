package cn.bizspring.cloud.auth.config;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.security.component.BaseWebResponseExceptionTranslator;
import cn.bizspring.cloud.common.security.service.RedisClientDetailsService;
import cn.bizspring.cloud.common.security.entity.BizUser;
import cn.bizspring.cloud.common.security.service.BizUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private  DataSource dataSource;
	@Autowired
	private  BizUserDetailsService bizUserDetailsService;
	@Autowired
	private  AuthenticationManager authenticationManagerBean;
	@Autowired
	private  RedisConnectionFactory redisConnectionFactory;

	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
		clientDetailsService.setSelectClientDetailsSql(SecurityEnum.SELECT_STATEMENT);
		clientDetailsService.setFindClientDetailsSql(SecurityEnum.FIND_STATEMENT);
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer
				.allowFormAuthenticationForClients()
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
				.tokenStore(tokenStore())
				.tokenEnhancer(tokenEnhancer())
				.userDetailsService(bizUserDetailsService)
				.authenticationManager(authenticationManagerBean)
				.reuseRefreshTokens(true)
				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
				.exceptionTranslator(new BaseWebResponseExceptionTranslator());
	}

/**
 *  redis token  配置
 **/
	@Bean
	public TokenStore tokenStore() {
		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(SecurityEnum.BASE_PREFIX + SecurityEnum.OAUTH_PREFIX);
		tokenStore.setAuthenticationKeyGenerator(new DefaultAuthenticationKeyGenerator() {
			@Override
			public String extractKey(OAuth2Authentication authentication) {
				return super.extractKey(authentication) + ":" + TenantContextHolder.getTenantId();
			}
		});
		return tokenStore;
	}

	/**
	 * token增强，客户端模式不增强。
	 *
	 * @return TokenEnhancer
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			if (SecurityEnum.CLIENT_CREDENTIALS
					.equals(authentication.getOAuth2Request().getGrantType())) {
				return accessToken;
			}

			final Map<String, Object> additionalInfo = new HashMap<>(8);
			BizUser bizUser = (BizUser) authentication.getUserAuthentication().getPrincipal();
			additionalInfo.put(SecurityEnum.DETAILS_USER, bizUser);
			additionalInfo.put(SecurityEnum.DETAILS_USER_ID, bizUser.getId());
			additionalInfo.put(SecurityEnum.DETAILS_USERNAME, bizUser.getUsername());
			additionalInfo.put(SecurityEnum.DETAILS_ORGAN_ID, bizUser.getOrganId());
			additionalInfo.put(SecurityEnum.DETAILS_TENANT_ID, bizUser.getTenantId());
			additionalInfo.put(SecurityEnum.DETAILS_LICENSE, SecurityEnum.BASE_LICENSE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}
}
