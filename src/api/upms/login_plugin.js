/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询登录插件列表
export function listLogin_plugin(query) {
  return request({
    url: '/upms/admin/login_plugin/list',
    method: 'get',
    params: query,
  })
}

// 查询登录插件详细
export function getLogin_plugin(id) {
  return request({
    url: '/upms/admin/login_plugin/view/' + id,
    method: 'get',
  })
}

// 新增登录插件
export function addLogin_plugin(data) {
  return request({
    url: '/upms/admin/login_plugin/add',
    method: 'get',
    data: data,
  })
}

// 保存登录插件
export function saveLogin_plugin(data) {
  return request({
    url: '/upms/admin/login_plugin/save',
    method: 'post',
    data: data,
  })
}

// 修改登录插件
export function editLogin_plugin(data) {
  return request({
    url: '/upms/admin/login_plugin/edit',
    method: 'get',
    data: data,
  })
}

// 更新登录插件
export function updateLogin_plugin(data) {
  return request({
    url: '/upms/admin/login_plugin/update',
    method: 'put',
    data: data,
  })
}

// 删除登录插件
export function delLogin_plugin(ids) {
  return request({
    url: '/upms/admin/login_plugin/delete?ids=' + ids,
    method: 'delete',
  })
}
