package cn.bizspring.cloud.goods.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.goods.admin.service.GoodsService;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.store.common.entity.Store;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *Provider Controller - 商品
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods", tags = "provider - 商品")
@RestController("providerGoodsController")
@RequestMapping("/provider/goods")
public class GoodsController extends BizController {

	private GoodsService goodsService;


	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找实体对象", notes = "查找实体对象")
	@ApiOperationSupport(order = 1)
	public Goods find(@RequestParam("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return goodsService.find(id);
	}

	/**
	 * 根据实体对象携带参数 查询所有对象
	 * @return
	 */
	@Inside
	@ApiOperation(value = "根据实体对象携带参数,查询所有对象", notes = "根据实体对象携带参数,查询所有对象")
	@ApiOperationSupport(order = 1)
	@PostMapping("/find_all_by_entity")
	public List<Goods> findAllByEntity(@RequestBody Goods goods,@RequestHeader(SecurityEnum.FROM) String from){
		return  goodsService.findAllByEntity(goods);
	}

	/**
	 * 刷新过期店铺商品有效状态
	 */
	@Inside
	@PostMapping("/_expired_store_goods_active")
	@ApiOperation(value = "刷新过期店铺商品有效状态", notes = "刷新过期店铺商品有效状态")
	@ApiOperationSupport(order = 2)
	public void refreshExpiredStoreGoodsActive( @RequestHeader(SecurityEnum.FROM) String from){
		goodsService.refreshExpiredStoreGoodsActive();
	}

	/**
	 * 刷新商品有效状态
	 *
	 * @param params
	 *            参数
	 */
	@Inside
	@PostMapping("/refresh_active")
	@ApiOperation(value = "刷新商品有效状态", notes = "刷新商品有效状态")
	@ApiOperationSupport(order = 3)
	public void refreshActive(@RequestBody Map<String,Object> params, @RequestHeader(SecurityEnum.FROM) String from){
		ObjectMapper mapper=new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Store store = null!=params.get("store")?mapper.convertValue(params.get("store"),Store.class):null;
		goodsService.refreshActive(store);
	}

}