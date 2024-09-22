import request from '@/router/axios'

// 查询店铺列表
export function listStore(query) {
  return request({
    url: '/store/admin/store/list',
    method: 'get',
    params: query,
  })
}

// 查询店铺详细
export function getStore(id) {
  return request({
    url: '/store/admin/store/view/' + id,
    method: 'get',
  })
}

// 新增店铺
export function addStore(data) {
  return request({
    url: '/store/admin/store/add',
    method: 'get',
    data: data,
  })
}

// 保存店铺
export function saveStore(data) {
  return request({
    url: '/store/admin/store/save',
    method: 'post',
    data: data,
  })
}

//搜索商家
export function merchantSelect(query) {
  return request({
    url: '/store/admin/store/merchant_select',
    method: 'get',
    params: query,
  })
}

// 修改店铺
export function editStore(id) {
  return request({
    url: '/store/admin/store/edit/' + id,
    method: 'get',
  })
}

// 更新店铺
export function updateStore(data) {
  return request({
    url: '/store/admin/store/update',
    method: 'put',
    data: data,
  })
}

// 删除店铺
export function delStore(ids) {
  return request({
    url: '/store/admin/store/delete?ids=' + ids,
    method: 'delete',
  })
}
//搜索店铺
export function storeSelect(query) {
  return request({
    url: '/store/admin/store/store_select',
    method: 'get',
    params: query,
  })
}
