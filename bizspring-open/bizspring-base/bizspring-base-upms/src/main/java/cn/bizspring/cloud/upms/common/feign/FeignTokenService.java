package cn.bizspring.cloud.upms.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.bizspring.cloud.common.core.enums.SecurityEnum;
import cn.bizspring.cloud.common.core.enums.ServiceNameEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author
 */
@FeignClient(contextId = "feignTokenService", value = ServiceNameEnum.AUTH_SERVICE)
public interface FeignTokenService {
	/**
	 * 分页查询token 信息
	 *
	 * @param from   内部调用标志
	 * @param params 分页参数
	 * @param from   内部调用标志
	 * @return page
	 */
	@PostMapping("/token/page")
	ResponseEntity<Page> getTokenPage(@RequestBody Map<String, Object> params, @RequestHeader(SecurityEnum.FROM) String from);

	/**
	 * 删除token
	 *
	 * @param from  内部调用标志
	 * @param token token
	 * @param from  内部调用标志
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	ResponseEntity<Boolean> removeTokenById(@PathVariable(value = "token") String token, @RequestHeader(SecurityEnum.FROM) String from);
}
