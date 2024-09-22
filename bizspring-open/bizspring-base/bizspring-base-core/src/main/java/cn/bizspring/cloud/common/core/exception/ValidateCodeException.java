package cn.bizspring.cloud.common.core.exception;

/**
 * @author
 */
public class ValidateCodeException extends Exception {
	private static final long serialVersionUID = 1L;

	public ValidateCodeException() {
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}
}
