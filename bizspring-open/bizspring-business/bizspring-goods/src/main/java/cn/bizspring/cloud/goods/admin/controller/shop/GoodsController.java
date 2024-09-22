package cn.bizspring.cloud.goods.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.goods.admin.service.*;
import cn.bizspring.cloud.goods.common.entity.*;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Controller - 商品
 * 
 */
@AllArgsConstructor
@Api(value = "goods", tags = "商城 - 商品")
@RestController("memberGoodsController")
@RequestMapping("/member/goods")
public class GoodsController extends BizController {

	private GoodsService goodsService;
	private FeignStoreService feignStoreService;
	private GoodsCategoryService goodsCategoryService;
	private BrandService brandService;
	private SkuService skuService;

	/**
	 * 详情
	 */
	@ApiOperation(value = "详情", notes = "详情")
	@ApiOperationSupport(order = 2)
	@GetMapping("/detail")
	public ResponseEntity detail(String goodsId,String skuId) {
		Goods goods = goodsService.find(goodsId);
		if(CommonUtils.isNull(goods)){
			Sku sku = skuService.find(skuId);
			if(CommonUtils.isNotNull(sku)){
				goods = goodsService.find(sku.getGoodsId());
				if(CommonUtils.isNotNull(goods)){
					goodsId=goods.getId();
				}
			}
		}
		if (goods == null || BooleanUtils.isNotTrue(goods.getIsActive()) || BooleanUtils.isNotTrue(goods.getIsMarketable())) {
			return R.failed("内容不存在");
		}
		Store store=feignStoreService.find(goods.getStoreId(),SecurityEnum.FROM_IN);

		Map<String, Object> data = new HashMap<>(2);
		data.put("goods",goods);
		data.put("store",store);

		data.put("brands", brandService.findAll());

		Sku sku= new Sku();
		sku.setGoodsId(goodsId);
		List skus = skuService.findAllByEntity(sku);
		data.put("skus",skus);

		data.put("defaultSku",goodsService.getDefaultSku(goods));
		Map<String, Object> paramsMapPP = new HashMap();
		paramsMapPP.put("goodss_id", goodsId);
		Map<String, Object> paramsMapPPT = new HashMap();
		paramsMapPPT.put("goodss_id", goodsId);
		Map<String, Object> paramsMapPSPT = new HashMap();
		paramsMapPSPT.put("goodss_id", goodsId);

		return R.ok(data);
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/store_list")
	public ResponseEntity list(String storeId, String goodsCategoryId, Boolean isActive, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert, Goods.OrderType orderType,
							   Pageable pageable) {
		Store store = feignStoreService.find(storeId,SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		data.put("data", goodsService.findPage(null,null,null, null, store, goodsCategoryService.find(goodsCategoryId), null, null, null, null, isMarketable, isList, isTop, isActive, isOutOfStock, isStockAlert,  orderType, feignStoreService.findAll(SecurityEnum.FROM_IN), pageable));
		return R.ok(data);
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@PostMapping("/list")
	public ResponseEntity list(@RequestBody List<Map> goodsPropertys,String goodsCategoryId, Store.Type storeType, String brandId, BigDecimal startPrice, BigDecimal endPrice, Boolean isOutOfStock, Goods.OrderType orderType, Integer pageNumber, Integer pageSize) {

		Map<String, Object> data = new HashMap<>(2);
		GoodsCategory goodsCategory = goodsCategoryService.find(goodsCategoryId);

		if (goodsCategory == null) {
			return R.failed("内容不存在");
		}
		//品牌 参数
		Brand brand = brandService.find(brandId);
		if (CommonUtils.isNotNull(goodsCategory)) {
			//关联下级GoodsCategory
			List<GoodsCategory> goodsCategories = goodsCategoryService.findChildren(goodsCategoryId, false, 999999, false);
			if (CommonUtils.isNotNull(goodsCategories)) {
				data.put("goodsCategories", goodsCategories);
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal tempPrice = startPrice;
			startPrice = endPrice;
			endPrice = tempPrice;
		}

		Pageable pageable = new Pageable(pageNumber, pageSize);
		data.put("orderTypes", Goods.OrderType.values());
		data.put("goodsCategory", goodsCategory);
		data.put("storeType", storeType);
		data.put("brand", brand);
		data.put("startPrice", startPrice);
		data.put("endPrice", endPrice);
		data.put("isOutOfStock", isOutOfStock);
		data.put("orderType", orderType);
		data.put("pageNumber", pageNumber);
		data.put("pageSize", pageSize);

		List<Store> stores=feignStoreService.findAll(SecurityEnum.FROM_IN);
		Page<Goods> goodss =goodsService.findPage(null,null,null, storeType, null, goodsCategory, null, brand,  startPrice, endPrice, true, true, null, true, isOutOfStock, null,  orderType,stores, pageable);

		data.put("data",goodss);
		return R.ok(data);
	}


	/**
	 * 首页楼层
	 */
	@ApiOperation(value = "首页楼层", notes = "首页楼层")
	@ApiOperationSupport(order = 2)
	@GetMapping("/index")
	public ResponseEntity index(Goods.OrderType orderType){
		Map<String, Object> data = new HashMap<>(2);

		GoodsCategory goodsCategoryParams = new GoodsCategory();
		goodsCategoryParams.setLevel(1);
		//首页横向二级分类
		data.put("goodsCategorys",goodsCategoryService.findAllByEntity(goodsCategoryParams));
		//获取根分类
		List<GoodsCategory> rootGoodsCategories = goodsCategoryService.findRoots(5, true);
		data.put("rootGoodsCategories",rootGoodsCategories);

		//甄选商品
		List<Goods> selectionGoodss = goodsService.findSelectionList(null,99999);
		data.put("selectionGoodss",selectionGoodss);

		List<GoodsCategory> rootCategories = goodsCategoryService.findRoots(1, true);
		//首页底部商品
		List floorGoodss=new ArrayList();
		if(CollectionUtils.isNotEmpty(rootCategories)){
			for(int i = 0;i<rootCategories.size();i++){
				List<Sort> sorts =new ArrayList<>();
				sorts.add(null);
				List<Goods> goodss = goodsService.findList(null, null, rootCategories.get(i).getId(),  null,  null, null, true, true, null, true, null, null, orderType, 999999, null,sorts, false);
				if(CollectionUtils.isNotEmpty(goodss)){
					floorGoodss.addAll(goodss);
				}
			}
		}
		data.put("floorGoodss",floorGoodss);
		return R.ok(data);
	}

}