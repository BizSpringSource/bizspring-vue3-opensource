import request from '@/router/axios'

// bizspring-open
export function getPage(query) {
  return request({
    url: '/upms/client/page',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/client/',
    method: 'post',
    data: obj,
  })
}

export function getObj(id) {
  return request({
    url: '/upms/client/' + id,
    method: 'get',
  })
}

export function delObj(id) {
  return request({
    url: '/upms/client/' + id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/client',
    method: 'put',
    data: obj,
  })
}
