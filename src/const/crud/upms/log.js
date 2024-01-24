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
  menuWidth: 150,
  align: 'center',
  refreshBtn: true,
  showColumnBtn: false,
  searchSize: 'default',
  addBtn: false,
  editBtn: false,
  viewBtn: true,
  excelBtn: true,
  printBtn: true,
  menuType:'text',
  searchMenuSpan: 6,
  props: {
    label: 'label',
    value: 'value'
  },
  column: [{
    label: '类型',
    prop: 'type',
    type: 'select',
    sortable:true,
    dicUrl: '/upms/dict/type/log_type',
    search: true
  }, {
    label: '标题',
    prop: 'title'
  }, {
    label: 'IP地址',
    prop: 'remoteAddr'
  }, {
    label: '资源路径',
    prop: 'requestUri'
  }, {
    label: '请求方式',
    prop: 'method',
    type: 'select',
    sortable:true,
    dicUrl: '/upms/dict/type/request_method',
    search: true
  }, {
    label: '操作人',
    sortable:true,
    prop: 'createBy',
    search: true
  }, {
    label: '客户端',
    sortable:true,
    prop: 'serviceId',
    hide: true
  }, {
    width: 80,
    label: '请求时间',
    prop: 'time'
  }, {
    width: 150,
    label: '创建时间',
    prop: 'createTime',
    type: 'datetime',
    sortable:true,
    format: 'YYYY-MM-DD',
    // valueFormat: 'yyyy-MM-dd HH:mm:ss'
  }, {
    label: '异常信息',
    prop: 'exception'
  }]
}
