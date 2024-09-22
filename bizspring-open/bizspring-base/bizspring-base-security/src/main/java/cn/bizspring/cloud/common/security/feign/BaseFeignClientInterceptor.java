package cn.bizspring.cloud.common.security.feign;

import cn.hutool.core.collection.CollUtil;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import java.util.Collection;

/**
 * @author
 * OAuth2FeignRequestInterceptor扩展
 */
@Slf4j
public class BaseFeignClientInterceptor extends OAuth2FeignRequestInterceptor {
	private final OAuth2ClientContext oAuth2ClientContext;
	private final AccessTokenContextRelay accessTokenContextRelay;

	/**
	 * Default constructor which uses the provided OAuth2ClientContext and Bearer tokens
	 * within Authorization header
	 *
	 * @param oAuth2ClientContext     provided context
	 * @param resource                type of resource to be accessed
	 * @param accessTokenContextRelay
	 */
	public BaseFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext
			, OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
		super(oAuth2ClientContext, resource);
		this.oAuth2ClientContext = oAuth2ClientContext;
		this.accessTokenContextRelay = accessTokenContextRelay;
	}


	/**
	 * fein拦截器将本服务的token,通过copyToken的形式传递给下游服务
	 * @param requestTemplate
	 */
	@Override
	public void apply(RequestTemplate requestTemplate) {
		Collection<String> fromHeader = requestTemplate.headers().get(SecurityEnum.FROM);
		if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityEnum.FROM_IN)) {
			return;
		}
		accessTokenContextRelay.copyToken();
		if (oAuth2ClientContext != null
				&& oAuth2ClientContext.getAccessToken() != null) {
			super.apply(requestTemplate);
		}
	}
}
