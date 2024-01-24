/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import request from '@/router/axios'

export function getObj() {
  return request({
    url: '/upms/sysServer',
    method: 'get',
  })
}
