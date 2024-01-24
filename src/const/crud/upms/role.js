/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import {getList} from '@/api/upms/role'

const validateRoleName = (rule, value, callback) => {
  if (window.openType === 'edit'){
    callback()
  }else{
    getList({
      roleName: value
    }).then(response => {
      let data = response.data
      if (data.length > 0) {
        callback(new Error('角色名称已经存在'))
      } else {
        callback()
      }
    })
  }
}

const validateRoleCode = (rule, value, callback) => {
  if (window.openType === 'edit'){
    callback()
  }else{
    getList({
      roleCode: value
    }).then(response => {
      let data = response.data
      if (data.length > 0) {
        callback(new Error('角色编码已经存在'))
      } else {
        callback()
      }
    })
  }
}

export const tableOption = {
  dialogDrag:true,
  border: true,
  index: true,
  indexLabel: '序号',
  stripe: true,
  menuAlign: 'center',
  align: 'center',
  viewBtn: true,
  excelBtn: true,
  printBtn: true,
  menuType:'text',
  searchMenuSpan: 6,
  column: [{
    label: '角色名称',
    prop: 'roleName',
    span: 24,
    rules: [{
      required: true,
      message: '角色名称不能为空',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 20,
      message: '长度在 3 到 20 个字符',
      trigger: 'blur'
    },
    {
      validator: validateRoleName,
      trigger: 'blur'
    }]
  }, {
    width: 120,
    label: '角色编码',
    prop: 'roleCode',
    span: 24,
    editDisabled: true,
    rules: [{
      required: true,
      message: '角色编码不能为空',
      trigger: 'blur'
    },
    {
      min: 3,
      max: 20,
      message: '长度在 3 到 20 个字符',
      trigger: 'blur'
    },
    {
      validator: validateRoleCode,
      trigger: 'blur'
    }
    ]
  }, {
    width: 150,
    label: '角色描述',
    prop: 'roleDesc',
    overHidden: true,
    span: 24,
    rules: [{
      required: true,
      message: '角色描述不能为空',
      trigger: 'blur'
    }]
  }, {
    width: 180,
    label: '数据权限',
    prop: 'dsType',
    type: 'select',
    span: 24,
    sortable:true,
    rules: [{
      required: true,
      message: '请选择数据权限类型',
      trigger: 'blur'
    }],
    dicData: [{
      label: '全部',
      value: 0
    }, {
      label: '自定义',
      value: 1
    }, {
      label: '本级及子级',
      value: 2
    }, {
      label: '本级',
      value: 3
    }]
  }, {
    label:'',
    prop: 'dsScope',
    formslot: true,
    showColumn:false,
    hide: true
  }, {
    label: '创建时间',
    prop: 'createTime',
    type: 'datetime',
    format: 'YYYY-MM-DD',
    // valueFormat: 'yyyy-MM-dd HH:mm:ss',
    editDisplay: false,
    addDisplay: false,
    sortable:true,
    span: 24
  }]
}
