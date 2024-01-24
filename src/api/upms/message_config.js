/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

// 查询消息配置列表
export function listMessage_config(query) {
  return request({
    url: '/upms/admin/message_config/list',
    method: 'get',
    params: query,
  })
}

// 查询消息配置详细
export function getMessage_config(id) {
  return request({
    url: '/upms/admin/message_config/view/' + id,
    method: 'get',
  })
}

// 新增消息配置
export function addMessage_config(data) {
  return request({
    url: '/upms/admin/message_config/add',
    method: 'get',
    data: data,
  })
}

// 保存消息配置
export function saveMessage_config(data) {
  return request({
    url: '/upms/admin/message_config/save',
    method: 'post',
    data: data,
  })
}

// 修改消息配置
export function editMessage_config(data) {
  return request({
    url: '/upms/admin/message_config/edit',
    method: 'get',
    data: data,
  })
}

// 更新消息配置
export function updateMessage_config(data) {
  return request({
    url: '/upms/admin/message_config/update',
    method: 'put',
    data: data,
  })
}

// 删除消息配置
export function delMessage_config(ids) {
  return request({
    url: '/upms/admin/message_config/delete?ids=' + ids,
    method: 'delete',
  })
}
