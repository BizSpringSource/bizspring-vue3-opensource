/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export const tableOption = {
    dialogDrag: true,
    border: true,
    index: true,
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    showColumnBtn: false,
    viewBtn: true,
    searchSize: 'default',
    menuType: 'text',
    searchMenuSpan: 6,
    column: [{
        label: '类型',
        prop: 'type',
        search: true,
        rules: [{
            required: true,
            message: '请输入字典类型',
            trigger: 'blur'
        }]
    }, {
        label: '描述',
        prop: 'description',
        rules: [{
            required: true,
            message: '请输入字典描述',
            trigger: 'blur'
        }]
    }, {
        width: 150,
        label: '创建时间',
        prop: 'createTime',
        type: 'datetime',
        addDisplay: false,
        editDisabled: true,
        sortable: true,
        format: 'YYYY-MM-DD HH:mm',
        // valueFormat: 'yyyy-MM-dd HH:mm:ss'
    }]
}


export const tableOption2 = {
    dialogDrag: true,
    border: true,
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    refreshBtn: true,
    showColumnBtn: false,
    searchSize: 'small',
    column: [
        {
            label: '数据值',
            prop: 'value',
            rules: [{
                required: true,
                message: '请输入数据值',
                trigger: 'blur'
            }]
        }, {
            label: '标签名',
            prop: 'label',
            rules: [{
                required: true,
                message: '请输入标签名',
                trigger: 'blur'
            }]
        }, {
            label: '排序',
            prop: 'sort',
            type: 'number',
            rules: [{
                required: true,
                message: '请输入排序',
                trigger: 'blur'
            }]
        }, {
            label: '描述',
            prop: 'description',
            rules: [{
                required: true,
                message: '描述',
                trigger: 'blur'
            }]
        }]
}
