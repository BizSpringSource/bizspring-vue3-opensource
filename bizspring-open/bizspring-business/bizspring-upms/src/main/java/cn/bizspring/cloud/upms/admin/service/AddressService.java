package cn.bizspring.cloud.upms.admin.service;

import cn.bizspring.cloud.common.core.service.BizService;
import cn.bizspring.cloud.common.core.util.Page;
import cn.bizspring.cloud.common.core.util.Pageable;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;

import java.util.List;

/**
 * Service - 收货地址
 * 
 */
public interface AddressService extends BizService<Address> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Address findDefault(Member member);

	/**
	 * 查找收货地址列表
	 * 
	 * @param member
	 *            会员
	 * @return 收货地址
	 */
	List<Address> findList(Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 收货地址分页
	 */
	Page<Address> findPage(Member member, Pageable pageable);

}