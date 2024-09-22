package cn.bizspring.cloud.upms.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.admin.service.StoreFavoriteService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.StoreFavorite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller - 店铺收藏
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "store_favorite", tags = "商城 - 店铺收藏")
@RestController("memberStoreFavoriteController")
@RequestMapping("/member/store_favorite")
public class StoreFavoriteController extends BizController {

	private StoreFavoriteService storeFavoriteService;
	private FeignStoreService feignStoreService;
	private MemberService memberService;

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@PostMapping("/add")
	public ResponseEntity add(String storeId) {
		Member currentUser =memberService.find(SecurityUtils.getUserId());
		if (storeId == null) {
			return R.NOT_FOUND;
		}
		if (storeFavoriteService.exists(SecurityUtils.getUserId(),storeId)) {
			return R.invalidEntity("该店铺已收藏");
		}
		if (StoreFavorite.MAXIMUM_NUMBER_STORE_FAVORITES != null && storeFavoriteService.count(currentUser) >= StoreFavorite.MAXIMUM_NUMBER_STORE_FAVORITES) {
			return R.invalidEntity("店铺收藏数量已超限", StoreFavorite.MAXIMUM_NUMBER_STORE_FAVORITES);
		}
		StoreFavorite storeFavorite = new StoreFavorite();
		storeFavorite.setMemberId(currentUser.getId());
		storeFavorite.setStoreId(storeId);
		storeFavoriteService.save(storeFavorite);
		return R.OK;
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Integer pageNumber) {
		Member currentUser =memberService.find(SecurityUtils.getUserId());
		Pageable pageable = new Pageable(pageNumber, Pageable.PAGE_SIZE);
		Page data=storeFavoriteService.findPage(currentUser, pageable);

		if(CommonUtils.isNotNull(data)){
			if(CommonUtils.isNotNull(data.getContent())){
				List<StoreFavorite> storeFavorites=data.getContent();
				for(int i=0;i<storeFavorites.size();i++){
					Store store =feignStoreService.find(storeFavorites.get(i).getStoreId(),SecurityEnum.FROM_IN);
					if (CommonUtils.isNotNull(store)) {
						storeFavorites.get(i).setStoreName(store.getName());
						storeFavorites.get(i).setStoreLogo(store.getLogo());
					}
				}
				data.setContent(storeFavorites);
			}
		}
		return R.ok(data);
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		if (ids == null) {
			return R.NOT_FOUND;
		}
		storeFavoriteService.delete(ids);
		return R.OK;
	}

}