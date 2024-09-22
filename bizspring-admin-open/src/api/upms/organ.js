import request from '@/router/axios'

export function fetchTree(query) {
  return request({
    url: '/upms/organ/tree',
    method: 'get',
    params: query,
  })
}

export function getList(query) {
  return request({
    url: '/upms/organ/list',
    method: 'get',
    params: query,
  })
}

export function addObj(obj) {
  return request({
    url: '/upms/organ/',
    method: 'post',
    data: obj,
  })
}

// bizspring-open
// export function getObj(id) {
//   return request({
//     url: '/upms/organ/' + id,
//     method: 'get',
//   })
// }

export function delObj(id) {
  return request({
    url: '/upms/organ/' + id,
    method: 'delete',
  })
}

export function putObj(obj) {
  return request({
    url: '/upms/organ/',
    method: 'put',
    data: obj,
  })
}
