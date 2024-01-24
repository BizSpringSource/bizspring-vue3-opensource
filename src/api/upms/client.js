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
    url: '/upms/client/page',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/client/',
    method: 'post',
    data: obj,
  })
}

export function getObj(id) {
  return request({
    url: '/upms/client/' + id,
    method: 'get',
  })
}

export function delObj(id) {
  return request({
    url: '/upms/client/' + id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/client',
    method: 'put',
    data: obj,
  })
}
