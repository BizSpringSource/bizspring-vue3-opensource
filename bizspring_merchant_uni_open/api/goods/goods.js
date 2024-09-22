import bizRequest from '@/utils/request.js'
var app = getApp();

/**
 * 检查编号是否存在
 */
export function check_sn(query) {
	return bizRequest({
		url: '/goods/merchant/goods/check_sn',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 上传商品图片
 */
export function upload_goods_image(item) {
	return uni.uploadFile({
		url: "/goods/merchant/goods/upload_goods_image?path=goods", //仅为示例，非真实的接口地址
		filePath: item,
		name: 'file',
		header: app.globalData.headersFile,
	})
}

/**
 * 添加
 */
export function add(query) {
	return bizRequest({
		url: '/goods/merchant/goods/add',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 保存
 */
export function save(data, params) {
	return bizRequest({
		url: '/goods/merchant/goods/save',
		method: 'post',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 编辑
 */
export function edit(query) {
	return bizRequest({
		url: '/goods/merchant/goods/edit',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 更新
 */
export function update(data, params) {
	return bizRequest({
		url: '/goods/merchant/goods/update',
		method: 'put',
		data: data,
		params: params,
		headers: app.globalData.headersJson,
	})
}

/**
 * 列表
 */
export function list(query) {
	return bizRequest({
		url: '/goods/merchant/goods/list',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

/**
 * 删除
 */
export function del(params) {
	return bizRequest({
		url: '/goods/merchant/goods/delete',
		method: 'delete',
		params: params,
		headers: app.globalData.headers,
	})
}


module.exports = {
	check_sn,
	upload_goods_image,
	add,
	save,
	edit,
	update,
	list,
	del,
}
