package cn.bizspring.cloud.common.security.annotation;

import java.lang.annotation.*;

/**
 * @author
 * <p>
 * 服务调用鉴权注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inside {

	/**
	 * 是否AOP统一处理
	 *
	 * @return false, true
	 */
	boolean value() default true;
}
