package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.upms.common.entity.Member;
import cn.bizspring.cloud.upms.common.entity.Address;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper - 收货地址
 * 
 */
public interface AddressMapper extends BizMapper<Address> {

	/**
	 * 查找默认收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 默认收货地址，若不存在则返回最新收货地址
	 */
	Address findDefault(@Param("member") Member member);

	/**
	 * 查找收货地址
	 * 
	 * @param member
	 *            会员
	 * @return 收货地址
	 */
	List<Address> findList(@Param("member")Member member);

	/**
	 * 查找收货地址分页
	 * 
	 * @param member
	 *            会员
	 * @param iPage
	 *            分页信息
	 * @return 收货地址分页
	 */
	List<Address> findPage(IPage<Address> iPage, @Param("queryWrapper")QueryWrapper<Address> queryWrapper, @Param("member")Member member);

	/**
	 * 清除默认
	 * 
	 * @param memberId
	 *            会员
	 */
	void clearDefault(@Param("memberId")String memberId);

	/**
	 * 清除默认
	 * 
	 * @param memberId
	 *            会员
	 * @param excludeId
	 *            排除收货地址
	 */
	void clearDefaultExclude(@Param("memberId")String memberId, @Param("excludeId")String excludeId);

}