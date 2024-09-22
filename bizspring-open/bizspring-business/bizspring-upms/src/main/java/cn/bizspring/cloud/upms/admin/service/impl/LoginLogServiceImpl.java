/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.common.entity.LoginLog;
import cn.bizspring.cloud.upms.admin.mapper.LoginLogMapper;
import cn.bizspring.cloud.upms.admin.service.LoginLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 登录日志表
 * @author www.bizspring.cn
 * @date 2023-04-29
 */
@Service
@AllArgsConstructor
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
