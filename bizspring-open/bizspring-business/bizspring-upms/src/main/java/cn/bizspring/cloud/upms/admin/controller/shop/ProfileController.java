package cn.bizspring.cloud.upms.admin.controller.shop;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.common.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller - 个人资料
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "profile", tags = "商城 - 个人资料")
@RestController("memberProfileController")
@RequestMapping("/member/profile")
public class ProfileController extends BizController {

	@Value("${showStoreCenter}")
	private String[] showStoreCenter;
	private MemberService memberService;

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/load_member")
	public ResponseEntity loadMember(String id) {
		Map<String, Object> data = new HashMap<>(2);
		Member member=memberService.find(id);
		if(CommonUtils.isNull(member)){
			return R.unauthorized();
		}
		data.put("member",member);
		return R.ok(data);
	}

	/**
	 * 编辑
	 */
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/show_store_center")
	public ResponseEntity showStoreCenter() {
		Map<String, String> data = new HashMap<>(2);
		data.put("showStoreCenter",showStoreCenter[0]);
		return R.ok(data);
	}

}