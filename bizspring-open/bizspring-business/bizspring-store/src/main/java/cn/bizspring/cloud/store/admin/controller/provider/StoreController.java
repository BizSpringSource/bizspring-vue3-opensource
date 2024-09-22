package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 店铺
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "store", tags = "provider - 店铺")
@RestController("providerStoreController")
@RequestMapping("/provider/store")
public class StoreController extends BizController {

	private StoreService storeService;

	/**
	 * 查找实体对象
	 *
	 * @param storeId
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找店铺实体对象", notes = "查找店铺实体对象")
	@ApiOperationSupport(order = 1)
	public Store find(@RequestParam("id") String storeId, @RequestHeader(SecurityEnum.FROM) String from){
		Store store = storeService.find(storeId);
		return store;
	};

	/**
	 * 增加到期天数
	 *
	 * @param store
	 *            店铺
	 * @param amount
	 *            值
	 */
	@Inside
	@PostMapping("/add_end_days")
	@ApiOperation(value = "增加到期天数", notes = "增加到期天数")
	@ApiOperationSupport(order = 7)
	public void addEndDays(@RequestBody Store store, int amount, @RequestHeader(SecurityEnum.FROM) String from){
		storeService.addEndDays(store,amount);
	};

	/**
	 * 获取当前登录商家店铺
	 *
	 * @return 当前登录商家店铺，若不存在则返回null
	 */
	@Inside
	@GetMapping("/get_current")
	@ApiOperation(value = "获取当前登录商家店铺", notes = "获取当前登录商家店铺")
	@ApiOperationSupport(order = 9)
	public Store getCurrent(String userId ,@RequestHeader(SecurityEnum.FROM) String from){
		return	storeService.getCurrent(userId);
	};

	/**
	 * 根据 ID 查询 店铺
	 * @param id
	 * @return
	 */
	@Inside
	@GetMapping("/get_by_id/{id}")
	@ApiOperation(value = "根据 ID 查询店铺", notes = "根据 ID 查询 店铺")
	@ApiOperationSupport(order = 10)
	public Store getById(@PathVariable("id") String id, @RequestHeader(SecurityEnum.FROM) String from){
		return	storeService.getById(id);
	};

	/**
	 * 查找所有实体对象集合
	 * @return 所有实体对象集合
	 */
	@Inside
	@GetMapping("/find_all")
	@ApiOperation(value = "查找所有店铺实体对象集合", notes = "查找所有店铺实体对象集合")
	@ApiOperationSupport(order = 11)
	public List<Store> findAll( @RequestHeader(SecurityEnum.FROM) String from){
		return storeService.findAll();
	};


}