/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
export default {
  menu: false,
  addBtn: false,
  page: false,
  border: true,
  expand: true,
  refreshBtn: false,
  headerAlign: 'center',
  column: [{
    label: '类型',
    prop: 'type',
    width: 80,
    align: 'center',
    slot: true,
    dicData: [{
      label: 'bug',
      value: 'error'
    }]
  }, {
    label: '地址',
    width: 200,
    prop: 'url',
    overHidden: true
  }, {
    label: '内容',
    prop: 'message',
    overHidden: true
  }, {
    label: '错误堆栈',
    prop: 'stack',
    hide: true
  }, {
    label: '时间',
    align: 'center',
    prop: 'time',
    width: 200
  }]
}
