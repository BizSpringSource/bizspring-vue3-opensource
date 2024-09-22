import Vue from 'vue'
import axios from 'axios'
import {baseURL} from '@/config/config.js'
import errorCode from '@/config/errorCode'

// create an axios instance
const service = axios.create({
	baseURL:baseURL, // url = base url + request url
	//withCredentials: true, // send cookies when cross-domain requests 注意：withCredentials和后端配置的cross跨域不可同时使用
	timeout: 30000, // request timeout
	crossDomain: true
})

service.defaults.timeout = 30000
// 跨域请求，允许保存cookie
service.defaults.withCredentials = true

let switchDemon = true

// request拦截器,在请求之前做一些处理
service.interceptors.request.use(
	config => {
		return config;
	},
	error => {
		console.log(error); // for debug
		return Promise.reject(error);
	}
);

//配置成功后的拦截器
service.interceptors.response.use(res => {
	  const status = Number(res.status) || 200
	  const message = res.data.message || errorCode[status] || errorCode['default'];
	  if (status === 401) {

		  uni.showToast({
		  	title: '登录过期，请重新登录！',
		  	duration: 2000
		  });

	    return
	  }

	  //非正常返回，弹出多语message
	  if (status != 200 || res.data.status === 1) {
	    return Promise.reject(new Error(message))
	  }

	  return res

}, error => {
	console.error("requestError",error);
	if (error.response.status) {
		switch (error.response.status) {
			case 401:
				break;
			default:
				break;
		}
	}
	return Promise.reject(error)
})

export default service
