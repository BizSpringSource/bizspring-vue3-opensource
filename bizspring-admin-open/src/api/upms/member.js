import request from '@/router/axios'

// bizspring-open
// 查询商家管理列表
export function listMember(query) {
  return request({
    url: '/upms/admin/member/list',
    method: 'get',
    params: query,
  })
}

// 查询商家管理详细
// export function getMember(id) {
//   return request({
//     url: '/upms/admin/member/view/' + id,
//     method: 'get',
//   })
// }

// 新增商家管理
export function addMember(data) {
  return request({
    url: '/upms/admin/member/add',
    method: 'get',
    data: data,
  })
}

// 保存商家管理
export function saveMember(data, params) {
  return request({
    url: '/upms/admin/member/save',
    method: 'post',
    data: data,
    params: params,
  })
}

// 修改商家管理
export function editMember(id) {
  return request({
    url: '/upms/admin/member/edit',
    method: 'get',
    params: { id: id },
  })
}

// 更新商家管理
export function updateMember(params) {
  return request({
    url: '/upms/admin/member/update',
    method: 'put',
    params: params,
  })
}

// 删除商家管理
export function delMember(ids) {
  return request({
    url: '/upms/admin/member/delete?ids=' + ids,
    method: 'delete',
  })
}
