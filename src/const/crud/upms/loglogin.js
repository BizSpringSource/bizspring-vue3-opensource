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
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    menuType: 'text',
    searchShow: false,
    excelBtn: true,
    printBtn: true,
    viewBtn: true,
    column: [
        {
            label: '登录者ID',
            prop: 'createId',
            sortable: true
        },
        {
            label: '登录者',
            prop: 'createBy',
            sortable: true,
            search: true
        },
        {
            label: '创建时间',
            prop: 'createTime',
            sortable: true
        },
        {
            label: 'IP地址',
            prop: 'remoteAddr',
            sortable: true
        },
        {
            label: '地址信息',
            prop: 'address',
            sortable: true
        },
        {
            label: '用户代理',
            prop: 'userAgent',
            sortable: true,
            overHidden: true
        },
        {
            label: '登录方式',
            prop: 'requestUri',
            sortable: true,
            type: 'radio',
            dicData: [{
                label: '账号密码登录',
                value: '/oauth/token'
            }, {
                label: '手机号登录',
                value: '/phone/token/sms'
            }]
        },
        {
            label: '操作提交的数据',
            prop: 'params',
            sortable: true,
            overHidden: true
        },
        {
            label: '类型',
            prop: 'type',
            type: 'select',
            sortable: true,
            dicUrl: '/upms/dict/type/log_type',
            search: true
        },
        {
            label: '异常信息',
            prop: 'exception'
        }
    ]
}
