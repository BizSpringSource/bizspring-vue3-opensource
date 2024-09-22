package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.upms.common.entity.LoginLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author
 */
@FeignClient(contextId = "feignLoginLogService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignLoginLogService {
	/**
	 * 保存登录日志
	 *
	 * @param loginLog 日志实体
	 * @param from   是否内部调用
	 * @return succes、false
	 */
	@PostMapping("/loginlog/save")
	R<Boolean> saveLoginLog(@RequestBody LoginLog loginLog, @RequestHeader(SecurityEnum.FROM) String from);
}
