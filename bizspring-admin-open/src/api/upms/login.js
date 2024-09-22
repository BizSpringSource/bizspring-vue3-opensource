import request from '@/router/axios'

// 查询登录方式列表
export function listLogin(query) {
  return request({
    url: '/upms/admin/login/list',
    method: 'get',
    params: query,
  })
}

// 查询登录方式详细
export function getLogin(id) {
  return request({
    url: '/upms/admin/login/view/' + id,
    method: 'get',
  })
}

// 新增登录方式
export function addLogin(data) {
  return request({
    url: '/upms/admin/login/add',
    method: 'get',
    data: data,
  })
}

// 保存登录方式
export function saveLogin(data) {
  return request({
    url: '/upms/admin/login/save',
    method: 'post',
    data: data,
  })
}

// 修改登录方式
export function editLogin(data) {
  return request({
    url: '/upms/admin/login/edit',
    method: 'get',
    data: data,
  })
}

// 更新登录方式
export function updateLogin(data) {
  return request({
    url: '/upms/admin/login/update',
    method: 'put',
    data: data,
  })
}

// 删除登录方式
export function delLogin(ids) {
  return request({
    url: '/upms/admin/login/delete?ids=' + ids,
    method: 'delete',
  })
}
