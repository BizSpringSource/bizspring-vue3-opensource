package cn.bizspring.cloud.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 * 聚合接口文档注册，和zuul实现类似
 */
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {
	public static final String API_URI = "/v2/api-docs";
	private final RouteDefinitionRepository routeDefinitionRepository;
	private final FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;


	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<RouteDefinition> routes = new ArrayList<>();
		routeDefinitionRepository.getRouteDefinitions().subscribe(route -> routes.add(route));
		routes.forEach(routeDefinition -> routeDefinition.getPredicates().stream()
			.filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
			.filter(predicateDefinition -> !filterIgnorePropertiesConfig.getSwaggerProviders().contains(routeDefinition.getId()))
			.forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
				predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
					.replace("/**", API_URI)))));

		return resources.stream().sorted(Comparator.comparing(SwaggerResource::getName))
			.collect(Collectors.toList());
	}

	private SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}
}
