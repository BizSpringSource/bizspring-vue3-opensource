package cn.bizspring.cloud.upms.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.upms.common.entity.Member;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Mapper - 会员
 * 
 */
public interface MemberMapper extends BizMapper<Member> {

	/**
	 * 查找会员分页
	 * 
	 * @param iPage
	 *            分页信息
	 * @return 会员分页
	 */
	List<Member> findPage(IPage<Member> iPage, @Param("queryWrapper")QueryWrapper<Member> queryWrapper);

	/**
	 * 查询会员数量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 会员数量
	 */
	Long count(@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

}