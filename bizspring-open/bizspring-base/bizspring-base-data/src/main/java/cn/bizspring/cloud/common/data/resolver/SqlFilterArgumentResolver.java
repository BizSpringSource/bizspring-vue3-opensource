package cn.bizspring.cloud.common.data.resolver;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.exception.CheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date
 * <p>
 * 解决Mybatis Plus Order By SQL注入问题
 */
@Slf4j
public class SqlFilterArgumentResolver implements HandlerMethodArgumentResolver {
	private final static String[] KEYWORDS = {"master", "truncate", "insert", "select"
			, "delete", "update", "declare", "alter", "drop", "sleep"};

	/**
	 * 判断Controller是否包含page 参数
	 *
	 * @param parameter 参数
	 * @return 是否过滤
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(Page.class);
	}

	/**
	 * @param parameter     入参集合
	 * @param mavContainer  model 和 view
	 * @param webRequest    web相关
	 * @param binderFactory 入参解析
	 * @return 检查后新的page对象
	 * <p>
	 * page 只支持查询 GET .如需解析POST获取请求报文体处理
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer
			, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		String[] ascs = request.getParameterValues("ascs");
		String[] descs = request.getParameterValues("descs");
		String current = request.getParameter("current");
		String size = request.getParameter("size");

		Page page = new Page();
		if (StrUtil.isNotBlank(current)) {
			page.setCurrent(Long.parseLong(current));
		}

		if (StrUtil.isNotBlank(size)) {
			page.setSize(Long.parseLong(size));
		}

		page.setAsc(sqlInject(ascs));
		page.setDesc(sqlInject(descs));
		return page;
	}

	/**
	 * SQL注入过滤
	 *
	 * @param str 待验证的字符串
	 */
	public static String[] sqlInject(String[] str) {
		if (ArrayUtil.isEmpty(str)) {
			return null;
		}
		//转换成小写
		String inStr = ArrayUtil.join(str, StrUtil.COMMA).toLowerCase();

		//判断是否包含非法字符
		for (String keyword : KEYWORDS) {
			if (inStr.contains(keyword)) {
				log.error("查询包含非法字符 {}", keyword);
				throw new CheckedException(keyword + "包含非法字符");
			}
		}

		return str;
	}
}
