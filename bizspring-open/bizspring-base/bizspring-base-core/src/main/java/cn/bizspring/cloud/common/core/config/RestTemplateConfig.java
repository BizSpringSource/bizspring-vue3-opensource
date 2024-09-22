package cn.bizspring.cloud.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

/**
 * @author
 * RestTemplate
 */
@Configuration
public class RestTemplateConfig {
	@Bean
	@SentinelRestTemplate
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
