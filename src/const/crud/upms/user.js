/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import {getByUserName, getCount} from '@/api/upms/user'

const validateUserName = (rule, value, callback) => {
    if (window.openType != 'add'){
        callback()
    }else{
        getByUserName(value).then(response => {
          console.log("getByUserName --- ",response);
            let user = response.data.data;
            if (user != null && null != user.id) {
                callback(new Error('账号已经存在'))
            } else {
                callback()
            }
        })
    }
}

const validateEmail = (rule, value, callback) => {
  if (window.openType != 'add'){
    callback()
  }else{
    getCount({
      email: value
    }).then(response => {
      let data = response.data.data
      if (data > 0) {
        callback(new Error('邮箱已经存在'))
      } else {
        callback()
      }
    })
  }
}

const validatePhone = (rule, value, callback) => {
  if (window.openType != 'add'){
    callback()
  }else{
    getCount({
      mobile: value
    }).then(response => {
      let data = response.data.data
          if (data > 0) {
        callback(new Error('手机号码已经存在'))
      } else {
        callback()
      }
    })
  }
}

export const tableOption = {
    dialogDrag: true,
    border: true,
    index: true,
    indexLabel: '序号',
    stripe: true,
    menuAlign: 'center',
    align: 'center',
    viewBtn: true,
    excelBtn: true,
    printBtn: true,
    searchMenuSpan: 6,
    column: [{
        fixed: true,
        label: 'id',
        prop: 'id',
        span: 24,
        hide: true,
        editDisabled: true,
        addDisplay: false
    }, {
        fixed: true,
        label: '账号',
        prop: 'username',
        editDisabled: true,
        search: true,
        span: 24,
        rules: [{
            required: true,
            message: '请输入账号'
        },
        {
            min: 3,
            max: 20,
            message: '长度在 3 到 20 个字符',
            trigger: 'blur'
        },{
                validator: validateUserName,
                trigger: 'blur'
        }]
    }, {
        label: '密码',
        prop: 'password',
        type: 'password',
        value: '',
        hide: true,
        viewDisplay: false,
        editDisplay: false,
        span: 24,
        rules: [{
            required: true,
            min: 6,
            max: 20,
            message: '长度在 6 到 20 个字符',
            trigger: 'blur'
        }]
    }, {
        label: '所属机构',
        prop: 'organId',
        span: 24,
        sortable: true,
        rules: [{
            required: true,
            message: '请选择机构',
            trigger: 'change'
        }],
        type: 'tree',
        search: true,
        props: {
            label: 'name',
            value: 'id'
        },
        defaultExpandAll: true,
        dicUrl: '/upms/organ/tree'
    }, {
        label: '角色',
        prop: 'roleIds',
        overHidden: true,
        dataType: 'array',
        span: 24,
        type: 'checkbox',
        rules: [{
            required: true,
            message: '请选择角色',
            trigger: 'blur'
        }],
        props: {
            label: 'roleName',
            value: 'id'
        },
        dicUrl: '/upms/role/list'
    }, {
        label: '',
        prop: 'lockFlag',
        type: 'radio',
        slot: true,
        sortable: true,
        span: 24,
        rules: [{
            required: true,
            message: '请选择状态',
            trigger: 'blur'
        }],
        dicData: [{
            label: '是',
            value: '0'
        }, {
            label: '否',
            value: '9'
        }]
    }, {
        width: 180,
        label: '创建时间',
        prop: 'createTime',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm',
        valueFormat: 'yyyy-MM-dd HH:mm:ss',
        editDisabled: true,
        addDisplay: false,
        span: 24
    }, {
      label: '邮箱',
      prop: 'email',
      editDisabled: true,
      rules: [{
        required: true,
        message: '请输入邮箱'
      },{
        validator: validateEmail,
        trigger: 'blur'
      }]
    }, {
      label: '手机号',
      prop: 'mobile',
      value: '',
      span: 24,
      editDisabled: true,
      rules: [{
        required: true,
        message: '请输入手机号'
      },{
        min: 11,
        max: 11,
        message: '长度在 11 个字符',
        trigger: 'blur'
      },{
        validator: validatePhone,
        trigger: 'blur'
      }]
    }]
}
