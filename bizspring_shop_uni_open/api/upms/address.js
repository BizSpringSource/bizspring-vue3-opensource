import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/upms/member/address/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 添加
 */
export function add(query) {
	return bizRequest({
		url: '/upms/member/address/add',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 保存
 */
export function save(data) {
	return bizRequest({
		url: '/upms/member/address/save',
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
		url: '/upms/member/address/edit',
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
		url: '/upms/member/address/update',
		method: 'post',
		data: data,
		headers: app.globalData.headersJson,
	})
}

/**
 * 删除
 */
export function del(params) {
	return bizRequest({
		url: '/upms/member/address/delete',
		method: 'delete',
		params: params,
		headers: app.globalData.headers,
	})
}

module.exports = {
	list,
	add,
	save,
	edit,
	update,
	del,
}
