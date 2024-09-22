package cn.bizspring.cloud.upms.admin.controller;

import cn.bizspring.cloud.common.core.util.R;
import cn.bizspring.cloud.upms.common.entity.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

/**
 * 服务器监控
 * 
 * @author www.bizspring.cn
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sysServer")
@Api(value = "server", tags = "服务器监控")
public class ServerController{

	@ApiOperation(value = "获取服务器监控信息")
	@GetMapping
	@PreAuthorize("@ato.hasAuthority('sys:server:index')")
    public ResponseEntity getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return R.ok(server);
    }
}
