package cn.bizspring.cloud.upms.common.feign;

import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import cn.bizspring.cloud.upms.common.entity.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author www.bizspring.cn
 */
@FeignClient(contextId = "feignMemberService", value = ServiceNameEnum.UMPS_ADMIN_SERVICE)
public interface FeignMemberService{

	/**
	 * 查找实体对象
	 *
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	@GetMapping("/provider/member/find")
	Member find(@RequestParam("id")String id,@RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 根据用户名查找会员
	 *
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	@GetMapping("/provider/member/find_by_username")
	Member findByUsername(@RequestParam("username") String username, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 查询会员数量
	 *
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	@GetMapping("/provider/member/count")
	long count(@RequestParam("beginDate") Date beginDate,
			   @RequestParam("endDate") Date endDate, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 增加消费金额
	 *
	 * @param member
	 *            会员
	 * @param amount
	 *            值
	 */
	@PostMapping("/provider/member/add_amount")
	void addAmount(@RequestBody Member member,@RequestParam("amount") BigDecimal amount, @RequestHeader(SecurityEnum.FROM) String from);

}
