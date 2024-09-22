import bizRequest from '@/utils/request.js'
var app = getApp();

export function load_member(query) {
	return bizRequest({
		url: '/upms/member/profile/load_member',
		method: 'get',
		params: query,
		headers: app.globalData.headers,
	})
}

export function show_store_center() {
	return bizRequest({
		url: '/upms/member/profile/show_store_center',
		method: 'get',
		headers: app.globalData.headers,
	})
}

module.exports = {
	load_member,
	show_store_center
}
