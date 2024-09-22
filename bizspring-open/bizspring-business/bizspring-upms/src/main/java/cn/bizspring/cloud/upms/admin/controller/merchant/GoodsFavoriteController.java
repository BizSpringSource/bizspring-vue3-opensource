package cn.bizspring.cloud.upms.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.admin.service.GoodsFavoriteService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.GoodsFavorite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller - 商品收藏
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods_favorite", tags = "商城 - 商品收藏")
@RestController("merchantGoodsFavoriteController")
@RequestMapping("/merchant/goods_favorite")
public class GoodsFavoriteController extends BizController {

	private GoodsFavoriteService goodsFavoriteService;
	private FeignGoodsService feignGoodsService;
	private FeignStoreService feignStoreService;
	private MemberService memberService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Integer pageNumber) {
		Store store = feignStoreService.getCurrent(SecurityUtils.getUserId(),SecurityEnum.FROM_IN);
		Pageable pageable = new Pageable(pageNumber, Pageable.PAGE_SIZE);
		Goods goods =new Goods();
		goods.setStoreId(store.getId());
		List<Goods> goodss = feignGoodsService.findAllByEntity(goods,SecurityEnum.FROM_IN);
		Page data=goodsFavoriteService.findFavoritePageOfStore(goodss, pageable);
		if(CommonUtils.isNotNull(data)){
			if(CommonUtils.isNotNull(data.getContent())){
				List<GoodsFavorite> goodsFavorites=data.getContent();
				for(int i=0;i<goodsFavorites.size();i++){
					Member member = memberService.find(goodsFavorites.get(i).getMemberId());
					if (CommonUtils.isNotNull(member)) {
						goodsFavorites.get(i).setMemberName(member.getUsername());
					}
				}
				data.setContent(goodsFavorites);
			}
		}
		return R.ok(data);
	}

}