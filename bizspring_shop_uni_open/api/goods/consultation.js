import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 列表
 */
export function goods_consultation_list(query) {
	return bizRequest({
		url: '/goods/member/consultation/goods_consultation_list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {
	goods_consultation_list
}
