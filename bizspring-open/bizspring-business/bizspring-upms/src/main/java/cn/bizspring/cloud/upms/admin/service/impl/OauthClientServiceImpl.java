package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.admin.service.OauthClientService;
import cn.bizspring.cloud.upms.common.entity.OauthClient;
import cn.bizspring.cloud.upms.admin.mapper.OauthClientMapper;
import cn.bizspring.cloud.common.core.enums.CacheEnum;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 */
@Service
@AllArgsConstructor
public class OauthClientServiceImpl extends ServiceImpl<OauthClientMapper, OauthClient> implements OauthClientService {

	/**
	 * 通过ID删除客户端
	 *
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.OAUTH_CLIENT_CACHE, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 根据客户端信息
	 *
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheEnum.OAUTH_CLIENT_CACHE, key = "#clientDetails.id")
	public Boolean updateClientDetailsById(OauthClient clientDetails) {
		return this.updateById(clientDetails);
	}
}
