package cn.bizspring.cloud.goods.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.storage.util.UploadFileUtils;
import cn.bizspring.cloud.goods.admin.repository.GoodsRepository;
import cn.bizspring.cloud.goods.admin.service.*;
import cn.bizspring.cloud.goods.common.entity.*;
import cn.bizspring.cloud.store.common.entity.*;
import cn.bizspring.cloud.store.common.feign.*;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import cn.bizspring.cloud.upms.common.feign.FeignConfigStorageService;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.List;

/**
 * Controller - 商品
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods", tags = "商家 - 商品")
@RestController("merchantGoodsController")
@RequestMapping("/merchant/goods")
public class GoodsController extends BizController {

	private GoodsService goodsService;
	private FeignStoreService feignStoreService;
	private GoodsCategoryService goodsCategoryService;
	private FeignStoreGoodsCategoryService feignStoreGoodsCategoryService;
	private BrandService brandService;
	private GoodsImageService goodsImageService;
	private SpecificationItemService specificationItemService;
	private FeignConfigStorageService feignConfigStorageService;
	private FeignGoodsCategoryStoreService feignGoodsCategoryStoreService;
	private GoodsRepository goodsRepository;

	/**
	 * 检查编号是否存在
	 */
	@ApiOperation(value = "检查编号是否存在", notes = "检查编号是否存在")
	@ApiOperationSupport(order = 2)
	@GetMapping("/check_sn")
	public  ResponseEntity checkSn(String sn) {
		return R.ok(StringUtils.isNotEmpty(sn) && !goodsService.snExists(sn));
	}

	/**
	 * 上传商品图片
	 */
	@ApiOperation(value = "上传商品图片", notes = "上传商品图片")
	@ApiOperationSupport(order = 2)
	@PostMapping("/upload_goods_image")
	public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file,
							 @RequestParam("path") String path) throws Exception {
//		File file = FileUtils.multipartFileToFile(mulFile);
		path = StrUtil.format("{}/{}/", TenantContextHolder.getTenantId(),  path);
		ConfigStorage configStorage = feignConfigStorageService.getOne(SecurityEnum.FROM_IN);
		if(configStorage == null){
			throw new RuntimeException("请先配置文件存储信息");
		}

		if (file == null || file.isEmpty()) {
			return R.INVALID_ENTITY;
		}

		GoodsImage goodsImage = new GoodsImage();
		String goodsVideo=new String();
		if(file.getContentType().indexOf("image") != -1 ||  file.getContentType().indexOf("application")!=-1){
			//图片上传
			goodsImage = goodsImageService.generateImage(file,path);
			goodsImage.setType(GoodsImage.Type.IMAGE);
		}else if(file.getContentType().indexOf("video") != -1){
			//视频上传
			goodsImage = goodsImageService.generateVideo(file,path);
			goodsImage.setType(GoodsImage.Type.MEDIA);
		}else{
			goodsImage.setSource(UploadFileUtils.uploadFile(path,FileUtils.multipartFileToFile(file),configStorage));
			goodsImage.setType(GoodsImage.Type.FILE);
			return R.ok(goodsImage);
		}
		if (goodsImage == null&&CommonUtils.isEmpty(goodsVideo)) {
			return R.invalidEntity("上传文件出现错误");
		}
		return R.ok(goodsImage);
	}

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Store currentStore=feignStoreService.getCurrent(SecurityUtils.getUserId(),SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		List<GoodsCategoryStore> goodsCategoryStores= feignGoodsCategoryStoreService.findAll(SecurityEnum.FROM_IN);
		List<GoodsCategory> goodsCategories= goodsCategoryService.findList(currentStore, null, null,goodsCategoryStores,null);
		data.put("allowedGoodsCategories", goodsCategoryService.integrationToTree(goodsCategories));
		data.put("storeGoodsCategoryTree", feignStoreGoodsCategoryService.findTree(currentStore, SecurityEnum.FROM_IN));
		data.put("brands", brandService.findAll());
		Map paramsMap=new HashMap();
			paramsMap.put("store",currentStore);
			paramsMap.put("isEnabled",true);
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(@RequestBody Map params) {

		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Goods goodsForm = null != params.get("goodsForm") ? mapper.convertValue(params.get("goodsForm"), Goods.class) : null;
		SkuForm skuForm = null != params.get("skuForm") ? mapper.convertValue(params.get("skuForm"), SkuForm.class) : null;
		goodsForm.setStoreId(currentStore.getId());
		goodsForm.setIsList(true);
		goodsForm.setIsMarketable(true);
		goodsForm.setIsTop(true);
		Goods goods = null;
		if (CommonUtils.isNotNull(goodsForm)) {
			//商品sn是否被使用
			if (StringUtils.isNotEmpty(goodsForm.getSn()) && goodsService.snExists(goodsForm.getSn())) {
				return R.INVALID_ENTITY;
			}
				Sku sku = null;
				if (CommonUtils.isNotNull(skuForm)) {
					sku = skuForm.getSku();
					System.out.println(sku == null );
					if (sku == null || !isValid(sku,Sku.Save.class)) {
						return R.INVALID_ENTITY;
					}
				}
				goods = goodsService.create(goodsForm, sku);

		}
		//添加搜索
		goodsRepository.save(goods);
		return R.ok(goods);
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String goodsId) {
		Store currentStore=feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		if (goodsId == null) {
			return R.failed();
		}
		Goods goods=goodsService.find(goodsId);
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		List<GoodsCategoryStore> goodsCategoryStores= feignGoodsCategoryStoreService.findAll(SecurityEnum.FROM_IN);
		List<GoodsCategory> goodsCategories= goodsCategoryService.findList(currentStore, null, null,goodsCategoryStores,null);
		data.put("allowedGoodsCategories", goodsCategoryService.integrationToTree(goodsCategories));
		data.put("checkedGoodsCategory",goodsCategoryService.find(goods.getGoodsCategoryId()));
		data.put("storeGoodsCategoryTree", feignStoreGoodsCategoryService.findTree(currentStore, SecurityEnum.FROM_IN));
		data.put("checkedStoreGoodsCategory", feignStoreGoodsCategoryService.find(goods.getStoreGoodsCategoryId(),SecurityEnum.FROM_IN));
		data.put("brands", brandService.findAll());
		Map paramsMap=new HashMap();
		paramsMap.put("store",currentStore);
		paramsMap.put("isEnabled",true);
		data.put("defaultSku",goodsService.getDefaultSku(goods));
		Map<String, Object> paramsMapPPT = new HashMap();
			paramsMapPPT.put("goodss_id", goodsId);
		Map<String, Object> paramsMapPSPT = new HashMap();
			paramsMapPSPT.put("goodss_id", goodsId);
		data.put("goods", goods);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(@RequestBody Map params

	) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Goods goodsForm = null != params.get("goodsForm") ? mapper.convertValue(params.get("goodsForm"), Goods.class) : null;
		SkuForm skuForm = null != params.get("skuForm") ? mapper.convertValue(params.get("skuForm"), SkuForm.class) : null;

		goodsImageService.filter(goodsForm.getGoodsImages());
		specificationItemService.filter(goodsForm.getSpecificationItems());
		goodsForm.setIsActive(true);
		if (CommonUtils.isNotNull(goodsForm)) {
			if (!isValid(goodsForm, BizEntity.Update.class)) {
				return R.INVALID_ENTITY;
			}
				Sku sku = skuForm.getSku();
				if (sku == null || !isValid(sku,BizEntity.Update.class)) {
					return R.INVALID_ENTITY;
				}
				goodsService.modify(goodsForm, sku);

		}

		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(String goodsCategoryId, String brandId,   Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType,
							   Pageable pageable) {
		Store currentStore=feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		Brand brand = brandService.find(brandId);
		List<Store> stores=feignStoreService.findAll(SecurityEnum.FROM_IN);
		data.put("goodsCategoryTree", goodsCategoryService.findTree());
		List<GoodsCategoryStore> goodsCategoryStores= feignGoodsCategoryStoreService.findAll(SecurityEnum.FROM_IN);
		data.put("allowedGoodsCategories", goodsCategoryService.findList(currentStore, null, null,goodsCategoryStores, null));
		data.put("brands", brandService.findAll());

		data.put("goodsCategoryId", null != goodsCategoryId ? goodsCategoryId : null);
		data.put("brandId", brandId);
		data.put("isMarketable", isMarketable);
		data.put("isList", isList);
		data.put("isTop", isTop);
		data.put("isActive", isActive);
		data.put("isOutOfStock", isOutOfStock);
		data.put("isStockAlert", isStockAlert);
		data.put("data", goodsService.findPage(null,null,null, null, currentStore, goodsCategoryService.find(goodsCategoryId), null, brand,   null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert, orderType,stores, pageable));
		return R.ok(data);
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		Store currentStore=feignStoreService.getCurrent(SecurityUtils.getUserId(), SecurityEnum.FROM_IN);
		if (ids != null) {
			for (String id : ids) {
				Goods goods = goodsService.find(id);
				if (goods == null) {
					return R.INVALID_ENTITY;
				}
				if (!currentStore.getId().equals(goods.getStoreId())) {
					return R.INVALID_ENTITY;
				}
				goodsService.delete(goods.getId());
				//删除搜索
				goodsRepository.delete(goods);
			}
		}
		return R.OK;
	}

	/**
	 * FormBean - SKU
	 * 
	 */
	public static class SkuForm {

		/**
		 * SKU
		 */
		private Sku sku;

		/**
		 * 获取SKU
		 * 
		 * @return SKU
		 */
		public Sku getSku() {
			return sku;
		}

		/**
		 * 设置SKU
		 * 
		 * @param sku
		 *            SKU
		 */
		public void setSku(Sku sku) {
			this.sku = sku;
		}

	}

}