/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
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
    width: 150,
    label: '终端ID',
    prop: 'id',
    align: 'center',
    sortable: true,
    editDisabled: true,
    rules: [{
      required: true,
      message: '请输入终端ID',
      trigger: 'blur'
    }]
  }, {
    label: '终端密钥',
    prop: 'clientSecret',
    align: 'center',
    sortable: true,
    overHidden: true,
    width: 120,
    rules: [{
      required: true,
      message: '请输入终端密钥',
      trigger: 'blur'
    }]
  }, {
    label: '作用域',
    prop: 'scope',
    align: 'center',
    rules: [{
      required: true,
      message: '请输入作用域',
      trigger: 'blur'
    }]
  }, {
    label: '授权类型',
    prop: 'authorizedGrantTypes',
    align: 'center',
    type: 'checkbox',
    dicUrl: '/upms/dict/type/authorized_grant_types',
    overHidden: true,
    rules: [{
      required: true,
      message: '请输入授权模式',
      trigger: 'blur'
    }]
  }, {
    label: '回调地址',
    prop: 'webServerRedirectUri',
    align: 'center',
    hide: true
  }, {
    label: '权限',
    prop: 'authorities',
    align: 'center',
    hide: true
  }, {
    label: '自动放行',
    prop: 'autoapprove',
    align: 'center',
    type: 'radio',
    dicUrl: '/upms/dict/type/true_false',
    hide: true,
    rules: [{
      required: true,
      message: '请选择是否放行',
      trigger: 'blur'
    }]
  }, {
    label: '令牌时效(s)',
    prop: 'accessTokenValidity',
    type: 'number',
    align: 'center',
  }, {
    label: '刷新时效(s)',
    prop: 'refreshTokenValidity',
    type: 'number',
    align: 'center',
  }, {
    label: '扩展信息',
    prop: 'additionalInformation',
    align: 'center',
    hide: true
  }, {
    label: '资源ID',
    prop: 'resourceIds',
    align: 'center',
    hide: true
  }]
}
