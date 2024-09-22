package cn.bizspring.cloud.store.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.store.common.entity.Store;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 店铺
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "store", tags = "商城 - 店铺")
@RestController("memberStoreController")
@RequestMapping("/member/store")
public class StoreController extends BizController {

	private StoreService storeService;

	/**
	 * 首页
	 */
	@ApiOperation(value = "首页", notes = "首页")
	@ApiOperationSupport(order = 2)
	@GetMapping("/index")
	public ResponseEntity index(String id) {
		Map<String,Object> data =new HashMap();
		Store store = storeService.find(id);
		//店铺信息
		data.put("store",store);
		return R.ok(data);
	}

}