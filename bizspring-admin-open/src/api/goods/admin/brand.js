import request from '@/router/axios'

// bizspring-open
// 查询品牌列表
export function listBrand(query) {
  return request({
    url: '/goods/admin/brand/list',
    method: 'get',
    params: query,
  })
}

// 新增品牌
export function addBrand(data) {
  return request({
    url: '/goods/admin/brand/add',
    method: 'get',
    data: data,
  })
}

// 保存品牌
export function saveBrand(data) {
  return request({
    url: '/goods/admin/brand/save',
    method: 'post',
    data: data,
  })
}

// 修改品牌
export function editBrand(id) {
  return request({
    url: '/goods/admin/brand/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新品牌
export function updateBrand(data) {
  return request({
    url: '/goods/admin/brand/update',
    method: 'put',
    data: data,
  })
}

// 删除品牌
export function delBrand(ids) {
  return request({
    url: '/goods/admin/brand/delete?ids=' + ids,
    method: 'delete',
  })
}
