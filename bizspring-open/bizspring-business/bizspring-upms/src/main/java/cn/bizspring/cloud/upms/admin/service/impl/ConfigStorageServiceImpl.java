/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.common.entity.ConfigStorage;
import cn.bizspring.cloud.upms.admin.mapper.ConfigStorageMapper;
import cn.bizspring.cloud.upms.admin.service.ConfigStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 存储配置
 * @author www.bizspring.cn
 * @date 2023-03-04
 */
@Service
@AllArgsConstructor
public class ConfigStorageServiceImpl extends ServiceImpl<ConfigStorageMapper, ConfigStorage> implements ConfigStorageService {

}
