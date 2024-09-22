import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 首页
 */
export function index(storeId) {
	return bizRequest({
		url: '/store/member/store/index?id=' + storeId,
		method: 'get',
		headers: app.globalData.headers,
	})
}

module.exports = {
	index,
}
