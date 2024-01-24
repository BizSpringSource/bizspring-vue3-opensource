/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询友情链接列表
export function listFriend_link(query) {
  return request({
    url: '/upms/admin/friend_link/list',
    method: 'get',
    params: query,
  })
}

// 查询友情链接详细
export function getFriend_link(id) {
  return request({
    url: '/upms/admin/friend_link/view/' + id,
    method: 'get',
  })
}

// 新增友情链接
export function addFriend_link(data) {
  return request({
    url: '/upms/admin/friend_link/add',
    method: 'get',
    data: data,
  })
}

// 保存友情链接
export function saveFriend_link(data) {
  return request({
    url: '/upms/admin/friend_link/save',
    method: 'post',
    data: data,
  })
}

// 修改友情链接
export function editFriend_link(id) {
  return request({
    url: '/upms/admin/friend_link/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新友情链接
export function updateFriend_link(data) {
  return request({
    url: '/upms/admin/friend_link/update',
    method: 'put',
    data: data,
  })
}

// 删除友情链接
export function delFriend_link(ids) {
  return request({
    url: '/upms/admin/friend_link/delete?ids=' + ids,
    method: 'delete',
  })
}
