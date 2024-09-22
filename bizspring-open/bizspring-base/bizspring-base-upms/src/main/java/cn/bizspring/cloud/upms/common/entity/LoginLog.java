/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;

/**
 * 登录日志
 * @author www.bizspring.cn
 * Copyright (C) 2023-2050
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "登录日志")
@TableName("biz_login_log")
public class LoginLog extends BizEntity<LoginLog> {
    private static final long serialVersionUID=1L;

    /**
     * 创建者ID
     */
    @ApiModelProperty(value = "创建者ID")
    private String createId;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;
    /**
     * IP地址
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
     * 操作提交的数据
     */
    @ApiModelProperty(value = "操作提交的数据")
    private String params;
    /**
     * 地址信息
     */
    @ApiModelProperty(value = "地址信息")
    private String address;
    /**
     * 所属租户
     */
    @ApiModelProperty(value = "所属租户")
    private String tenantId;
	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	private String type;
	/**
	 * 异常信息
	 */
	@ApiModelProperty(value = "异常信息")
	private String exception;
}
