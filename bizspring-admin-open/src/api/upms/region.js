import request from '@/router/axios'

// 查询地区列表
export function listRegion(query) {
  return request({
    url: '/upms/admin/region/list',
    method: 'get',
    params: query,
  })
}

// 查询地区详细
// export function getRegion(id) {
//   return request({
//     url: '/upms/admin/region/view/' + id,
//     method: 'get',
//   })
// }

export function loadChildsRegion(id) {
  return request({
    url: '/upms/admin/region/loadChildsRegion?id=' + id,
    method: 'get',
  })
}

// 新增地区
export function addRegion(data) {
  return request({
    url: '/upms/admin/region/add',
    method: 'get',
    data: data,
  })
}

// 保存地区
export function saveRegion(data) {
  return request({
    url: '/upms/admin/region/save',
    method: 'post',
    data: data,
  })
}

// 修改地区
export function editRegion(id) {
  return request({
    url: '/upms/admin/region/edit?id=' + id,
    method: 'get',
  })
}

// 更新地区
export function updateRegion(data) {
  return request({
    url: '/upms/admin/region/update',
    method: 'put',
    data: data,
  })
}

// 删除地区
export function delRegion(id) {
  return request({
    url: '/upms/admin/region/delete?id=' + id,
    method: 'delete',
  })
}
