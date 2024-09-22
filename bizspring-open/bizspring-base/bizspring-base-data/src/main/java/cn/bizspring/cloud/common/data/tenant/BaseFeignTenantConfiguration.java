package cn.bizspring.cloud.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * feign 租户信息拦截
 */
@Configuration
public class BaseFeignTenantConfiguration {
	@Bean
	public RequestInterceptor baseFeignTenantInterceptor() {
		return new BaseFeignTenantInterceptor();
	}
}
