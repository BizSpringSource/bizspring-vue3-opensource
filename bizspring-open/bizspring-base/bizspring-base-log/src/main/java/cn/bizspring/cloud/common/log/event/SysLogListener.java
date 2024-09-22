package cn.bizspring.cloud.common.log.event;

import cn.bizspring.cloud.upms.common.entity.SysLog;
import cn.bizspring.cloud.upms.common.feign.FeignLogService;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {
	private final FeignLogService feignLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
	}
}
