/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.gateway.entity;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;
import java.util.List;

/**
 * 路由定义模型
 * @author www.bizspring.cn
 * @date 2023-01-25
 */
@Data
public class GatewayRouteList {
private static final long serialVersionUID = 1L;

	List<RouteDefinition> routes;
  
}
