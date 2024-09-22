package cn.bizspring.cloud.common.core.util;

import lombok.*;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 响应信息主体
 * @author BizSpring
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "响应信息主体")
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "JSON内容类型")
	private static final String JSON_CONTENT_TYPE = "application/json";

	@ApiModelProperty(value = "消息KEY")
	private static final String MESSAGE_KEY = "message";

	@ApiModelProperty(value = "数据KEY")
	private static final String DATA_KEY = "data";

	@ApiModelProperty(value = "返回标记：成功=0，失败=1")
	private int status;

	@ApiModelProperty(value = "返回信息")
	private String message;

	@ApiModelProperty(value = "数据")
	private T data;

	@ApiModelProperty(value = "正常返回")
	public static final String COMMON_MESSAGE_OK = "common_message_ok";

	@ApiModelProperty(value = "你访问的页面域名不存在或者请求错误")
	public static final String COMMON_MESSAGE_BAD_REQUEST = "common_message_badRequest";

	@ApiModelProperty(value = "没有访问权限")
	public static final String COMMON_MESSAGE_UNAUTHORIZED = "common_message_unauthorized";

	@ApiModelProperty(value = "请求资源未找到")
	public static final String COMMON_MESSAGE_NOT_FOUND = "common_message_notFound";

	@ApiModelProperty(value = "无效的实体")
	public static final String COMMON_MESSAGE_INVALID_ENTITY = "common_message_unprocessableEntity";

	/**
	 * 正常返回
	 */
	@ApiModelProperty(value = "正常返回")
	public static final ResponseEntity<Map<String, String>> OK = R.ok(COMMON_MESSAGE_OK);

	/**
	 * 请求资源未找到
	 */
	@ApiModelProperty(value = "请求资源未找到")
	public static final ResponseEntity<Map<String, String>> NOT_FOUND = R.notFound(COMMON_MESSAGE_NOT_FOUND);

	/**
	 * 实体无效
	 */
	@ApiModelProperty(value = "实体无效")
	public static final ResponseEntity<Map<String, String>> INVALID_ENTITY = R.invalidEntity(COMMON_MESSAGE_INVALID_ENTITY);

	public Boolean isOk() {
		if(status!= 0){
			return false;
		}else{
			return true;
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * 请求正常返回
	 * @return
	 */
	public static ResponseEntity ok() {
		return restResult(COMMON_MESSAGE_OK,HttpStatus.OK);
	}

	/**
	 * 请求正常返回
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> ok(String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(COMMON_MESSAGE_OK,HttpStatus.OK);
		}
		return restResult(commonMsg, HttpStatus.OK);
	}

	/**
	 * 请求正常返回
	 * @param data 数据
	 * @return
	 */
	public static  <T> ResponseEntity<T> ok(T data) {
		return restResult(data, COMMON_MESSAGE_OK,HttpStatus.OK);
	}

	/**
	 * 请求正常返回
	 * @param data 数据
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> ok(T data, String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(data, COMMON_MESSAGE_OK,HttpStatus.OK);
		}
		return restResult(data,commonMsg,HttpStatus.OK);
	}

	/**
	 * 请求错误
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed() {
		return restResult(COMMON_MESSAGE_BAD_REQUEST, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 请求错误
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed(String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(COMMON_MESSAGE_BAD_REQUEST,HttpStatus.BAD_REQUEST);
		}
		return restResult(commonMsg, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 请求错误
	 * @param data 数据
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed(T data) {
		return restResult(data, COMMON_MESSAGE_BAD_REQUEST, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 请求错误
	 * @param data 数据
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed(T data, String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(COMMON_MESSAGE_BAD_REQUEST,HttpStatus.BAD_REQUEST);
		}
		return restResult(data, commonMsg,HttpStatus.BAD_REQUEST);
	}

	/**
	 * 请求错误
	 * @param data 数据
	 * @param httpStatusCode 状态码代码
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed(T data, int httpStatusCode, String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(COMMON_MESSAGE_BAD_REQUEST,HttpStatus.BAD_REQUEST);
		}
		return restResult(data, commonMsg, HttpStatus.valueOf(httpStatusCode));
	}

	/**
	 * 请求错误
	 * @param httpStatusCode 状态码代码
	 * @param commonMsg 消息
	 * @return
	 */
	public static  <T> ResponseEntity<T> failed(int httpStatusCode, String commonMsg) {
		if(StringUtils.isEmpty(commonMsg)){
			return restResult(COMMON_MESSAGE_BAD_REQUEST,HttpStatus.BAD_REQUEST);
		}
		return restResult(commonMsg,HttpStatus.valueOf(httpStatusCode));
	}

	/**
	 * 请求返回
	 * @param commonMsg 消息
	 * @param httpStatus 状态码
	 * @return
	 */
	public static <T> ResponseEntity<T> restResult(String commonMsg,HttpStatus httpStatus) {
		Map<String, Object> body = new HashMap<>();
		if(StringUtils.isNotEmpty(commonMsg)){
			body.put(MESSAGE_KEY,commonMsg);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(JSON_CONTENT_TYPE));
		return new ResponseEntity(body,headers, httpStatus);
	}

	/**
	 * 请求返回
	 * @param data 数据
	 * @param commonMsg 消息
	 * @param httpStatus 状态码
	 * @return
	 */
	public static <T> ResponseEntity<T> restResult( T data,String commonMsg,HttpStatus httpStatus) {
		Map<String, Object> body = new HashMap<>();
		if(null!=data){
			body.put(DATA_KEY,data);
		}
		if(StringUtils.isNotEmpty(commonMsg)){
			body.put(MESSAGE_KEY,commonMsg);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(JSON_CONTENT_TYPE));
		return new ResponseEntity(body,headers, httpStatus);
	}

	/**
	 * 请求返回
	 * @param data 数据
	 * @param commonMsg 消息
	 * @param httpStatus 状态码
	 * @return
	 */
	public static  ResponseEntity<Map<String, String>> statusResult( Object data,String commonMsg,HttpStatus httpStatus) {
		Map<String, Object> body = new HashMap<>();
		if(null!=data){
			body.put(DATA_KEY,data);
		}
		if(StringUtils.isNotEmpty(commonMsg)){
			body.put(MESSAGE_KEY,commonMsg);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(JSON_CONTENT_TYPE));
		return new ResponseEntity(body,headers, httpStatus);
	}


	/**
	 * 请求正常返回
	 * @param commonMsg 消息
	 * @param data 数据
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> ok(String commonMsg, Object... data) {
		if(StringUtils.isEmpty(commonMsg)){
			return statusResult(data, COMMON_MESSAGE_OK,HttpStatus.OK);
		}
		return statusResult(data, commonMsg,HttpStatus.OK);
	}

	/**
	 * 未授权
	 * @param commonMsg 消息
	 * @param data 数据
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> unauthorized(String commonMsg, Object... data) {
		if(StringUtils.isEmpty(commonMsg)){
			return statusResult(data, COMMON_MESSAGE_UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
		}
		return statusResult(data,commonMsg,HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 未授权
	 * @param data 参数
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> unauthorized(Object... data) {
		return statusResult(data,null,HttpStatus.UNAUTHORIZED);
	}

	/**
	 * 资源未找到
	 * @param commonMsg 消息
	 * @param data 数据
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> notFound(String commonMsg, Object... data) {
		if(StringUtils.isEmpty(commonMsg)){
			return statusResult(data, COMMON_MESSAGE_NOT_FOUND,HttpStatus.NOT_FOUND);
		}
		return statusResult(data,commonMsg,HttpStatus.NOT_FOUND);
	}

	/**
	 * 实体无效
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> invalidEntity() {
			return statusResult(null, COMMON_MESSAGE_INVALID_ENTITY,HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * 实体无效
	 * @param commonMsg 消息
	 * @param data  数据
	 * @return
	 */
	public static ResponseEntity<Map<String, String>> invalidEntity(String commonMsg, Object... data) {
		if(StringUtils.isEmpty(commonMsg)){
			return statusResult(data, COMMON_MESSAGE_INVALID_ENTITY,HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return statusResult(data, commonMsg,HttpStatus.UNPROCESSABLE_ENTITY );
	}

}