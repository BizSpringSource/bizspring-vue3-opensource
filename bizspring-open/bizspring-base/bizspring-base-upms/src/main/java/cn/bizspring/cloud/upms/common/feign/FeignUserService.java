package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.common.dto.UserInfo;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.User;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignUserService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignUserService {


	/**
	 * 通过用户类型 merchant，member 查找所有用户
	 * @param type
	 * @param from
	 * @return
	 */
	@GetMapping("/provider/user/find_all_by_dtype")
	List<User> findAllBydType(@RequestParam("type") User.Type type, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查找所有用户
	 * @param from
	 * @return
	 */
	@GetMapping("/provider/user/find_all")
	List<User> findAll( @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 通过 keyword查找不通用户类型 merchant，member 用户
	 * @param keyword
	 * @param type
	 * @param count
	 * @return
	 */
	@GetMapping("/provider/user/keyword_search_by_dtype")
	List keywordSearchBydType(@RequestParam("keyword")String keyword,@RequestParam("type") User.Type type,@RequestParam("count") Integer count,@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 通过用户Id查找用户
	 * @param id
	 * @param from
	 * @return
	 */
	@GetMapping("/provider/user/find_by_id")
	User findByUserId(@RequestParam("id") String id, @RequestHeader(SecurityEnum.FROM) String from);


	/**
	 * 通过用户名查找用户
	 * @param username
	 * @param from
	 * @return
	 */
	@GetMapping("/provider/member/find")
	Member find(@RequestParam("username") String username, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/user/info/{username}")
	R<UserInfo> info(@PathVariable(value = "username")String username
			, @RequestHeader(SecurityEnum.FROM) String from);

}
