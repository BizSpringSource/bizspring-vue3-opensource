package cn.bizspring.cloud.goods.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.goods.admin.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 商品分类
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "goods_category", tags = "商城 - 商品分类")
@RestController("memberGoodsCategoryController")
@RequestMapping("/member/goods_category")
public class GoodsCategoryController extends BizController {

	private GoodsCategoryService goodsCategoryService;
	/**
	 * 首页
	 */
	@ApiOperation(value = "首页", notes = "首页")
	@ApiOperationSupport(order = 2)
	@GetMapping
	public ResponseEntity index() {
		return R.ok(goodsCategoryService.findTree());
	}
}