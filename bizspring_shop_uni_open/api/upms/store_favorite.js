import bizRequest from '@/utils/request.js'
var app = getApp();


/**
 * 添加
 */
export function add(params) {
	return bizRequest({
		url: '/upms/member/store_favorite/add',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}
/**
 * 列表
 */
export function list(params) {
	return bizRequest({
		url: '/upms/member/store_favorite/list',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 删除
 */
export function del(ids) {
	return bizRequest({
		url: '/upms/member/store_favorite/delete?ids=' + ids,
		method: 'delete',
		headers: app.globalData.headers,
	})
}

module.exports = {
	add,
	list,
	del,
}
