package cn.bizspring.cloud.common.log.event;

import cn.bizspring.cloud.upms.common.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author
 * 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {
	private final SysLog sysLog;
}
