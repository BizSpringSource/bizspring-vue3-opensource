import bizRequest from '@/utils/request.js'
var app = getApp();
/**
 * 获取当前购物车
 */
export function get_current(params) {
	return bizRequest({
		url: '/order/member/cart/get_current',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 添加
 */
export function add(params) {
	return bizRequest({
		url: '/order/member/cart/add',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 修改
 */
export function modify(params) {
	return bizRequest({
		url: '/order/member/cart/modify',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 选择
 */
export function select(params) {
	return bizRequest({
		url: '/order/member/cart/select',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}


/**
 * 移除
 */
export function remove(params) {
	return bizRequest({
		url: '/order/member/cart/remove',
		method: 'delete',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 清空
 */
export function clear(data) {
	return bizRequest({
		url: '/order/member/cart/clear',
		method: 'post',
		data: data,
		headers: app.globalData.headersJson,
	})
}


module.exports = {
	get_current,
	add,
	modify,
	select,
	remove,
	clear,
}
