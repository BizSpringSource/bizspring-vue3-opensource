/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bizspring.cloud.upms.common.entity.ConfigEditor;
import cn.bizspring.cloud.upms.admin.mapper.ConfigEditorMapper;
import cn.bizspring.cloud.upms.admin.service.ConfigEditorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 编辑器配置
 * @author www.bizspring.cn
 * @date 2023-03-11
 */
@Service
@AllArgsConstructor
public class ConfigEditorServiceImpl extends ServiceImpl<ConfigEditorMapper, ConfigEditor> implements ConfigEditorService {

}
