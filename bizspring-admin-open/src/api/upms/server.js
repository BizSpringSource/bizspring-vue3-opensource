import request from '@/router/axios'
// bizspring-open
export function getObj() {
  return request({
    url: '/upms/sysServer',
    method: 'get',
  })
}
