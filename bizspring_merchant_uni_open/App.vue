<script>
	import {
		refreshToken
	} from '@/utils/login.js'
	import bizRequest from '@/utils/request.js'

	export default {
		globalData: {
			headers: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'Content-Type': 'application/x-www-form-urlencoded',
				'Access-Control-Max-Age': 86400,
				'messages':null,
			},
			headersJson: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'Content-Type': 'application/json',
				'messages':null,
			},
			headersFile: {
				'Authorization': '',
				'username': '',
				'UserId': '',
				'messages':null,
			},
			messages:null,
			userInfo:null,
		},		
		async onLaunch() {
			this.wxUpdateManager();
		},
		onShow: function() {
			this.onAuth();
		},
		onHide: function() {
		},
		methods: {
			wxUpdateManager: function() {
				// 版本更新  
				const updateManager = uni.getUpdateManager();
				updateManager.onCheckForUpdate(function(res) {
					// 请求完新版本信息的回调  
					if (res.hasUpdate) {
						this.logoutRemoveStorage()
						updateManager.onUpdateReady(function(res) {
							uni.showToast({
								title: '发现新版本',
								success() {
									updateManager.applyUpdate();
								}
							})
						});
						updateManager.onUpdateFailed(function(res) {
							// 新的版本下载失败  
							// wx.showModal({
							// 	title: '已经有新版本了哟~',
							// 	content: '新版本已经上线啦~，请您删除当前小程序，重新搜索打开哟~'
							// })
						});
					}
				});
			},
			onAuth: function() {
				const userInfoStorage = uni.getStorageSync('userInfoStorage');
				if (userInfoStorage) {
					let rf_token = userInfoStorage.refresh_token;
					uni.showLoading({
						title: 'Loading...'
					})
					refreshToken(rf_token).then(response => {
						uni.hideLoading();
						if (response.data.user) {
							this.setAuthorization('Bearer ' + response.data.access_token);
							this.setUserId(response.data.user.id);
							this.setUsername(response.data.user.username);
							this.globalData.userInfo = response.data.user;
							this.loginSetStorage(response.data);
						} else {
							this.logoutRemoveStorage();
							// uni.showToast({
							// 	title: '登录失败！',
							// 	duration: 3000,
							// 	icon:'none'
							// });
							uni.navigateTo({
								url: '../pages/index/login'
							});
						}
						// resolve();
					}).catch(error => {
						uni.showToast({
								title: '系统未知错误,请反馈给管理员',
								duration: 2000
						});
					})
				}else{
					uni.navigateTo({
						url: '../pages/index/login'
					});
				}
			},
			
			//--------------------------------------------------- toket -------------------------------------------
			
			//存储登录storage 信息，保持登录  增
			loginSetStorage: function(e) {
				uni.setStorageSync('userInfoStorage', e);
			},
			
			//删除登录storage 信息，删除保持登录  删
			logoutRemoveStorage: function() {
				uni.removeStorageSync('userInfoStorage');
				this.setAuthorization(null);
				this.setUsername(null);				
				this.setUserId(null);
				this.globalData.userInfo=null;
			},
			setUsername: function(username) {
				this.globalData.headersJson.username = username;
				this.globalData.headers.username = username;
				this.globalData.headersFile.username = username;
			},
			setAuthorization: function(authorization) {
				this.globalData.headersJson.Authorization = authorization;
				this.globalData.headers.Authorization = authorization;
				this.globalData.headersFile.Authorization = authorization;
			},
			setUserId: function(userId) {
				this.globalData.headersJson.UserId = userId;
				this.globalData.headers.UserId = userId;
				this.globalData.headersFile.UserId = userId;
			},
			//将 messages 设置到 hander 头中。
			setHanderMessages: function(messages) {
				this.globalData.headersJson.messages = messages;
				this.globalData.headers.messages = messages;
				this.globalData.headersFile.messages = messages;
			},

		}
	};
</script>
<style lang="scss">
	/*每个页面公共css */
	@import './static/iconfont.css';
	@import './static/app.scss';
</style>
