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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 管理员登录
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "admin", tags = "管理 - 管理员登录")
@RestController("adminLoginController")
@RequestMapping("/admin")
public class LoginController extends BizController {

	private AdminService adminService;

	/**
	 * 登录跳转
	 */
	@SysLog("登录跳转")
	@ApiOperation(value = "登录跳转", notes = "登录跳转")
	@ApiOperationSupport(order = 2)
	@GetMapping({ "", "/" })
	public ResponseEntity index() {
		return R.ok();
	}

	/**
	 * 登录页面
	 */
	@SysLog("登录页面")
	@ApiOperation(value = "登录页面", notes = "登录页面")
	@ApiOperationSupport(order = 2)
	@GetMapping("/login")
	public ResponseEntity login() {
		Admin currentUser =adminService.find(SecurityUtils.getUserId());
		return R.ok(currentUser);
	}

}