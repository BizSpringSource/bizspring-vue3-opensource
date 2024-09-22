package cn.bizspring.cloud.upms.admin.controller.provider;

import cn.bizspring.cloud.common.core.controller.BizController;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.security.annotation.Inside;
import cn.bizspring.cloud.upms.admin.service.UserService;
import cn.bizspring.cloud.upms.common.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *Provider Controller - 用户
 * 
 */
@Slf4j
@AllArgsConstructor
@Api(value = "user", tags = "provider - 用户")
@RestController("providerUserController")
@RequestMapping("/provider/user")
public class UserController extends BizController {

	private UserService userService;

	/**
	 * 使用手机号注册用户
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "使用手机号注册用户", notes = "使用手机号注册用户")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/auto_register_user")
	public User autoRegisterUser(@RequestParam("mobile") String mobile, @RequestHeader(SecurityEnum.FROM) String from) {
		return userService.autoRegisterUser(mobile);
	}

	/**
	 * 通过用户类型 merchant，member 查找所有用户
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "根据用户类型分页查找用户", notes = "根据用户类型分页查找用户")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/find_all_by_dtype")
	public List<User> findAllBydType(@RequestParam("type") User.Type type, @RequestHeader(SecurityEnum.FROM) String from) {
		return userService.findAllBydType(type);
	}


	/**
	 * 查找所有用户
	 * @return
	 */
	@ApiOperation(value = "查找所有用户", notes = "查找所有用户")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/find_all")
	public List<User> findAll(@RequestHeader(SecurityEnum.FROM) String from) {
		return userService.findAll();
	}


	/**
	 * 通过 keyword查找不通用户类型 merchant，member 用户
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "通过 keyword查找不通用户类型 merchant，member 用户", notes = "通过 keyword查找不通用户类型 merchant，member 用户")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/keyword_search_by_dtype")
	public List keywordSearchBydType(@RequestParam("keyword")String keyword,@RequestParam("type") User.Type type,@RequestParam("count") Integer count, @RequestHeader(SecurityEnum.FROM) String from) {
		List l= userService.keywordSearchBydType(keyword,type,count);
		return l;
	}



	/**
	 * 通过用户类型 merchant，member 查找所有用户
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据用户类型分页查找用户", notes = "根据用户类型分页查找用户")
	@ApiOperationSupport(order = 12)
	@Inside
	@GetMapping("/find_by_id")
	public User findByUserId(@RequestParam("id") String id, @RequestHeader(SecurityEnum.FROM) String from) {
		return userService.getById(id);
	}


	/**
	 * 获取当前登录用户
	 *
	 * @return 当前登录用户，若不存在则返回null
	 */
	@Inside
	@GetMapping("/get_current")
	@ApiOperation(value = "获取当前登录用户", notes = "根据session获当前取登录用户")
	@ApiOperationSupport(order = 2)
	public User getCurrent( @RequestHeader(SecurityEnum.FROM) String from){
		return userService.getCurrent();
	};

	/**
	 * 获取当前登录用户
	 *
	 * @param userClass
	 *            用户类型
	 * @return 当前登录用户，若不存在则返回null
	 */
	@PostMapping("/get_current")
	@Inside
	@ApiOperation(value = "获取当前登录用户", notes = "获取当前登录用户")
	@ApiOperationSupport(order = 3)
	public <T extends User> T getCurrent(@RequestBody Class<T> userClass, @RequestHeader(SecurityEnum.FROM) String from){
		return userService.getCurrent(userClass);
	};

}