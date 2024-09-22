package cn.bizspring.cloud.goods.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.goods.common.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignGoodsService", value = ServiceNameEnum.GOODS_SERVICE)
public interface FeignGoodsService {

	/**
	 * 根据实体携带参数，查找所有实体对象
	 * @return 实体对象，若不存在则返回null
	 */
	@PostMapping("/provider/goods/find_all_by_entity")
	List<Goods> findAllByEntity(@RequestBody Goods goods,@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/goods/find")
	Goods find(@RequestParam(value = "id") String id, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 刷新过期店铺商品有效状态
	 */
	@PostMapping("/provider/goods/refresh_expired_store_goods_active")
	void refreshExpiredStoreGoodsActive(@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 刷新商品有效状态
	 *
	 * @param params
	 *            店铺
	 */
	@PostMapping("/provider/goods/refresh_active")
	void refreshActive(@RequestBody Map<String,Object> params, @RequestHeader(SecurityEnum.FROM) String from);


	/**
	 *
	 * @param params
	 *            参数
	 * @return
	 */
	@PostMapping("/provider/goods/is_valid")
	boolean isValid(@RequestBody Map params, @RequestHeader(SecurityEnum.FROM) String from);


}
