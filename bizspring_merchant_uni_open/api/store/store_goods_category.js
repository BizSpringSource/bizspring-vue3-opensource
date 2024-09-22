import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 添加
 */
export function add() {
	return bizRequest({
		url: '/store/merchant/store_goods_category/add',
		method: 'get',
		headers: app.globalData.headers,
	})
}
/**
 * 保存
 */
export function save(data, params) {
	return bizRequest({
		url: '/store/merchant/store_goods_category/save',
		method: 'post',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 编辑
 */
export function edit(params) {
	return bizRequest({
		url: '/store/merchant/store_goods_category/edit',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 更新
 */
export function update(data, params) {
	return bizRequest({
		url: '/store/merchant/store_goods_category/update',
		method: 'post',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 列表
 */
export function list(params) {
	return bizRequest({
		url: '/store/merchant/store_goods_category/list',
		method: 'get',
		params: params,
		headers: app.globalData.headers,
	})
}

/**
 * 删除
 */
export function del(params) {
	return bizRequest({
		url: '/store/merchant/store_goods_category/delete',
		method: 'delete',
		params: params,
		headers: app.globalData.headers,
	})
}

module.exports = {
	add,
	save,
	edit,
	update,
	list,
	del,
}
