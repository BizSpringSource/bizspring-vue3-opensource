package cn.bizspring.cloud.auth.handler;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.security.entity.BizUser;
import cn.bizspring.cloud.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import cn.bizspring.cloud.common.security.util.SecurityUtils;
import cn.bizspring.cloud.upms.common.entity.LoginLog;
import cn.bizspring.cloud.upms.common.feign.FeignLoginLogService;
import cn.bizspring.cloud.upms.common.util.IpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author
 */
@Slf4j
@Component
@AllArgsConstructor
public class BaseAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

	private final FeignLoginLogService feignLoginLogService;

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		BizUser bizUser = SecurityUtils.getUser(authentication);
		TenantContextHolder.setTenantId(bizUser.getTenantId());
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		//异步处理登录日志
		CompletableFuture.runAsync(() -> {
			LoginLog loginLog = new LoginLog();
			loginLog.setType(CommonEnum.LOG_TYPE_0);
			loginLog.setCreateId(bizUser.getId());
			loginLog.setCreateBy(bizUser.getUsername());
			loginLog.setRemoteAddr(ServletUtil.getClientIP(request));
			loginLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
			loginLog.setUserAgent(request.getHeader("user-agent"));
			String address = IpUtils.getAddresses(loginLog.getRemoteAddr());
			loginLog.setAddress(address);
			feignLoginLogService.saveLoginLog(loginLog, SecurityEnum.FROM_IN);
		});
	}
}
