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
// 返回其他状态吗
// axios.defaults.validateStatus = function (status) {
//   return status // 默认的
// }
let switchDemon = true

// request拦截器,在请求之前做一些处理
service.interceptors.request.use(
	config => {
		// if (switchDemon) {
		// 	if (config.url && config.url.indexOf("/auth/") == -1 &&config.url.indexOf("/register") == -1 &&config.url.indexOf("/generator/view") == -1 &&config.method !== 'get'&&(store.getters.userInfo.id == '5' || store.getters.userInfo.id == '6' || store.getters.userInfo.id == '1262364936351555585')) {
		// 			uni.showToast({
		// 				title: '演示账号，不能操作！',
		// 				duration: 2000
		// 			});
		// 		return Promise.reject('演示环境')
		// 	}
		// }
		// const isToken = (config.headers || {}).isToken === false
		// let token = store.getters.access_token
		// if (token && !isToken) {
		// 	config.headers['Authorization'] = 'Bearer ' + token // token
		// }
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
	    // store.dispatch('FedLogOut').then(() => {
	    //   router.push({ path: '/login' })
	    // })
	    return
	  }

	  //非正常返回，弹出多语message
	  if (status != 200 || res.data.status === 1) {
		 uni.showToast({
		 	title: message,
		 	duration: 2000
		 });
	    return Promise.reject(new Error(message))
	  }
	  return res


	// if (res.data.status == 200) {
	// 	return res.data
	// } else {
	// 	return Promise.reject(res.data.msg);
	// }
}, error => {
	if (error.response&&error.response.status) {
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
