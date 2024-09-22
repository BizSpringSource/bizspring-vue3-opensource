import request from '@/router/axios'
// bizspring-open
export function getPage(query) {
  return request({
    url: '/upms/dict/page',
    method: 'get',
    params: query,
  })
}

export function getAll(query) {
  return request({
    url: '/upms/dict/all',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/dict/',
    method: 'post',
    data: obj,
  })
}

export function getObj(id) {
  return request({
    url: '/upms/dict/' + id,
    method: 'get',
  })
}

export function delObj(row) {
  return request({
    url: '/upms/dict/' + row.id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/dict/',
    method: 'put',
    data: obj,
  })
}

export function getPage2(query) {
  return request({
    url: '/upms/dict/item/page',
    method: 'get',
    params: query,
  })
}

export function addObj2(obj) {
  return request({
    url: '/upms/dict/item',
    method: 'post',
    data: obj,
  })
}

export function getObj2(id) {
  return request({
    url: '/upms/dict/item/' + id,
    method: 'get',
  })
}

export function delObj2(id) {
  return request({
    url: '/upms/dict/item/' + id,
    method: 'delete',
  })
}

export function putObj2(obj) {
  return request({
    url: '/upms/dict/item',
    method: 'put',
    data: obj,
  })
}

export function remote(type) {
  return request({
    url: '/upms/dict/type/' + type,
    method: 'get',
  })
}
