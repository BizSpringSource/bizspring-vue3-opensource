package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author
 */
@Data
@ApiModel(description = "日志")
@TableName("biz_sys_log")
public class SysLog extends BizEntity<SysLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	@NotNull(message = "日志类型不能为空")
	private String type;
	/**
	 * 日志标题
	 */
	@ApiModelProperty(value = "标题")
	@NotNull(message = "日志标题不能为空")
	private String title;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者ID")
	private String createId;
	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者名")
	private String createBy;
	/**
	 * 操作IP地址
	 */
	@ApiModelProperty(value = "IP地址")
	private String remoteAddr;
	/**
	 * 用户代理
	 */
	@ApiModelProperty(value = "用户代理")
	private String userAgent;
	/**
	 * 请求URI
	 */
	@ApiModelProperty(value = "请求URI")
	private String requestUri;
	/**
	 * 操作方式
	 */
	@ApiModelProperty(value = "操作方式")
	private String method;
	/**
	 * 操作提交的数据
	 */
	@ApiModelProperty(value = "提交的数据")
	private String params;
	/**
	 * 执行时间
	 */
	@ApiModelProperty(value = "执行时间")
	private Long time;

	/**
	 * 异常信息
	 */
	@ApiModelProperty(value = "异常信息")
	private String exception;

	/**
	 * 服务ID
	 */
	@ApiModelProperty(value = "服务ID")
	private String serviceId;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	private String tenantId;

}
