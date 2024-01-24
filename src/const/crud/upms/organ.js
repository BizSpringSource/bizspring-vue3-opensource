/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import {getList} from '@/api/upms/organ'

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
  searchMenuSpan: 6,
  column: [
    {
      label: '父类机构',
      prop: 'parentId',
      type: 'tree',
      hide: true,
      defaultExpandAll: true,
      props: {
        label: 'name',
        value: 'id'
      },
      rules: [{
        required: true,
        message: '请选择父类机构',
        trigger: 'change'
      }]
    },
    {
      label: '机构名称',
      prop: 'name',
      align: 'left',
      width: 200,
      rules: [{
        required: true,
        message: '机构名称不能为空',
        trigger: 'blur'
      }]
    },
    {
      label: '机构类型',
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
      label: '机构编码',
      hide:true,
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
      label: '排序',
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
      prop: 'mobile'
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
      prop: 'address'
    },
    {
      label: '备注',
      hide: true,
      prop: 'comment'
    },
    {
      label: '创建时间',
      prop: 'createTime',
      editDisplay:false,
      addDisplay:false
    },
    {
      label: '修改时间',
      prop: 'updateTime',
      hide: true,
      editDisplay:false,
      addDisplay:false
    }
  ]
}
