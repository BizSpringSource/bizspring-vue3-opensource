import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/order/member/order/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 取消
 */
export function cancel(params) {
	return bizRequest({
		url: '/order/member/order/cancel',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 收货
 */
export function receive(params) {
	return bizRequest({
		url: '/order/member/order/receive',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 结算
 */
export function checkout(query) {
	return bizRequest({
		url: '/order/member/order/checkout',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 计算
 */
export function calculate(query) {
	return bizRequest({
		url: '/order/member/order/calculate',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 创建
 */
export function create(params) {
	return bizRequest({
		url: '/order/member/order/create',
		method: 'post',
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 支付
 */
export function payment(query) {
	return bizRequest({
		url: '/order/member/order/payment',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {
	list,
	cancel,
	receive,
	checkout,
	calculate,
	create,
	payment,
}
