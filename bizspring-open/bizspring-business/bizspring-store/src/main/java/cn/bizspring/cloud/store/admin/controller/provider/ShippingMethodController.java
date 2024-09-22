package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.ShippingMethodService;
import cn.bizspring.cloud.store.common.entity.ShippingMethod;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.Address;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *Provider Controller - 配送方式
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "shipping_method", tags = "provider - 配送方式")
@RestController("providerShippingMethodController")
@RequestMapping("/provider/shipping_method")
public class ShippingMethodController extends BizController {

	private ShippingMethodService shippingMethodService;

	/**
	 * 查找所有实体对象集合
	 *
	 * @return 所有实体对象集合
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找配送方式所有实体对象集合", notes = "查找配送方式所有实体对象集合")
	@ApiOperationSupport(order = 1)
	public List<ShippingMethod> findAll( @RequestHeader(SecurityEnum.FROM) String from){
		return 	shippingMethodService.findAll();
	};

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find/{id}")
	@ApiOperation(value = "查找配送方式实体对象", notes = "查找配送方式实体对象")
	@ApiOperationSupport(order = 2)
	public ShippingMethod find(@PathVariable("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return 	shippingMethodService.find(id);
	};

	/**
	 * 计算运费
	 *
	 * @param params
	 *            参数
	 * @return 运费
	 */
	@Inside
	@PostMapping("/calculate_freight")
	@ApiOperation(value = "计算运费", notes = "计算运费")
	@ApiOperationSupport(order = 3)
	public BigDecimal calculateFreight(@RequestBody Map<String,Object> params, @RequestHeader(SecurityEnum.FROM) String from){
		ObjectMapper mapper=new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ShippingMethod shippingMethod = null!=params.get("shippingMethod")?mapper.convertValue(params.get("shippingMethod"),ShippingMethod.class):null;
		Store store = null!=params.get("store")?mapper.convertValue(params.get("store"),Store.class):null;
		Address address = null!=params.get("address")?mapper.convertValue(params.get("address"),Address.class):null;
		int weight = null!=params.get("weight")?mapper.convertValue(params.get("weight"),int.class):0;
		return shippingMethodService.calculateFreight(shippingMethod, store, address, weight);
	}

}