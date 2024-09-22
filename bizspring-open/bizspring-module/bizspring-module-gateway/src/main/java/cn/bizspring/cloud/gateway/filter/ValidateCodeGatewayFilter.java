package cn.bizspring.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.gateway.config.FilterIgnorePropertiesConfig;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.exception.ValidateCodeException;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.WebUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author
 * 验证码处理
 */
@Slf4j
@Component
@AllArgsConstructor
public class ValidateCodeGatewayFilter extends AbstractGatewayFilterFactory {
	private final ObjectMapper objectMapper;
	private final RedisTemplate redisTemplate;
	private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// 不是登录请求，直接向下执行
			if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath()
					, SecurityEnum.OAUTH_TOKEN_URL, SecurityEnum.SMS_TOKEN_URL
					, SecurityEnum.THIRDPARTY_TOKEN_URL)) {
				return chain.filter(exchange);
			}

			// 刷新token，直接向下执行
			String grantType = request.getQueryParams().getFirst("grant_type");
			if (StrUtil.equals(SecurityEnum.REFRESH_TOKEN, grantType)) {
				return chain.filter(exchange);
			}

			try {
				// 终端设置不校验， 直接向下执行
				String[] clientInfos = WebUtils.getClientId(request);
				if (filterIgnorePropertiesConfig.getClients().contains(clientInfos[0])) {
					return chain.filter(exchange);
				}

				//校验验证码
				checkCode(request);
			} catch (Exception e) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
				try {
					return response.writeWith(Mono.just(response.bufferFactory()
							.wrap(objectMapper.writeValueAsBytes(
									R.failed(e.getMessage())))));
				} catch (JsonProcessingException e1) {
					log.error("对象输出异常", e1);
				}
			}

			return chain.filter(exchange);
		};
	}

	/**
	 * 检查code
	 *
	 * @param request
	 */
	@SneakyThrows
	private void checkCode(ServerHttpRequest request) {
		String code = request.getQueryParams().getFirst("code");

		if (StrUtil.isBlank(code)) {
			throw new ValidateCodeException("common_error_ncorrectCaptcha");
		}

		String randomStr = request.getQueryParams().getFirst("randomStr");
		String grantType = request.getQueryParams().getFirst("grant_type");
		if (StrUtil.equals(SecurityEnum.SMS_LOGIN, grantType)) {
			randomStr = SecurityEnum.SMS_LOGIN + ":" + request.getQueryParams().getFirst("phone");
		}

		String key = CacheEnum.VER_CODE_DEFAULT + randomStr;
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		if (!redisTemplate.hasKey(key)) {
			throw new ValidateCodeException("common_error_invalidCaptcha");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);
		if (codeObj == null) {
			throw new ValidateCodeException("common_error_invalidCaptcha");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			throw new ValidateCodeException("common_error_invalidCaptcha");
		}

		if (!StrUtil.equals(saveCode, code)) {
			throw new ValidateCodeException("common_error_invalidCaptcha");
		}

		redisTemplate.delete(key);
	}
}
