/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import axios from 'axios'
import {  serialize} from '@/util'
import NProgress from 'nprogress' // progress bar
import errorCode from '@/const/errorCode'
import {  ElMessage,  ElMessageBox} from 'element-plus'
import 'nprogress/nprogress.css'
import qs from 'qs'
import store from '@/store'
import {  baseUrl} from '@/config/env' // progress bar style
axios.defaults.timeout = 30000
// 返回其他状态吗
axios.defaults.validateStatus = function (status) {
  return status >= 200 && status <= 500 // 默认的
}
// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true
// NProgress Configuration
NProgress.configure({
  showSpinner: false
})

// HTTPrequest拦截
axios.defaults.baseURL = baseUrl
axios.interceptors.request.use(config => {
  NProgress.start() // start progress bar
  const isToken = (config.headers || {}).isToken === false
  const token = store.getters.access_token

  if (token && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + token // token
  }

  // headers中配置serialize为true开启序列化
  if (config.method === 'post' && config.headers.serialize) {
    config.data = serialize(config.data)
    delete config.data.serialize
  }
  if (config.method === 'get' && config.params) {
    let url = config.url + '?';
    for (const propName of Object.keys(config.params)) {
      const value = config.params[propName];
      var part = encodeURIComponent(propName) + "=";
      if (value !== null && typeof (value) !== "undefined") {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            let params = propName + '[' + key + ']';
            var subPart = encodeURIComponent(params) + "=";
            url += subPart + encodeURIComponent(value[key]) + "&";
          }
        } else {
          url += part + encodeURIComponent(value) + "&";
        }
      }
    }
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }

  return config
}, error => {
  return Promise.reject(error)
})

// HTTPresponse拦截
axios.interceptors.response.use(res => {
  NProgress.done()
  const status = Number(res.status) || 200
  const message = res.data.msg || errorCode[status] || errorCode['default']

  // 后台定义 424 针对令牌过去的特殊响应码
  if (status === 424) {
    ElMessageBox.confirm('令牌状态已过期，请点击重新登录', '系统提示', {
      confirmButtonText: '重新登录',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      store.dispatch('LogOut').then(() => {
        // 刷新登录页面，避免多次弹框
        window.location.reload()
      })
    }).catch(() => {})
    return
  }
  if (status === 401) {
    ElMessageBox({
      message: '登录过期，请重新登录',
      type: 'error'
    }).then(() => {
      store.dispatch('LogOut').then(() => {
        // 刷新登录页面，避免多次弹框
        window.location.reload()
      })
    })
    return
  }
  if (status !== 200 || res.data.code === 1) {
    if (status === 400) {
      ElMessageBox({
        message: i18nMessageJsons[res.data.message] || i18nMessageJsons['common_message_badRequest'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_badRequest']));
    }
    if (status === 401) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_unauthorized'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_unauthorized']));
    }
    if (status === 403) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_forbidden'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_forbidden']));
    }
    if (status === 404) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_notFound'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_notFound']));
    }
    if (status === 422) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_unprocessableEntity'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_unprocessableEntity']));
    }
    if (status === 503) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_serviceUnavailable'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_serviceUnavailable']));
    }
    if (status === 500) {
      ElMessageBox({
        message: i18nMessageJsons['common_message_internalServerError'],
        type: 'error'
      })
      return Promise.reject(new Error(i18nMessageJsons['common_message_internalServerError']));
    }
    ElMessageBox({
      message: i18nMessageJsons[message],
      type: 'error'
    })
    return Promise.reject(new Error(message))
  }

  return res
}, error => {
  NProgress.done()
  return Promise.reject(new Error(error))
})

// 通用下载方法
export function download(url, params, filename) {
  return axios.post(url, params, {
    transformRequest: [(params) => {
      return tansParams(params)
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    responseType: 'blob'
  }).then((data) => {
    const content = data
    const blob = new Blob([content])
    if ('download' in document.createElement('a')) {
      const elink = document.createElement('a')
      elink.download = filename
      elink.style.display = 'none'
      elink.href = URL.createObjectURL(blob)
      document.body.appendChild(elink)
      elink.click()
      URL.revokeObjectURL(elink.href)
      document.body.removeChild(elink)
    } else {
      navigator.msSaveBlob(blob, filename)
    }
  }).catch((r) => {
    console.error(r)
  })
}
export default axios