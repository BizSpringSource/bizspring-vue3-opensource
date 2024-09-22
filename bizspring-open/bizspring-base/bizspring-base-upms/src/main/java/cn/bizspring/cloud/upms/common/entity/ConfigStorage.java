/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 存储配置
 * @author www.bizspring.cn
 * Copyright (C) 2023-2050
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "存储配置")
@TableName("biz_config_storage")
public class ConfigStorage extends BizEntity<ConfigStorage> {
    private static final long serialVersionUID=1L;

    /**
     * 所属租户
     */
	@ApiModelProperty(value = "所属租户")
    private String tenantId;
    /**
     * 存储类型1、阿里OSS；2、七牛云；3、本地
     */
	@ApiModelProperty(value = "存储类型1、阿里OSS；2、七牛云；3、本地")
    private String storageType;
    /**
     * 地域节点
     */
	@ApiModelProperty(value = "地域节点")
    private String endpoint;
    /**
     * accessKeyId
     */
	@ApiModelProperty(value = "accessKeyId")
    private String accessKeyId;
    /**
     * 密钥
     */
	@ApiModelProperty(value = "密钥")
    private String accessKeySecret;
    /**
     * 域名（ OSS前缀 ）
     */
	@ApiModelProperty(value = "域名")
    private String bucket;
	/**
	 * 图片水印内容
	 */
	@ApiModelProperty(value = "图片水印内容")
    private String waterMarkContent;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 排序
	 */
	@TableField(value = "sort")
	private Integer sort;
	/**
	 * 网址 （网站网址，系统网址）
	 */
	@ApiModelProperty(value = "网址")
	private String siteUrl;



}
