import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 主页
 */
export function index(query) {
	return bizRequest({
		url: '/upms/merchant/profile/index',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {
	index,
}
