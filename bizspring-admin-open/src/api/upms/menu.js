import request from '@/router/axios'

export function getMenu(id) {
  return request({
    url: '/upms/menu',
    method: 'get'
  })
}

export function fetchAllMenuTree(query) {
  return request({
    url: '/upms/menu/all/tree',
    method: 'get',
    params: query,
  })
}

export function fetchMenuTree(query) {
  return request({
    url: '/upms/menu/tree',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/menu',
    method: 'post',
    data: obj,
  })
}

// bizspring-open

// export function getObj(id) {
//   return request({
//     url: '/upms/menu/' + id,
//     method: 'get',
//   })
// }

export function delObj(id) {
  return request({
    url: '/upms/menu/' + id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/menu',
    method: 'put',
    data: obj,
  })
}
