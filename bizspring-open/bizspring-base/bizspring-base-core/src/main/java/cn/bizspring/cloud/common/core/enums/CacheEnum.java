package cn.bizspring.cloud.common.core.enums;

/**

 * 缓存的key 常量
 */
public interface CacheEnum {

	/**
	 * 用户信息缓存
	 */
	String USER_CACHE = "user_cache";

	/**
	 * oauth 客户端信息
	 */
	String OAUTH_CLIENT_CACHE = "base_oauth:client:cache";

	/**
	 * 菜单信息缓存
	 */
	String MENU_CACHE = "menu_cache";

	/**
	 * spring boot admin 事件key
	 */
	String EVENT_KEY = "event_key";

	/**
	 * 路由缓存
	 */
	String ROUTE_CACHE = "gateway_route_cache";

	/**
	 * 字典缓存
	 */
	String DICT_CACHE = "dict_cache";

	/**
	 * 默认验证码前缀
	 */
	String VER_CODE_DEFAULT = "ver_code_default:";

	/**
	 * 注册验证码前缀
	 */
	String VER_CODE_REGISTER = "ver_code_register:";

}
