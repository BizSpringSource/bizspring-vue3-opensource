package cn.bizspring.cloud.upms.admin.controller.admin;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.core.util.SecurityUtils;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.upms.admin.service.AdminService;
import cn.bizspring.cloud.upms.common.entity.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 个人资料
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "profile", tags = "管理 - 个人资料")
@RestController("adminProfileController")
@RequestMapping("/admin/profile")
public class ProfileController extends BizController {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private AdminService adminService;

	/**
	 * 验证当前密码
	 */
	@SysLog("验证当前密码")
	@ApiOperation(value = "验证当前密码", notes = "验证当前密码")
	@ApiOperationSupport(order = 2)
	@GetMapping("/check_current_password")
	public @ResponseBody boolean checkCurrentPassword(String currentPassword) {
		Admin currentUser =adminService.find(SecurityUtils.getUserId());
		return StringUtils.isNotEmpty(currentPassword) && currentUser.isValidCredentials(currentPassword);
	}

	/**
	 * 编辑
	 */
	@SysLog("编辑")
	@ApiOperation(value = "编辑", notes = "编辑")
	@ApiOperationSupport(order = 2)
	@GetMapping("/edit")
	public ResponseEntity edit() {
		Admin currentUser = adminService.find(SecurityUtils.getUserId());
		Map<String, Object> data = new HashMap<>(2);
		data.put("admin", currentUser);
		return R.ok(data);
	}

	/**
	 * 更新
	 */
	@SysLog("更新")
	@ApiOperation(value = "更新", notes = "更新")
	@ApiOperationSupport(order = 2)
	@PostMapping("/update")
	public ResponseEntity update(String currentPassword, String password, String email) {
		Admin currentUser =adminService.find(SecurityUtils.getUserId());
		if (!isValid(Admin.class, "email", email)) {
			return R.INVALID_ENTITY;
		}
		if (StringUtils.isNotEmpty(currentPassword) && StringUtils.isNotEmpty(password)) {
			if (!isValid(Admin.class, "password", password)) {
				return R.INVALID_ENTITY;
			}
			if (!currentUser.isValidCredentials(currentPassword)) {
				return R.INVALID_ENTITY;
			}
			currentUser.setPassword(ENCODER.encode(password));
		}
		currentUser.setEmail(email);
		adminService.update(currentUser);
		return R.OK;
	}

}