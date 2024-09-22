package cn.bizspring.cloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.bizspring.cloud.common.security.component.BaseAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author
 */
@JsonSerialize(using = BaseAuth2ExceptionSerializer.class)
public class ForbiddenException extends BaseAuth2Exception {

	public ForbiddenException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "access_denied";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FORBIDDEN.value();
	}

}

