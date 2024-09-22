import bizRequest from '@/utils/request.js'
import {baseURL} from '@/config/config.js'
var app = getApp();
const scope = 'server'


export const getCaptcha = (randomStr) => {
	return  bizRequest({
		url:'/code?randomStr=' + randomStr,
		method: 'GET',
		responseType: "arraybuffer",
		})
}

export const loginByUsername = (username, password, code, randomStr) => {
	const grant_type = 'password'
	return bizRequest({
		url: '/auth/oauth/token',
		headers: {
			isToken: false,
			'Authorization': 'Basic bWVyY2hhbnQ6bWVyY2hhbnQ='
		},
		method: 'POST',
		params: {
			username,
			password,
			randomStr,
			code,
			grant_type,
			scope
		}
	})
}

export const refreshToken = (refresh_token) => {
	const grant_type = 'refresh_token'
	return bizRequest({
		url: '/auth/oauth/token',
		headers: {
			'isToken': false,
			'Authorization': 'Basic bWVyY2hhbnQ6bWVyY2hhbnQ=',
		},
		method: 'post',
		params: {
			refresh_token,
			grant_type,
			scope
		}
	})
}

export const loginByPhone = (mobile, code) => {
	const grant_type = 'sms_login'
	return bizRequest({
		url: '/auth/phone/token/sms',
		headers: {
			'Authorization': 'Basic bWVyY2hhbnQ6bWVyY2hhbnQ='
		},
		method: 'post',
		params: {
			phone: mobile,
			code: code,
			grant_type
		}
	})
}

export const loginByThirdParty = (state, code) => {
	const grant_type = 'mobile'
	return bizRequest({
		url: '/auth/phone/token/thirdparty',
		headers: {
			'Authorization': 'Basic bWVyY2hhbnQ6bWVyY2hhbnQ='
		},
		method: 'post',
		params: {
			mobile: state + '@' + code,
			grant_type
		}
	})
}

export const getUserInfo = () => {
	return bizRequest({
		url: '/upms/user/info',
		method: 'get'
	})
}

export const logout = () => {
	return bizRequest({
		url: '/auth/token/logout',
		method: 'delete'
	})
}

export function register(obj) {
	return bizRequest({
		url: '/upms/user/register',
		method: 'post',
		data: obj
	})
}
