import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/upms/merchant/goods_favorite/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {	
	list,	
}
