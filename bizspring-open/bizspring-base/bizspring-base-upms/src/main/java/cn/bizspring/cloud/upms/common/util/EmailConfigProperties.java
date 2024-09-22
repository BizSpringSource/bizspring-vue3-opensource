package cn.bizspring.cloud.upms.common.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 邮箱配置
 *
 * @author
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfigProperties {

	private String mailSmtpHost = "smtpdm.aliyun.com";

	private String mailSmtpUsername = "official@email.bizspring.cn";

	private String mailSmtpPassword = "BizSpring12345678";

	private String siteName = "BizSpring";

}
