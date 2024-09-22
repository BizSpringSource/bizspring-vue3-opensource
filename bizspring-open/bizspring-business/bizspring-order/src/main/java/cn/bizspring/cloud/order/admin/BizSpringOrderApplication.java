package cn.bizspring.cloud.order.admin;

import cn.bizspring.cloud.common.security.annotation.EnableBaseFeignClients;
import cn.bizspring.cloud.common.security.annotation.EnableBaseResourceServer;
import cn.bizspring.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author www.bizspring.cn
 * 订单管理系统
 */
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BizSpringOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(BizSpringOrderApplication.class, args);
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(DataSize.ofMegabytes(10));
		// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(10));
		return factory.createMultipartConfig();
	}
}
