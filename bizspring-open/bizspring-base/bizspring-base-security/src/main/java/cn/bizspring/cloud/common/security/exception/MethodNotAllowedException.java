package cn.bizspring.cloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.bizspring.cloud.common.security.component.BaseAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author
 */
@JsonSerialize(using = BaseAuth2ExceptionSerializer.class)
public class MethodNotAllowedException extends BaseAuth2Exception {

	public MethodNotAllowedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "method_not_allowed";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.METHOD_NOT_ALLOWED.value();
	}

}
