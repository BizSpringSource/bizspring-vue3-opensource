import request from '@/router/axios'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/order/admin/order/list',
    method: 'get',
    params: query,
  })
}
