import request from '@/router/axios'
// bizspring-open

// 查询商品分类列表
export function listGoods_category(query) {
  return request({
    url: '/goods/admin/goods_category/list',
    method: 'get',
    params: query,
  })
}

// 新增商品分类
export function addGoods_category(data) {
  return request({
    url: '/goods/admin/goods_category/add',
    method: 'get',
    data: data,
  })
}

// 保存商品分类
export function saveGoods_category(data) {
  return request({
    url: '/goods/admin/goods_category/save',
    method: 'post',
    data: data,
  })
}

// 修改商品分类
export function editGoods_category(id) {
  return request({
    url: '/goods/admin/goods_category/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新商品分类
export function updateGoods_category(data) {
  return request({
    url: '/goods/admin/goods_category/update',
    method: 'put',
    data: data,
  })
}

// 删除商品分类
export function delGoods_category(ids) {
  return request({
    url: '/goods/admin/goods_category/delete?ids=' + ids,
    method: 'delete',
  })
}
