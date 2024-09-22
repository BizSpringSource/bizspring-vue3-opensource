package cn.bizspring.cloud.upms.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.upms.common.entity.Config;

/**
 * Service - 方式配置
 * 
 */
public interface ConfigService extends BizService<Config> {


	/**
	 * 根据方式ID查找配置
	 * 
	 * @param modeId
	 *            方式ID
	 * @return 方式配置，若不存在则返回null
	 */
	Config findByModeId(String modeId);


}