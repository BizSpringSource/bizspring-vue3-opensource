import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 更新
 */
export function update(data, params) {
	return bizRequest({
		url: '/order/merchant/order/update',
		method: 'post',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 查看
 */
export function view(query) {
	return bizRequest({
		url: '/order/merchant/order/view',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}


/**
 * 审核
 */
export function review(params) {
	return bizRequest({
		url: '/order/merchant/order/review',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 发货
 */
export function shipping(data, params) {
	return bizRequest({
		url: '/order/merchant/order/shipping',
		method: 'post',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 完成
 */
export function complete(params) {
	return bizRequest({
		url: '/order/merchant/order/complete',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 失败
 */
export function fail(params) {
	return bizRequest({
		url: '/order/merchant/order/fail',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/order/merchant/order/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

export function regions_childs(params) {
	return bizRequest({
		url: '/order/merchant/order/regions_childs',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 统计
 * @param {Object} params
 */
export function count(params) {
	return bizRequest({
		url: '/order/merchant/order/count',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

export function list_paymented_order(params) {
	return bizRequest({
		url: '/order/merchant/order/list_paymented_order',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}



module.exports = {
	update,
	view,
	review,
	shipping,
	complete,
	fail,
	list,
	count,
	list_paymented_order
}
