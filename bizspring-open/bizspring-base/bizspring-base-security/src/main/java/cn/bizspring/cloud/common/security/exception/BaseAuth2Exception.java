package cn.bizspring.cloud.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cn.bizspring.cloud.common.security.component.BaseAuth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = BaseAuth2ExceptionSerializer.class)
public class BaseAuth2Exception extends OAuth2Exception {
	@Getter
	private String errorCode;

	public BaseAuth2Exception(String msg) {
		super(msg);
	}

	public BaseAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
}
