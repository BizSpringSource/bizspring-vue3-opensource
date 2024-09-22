package cn.bizspring.cloud.order.admin.mapper;

import cn.bizspring.cloud.common.core.mapper.BizMapper;
import cn.bizspring.cloud.common.core.entity.Sn;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper - 序列号
 * 
 */
public interface SnMapper extends BizMapper<Sn> {

	Sn findByType(@Param("type")int type);

}