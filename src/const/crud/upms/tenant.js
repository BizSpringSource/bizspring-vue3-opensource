/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import {getByUserName} from '@/api/upms/user'
import {getList} from '@/api/upms/tenant'

const validateUserName = (rule, value, callback) => {
    if (window.openType === 'edit') {
        callback()
    } else {
        getByUserName(value).then(response => {
            let data = response.data.data
            if (data !== null) {
                callback(new Error('账号已经存在'))
            } else {
                callback()
            }
        })
    }
}

const validateCode = (rule, value, callback) => {
    if (window.openType === 'edit'){
        callback()
    }else{
        getList({
            code: value
        }).then(response => {
            let data = response.data.data
            if (data.length > 0) {
                callback(new Error('机构编码已经存在'))
            } else {
                callback()
            }
        })
    }
}

export const tableOption = {
    dialogDrag: true,
    headerAlign: 'center',
    align: 'center',
    border: true,
    viewBtn: true,
    defaultExpandAll: true,
    labelWidth: 100,
    searchMenuSpan: 6,
    column: [
        {
            label: '租户ID',
            prop: 'id',
            editDisplay: false,
            addDisplay: false,
        },
        {
            label: '租户名称',
            prop: 'name',
            width: 200,
            rules: [{
                required: true,
                message: '机构名称不能为空',
                trigger: 'blur'
            }]
        },
        {
            label: '租户类型',
            prop: 'type',
            type: 'select',
            dicUrl: '/upms/dict/type/organ_type',
            rules: [{
                required: true,
                message: '请选择机构类型',
                trigger: 'blur'
            }]
        },
        {
            label: '租户编码',
            hide: true,
            prop: 'code',
            editDisabled: true,
            rules: [{
                required: true,
                message: '机构编码不能为空',
                trigger: 'blur'
            },{
                validator: validateCode,
                trigger: 'blur'
            }]
        },
        {
            label: '状态',
            prop: 'status',
            type: 'radio',
            search: true,
            sortable: true,
            slot: true,
            dicData: [{
                label: '正常',
                value: '0'
            }, {
                label: '冻结',
                value: '9'
            }],
            tip: '冻结后，该租户下所有用户将无法登录'
        },
        {
            label: '排序',
            hide: true,
            type: 'number',
            prop: 'sort',
            rules: [{
                required: true,
                message: '排序不能为空',
                trigger: 'blur'
            }]
        },
        {
            label: '电话',
            hide: true,
            prop: 'phone'
        },
        {
            label: '传真',
            hide: true,
            prop: 'fax'
        },
        {
            label: '邮箱',
            hide: true,
            prop: 'email'
        },
        {
            label: '地址',
            hide: true,
            prop: 'address',
            span: 24
        },
        {
            label: '创建时间',
            prop: 'createTime',
            editDisplay: false,
            addDisplay: false
        },
        {
            label: '修改时间',
            prop: 'updateTime',
            hide: true,
            editDisplay: false,
            addDisplay: false
        },
        {
            label: '管理员账号',
            prop: 'username',
            hide: true,
            viewDisplay: false,
            editDisplay: false,
            rules: [{
                required: true,
                message: '管理员账号',
                trigger: 'blur'
            }, {
                validator: validateUserName,
                trigger: 'blur'
            }],
            tip: '新租户管理员的登录账号'
        },
        {
            label: '管理员密码',
            prop: 'password',
            hide: true,
            viewDisplay: false,
            editDisplay: false,
            rules: [{
                required: true,
                message: '管理员密码',
                trigger: 'blur'
            }],
            tip: '新租户管理员的登录密码'
        }
    ]
}
