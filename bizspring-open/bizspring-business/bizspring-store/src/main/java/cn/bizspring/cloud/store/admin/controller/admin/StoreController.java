package cn.bizspring.cloud.store.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.goods.common.entity.GoodsCategory;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsCategoryService;
import cn.bizspring.cloud.store.admin.service.StoreCategoryService;
import cn.bizspring.cloud.store.admin.service.StoreService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.upms.common.feign.FeignUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller - 店铺
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "store", tags = "管理 - 店铺")
@RestController("adminStoreController")
@RequestMapping("/admin/store")
public class StoreController extends BizController {

	private StoreService storeService;
	private StoreCategoryService storeCategoryService;
	private FeignGoodsCategoryService feignGoodsCategoryService;

	private FeignUserService feignUserService;

	/**
	 * 检查名称是否唯一
	 */
	@SysLog("检查名称是否唯一")
	@ApiOperation(value = "检查名称是否唯一", notes = "检查名称是否唯一")
	@ApiOperationSupport(order = 2)
	@GetMapping("/check_name")
	public @ResponseBody boolean checkName(String id, String name) {
		return StringUtils.isNotEmpty(name) && !storeService.nameUnique(id, name);
	}

	/**
	 * 商家选择
	 */
	@SysLog("商家选择")
	@ApiOperation(value = "商家选择", notes = "商家选择")
	@ApiOperationSupport(order = 2)
	@GetMapping("/merchant_select")
	public ResponseEntity merchantSelect(String keyword, Integer count) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return R.ok(data);
		}
		List<Object> merchants =feignUserService.keywordSearchBydType(keyword, User.Type.MERCHANT,count,SecurityEnum.FROM_IN);
		if(null!=merchants&&merchants.size()>0){
			for(Object merchante : merchants){
				JSONObject jsonObject=JSONObject.fromObject(merchante); // 将数据转成json字符串
				Merchant merchant = (Merchant)JSONObject.toBean(jsonObject, Merchant.class); //将json转成需要的对象
				// 这里要查询出这个商家是否关联store
				if (merchant.getStoreId() == null) {
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("id", String.valueOf(merchant.getId()));
					item.put("username", merchant.getUsername());
					data.add(item);
				}
			}
		}
		return R.ok(data);
	}


	/**
	 * 店铺选择
	 */
	@SysLog("店铺选择")
	@ApiOperation(value = "店铺选择", notes = "店铺选择")
	@ApiOperationSupport(order = 2)
	@GetMapping("/store_select")
	public ResponseEntity storeSelect(String keyword, Integer count) {
		List<Map<String, Object>> data = new ArrayList<>();
		if (StringUtils.isEmpty(keyword)) {
			return R.ok(data);
		}
		List<Store> stores =storeService.keywordSearch(keyword,count);
		if(null!=stores&&stores.size()>0){
			for(Store store : stores){
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("id", String.valueOf(store.getId()));
				item.put("name", store.getName());
				data.add(item);
			}
		}
		return R.ok(data);
	}



	/**
	 * 查看
	 */
	@SysLog("查看")
	@ApiOperation(value = "查看", notes = "查看")
	@ApiOperationSupport(order = 2)
	@GetMapping("/view/{id}")
	public ResponseEntity view(@PathVariable(value = "id")  String id) {
		Map<String, Object> data = new HashMap<>(2);
		Store store = storeService.find(id);
		data.put("store", store);
		data.put("goodsCategoryRoots", feignGoodsCategoryService.findRoots(SecurityEnum.FROM_IN));
		data.put("allowedGoodsCategoryParents", getAllowedGoodsCategoryParents(store));
		return R.ok(store);
	}

	/**
	 * 添加
	 */
	@SysLog("添加")
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Map<String, Object> data = new HashMap<>(2);
		data.put("types", Store.Type.values());
		data.put("storeCategories", storeCategoryService.findAll());
		data.put("goodsCategoryTree", feignGoodsCategoryService.findTree(SecurityEnum.FROM_IN));
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@SysLog("保存")
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody Store store, String merchantId, String storeCategoryId, String[] goodsCategoryIds) {
		store.setStatus(Store.Status.PENDING);
		store.setEndDate(new Date());
		if (storeService.nameExists(store.getName())) {
			return R.INVALID_ENTITY;
		}
		if (!isValid(store, BizEntity.Save.class)) {
			return R.INVALID_ENTITY;
		}
		store.setStatus(Store.Status.SUCCESS);
		storeService.save(store);
		return R.OK;
	}

	/**
	 * 编辑
	 */
	@SysLog("编辑")
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit/{id}")
	public ResponseEntity edit(@PathVariable(value = "id")  String id) {
		Map<String, Object> data = new HashMap<>(2);
		Store store=storeService.find(id);
		if (null != store) {
			data.put("store", storeService.find(id));
			User user=feignUserService.findByUserId(store.getMerchantId(),SecurityEnum.FROM_IN);
			if(null!=user){
				data.put("merchant",user);
			}
		}
		data.put("types", Store.Type.values());
		data.put("storeCategories", storeCategoryService.findAll());
		data.put("goodsCategoryTree", feignGoodsCategoryService.findTree(SecurityEnum.FROM_IN));
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody Store store) {
		if (storeService.nameUnique(store.getId(), store.getName())) {
			return R.INVALID_ENTITY;
		}
		Store pStore = storeService.find(store.getId());
		pStore.setName(store.getName());
		pStore.setLogo(store.getLogo());
		pStore.setEmail(store.getEmail());
		pStore.setMobile(store.getMobile());
		pStore.setPhone(store.getPhone());
		pStore.setAddress(store.getAddress());
		pStore.setZipCode(store.getZipCode());
		pStore.setIntroduction(store.getIntroduction());
		pStore.setKeyword(store.getKeyword());
		pStore.setEndDate(store.getEndDate());
		pStore.setIsEnabled(store.getIsEnabled());
		pStore.setType(store.getType());
		pStore.setStoreCategoryId(store.getStoreCategoryId());
		pStore.setMerchantId(store.getMerchantId());

		if (!isValid(pStore, BizEntity.Update.class)) {
			return R.INVALID_ENTITY;
		}
		storeService.update(pStore);
		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable,Store.Type type, Store.Status status, Boolean isEnabled, Boolean hasExpired, String name,String storeCategoryId) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("type", type);
		data.put("status", status);
		data.put("statuses", Store.Status.values());
		//common.lang  中用yes no  绑定
		data.put("isEnabled", isEnabled);
		data.put("hasExpired", hasExpired);
		List storeCategories = storeCategoryService.findAll();
		List merchantes =feignUserService.findAllBydType(User.Type.MERCHANT,SecurityEnum.FROM_IN);
		data.put("types", Store.Type.values());
		data.put("storeCategories", storeCategories);
		Page stores =storeService.findPageList(merchantes,storeCategories,type, status, isEnabled, hasExpired,name,storeCategoryId,pageable);
		data.put("data", stores);
		return R.ok(data);
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		if (ids != null) {
			storeService.delete(ids);
		}
		return R.OK;
	}

	/**
	 * 获取允许发布商品分类上级分类
	 * 
	 * @param store
	 *            店铺
	 * @return 允许发布商品分类上级分类
	 */
	private Set<GoodsCategory> getAllowedGoodsCategoryParents(Store store) {
		Set<GoodsCategory> result = new HashSet<>();
		return result;
	}

}