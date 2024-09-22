package cn.bizspring.cloud.upms.common.util;

import io.github.biezhi.ome.OhMyEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Slf4j
@Configuration
public class EmailConfiguration {

	private final EmailConfigProperties emailConfigProperties;

	public EmailConfiguration(EmailConfigProperties emailConfigProperties){
		this.emailConfigProperties = emailConfigProperties;
	}

	/**
	 * 邮箱配置初始化
	 */
	@PostConstruct
	public void init() {
		String mailHost = emailConfigProperties.getMailSmtpHost();
		String mailUsername = emailConfigProperties.getMailSmtpUsername();
		String mailPassowrd = emailConfigProperties.getMailSmtpPassword();

		final Properties properties = OhMyEmail.defaultConfig(false);
		properties.setProperty("mail.smtp.host", mailHost);
		OhMyEmail.config(properties, mailUsername, mailPassowrd);
	}
}
