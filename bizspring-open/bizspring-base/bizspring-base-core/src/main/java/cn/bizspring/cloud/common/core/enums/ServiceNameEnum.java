package cn.bizspring.cloud.common.core.enums;

/**
 * 服务名常量
 */
public interface ServiceNameEnum {
	/**
	 * nacos
	 */
	String NACOS_SERVICE = "bizspring-module-nacos";
	/**
	 * 网关
	 */
	String GATEWAY_SERVICE = "bizspring-module-gateway";
	/**
	 * 认证中心
	 */
	String AUTH_SERVICE = "bizspring-auth";
	/**
	 * UMPS ADMIN服务
	 */
	String UMPS_ADMIN_SERVICE = "bizspring-upms";

	/**
	 * STORE ADMIN服务
	 */
	String STORE_SERVICE = "bizspring-store";

	/**
	 * GOODS ADMIN服务
	 */
	String GOODS_SERVICE = "bizspring-goods";

	/**
	 * ORDER ADMIN服务
	 */
	String ORDER_SERVICE = "bizspring-order";

}
