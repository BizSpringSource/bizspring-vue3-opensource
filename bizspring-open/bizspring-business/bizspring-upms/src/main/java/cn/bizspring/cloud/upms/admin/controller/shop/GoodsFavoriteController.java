package cn.bizspring.cloud.upms.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.admin.service.GoodsFavoriteService;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.GoodsFavorite;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
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
@RestController("memberGoodsFavoriteController")
@RequestMapping("/member/goods_favorite")
public class GoodsFavoriteController extends BizController {

	private GoodsFavoriteService goodsFavoriteService;
	private FeignGoodsService feignGoodsService;
	private MemberService memberService;

	/**
	 * 添加
	 */
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@PostMapping("/add")
	public ResponseEntity add(String goodsId) {
		Member currentUser =memberService.find(SecurityUtils.getUserId());
		Goods goods = feignGoodsService.find(goodsId,SecurityEnum.FROM_IN);
		if (goods == null || BooleanUtils.isNotTrue(goods.getIsActive())) {
			return R.NOT_FOUND;
		}
		if (goodsFavoriteService.exists(currentUser, String.valueOf(goodsId))) {
			return R.invalidEntity("该商品已收藏");
		}
		if (BooleanUtils.isNotTrue(goods.getIsMarketable())) {
			return R.invalidEntity("已下架");
		}
		if (GoodsFavorite.MAXIMUM_NUMBER_GOODS_FAVORITES != null && goodsFavoriteService.count(currentUser) >= GoodsFavorite.MAXIMUM_NUMBER_GOODS_FAVORITES) {
			return R.invalidEntity("添加收藏商品数量超限", GoodsFavorite.MAXIMUM_NUMBER_GOODS_FAVORITES);
		}
		GoodsFavorite goodsFavorite = new GoodsFavorite();
		goodsFavorite.setMemberId(currentUser.getId());
		goodsFavorite.setGoodsId(goodsId);
		goodsFavoriteService.save(goodsFavorite);
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
		Page data=goodsFavoriteService.findPage(currentUser, pageable);
		if(CommonUtils.isNotNull(data)){
			if(CommonUtils.isNotNull(data.getContent())){
				List<GoodsFavorite> goodsFavorites=data.getContent();
				for(int i=0;i<goodsFavorites.size();i++){
					Goods goods =feignGoodsService.find(goodsFavorites.get(i).getGoodsId(),SecurityEnum.FROM_IN);
					if (CommonUtils.isNotNull(goods)) {
						if (CommonUtils.isNotNull(goods.getThumbnail())) {
							goodsFavorites.get(i).setGoodsThumbnail(goods.getThumbnail());
						}
						goodsFavorites.get(i).setGoodsName(goods.getName());
						goodsFavorites.get(i).setGoodsPrice(goods.getPrice());
					}
				}
				data.setContent(goodsFavorites);
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
		goodsFavoriteService.delete(ids);
		return R.OK;
	}

}