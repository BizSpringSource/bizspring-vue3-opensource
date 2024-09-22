import request from '@/router/axios'

// bizspring-open
// 查询商品列表
export function listGoods(query) {
  return request({
    url: '/goods/admin/goods/list',
    method: 'get',
    params: query,
  })
}

// 删除商品
export function delGoods(ids) {
  return request({
    url: '/goods/admin/goods/delete?ids=' + ids,
    method: 'delete',
  })
}

// // 初始化搜索引擎
export function init_search_data(ids) {
  return request({
    url: '/goods/admin/goods/init_search_data',
    method: 'get',
  })
}

