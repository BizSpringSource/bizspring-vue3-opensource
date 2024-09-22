import bizRequest from '@/utils/request.js'
var app = getApp();

export function index(query) {
	return bizRequest({
		url: '/goods/member/goods_category',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {
	index
}
