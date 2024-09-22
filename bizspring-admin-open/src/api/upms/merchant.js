import request from '@/router/axios'

// bizspring-open
// 查询商家管理列表
export function listMerchant(query) {
  return request({
    url: '/upms/admin/merchant/list',
    method: 'get',
    params: query,
  })
}

// 查询商家管理详细
export function getMerchant(id) {
  return request({
    url: '/upms/admin/merchant/view/' + id,
    method: 'get',
  })
}

// 新增商家管理
export function addMerchant(data) {
  return request({
    url: '/upms/admin/merchant/add',
    method: 'get',
    data: data,
  })
}

// 保存商家管理
export function saveMerchant(params) {
  return request({
    url: '/upms/admin/merchant/save',
    method: 'post',
    params: params,
  })
}

// 修改商家管理
export function editMerchant(id) {
  return request({
    url: '/upms/admin/merchant/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新商家管理
export function updateMerchant(params) {
  return request({
    url: '/upms/admin/merchant/update',
    method: 'put',
    params: params,
  })
}

// 删除商家管理
export function delMerchant(ids) {
  return request({
    url: '/upms/admin/merchant/delete?ids=' + ids,
    method: 'delete',
  })
}
