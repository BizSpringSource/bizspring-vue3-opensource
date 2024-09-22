import request from '@/router/axios'

// 查询订单发货列表
export function listOrder_shipping(query) {
  return request({
    url: '/order/admin/order_shipping/list',
    method: 'get',
    params: query,
  })
}

// 删除订单发货
export function delOrder_shipping(ids) {
  return request({
    url: '/order/admin/order_shipping/delete?ids=' + ids,
    method: 'delete',
  })
}
