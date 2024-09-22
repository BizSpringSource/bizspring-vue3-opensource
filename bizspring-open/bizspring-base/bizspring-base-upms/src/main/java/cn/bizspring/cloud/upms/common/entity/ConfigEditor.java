/**
 * Copyright (C) 2023-2050
 * All rights reserved, Designed By www.bizspring.cn
 */
package cn.bizspring.cloud.upms.common.entity;

import cn.bizspring.cloud.common.core.entity.BizEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 编辑器配置
 * @author www.bizspring.cn
 * Copyright (C) 2023-2050
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "编辑器配置")
@TableName("biz_config_editor")
public class ConfigEditor extends BizEntity<ConfigEditor> {
    private static final long serialVersionUID=1L;

    /**
     * 所属租户
     */
	@ApiModelProperty(value = "所属租户")
    private String tenantId;

    /**
     * 编辑器类型1、quill-editor；2、froala
     */
	@ApiModelProperty(value = "编辑器类型")
    private String editorType;

}
