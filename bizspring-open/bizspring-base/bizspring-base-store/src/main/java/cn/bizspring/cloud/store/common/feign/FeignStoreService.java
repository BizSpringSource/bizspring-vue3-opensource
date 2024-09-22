package cn.bizspring.cloud.store.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.store.common.entity.Store;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignStoreService", value = ServiceNameEnum.STORE_SERVICE)
public interface FeignStoreService {

	/**
	 * 查找实体对象
	 *
	 * @param storeId
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/store/find")
	Store find(@RequestParam(value = "id") String storeId, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 增加到期天数
	 *
	 * @param store
	 *            店铺
	 * @param amount
	 *            值
	 */
	@PostMapping("/provider/store/add_end_days")
	void addEndDays(@RequestBody Store store, @RequestParam("amount") int amount, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 获取当前登录商家店铺
	 *
	 * @return 当前登录商家店铺，若不存在则返回null
	 */
	@GetMapping("/provider/store/get_current")
	Store getCurrent(@RequestParam("userId") String userId,@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 根据 ID 查询 店铺
	 * @param id
	 * @return
	 */
	@GetMapping("/provider/store/get_by_id/{id}")
	Store getById(@PathVariable(value = "id") String id, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找所有实体对象集合
	 * @return 所有实体对象集合
	 */
	@GetMapping("/provider/store/find_all")
	List<Store> findAll(@RequestHeader(SecurityEnum.FROM) String from);

}
