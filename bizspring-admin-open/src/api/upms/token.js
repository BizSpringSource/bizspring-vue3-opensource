import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/upms/token/page',
    method: 'get',
    params: query,
  })
}

export function delObj(token) {
  return request({
    url: '/upms/token/' + token,
    method: 'delete',
  })
}

// bizspring-open