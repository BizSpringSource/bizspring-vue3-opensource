import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 商家首页
 */
export function index(params) {
	return bizRequest({
		url: '/upms/merchant/index',
		method: 'get',
		params: params,
		headers: app.globalData.headersJson,
	})
}

module.exports = {
	index
}
