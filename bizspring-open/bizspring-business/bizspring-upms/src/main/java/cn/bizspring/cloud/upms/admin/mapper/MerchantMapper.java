package cn.bizspring.cloud.upms.admin.mapper;

import java.util.Date;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.upms.common.entity.Merchant;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 商家
 * 
 */
public interface MerchantMapper extends BizMapper<Merchant> {

	/**
	 * 查询商家数量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 商家数量
	 */
	Long count(@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

	/**
	 * 删除实体对象
	 * 
	 * @param id
	 *            ID
	 */
	void delete(String id);

}