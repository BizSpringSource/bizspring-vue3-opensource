package cn.bizspring.cloud.upms.admin.controller.merchant;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.goods.common.entity.Goods;
import cn.bizspring.cloud.goods.common.feign.FeignGoodsService;
import cn.bizspring.cloud.store.common.entity.Store;
import cn.bizspring.cloud.store.common.feign.FeignStoreService;
import cn.bizspring.cloud.upms.admin.service.GoodsFavoriteService;
import cn.bizspring.cloud.upms.admin.service.StoreFavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Controller - 首页
 * 
 */
@AllArgsConstructor
@RestController("merchantIndexController")
@RequestMapping("/merchant/index")
public class IndexController extends BizController {

	private GoodsFavoriteService goodsFavoriteService;
	private StoreFavoriteService storeFavoriteService;
	private FeignStoreService feignStoreService;
	private FeignGoodsService feignGoodsService;

	/**
	 * 商家首页
	 */
	@GetMapping
	public ResponseEntity index() {
		Store currentStore = feignStoreService.getCurrent(SecurityUtils.getUserId(),SecurityEnum.FROM_IN);
		Map<String, Object> data = new HashMap<>(2);
		Date now=new Date();
		Calendar calendar = Calendar.getInstance();
		//今日起始时间
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date thatdayBeginDate = calendar.getTime();
		//昨日起始时间
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-1);
		//昨日结束时间
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		//今日结束时间
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
		Date thatdayEndDate = calendar.getTime();

		Goods goods = new Goods();
		goods.setStoreId(currentStore.getId());
		List<Goods> goodss = feignGoodsService.findAllByEntity(goods,SecurityEnum.FROM_IN) ;
		data.put("goodsFavoriteCount", goodsFavoriteService.countStoreGoods(goodss,thatdayBeginDate,thatdayEndDate));
		data.put("storeFavoriteCount", storeFavoriteService.countStore(currentStore,thatdayBeginDate,thatdayEndDate));
		return R.ok(data);
	}

}