import bizRequest from '@/utils/request.js'
var app = getApp();
/**
 * 列表
 */
export function list(params) {
	return bizRequest({
		url: '/store/merchant/shipping_method/list',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 编辑
 */
export function edit(params) {
	return bizRequest({
		url: '/store/merchant/shipping_method/edit',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 更新
 */
export function update(data) {
	return bizRequest({
		url: '/store/merchant/shipping_method/update',
		method: 'post',
		data: data,
		headers: app.globalData.headersJson,
	})
}

module.exports = {
	list,
	edit,
	update,
}
