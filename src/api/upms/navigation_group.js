/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询导航组列表
export function listNavigation_group(query) {
  return request({
    url: '/upms/admin/navigation_group/list',
    method: 'get',
    params: query,
  })
}

// 查询导航组详细
export function getNavigation_group(id) {
  return request({
    url: '/upms/admin/navigation_group/view/' + id,
    method: 'get',
  })
}

// 新增导航组
export function addNavigation_group(data) {
  return request({
    url: '/upms/admin/navigation_group/add',
    method: 'get',
    data: data,
  })
}

// 保存导航组
export function saveNavigation_group(data) {
  return request({
    url: '/upms/admin/navigation_group/save',
    method: 'post',
    data: data,
  })
}

// 修改导航组
export function editNavigation_group(data) {
  return request({
    url: '/upms/admin/navigation_group/edit',
    method: 'get',
    data: data,
  })
}

// 更新导航组
export function updateNavigation_group(data) {
  return request({
    url: '/upms/admin/navigation_group/update',
    method: 'put',
    data: data,
  })
}

// 删除导航组
export function delNavigation_group(ids) {
  return request({
    url: '/upms/admin/navigation_group/delete?ids=' + ids,
    method: 'delete',
  })
}
