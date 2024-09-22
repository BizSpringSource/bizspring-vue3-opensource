package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.common.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 *Provider Controller - 会员
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "member", tags = "provider - 会员")
@RestController("providerMemberController")
@RequestMapping("/provider/member")
public class MemberController extends BizController {

	private MemberService memberService;




	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@Inside
	@GetMapping("/find")
	@ApiOperation(value = "查找会员实体对象", notes = "查找会员实体对象")
	@ApiOperationSupport(order = 5)
	public Member find(@RequestParam("id")String id, @RequestHeader(SecurityEnum.FROM) String from){
		return	memberService.find(id);
	};

	/**
	 * 根据用户名查找会员
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	@ApiOperation(value = "根据用户名查找会员", notes = "根据用户名查找会员")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/find_by_username")
	public Member findByUsername(String username, @RequestHeader(SecurityEnum.FROM) String from){
		return	memberService.findByUsername(username);
	};

	/**
	 * 查询会员数量
	 *
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	@Inside
	@GetMapping("/count")
	@ApiOperation(value = "查询会员数量", notes = "查询会员数量")
	@ApiOperationSupport(order = 15)
	public long count(@RequestParam("beginDate") Date beginDate,
			   @RequestParam("endDate") Date endDate, @RequestHeader(SecurityEnum.FROM) String from){
		return	memberService.count(beginDate,endDate);
	};

}