package cn.bizspring.cloud.store.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.store.admin.repository.StoreRepository;
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
@Api(value = "store_repository", tags = "provider - 店铺")
@RestController("providerStoreRepositoryController")
@RequestMapping("/provider/store_repository")
public class StoreRepositoryController extends BizController {

	private StoreRepository storeRepository;

	/**
	 * 保存所有
	 * @param stores 店铺
	 */
	@Inside
	@PostMapping("/save_all")
	@ApiOperation(value = "删除用户", notes = "根据ID删除用户")
	@ApiOperationSupport(order = 1)
	public void saveAll(@RequestBody List<Store> stores, @RequestHeader(SecurityEnum.FROM) String from){
		storeRepository.saveAll(stores);
	};

}