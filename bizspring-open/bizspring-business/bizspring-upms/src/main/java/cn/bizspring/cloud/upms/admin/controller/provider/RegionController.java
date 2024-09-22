package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.RegionService;
import cn.bizspring.cloud.upms.common.entity.Region;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 地区
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "region", tags = "provider - 地区")
@RestController("providerRegionController")
@RequestMapping("/provider/region")
public class RegionController extends BizController {

	private RegionService regionService;

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find/{id}")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	public Region find(@PathVariable("id")String id, @RequestHeader(SecurityEnum.FROM) String from){
		return	regionService.find(id);
	};

	/**
	 * 列表
	 */
	@Inside
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/findChildren")
	public List findChildren(@RequestParam(value = "regionId",required = false) String regionId, @RequestParam(value = "recursive",required = false) boolean recursive,@RequestParam(value = "count",required = false) Integer count,@RequestHeader(SecurityEnum.FROM) String from) {
		if(CommonUtils.isNotEmpty(regionId)){
			Region region=new Region();
			return	regionService.findChildren(region,recursive,count);
		}else{
			return	regionService.findChildren(null,recursive,count);
		}
	}

}