package cn.bizspring.cloud.auth.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.security.handler.AbstractAuthenticationFailureEvenHandler;
import cn.bizspring.cloud.upms.common.entity.LoginLog;
import cn.bizspring.cloud.upms.common.feign.FeignLoginLogService;
import cn.bizspring.cloud.upms.common.util.IpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
public class BaseAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

	private final FeignLoginLogService feignLoginLogService;
	/**
	 * 处理登录失败方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
		//登录失败日志统一存在租户1
		TenantContextHolder.setTenantId(CommonEnum.TENANT_ID_1);
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		//异步处理登录日志
		CompletableFuture.runAsync(() -> {
			LoginLog loginLog = new LoginLog();
			loginLog.setType(CommonEnum.LOG_TYPE_9);
			loginLog.setRemoteAddr(ServletUtil.getClientIP(request));
			loginLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
			loginLog.setUserAgent(request.getHeader("user-agent"));
			loginLog.setException(StrUtil.format("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage()));
			String address = IpUtils.getAddresses(loginLog.getRemoteAddr());
			loginLog.setAddress(address);
			feignLoginLogService.saveLoginLog(loginLog, SecurityEnum.FROM_IN);
		});
	}
}
