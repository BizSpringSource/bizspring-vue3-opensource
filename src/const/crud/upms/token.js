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
  addBtn: false,
  editBtn: false,
  excelBtn: true,
  printBtn: true,
  menuType:'text',
  searchMenuSpan: 6,
  column: [{
    label: '用户ID',
    prop: 'user_id',
    hide: true,
    sortable:true,
    align: 'center'
  }, {
    label: '用户名',
    prop: 'username',
    align: 'center'
  }, {
    label: '令牌',
    prop: 'access_token',
    align: 'center',
    'overHidden': true
  }, {
    label: '类型',
    prop: 'token_type',
    sortable:true,
    hide: true,
    align: 'center'
  }, {
    label: '过期时间',
    prop: 'expires_in',
    sortable:true,
    align: 'center'
  }]
}
