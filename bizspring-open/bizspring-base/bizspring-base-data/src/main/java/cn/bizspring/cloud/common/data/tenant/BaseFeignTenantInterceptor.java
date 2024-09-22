package cn.bizspring.cloud.common.data.tenant;

import cn.hutool.core.util.StrUtil;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 */
@Slf4j
public class BaseFeignTenantInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (StrUtil.isBlank(TenantContextHolder.getTenantId())) {
			return;
		}
		requestTemplate.header(CommonEnum.TENANT_ID, TenantContextHolder.getTenantId());
	}
}
