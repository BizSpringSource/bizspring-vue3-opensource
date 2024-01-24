/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询积分列表
export function listPoint_log(query) {
  return request({
    url: '/upms/admin/point_log/list',
    method: 'get',
    params: query,
  })
}

// 查询积分详细
export function getPoint_log(id) {
  return request({
    url: '/upms/admin/point_log/view/' + id,
    method: 'get',
  })
}

// 新增积分
export function addPoint_log(data) {
  return request({
    url: '/upms/admin/point_log/add',
    method: 'get',
    data: data,
  })
}

// 保存积分
export function savePoint_log(data) {
  return request({
    url: '/upms/admin/point_log/adjust',
    method: 'post',
    params: data,
  })
}

//搜索会员
export function memberSelect(query) {
  return request({
    url: '/upms/admin/point_log/member_select',
    method: 'get',
    params: query,
  })
}

// 修改积分
export function editPoint_log(data) {
  return request({
    url: '/upms/admin/point_log/edit',
    method: 'get',
    data: data,
  })
}

// 更新积分
export function updatePoint_log(data) {
  return request({
    url: '/upms/admin/point_log/update',
    method: 'put',
    data: data,
  })
}

// 删除积分
export function delPoint_log(ids) {
  return request({
    url: '/upms/admin/point_log/delete?ids=' + ids,
    method: 'delete',
  })
}
