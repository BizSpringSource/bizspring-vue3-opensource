/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询导航列表
export function listNavigation(query) {
  return request({
    url: '/upms/admin/navigation/list',
    method: 'get',
    params: query,
  })
}

// 查询导航详细
export function getNavigation(id) {
  return request({
    url: '/upms/admin/navigation/view/' + id,
    method: 'get',
  })
}

// 新增导航
export function addNavigation(data) {
  return request({
    url: '/upms/admin/navigation/add',
    method: 'get',
    data: data,
  })
}

// 保存导航
export function saveNavigation(data) {
  return request({
    url: '/upms/admin/navigation/save',
    method: 'post',
    data: data,
  })
}

// 修改导航
export function editNavigation(id) {
  return request({
    url: '/upms/admin/navigation/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新导航
export function updateNavigation(data) {
  return request({
    url: '/upms/admin/navigation/update',
    method: 'put',
    data: data,
  })
}

// 删除导航
export function delNavigation(ids) {
  return request({
    url: '/upms/admin/navigation/delete?ids=' + ids,
    method: 'delete',
  })
}
