import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 首页
 */
export function index(data,params) {
	return bizRequest({
		url: '/order/member/payment',
		method: 'post',
		data:data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 支付处理
 */
export function pay(params) {
	return bizRequest({
		url: '/order/member/payment/pay',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

//支付前 生成支付记录
export function load(data,params) {
	return bizRequest({
		url: '/order/member/payment/load',
		method: 'post',
		data:data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

export function testPostPay(params,paymentLogSn) {
	return bizRequest({
		url: '/order/member/payment/test_post_pay_'+paymentLogSn,
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}


module.exports = {
	index,
	pay,
	load,
	testPostPay
}
