import request from '@/utils/request.js'
const scope = 'server'

export const loginByUsername = (username, password, code, randomStr) => {
  const grant_type = 'password'
  return request({
    url: '/auth/oauth/token',
    headers: {
      isToken:false,
      'Authorization': 'Basic c2hvcDpzaG9w'
    },
    method: 'POST',
    params: { username, password, randomStr, code, grant_type, scope }
  })
}

export const refreshToken = (refresh_token) => {
  const grant_type = 'refresh_token'
  return request({
    url: '/auth/oauth/token',
    headers: {
      'isToken': false,
      'Authorization': 'Basic c2hvcDpzaG9w',
    },
    method: 'post',
    params: { refresh_token, grant_type, scope }
  })
}

export const loginByPhone = (mobile, code) => {
  const grant_type = 'sms_login'
  return request({
    url: '/auth/phone/token/sms',
    headers: {
      'Authorization': 'Basic c2hvcDpzaG9w'
    },
    method: 'post',
    params: { phone: mobile, code: code, grant_type }
  })
}

export const loginByThirdParty = (state, code) => {
  const grant_type = 'mobile'
  return request({
    url: '/auth/phone/token/thirdparty',
    headers: {
      'Authorization': 'Basic c2hvcDpzaG9w'
    },
    method: 'post',
    params: { mobile: state + '@' + code, grant_type }
  })
}

export const getUserInfo = () => {
  return request({
    url: '/upms/user/info',
    method: 'get'
  })
}

export const logout = () => {
  return request({
    url: '/auth/token/logout',
    method: 'delete'
  })
}

export function register(obj) {
  return request({
    url: '/upms/user/register',
    method: 'post',
    data: obj
  })
}
