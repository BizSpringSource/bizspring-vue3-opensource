package cn.bizspring.cloud.upms.admin.controller.merchant;

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
@RestController("merchantStoreFavoriteController")
@RequestMapping("/merchant/store_favorite")
public class StoreFavoriteController extends BizController {

	private StoreFavoriteService storeFavoriteService;
	private FeignStoreService feignStoreService;
	private MemberService memberService;


	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Integer pageNumber) {
		Store currentStore =feignStoreService.getCurrent(SecurityUtils.getUserId(),SecurityEnum.FROM_IN);
		Pageable pageable = new Pageable(pageNumber, Pageable.PAGE_SIZE);
		StoreFavorite storeFavorite = new StoreFavorite();
		storeFavorite.setStoreId(currentStore.getId());
		Page data=storeFavoriteService.findPageByEntity(pageable,storeFavorite);
		if(CommonUtils.isNotNull(data)){
			if(CommonUtils.isNotNull(data.getContent())){
				List<StoreFavorite> storeFavorites=data.getContent();
				for(int i=0;i<storeFavorites.size();i++){
					Member member = memberService.find(storeFavorites.get(i).getMemberId());
					if (CommonUtils.isNotNull(member)) {
						storeFavorites.get(i).setMemberName(member.getUsername());
					}
				}
				data.setContent(storeFavorites);
			}
		}
		return R.ok(data);
	}

}