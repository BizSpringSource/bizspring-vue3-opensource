package cn.bizspring.cloud.order.admin.service;

import cn.bizspring.cloud.common.core.entity.Sn;
import cn.bizspring.cloud.common.core.service.BizService;

/**
 * Service - 序列号
 * 
 */
public interface SnService extends BizService<Sn> {

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	String generate(Sn.Type type);

}