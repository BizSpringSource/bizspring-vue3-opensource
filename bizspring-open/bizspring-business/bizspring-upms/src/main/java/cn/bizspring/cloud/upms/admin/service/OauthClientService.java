package cn.bizspring.cloud.upms.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.bizspring.cloud.upms.common.entity.OauthClient;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 */
public interface OauthClientService extends IService<OauthClient> {
	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 根据客户端信息
	 *
	 * @param sysOauthClient
	 * @return
	 */
	Boolean updateClientDetailsById(OauthClient sysOauthClient);
}
