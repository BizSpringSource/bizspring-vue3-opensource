package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.upms.common.entity.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignRegionService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignRegionService {

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/region/find/{id}")
	Region find(@PathVariable(value = "id",required = false)String id, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 列表
	 */
	@GetMapping("/provider/region/findChildren")
	List findChildren(@RequestParam(value = "regionId",required = false) String regionId, @RequestParam(value = "recursive",required = false) boolean recursive,@RequestParam(value = "count",required = false) Integer count,@RequestHeader(SecurityEnum.FROM) String from);

}
