package cn.bizspring.cloud.upms.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.admin.service.MemberService;
import cn.bizspring.cloud.upms.admin.service.UserService;
import cn.bizspring.cloud.upms.common.entity.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Controller - 会员
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "member", tags = "管理 - 会员")
@RestController("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController extends BizController {

	private final CacheManager cacheManager;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private UserService userService;
	private MemberService memberService;

	/**
	 * 查看
	 */
	@SysLog("查看")
	@ApiOperation(value = "查看", notes = "查看")
	@ApiOperationSupport(order = 2)
	@GetMapping("/view")
	public ResponseEntity view(String id) {
		Member member = memberService.find(id);
		Map<String, Object> data = new HashMap<>(2);
		data.put("genders", Member.Gender.values());
		data.put("member", member);
		return R.ok(data);
	}

	/**
	 * 添加
	 */
	@SysLog("添加")
	@ApiOperation(value = "添加", notes = "添加")
	@ApiOperationSupport(order = 2)
	@GetMapping("/add")
	public ResponseEntity add() {
		Map<String, Object> data = new HashMap<>(2);
		data.put("genders", Member.Gender.values());
		return R.ok(data);
	}

	/**
	 * 保存
	 */
	@SysLog("保存")
	@ApiOperation(value = "保存", notes = "保存")
	@ApiOperationSupport(order = 2)
	@PostMapping("/save")
	public ResponseEntity save(String username, String password, String email, String mobile,Boolean isEnabled, HttpServletRequest request) {
		if (memberService.usernameExists(username)) {
			return R.invalidEntity("用户名已被注册");
		}
		if (memberService.emailExists(email)) {
			return R.invalidEntity("E-mail已被注册");
		}
		if (memberService.mobileExists(mobile)) {
			return R.invalidEntity("手机已被注册");
		}
		Member member = new Member();
		member.setLockDate(null);
		member.setLoginIp(request.getRemoteAddr());
		member.setLoginDate(new Date());
		member.setUsername(username);
		member.setPassword(ENCODER.encode(password));
		member.setEmail(email);
		member.setMobile(mobile);
		member.setIsEnabled(isEnabled);
		member.setDelFlag(false);
		member.setLockFlag(false);
		member.setPoint(0L);
		member.setDistributorId(null);
		member.setSafeKey(null);
		member.setCartId(null);
		memberService.save(member);
		return R.OK;
	}

	/**
	 * 编辑
	 */
	@SysLog("编辑")
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit(String id) {
		Member member = memberService.find(id);
		Map<String, Object> data = new HashMap<>(2);
		data.put("genders", Member.Gender.values());
		data.put("data", member);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(String id, String password, String email, String mobile,Boolean isEnabled) {
		Member member = memberService.find(id);
		if (CommonUtils.isNull(member)) {
			return R.INVALID_ENTITY;
		}
		if (memberService.emailUnique(member.getId(), email)) {
			return R.invalidEntity("E-mail已被注册");
		}
		if (memberService.mobileUnique(member.getId(), mobile)) {
			return R.invalidEntity("手机已被注册");
		}
		member.setPassword(ENCODER.encode(password));
		member.setEmail(email);
		member.setMobile(mobile);
		member.setIsEnabled(isEnabled);
		Member pMember = memberService.find(member.getId());
		if (pMember == null) {
			return R.INVALID_ENTITY;
		}
		memberService.update(member, "username", "frozenAmount", "amount", "lockDate", "loginIp", "loginDate", "loginId", "safeKey", "cart", "orders", "paymentLogs",
				 "addresss", "goodsFavorites");
		cacheManager.getCache(CacheEnum.USER_CACHE).evict(member.getUsername());
		return R.OK;
	}

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable,String username) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("data", memberService.findPage(pageable));
		Member member=new Member();
		if(StringUtils.isNotEmpty(username)){
			member.setUsername(username);
		}
		return R.ok(memberService.findPageByEntity(pageable,member));
	}

	/**
	 * 删除
	 */
	@SysLog("删除")
	@ApiOperation(value = "删除", notes = "删除")
	@ApiOperationSupport(order = 2)
	@DeleteMapping("/delete")
	public ResponseEntity delete(String[] ids) {
		if (ids != null) {
			memberService.delete(ids);
		}
		return R.OK;
	}

}