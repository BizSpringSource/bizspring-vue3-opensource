package cn.bizspring.cloud.goods.admin;

import cn.bizspring.cloud.common.security.annotation.EnableBaseFeignClients;
import cn.bizspring.cloud.common.security.annotation.EnableBaseResourceServer;
import cn.bizspring.cloud.common.swagger.annotation.BaseEnableSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author www.bizspring.cn
 * 用户管理系统
 */
@EnableElasticsearchRepositories(basePackages = "cn.bizspring.cloud.goods.admin.repository")
@BaseEnableSwagger
@SpringCloudApplication
@EnableBaseFeignClients
@EnableBaseResourceServer
public class BizSpringGoodsApplication {
	public static void main(String[] args) {
		System.setProperty("es.set.netty.runtime.available.processors","false");
		SpringApplication.run(BizSpringGoodsApplication.class, args);
	}

	/**
	 * 文件上传配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(DataSize.ofMegabytes(20));
		// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(20));
		return factory.createMultipartConfig();
	}
}
