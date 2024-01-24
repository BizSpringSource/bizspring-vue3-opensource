/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

export function getMenu(id) {
  return request({
    url: '/upms/menu',
    method: 'get'
  })
}

export function fetchAllMenuTree(query) {
  return request({
    url: '/upms/menu/all/tree',
    method: 'get',
    params: query,
  })
}

export function fetchMenuTree(query) {
  return request({
    url: '/upms/menu/tree',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/menu',
    method: 'post',
    data: obj,
  })
}

export function getObj(id) {
  return request({
    url: '/upms/menu/' + id,
    method: 'get',
  })
}

export function delObj(id) {
  return request({
    url: '/upms/menu/' + id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/menu',
    method: 'put',
    data: obj,
  })
}
