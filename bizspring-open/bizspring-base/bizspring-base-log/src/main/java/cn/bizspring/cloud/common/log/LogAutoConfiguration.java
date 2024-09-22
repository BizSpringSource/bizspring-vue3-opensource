package cn.bizspring.cloud.common.log;

import cn.bizspring.cloud.common.log.aspect.SysLogAspect;
import cn.bizspring.cloud.common.log.event.SysLogListener;
import cn.bizspring.cloud.upms.common.feign.FeignLogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author
 * <p>
 * 日志自动配置
 */
@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration {
	private final FeignLogService feignLogService;

	@Bean
	public SysLogListener sysLogListener() {
		return new SysLogListener(feignLogService);
	}

	@Bean
	public SysLogAspect sysLogAspect(ApplicationEventPublisher publisher) {
		return new SysLogAspect(publisher);
	}
}
