package cn.bizspring.cloud.common.core.enums;

/**
 *认证常量
 */
public interface SecurityEnum {
	/**
	 * 密码登录
	 */
	String PASSWORD = "password";
	/**
	 * 短信登录验证码
	 */
	String SMS_LOGIN = "sms_login";
	/**
	 * 刷新
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * 验证码有效期（5分钟）
	 */
	int CODE_TIME = 300;
	/**
	 * 验证码长度
	 */
	String CODE_SIZE = "4";
	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";
	/**
	 * 前缀
	 */
	String BASE_PREFIX = "base_";

	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";
	/**
	 * 项目的license
	 */
	String BASE_LICENSE = "Copyright © bizspring";

	/**
	 * 内部
	 */
	String FROM_IN = "Y";

	/**
	 * 标志
	 */
	String FROM = "from";

	//	客户端
	String SHOP_PORT="shop";

	//	商家端
	String MERCHANT_PORT="merchant";

	//短信登录
	String LOGIN_SMS="sms_login";

	/**
	 * OAUTH URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * 手机号登录URL
	 */
	String SMS_TOKEN_URL = "/phone/token/sms";

	/**
	 * 社交登录URL
	 */
	String THIRDPARTY_TOKEN_URL = "/phone/token/thirdparty";
	/**
	 * 自定义登录URL
	 */
	String PHONE_TOKEN_URL = "/phone/token/*";

	/**
	 * 微信获取OPENID
	 */
	String WX_AUTHORIZATION_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token" +
			"?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	/**
	 * 码云获取token
	 */
	String GITEE_AUTHORIZATION_CODE_URL = "https://gitee.com/oauth/token?grant_type=" +
			"authorization_code&code=%S&client_id=%s&redirect_uri=" +
			"%s&client_secret=%s";

	/**
	 * 开源中国获取token
	 */
	String OSC_AUTHORIZATION_CODE_URL = "https://www.oschina.net/action/openapi/token";

	/**
	 * 码云获取用户信息
	 */
	String GITEE_USER_INFO_URL = "https://gitee.com/api/v5/user?access_token=%s";

	/**
	 * 开源中国用户信息
	 */
	String OSC_USER_INFO_URL = "https://www.oschina.net/action/openapi/user?access_token=%s&dataType=json";

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";
	/**
	 * oauth_client 表的字段，id、client_secret
	 */
	String CLIENT_FIELDS = "id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
			+ " from biz_oauth_client";

	/**
	 * 查询语句
	 */
	String FIND_STATEMENT = BASE_FIND_STATEMENT + " order by id";

	/**
	 * 按条件client_id 查询
	 */
	String SELECT_STATEMENT = BASE_FIND_STATEMENT + " where id = ?";

	/**
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";
	/**
	 * 用户数据
	 */
	String DETAILS_USER = "user";

	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "user_id";

	/**
	 * 用户名字段
	 */
	String DETAILS_USERNAME = "username";

	/**
	 * 用户机构字段
	 */
	String DETAILS_ORGAN_ID = "organ_id";

	/**
	 * 租户ID 字段
	 */
	String DETAILS_TENANT_ID = "tenant_id";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";

	/**
	 * AES 加密
	 */
	String AES = "aes";
}
