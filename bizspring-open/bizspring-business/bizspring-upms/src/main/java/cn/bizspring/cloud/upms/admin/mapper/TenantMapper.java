package cn.bizspring.cloud.upms.admin.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.bizspring.cloud.upms.common.entity.Tenant;
import java.io.Serializable;

/**
 * <p>
 * 租户管理 Mapper 接口
 * </p>
 *
 * @author www.bizspring.cn
 * Copyright (C) 2023-2050
 */
@SqlParser(filter=true)
public interface TenantMapper extends BaseMapper<Tenant> {

	/**
	 * 通过租户ID删除租户
	 * 包括bizspring_upms库所有表的当前租户数据
	 */
	void deleteTenantById(Serializable id);
}
