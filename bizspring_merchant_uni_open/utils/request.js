import {
	baseURL
} from '@/config/config.js'
import errorCode from '@/config/errorCode'

const bizRequest = (paramDatas) => {

	let url = paramDatas.url || '';
	let method = paramDatas.method || '';
	let data = paramDatas.data || {};
	let headers = paramDatas.headers || {};

	let header = {'Authorization': headers.Authorization|| '','username': headers.username|| '','UserId': headers.UserId|| ''};
	if(headers['Content-Type']){
		header['Content-Type'] = headers['Content-Type'];
	}
	if(headers['Access-Control-Max-Age']){
		header['Access-Control-Max-Age'] = headers['Access-Control-Max-Age'];
	}
	let responseType = paramDatas.responseType || '';
	let params = paramDatas.params;
	let paramsStr = '';
	if(params){
		paramsStr += "?";
		for (var key in params) {
			if (null != params[key]) {
				paramsStr += "&" + key + "=" + params[key];
			}
		}
	}
	if((headers=={})  &&url!= '/auth/oauth/token' &&url!= '/auth/token/logout' &&url!= '/upms/user/register' ){
		if(!url.includes("/code?")){
			uni.reLaunch({
				url:"../../pages/index/login"
			})
		}
	}

	return new Promise((resolve, reject) => {
		uni.showLoading({
			title: 'Loading...'
		})
		uni.request({
			method: method,
			url: baseURL + url + paramsStr,
			data: data,
			header: header,
			responseType: responseType,
			success: (res) => { //数据获取成功
				uni.hideLoading();
				const status = Number(res.statusCode) || 200;
				const message = res.data.message || errorCode[status] || errorCode['default'];

				if (status != 200) {
					if (status === 400) {
						return uni.showToast({
							title: message ||'请求异常',
							duration: 5000,
							icon: 'none'
						})
					}
					if (status === 401) {
						uni.showToast({
							title: message || '请登录后继续操作',
							duration: 5000,
							icon: 'none'
						})
						uni.removeStorageSync('userInfoStorage');
						setTimeout(() => {
							uni.reLaunch({
								url:"../../pages/index/login"
							})
						}, 3000)
					}
					if (status === 403) {
						return uni.showToast({
							title: message || '禁止访问',
							duration: 5000,
							icon: 'none'
						})
					}
					if (status === 404) {
						return uni.showToast({
							title: message || '请求资源未找到',
							duration: 5000,
							icon: 'none'
						})
					}
					if (status === 422) {
						return uni.showToast({
							title: message || '请求数据异常,无法处理!',
							duration: 5000,
							icon: 'none'
						})
					}
					if (status === 426) {
					    return	resolve(res);
					}
					if (status === 503) {
						return uni.showToast({
							title: message || '服务不可用,请联系管理员',
							duration: 5000,
							icon: 'none'
						})
					}
					if (status === 500) {
						return uni.showToast({
							title: message || '操作遇到错误,请反馈管理员',
							duration: 5000,
							icon: 'none'
						})
					}
					return uni.showToast({
						title: message,
						duration: 5000,
						icon: 'none'
					})
				}
				return resolve(res) //成功,将数据返回
			},
			fail: (err) => { //失败操作
				uni.hideLoading();
				return uni.showToast({
					title: errorCode['fail'],
					duration: 5000,
					icon: 'none'
				})
				reject(err)
			}
		});
	});
}

export default bizRequest
