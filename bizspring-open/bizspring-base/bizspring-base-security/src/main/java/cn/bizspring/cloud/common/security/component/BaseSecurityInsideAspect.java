package cn.bizspring.cloud.common.security.component;

import cn.hutool.core.util.StrUtil;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * <p>
 * 服务间接口不鉴权处理逻辑
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class BaseSecurityInsideAspect {
	private final HttpServletRequest request;

	@SneakyThrows
	@Around("@annotation(inside)")
	public Object around(ProceedingJoinPoint point, Inside inside) {
		String header = request.getHeader(SecurityEnum.FROM);
		if (inside.value() && !StrUtil.equals(SecurityEnum.FROM_IN, header)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("访问被拒绝，没有权限");
		}
		return point.proceed();
	}

}
