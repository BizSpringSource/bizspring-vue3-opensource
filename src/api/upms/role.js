/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/upms/role/page',
    method: 'get',
    params: query,
  })
}

export function getList(query) {
  return request({
    url: '/upms/role/list',
    method: 'get',
    params: query,
  })
}

export function getObj(id) {
  return request({
    url: '/upms/role/' + id,
    method: 'get',
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/role',
    method: 'post',
    data: obj,
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/role',
    method: 'put',
    data: obj,
  })
}

export function delObj(id) {
  return request({
    url: '/upms/role/' + id,
    method: 'delete',
  })
}

export function permissionUpd(obj) {
  return request({
    url: '/upms/role/menu',
    method: 'put',
    data: obj,
  })
}

export function fetchRoleTree(roleId) {
  return request({
    url: '/upms/menu/tree/' + roleId,
    method: 'get',
  })
}

export function fetchRoleTreeTenant(tenantId) {
  return request({
    url: '/upms/menu/tree/tenant/' + tenantId,
    method: 'get',
  })
}

export function permissionUpdTenant(obj) {
  return request({
    url: '/upms/role/menu/tenant',
    method: 'put',
    data: obj,
  })
}
