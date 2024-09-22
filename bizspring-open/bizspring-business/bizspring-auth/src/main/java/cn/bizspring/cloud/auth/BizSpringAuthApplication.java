package cn.bizspring.cloud.auth;

import cn.bizspring.cloud.common.security.annotation.EnableBaseFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author
 * 认证授权中心
 */
@SpringCloudApplication
@EnableBaseFeignClients
public class BizSpringAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizSpringAuthApplication.class, args);
	}
}
