import bizRequest from '@/utils/request.js'
var app = getApp();


/**
 * 详情
 */
export function detail(params) {
	return bizRequest({
		url: '/goods/member/goods/detail',
		method: 'get',
		params:params,
		headers: app.globalData.headers,
	})
}

/**
 * 店铺商品_列表
 */
export function store_list(params) {
	return bizRequest({
		url: '/goods/member/goods/store_list',
		method: 'GET',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 首页楼层
 */
export function index(params) {
	return bizRequest({
		url: '/goods/member/goods/index',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 列表
 */
export function list(data,params) {
	return bizRequest({
		url: '/goods/member/goods/list',
		method: 'POST',
		data:data,
		params: params,
		headers: app.globalData.headersJson,
	})
}


module.exports = {
	detail,
	store_list,
	index,
	list,
}
