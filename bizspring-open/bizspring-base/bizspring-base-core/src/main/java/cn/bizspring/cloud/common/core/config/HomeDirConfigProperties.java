/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 文件存放目录配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "home-dir")
public class HomeDirConfigProperties {

	/**
	 *
	 */
	private String windows = "windows";

	private String linux = "linux";
}
