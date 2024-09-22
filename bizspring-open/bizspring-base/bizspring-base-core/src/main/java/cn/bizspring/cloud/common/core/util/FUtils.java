package cn.bizspring.cloud.common.core.util;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public final class FUtils {

	private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_26);

	private static final BeansWrapper BEANS_WRAPPER = new BeansWrapperBuilder(Configuration.VERSION_2_3_26).build();

	private static final ConversionService CONVERSION_SERVICE = new DefaultConversionService();

	private FUtils() {
	}

	/**
	 * 获取当前环境变量
	 */
	public static Environment getCurrentEnvironment() {
		return Environment.getCurrentEnvironment();
	}

	/**
	 * 解析字符串模板
	 */
	public static String process(String template) throws IOException, TemplateException {
		return process(template, null);
	}

	/**
	 * 解析字符串模板
	 */
	public static String process(String template, Object model) throws IOException, TemplateException {
		Configuration configuration = null;
		ApplicationContext applicationContext = SpringUtils.getApplicationContext();
		return process(template, model, configuration);
	}

	/**
	 * 解析字符串模板
	 */
	public static String process(String template, Object model, Configuration configuration) throws IOException, TemplateException {
		if (template == null) {
			return null;
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration != null ? configuration : CONFIGURATION).process(model, out);
		return String.valueOf(out);
	}

	/**
	 * 获取参数
	 * @return 参数，若不存在则返回null
	 */
	public static <T> T getParameter(String name, Class<T> type, Map<String, TemplateModel> params) throws TemplateModelException {
		TemplateModel templateModel = params.get(name);
		if (templateModel != null) {
			Object value = DeepUnwrap.unwrap(templateModel);
			if (value != null) {
				return (T) CONVERSION_SERVICE.convert(value, type);
			}
		}
		return null;
	}

}