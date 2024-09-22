<template>
	<view class="content">
		<view class="topbg">
			<view class="logo">
				<image src="../../static/images/default_logo.png" class="img"></image>
			</view>
		</view>
		<form @submit="login">
			<view class="iptbox"><input type="text" focus   v-model="uInfo.username" maxlength="15" value="" class="ipt" placeholder="请填写用户名"
					name="username" /></view>
			<view class="iptbox"><input  password  v-model="uInfo.password" maxlength="15" value="" class="ipt"
					placeholder="请填写密码" name="password" /></view>
			<view class="iptboxcode" style="display:flex;flex-direction:row;">
				<input name="code" type="text"  maxlength="8" v-model="uInfo.code" id="code" style="margin-right:10rpx;"
					placeholder="请填写验证码"></input>
				<image
					style=" height: 38px;line-height: 38px;width: calc(35% - 5px);font-weight: bold;text-align: center;margin-top: 0px;"
					:src="code.src" @tap="refreshCode"></image>
			</view>
			<button form-type="submit" class="btn">登录</button>
		</form>
	</view>
</template>

<script>
	import bizRequest from '@/utils/request.js'
	import util from '@/utils/util'
	import {
		getCaptcha,
		getUserInfo,
		loginByPhone,
		loginByThirdParty,
		loginByUsername,
		logout,
		refreshToken
	} from '@/utils/login.js'
	import {
		securityKey
	} from '@/config/config.js'

	import {
		baseURL
	} from '@/config/config.js'

	var app = getApp();

	export default {
		data() {
			return {
				aaa: "",
				code: {
					src: "",
					value: "",
					len: 4,
					type: "text"
				},
				uInfo: {
					username: "biz",
					password: "123456",
					code: "",
					randomStr: "",
				},
			};
		},
		onLoad(option) {
		},
		async onShow() {
			 this.refreshCode();
			 uni.showToast({
			 	title: '请登录',
			 	duration: 2000,
			 	icon: 'none'
			 });
		},
		computed: {
			regions: function() {
				return this.length * this.width
			}
		},
		methods: {
			login(e) {

				var that = this;
				if (this.uInfo.username === '') {
					uni.showToast({
						title: '用户名不能为空',
						duration: 3000,
						icon: "none"
					});
					return;
				}
				if (this.uInfo.password === '') {
					uni.showToast({
						title: '密码不能为空',
						duration: 3000,
						icon: "none"
					});
					return;
				}
				if (this.uInfo.code === '') {
					uni.showToast({
						title: '验证码不能为空',
						duration: 3000,
						icon: "none"
					});
					return;
				}
				const user = util.encryption({
					data: that.uInfo,
					key: securityKey,
					param: ['password']
				});
				return new Promise((resolve, reject) => {
					loginByUsername(user.username, user.password, user.code, user.randomStr).then(response => {
						if (response.data.user) {
							app.setAuthorization('Bearer ' + response.data.access_token);
							app.setUserId(response.data.user.id);
							app.setUsername(response.data.user.username);
							app.globalData.userInfo = response.data.user;
							app.loginSetStorage(response.data);
							uni.showToast({
								title: '登录成功',
								duration: 3000,
								icon: "none"
							});
							setTimeout(function() {
								uni.reLaunch({
									url: "../../pages/index/index",
								});
							}, 3000)
						} else {
							this.refreshCode();
							uni.showToast({
								title: response.data.message,
								duration: 5000,
								icon: 'none'
							})
						}
						// resolve();
					}).catch(error => {
						this.refreshCode();
						uni.showToast({
							title: '系统未知错误,请反馈给管理员',
							duration: 5000
						});
					})
				})
			},
			/**
			 * 刷新验证码
			 */
			refreshCode: function() {
				this.uInfo.randomStr = util.randomLenNum(this.code.len, true)
				if (this.code.value === 'text') {
					this.code.value = randomLenNum(this.code.len)
				} else {
					getCaptcha(this.uInfo.randomStr).then(res => {
						const arrayBuffer = new Uint8Array(res.data);
						const base64 = "data:image/png;base64," + uni.arrayBufferToBase64(
							arrayBuffer);
						this.$set(this.code, 'src', base64.replace(/[\r\n]/g, ""));
					})
				}
			},
		}
	};
</script>
<style lang="scss">
	page {
		background: #ffffff;
	}

	.topbg {
		background: url(../../static/loginbg.png) no-repeat bottom #4d9af1;
		background-size: 100%;
		width: 100vw;
		height: 25vh;
		display: flex;
		justify-content: center;
		align-items: center;
		padding-bottom: 8vh;
		margin-bottom: 10vh;

		.logo {
			width: 15vw;
			height: 15vw;
			overflow: hidden;

			.img {
				width: 15vw;
				height: 15vw;
				border-radius: 15vw;
			}
		}
	}

	.iptbox {
		width: 80%;
		margin: auto;
		height: 6vh;
		line-height: 6vh;
		border: $color-hui-e 2rpx solid;
		border-radius: 6vh;
		box-sizing: border-box;
		padding: 0 20rpx;
		margin-bottom: 40rpx;

		.ipt {
			height: 6vh;
			line-height: 6vh;
			font-size: 24rpx;

		}
	}


	.iptboxcode {
		width: 80%;
		margin: auto;
		height: 6vh;
		line-height: 6vh;
		border: #eeeeee 1px solid;
		border-radius: 6vh;
		box-sizing: border-box;
		padding: 0 10px;
		margin-bottom: 20px;
	}

	.iptboxcode input {
		width: 70%;
		border: 1px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
		font-size: 24rpx;
		height: 38px;
		border: none;

	}

	.iptboxcode img {
		width: 20%;
		margin-left: 10px;
		margin-top: 5px;
	}


	.btn {
		width: 80%;
		margin: auto;
		height: 6vh;
		line-height: 6vh;
		text-align: center;
		border-radius: 6vh;
		background-color: #4d9af1;
		color: #ffffff;
		margin-top: 10vh;
		border: none;
	}

	.nav {
		width: 80%;
		margin: auto;
		height: 6vh;
		line-height: 6vh;
		text-align: center;
		margin-top: 5vh;
		border: none;
	}
</style>
