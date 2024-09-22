import bizRequest from '@/utils/request.js'
var app = getApp();
/**
 * 添加
 */
export function add(params) {
	return bizRequest({
		url: '/store/merchant/region_freight/add',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 保存
 */
export function save(data) {
	return bizRequest({
		url: '/store/merchant/region_freight/save',
		method: 'post',
		data: data,
		headers: app.globalData.headersJson,
	})
}

/**
 * 编辑
 */
export function edit(query) {
	return bizRequest({
		url: '/store/merchant/region_freight/edit',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 更新
 */
export function update(data) {
	return bizRequest({
		url: '/store/merchant/region_freight/update',
		method: 'post',
		data: data,
		headers: app.globalData.headersJson,
	})
}

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/store/merchant/region_freight/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

module.exports = {
	add,
	save,
	edit,
	update,
	list,
}
