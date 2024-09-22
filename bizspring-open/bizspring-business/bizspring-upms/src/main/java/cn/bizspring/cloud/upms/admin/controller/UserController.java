package cn.bizspring.cloud.upms.admin.controller;

import cn.bizspring.cloud.common.core.util.CommonUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.bizspring.cloud.upms.common.entity.*;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.upms.admin.service.*;
import cn.bizspring.cloud.upms.common.dto.UserDTO;
import cn.bizspring.cloud.upms.common.dto.UserInfo;
import cn.bizspring.cloud.upms.common.dto.UserRegister;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import cn.bizspring.cloud.common.core.enums.CommonEnum;
import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.common.data.tenant.TenantContextHolder;
import cn.bizspring.cloud.common.log.annotation.SysLog;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class UserController {



	private final UserService userService;
	private final RoleService roleService;
	private final UserRoleService userRoleService;
	private final RedisTemplate redisTemplate;
	private final TenantService tenantService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private NacosConfigManager nacosConfigManager;

	/**
	 * 获取当前用户全部信息
	 *
	 * @return 用户信息
	 */
	@ApiOperation(value = "获取当前用户全部信息")
	@GetMapping(value = {"/info"})
	public ResponseEntity info() {
		String username = SecurityUtils.getUser().getUsername();
		TenantContextHolder.setTenantId("1");
		User user = userService.getOne(Wrappers.<User>query()
				.lambda().eq(User::getUsername, username));
		user.setTenantId(TenantContextHolder.getTenantId());
		user.setLanguageValues(User.Language.values());
		if (user == null) {
			return R.failed(null, "获取当前用户信息失败");
		}
		return R.ok(userService.findUserInfo(user));
	}

	/**
	 * 获取指定用户全部信息
	 *
	 * @return 用户信息
	 */
	@ApiOperation(value = "获取指定用户全部信息")
	@Inside
	@GetMapping("/info/{username}")
	public ResponseEntity info(@PathVariable("username") String username, @RequestHeader(SecurityEnum.FROM) String from) {
		User user = new User();
		user.setUsername(username);
		user = userService.getByNoTenant(user);
		if (CommonUtils.isNull(user)) {
			return null;
		}
		TenantContextHolder.setTenantId(user.getTenantId());
		if(BooleanUtils.isTrue(user.getIsEnabled())){
			//查询所属租户状态是否正常，否则禁止登录
			Tenant tenant = tenantService.getById(user.getTenantId());
			if(CommonUtils.isNotNull(tenant)&&"9".equals(tenant.getStatus())){
				user.setIsEnabled(false);
			}
		}
		UserInfo userInfo = userService.findUserInfo(user);
		return R.ok(userInfo);
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id ID
	 * @return 用户信息
	 */
	@ApiOperation(value = "通过ID查询用户信息")
	@GetMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:user:get')")
	public ResponseEntity user(@PathVariable String id) {
		return R.ok(userService.selectUserVoById(id));
	}

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param username 用户名
	 * @return
	 */
	@ApiOperation(value = "根据用户名查询用户信息")
	@GetMapping("/detail/{username}")
	public ResponseEntity userByUsername(@PathVariable String username) {
		User user = new User();
		user.setUsername(username);
		return R.ok(userService.getByNoTenant(user));
	}

	/**
	 * 删除用户信息
	 *
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@ato.hasAuthority('sys:user:del')")
	@ApiOperation(value = "删除用户", notes = "根据ID删除用户")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
	public ResponseEntity userDel(@PathVariable(value = "id") String id) {
		//拥有管理员角色的用户不让删除
		Role role = roleService.getOne(Wrappers.<Role>update().lambda()
				.eq(Role::getRoleCode,CommonEnum.ROLE_CODE_ADMIN));
		List<UserRole> listUserRole = userRoleService.list(Wrappers.<UserRole>update().lambda()
				.eq(UserRole::getUserId,id).eq(UserRole::getRoleId,role.getId()));
		if("1".equals(id)){
			return R.failed("无法删除系统管理");
		}
		if(listUserRole.size()>0){
			return R.failed("无法删除拥有管理员角色的用户");
		}
		User user = userService.getById(id);
		return R.ok(userService.deleteUserById(user));
	}

	/**
	 * 添加用户
	 *
	 * @param userDto 用户信息
	 * @return ok/false
	 */
	@ApiOperation(value = "添加用户")
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@ato.hasAuthority('sys:user:add')")
	public ResponseEntity user(@RequestBody UserDTO userDto) {
		try{
			userDto.setTenantId(TenantContextHolder.getTenantId());
			userDto.setCreatedTime(new Date());
			return R.ok(userService.saveUser(userDto));
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 * 更新用户信息
	 *
	 * @param userDto 用户信息
	 * @return R
	 */
	@ApiOperation(value = "更新用户信息")
	@SysLog("更新用户信息")
	@PutMapping
	@PreAuthorize("@ato.hasAuthority('sys:user:edit')")
	public ResponseEntity updateUser(@Valid @RequestBody UserDTO userDto) {
		try{
			//查询出管理员角色，判断管理员角色是否至少有1个用户
			Role role = roleService.getOne(Wrappers.<Role>update().lambda()
					.eq(Role::getRoleCode,CommonEnum.ROLE_CODE_ADMIN));
			if(!CollUtil.contains(userDto.getRoleIds(),role.getId())){
				List<UserRole> listUserRole = userRoleService.list(Wrappers.<UserRole>update().lambda()
						.eq(UserRole::getRoleId,role.getId()));
				if(listUserRole.size()<=1){//只有一条记录，判断是否当前用户拥有
					listUserRole = userRoleService.list(Wrappers.<UserRole>update().lambda()
							.eq(UserRole::getRoleId,role.getId())
							.eq(UserRole::getUserId,userDto.getId()));
					if(listUserRole.size()>0){
						return R.failed("至少需要一个用户拥有管理员角色");
					}
				}
			}
			userDto.setUpdatedTime(new Date());
			return R.ok(userService.updateUser(userDto));
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 * 修改用户密码
	 * @param userDto
	 * @return
	 */
	@ApiOperation(value = "修改用户密码")
	@SysLog("修改用户密码")
	@PutMapping("/password")
	@PreAuthorize("@ato.hasAuthority('sys:user:password')")
	public ResponseEntity editPassword(@Valid @RequestBody UserDTO userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setPassword(ENCODER.encode(userDto.getPassword()));
		userService.updateById(user);
		return R.ok();
	}

	/**
	 * 分页查询用户
	 *
	 * @param page    参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@ApiOperation(value = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@ato.hasAuthority('sys:user:index')")
	public ResponseEntity getUserPage(Page page, UserDTO userDTO) {
		IPage ipage = userService.getUsersWithRolePage(page, userDTO);
		return R.ok(ipage);
	}

	/**
	 * 数量查询
	 * @param user
	 * @return
	 */
	@ApiOperation(value = "数量查询")
	@GetMapping("/count")
	@PreAuthorize("@ato.hasAuthority('sys:user:index')")
	public ResponseEntity getCount(User user) {
		return R.ok(userService.count(Wrappers.query(user)));
	}

	/**
	 * 修改个人信息
	 *
	 * @param userDto userDto
	 * @return ok/false
	 */
	@ApiOperation(value = "修改个人信息")
	@SysLog("修改个人信息")
	@PutMapping("/edit")
	public ResponseEntity updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUserInfo(userDto));
	}

	/**
	 * 绑定/解绑手机号
	 *
	 * @param userDto userDto
	 * @return
	 */
	@ApiOperation(value = "绑定/解绑手机号")
	@SysLog("绑定/解绑手机号")
	@PutMapping("/phone")
	public ResponseEntity bindPhone(@RequestBody UserDTO userDto) {
		//校验验证码
		if(StrUtil.isBlank(userDto.getCode())){
			return R.failed("验证码输入错误");
		}
		String key = CacheEnum.VER_CODE_DEFAULT + SecurityEnum.SMS_LOGIN + ":" + userDto.getMobile();
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		if (!redisTemplate.hasKey(key)) {
			return R.failed("验证码输入错误");
		}
		Object codeObj = redisTemplate.opsForValue().get(key);
		if (codeObj == null) {
			return R.failed("验证码输入错误");
		}
		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			return R.failed("验证码输入错误");
		}
		if (!StrUtil.equals(saveCode, userDto.getCode())) {
			return R.failed("验证码输入错误");
		}
		userService.bindPhone(userDto);
		return R.ok();
	}

	/**
	 * @param username 用户名称
	 * @return 上级机构用户列表
	 */
	@ApiOperation(value = "查询")
	@GetMapping("/ancestor/{username}")
	public ResponseEntity listAncestorUsers(@PathVariable String username) {
		return R.ok(userService.listAncestorUsers(username));
	}

	/**
	 * 自助注册
	 * @param userRegister
	 * @return
	 */
	@ApiOperation(value = "自助注册")
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody UserRegister userRegister){
		//校验验证码
		String key = CacheEnum.VER_CODE_REGISTER + CommonEnum.EMAIL + ":" + userRegister.getEmail();
		Object codeObj = redisTemplate.opsForValue().get(key);
		if(codeObj == null || !codeObj.equals(userRegister.getCode())){
			return R.failed("验证码输入错误");
		}
		try{
			return userService.register(userRegister);
		}catch (DuplicateKeyException e){
			return judeParm(e);
		}
	}

	/**
	 *
	 * @param e
	 * @return
	 */
	ResponseEntity judeParm(DuplicateKeyException e){
		if(e.getMessage().contains("uk_username")){
			return R.failed("用户名已被注册");
		}else if(e.getMessage().contains("uk_email")){
			return R.failed("E-mail已被注册");
		}else{
			return R.failed(e.getMessage());
		}
	}
}
