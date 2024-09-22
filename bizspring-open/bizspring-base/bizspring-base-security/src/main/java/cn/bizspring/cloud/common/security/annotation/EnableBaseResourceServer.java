package cn.bizspring.cloud.common.security.annotation;

import cn.bizspring.cloud.common.security.component.BaseResourceServerAutoConfiguration;
import cn.bizspring.cloud.common.security.component.BaseSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * @author
 * <p>
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({BaseResourceServerAutoConfiguration.class, BaseSecurityBeanDefinitionRegistrar.class})
public @interface EnableBaseResourceServer {

}
