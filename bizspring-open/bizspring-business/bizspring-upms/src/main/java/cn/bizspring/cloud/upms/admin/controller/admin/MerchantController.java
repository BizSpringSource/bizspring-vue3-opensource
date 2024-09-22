package cn.bizspring.cloud.upms.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.entity.BizEntity;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.util.*;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.admin.service.MerchantService;
import cn.bizspring.cloud.upms.admin.service.UserService;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import cn.bizspring.cloud.upms.common.entity.User;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller - 商家
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "merchant", tags = "管理 - 商家")
@RestController("adminMerchantController")
@RequestMapping("/admin/merchant")
public class MerchantController extends BizController {

	private final CacheManager cacheManager;
	private MerchantService merchantService;
	private UserService userService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * 查看
	 */
	@SysLog("查看")
	@ApiOperation(value = "查看", notes = "查看")
	@ApiOperationSupport(order = 2)
	@GetMapping("/view")
	public ResponseEntity view(String id) {
		Map<String, Object> data = new HashMap<>(2);
		data.put("merchant", merchantService.find(id));
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

		if (merchantService.usernameExists(username)) {
			return R.invalidEntity("用户名已被注册");
		}
		if (merchantService.emailExists(email)) {
			return R.invalidEntity("邮箱已被注册");
		}
		if (merchantService.mobileExists(mobile)) {
			return R.invalidEntity("手机已被注册");
		}
		Merchant merchant = new Merchant();
		merchant.setLockDate(null);
		merchant.setLoginIp(request.getRemoteAddr());
		merchant.setLoginDate(new Date());
		merchant.setStoreId(null);
		merchant.setUsername(username);
		merchant.setPassword(ENCODER.encode(password));
		merchant.setEmail(email);
		merchant.setMobile(mobile);
		merchant.setIsEnabled(isEnabled);
		merchant.setDelFlag(false);
		merchant.setLockFlag(false);

		if (!isValid(Merchant.class, BizEntity.Save.class)) {
			return R.INVALID_ENTITY;
		}

		merchantService.save(merchant);
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
		Map<String, Object> data = new HashMap<>(2);
		Merchant currentMerchant=merchantService.find(id);
		data.put("data",currentMerchant);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PutMapping("/update")
	public ResponseEntity update(String id,String password, String email, String mobile,Boolean isEnabled) {
		Merchant merchant = merchantService.find(id);

		if (merchantService.emailUnique(id, email)) {
			return R.INVALID_ENTITY;
		}
		if (merchantService.mobileUnique(id, mobile)) {
			return R.INVALID_ENTITY;
		}
		merchant.setPassword(ENCODER.encode(password));
		merchant.setEmail(email);
		merchant.setMobile(mobile);
		merchant.setIsEnabled(isEnabled);
		if (!isValid(merchant)) {
			return R.INVALID_ENTITY;
		}

		Merchant pmerchant = merchantService.find(merchant.getId());
		if (pmerchant == null) {
			return R.INVALID_ENTITY;
		}
		merchantService.update(merchant, "username",  "frozenAmount", "loginIp", "loginDate");
		cacheManager.getCache(CacheEnum.USER_CACHE).evict(merchant.getUsername());
		return R.OK;
	}

	/**
	 * 列表
	 */
	@SysLog("列表")
	@ApiOperation(value = "列表", notes = "列表")
	@ApiOperationSupport(order = 2)
	@GetMapping("/list")
	public ResponseEntity list(Pageable pageable,String username,Boolean isEnabled) {
		User user=new User();
		if(StringUtils.isNotEmpty(username)){
			user.setUsername(username);
		}
		if(CommonUtils.isNotNull(isEnabled)){
			user.setIsEnabled(isEnabled);
		}
		Page page = userService.findPageUser(pageable,user,User.Type.MERCHANT);
		return R.ok(page);
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
			merchantService.delete(ids);
		}
		return R.OK;
	}

}