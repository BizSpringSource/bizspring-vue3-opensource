<template>
	<view>
	<uni-nav-bar :fixed="true" title="登录" background-color="#FFFFFF" :statusBar="true" :border="false"  style="border: none;margin: 0px;padding: 0px;" >
		<template v-slot:left>
			<image @click="backHome" style="width: 48rpx;height: 48rpx;" src="/static/images/icons/btn-tabs_01.png"></image>
		</template>
	</uni-nav-bar>
	
	<uni-forms ref="form" label-width="70px" :rules="rules" :model="uInfo" validate-trigger="bind"
		err-show-type="toast">
		<view class="phone_login">
			<view class="phone_login_ul">
				<view class=" flex-row border-bottom1 ai-c pd-t-20 line-height-90">
					<uni-forms-item required label="用户名:" class="uni-item" name="username">
						<input class="uni-item-value"  type="text" v-model="uInfo.username" placeholder="请输入用户名"></input>
					</uni-forms-item>
				</view>
				<view class=" flex-row border-bottom1 ai-c pd-t-20 line-height-90">
					<uni-forms-item required label="密码:" class="uni-item" name="password">
						<input class="uni-item-value"  maxlength="20" password type="text" v-model="uInfo.password"
							placeholder="请输入密码"></input>
					</uni-forms-item>
				</view>
				<view class="phone_login_ul_li yzm  flex-row ai-c line-height-90" style="display:flex;flex-direction:row;">
					<uni-forms-item required label="验证码:" class="uni-item" name="code">
						<input class="uni-item-value" type="text" v-model="uInfo.code" id="code" placeholder="请输入验证码"
							maxlength="10"></input>
					</uni-forms-item>
					<image
						style=" height: 48px;line-height: 48px;width: calc(40% - 5px);font-weight: bold;text-align: center;margin-top: 0px;"
						:src="code.src" @click="refreshCode"></image>
				</view>
				<view class="phone_login_ul_li ip_btn">
					<button style="text-align: center;background-color: #FF0000;color: #FFFFFF;"
						@click="vueLogin">登录</button>
				</view>
			</view>
		</view>
	</uni-forms>
	</view>
</template>

<script>
	import {
		loginByUsername,
	} from '@/utils/login.js'
	import util from '@/utils/util'
	import {
		securityKey
	} from '@/config/config.js'
	import bizRequest from '@/utils/request.js'

	var app = getApp();
	export default {
		data() {
			return {
				username: "",
				password: "",
				code: {
					src: "/code",
					value: "",
					len: 4,
					type: "text"
				},
				userInfo: {},
				uInfo: {
					username: "BizSpring",
					password: "111111",
					code: "",
					randomStr: "",
				},

				rules: {
					username: {
						rules: [{
							required: true,
							errorMessage: '用户名不能为空'
						}]
					},
					password: {
						rules: [{
							required: true,
							errorMessage: '密码不能为空'
						}]
					},
					code: {
						rules: [{
							required: true,
							errorMessage: '验证码不能为空'
						}]
					},
				},
			};
		},
		components: {},
		props: {},
		onLoad() {
			uni.showToast({
				title: '请登录',
				duration: 2000,
				icon: 'none'
			});
		},
		onReady() {
			this.$refs.form.setRules(this.rules);
		},
		onShow: function() {
			this.refreshCode();
		},
		onShareAppMessage: function() {
			return {
				title: 'BizSpring跨境电商',
				path: '/pages/index/index',
				success: function(res) { // 分享成功
				},
				fail: function(res) { // 分享失败
				}
			};
		},
		methods: {
			refreshCode: function() {
				var that = this;
				that.uInfo.randomStr = util.randomLenNum(that.code.len, true)
				if (that.code.value === 'text') {
					that.code.value = randomLenNum(that.code.len)
				} else {
					bizRequest({
						url: '/code?randomStr=' + that.uInfo.randomStr,
						method: 'GET',
						headers: app.globalData.headers,
						responseType: "arraybuffer",
					}).then(res => {
						const arrayBuffer = new Uint8Array(res.data);
						const base64 = "data:image/png;base64," + uni.arrayBufferToBase64(
							arrayBuffer);
						this.$set(this.code, 'src', base64.replace(/[\r\n]/g, ""));
					})
				}
			},
			backHome:function(){
				uni.reLaunch({
					url: "../../pages/index/index"
				})	
			},
			vueLogin: function() {
				this.$refs['form'].validate().then(res => {
					var that = this;
					const user = util.encryption({
						data: that.uInfo,
						key: securityKey,
						param: ['password']
					});
					return new Promise((resolve, reject) => {
						loginByUsername(user.username, user.password, user.code, user
							.randomStr).then(
							response => {
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
											url: "../../pages/member/index",
										});
									}, 2000)
								} else {
									this.refreshCode();
									uni.showToast({
										title: response.data.message,
										duration: 5000,
										icon: 'none'
									})
								}
							}).catch(error => {
							this.refreshCode();
							uni.showToast({
								title: '系统未知错误,请反馈给管理员',
								duration: 2000
							});
						})
					})
				}).catch(err => {
					console.log('err', err);
				})

			}
		}
	};
</script>
<style scoped>
	/deep/ .uni-date-x--border {
		border: none;
	}
	/deep/ .uni-forms-item {
		position: relative;
		display: flex;
		margin-bottom: 20rpx;
		flex-direction: row;
		width: 100%;
	}	
	/deep/ .uni-forms-item__label {
	    display: flex;
	    flex-direction: row;
	    align-items: center;
	    text-align: left;
	    font-size: 23rpx;
	    color: #606266;
	    height: 36px;
	    padding: 0 12px 0 0;
	    vertical-align: middle;
	    flex-shrink: 0;
	    box-sizing: border-box;
	}
	
</style>
<style>
	.content {
		font-size: 26rpx;
	}

	.uni-forms-item {
		position: relative;
		display: flex;
		margin-bottom: 22px;
		flex-direction: row;
		width: 100%;
	}

	.uni-item {
		width: 100%;
	}

	.coltitle {
		width: 200rpx;
	}

	.uni-item-value {
		line-height: 72rpx;
		height: 72rpx;
		width: 100%;
		color: #666666;
	}

	.height-100 .iconfont[data-v-0456bfcd] {
		font-size: 28rpx !important;
		color: #a2a2a2;
		margin-left: 10px;
	}

	/*登陆-注册*/
	.phone_hd .home {
		top: 10px;
	}

	.phone_login {
		background-color: #fff;
		width: 96%;
		overflow: hidden;
		padding: 0px 2%;
		margin-top: 10px;
		margin-bottom: 15px;
	}

	.phone_login_ul_li {
		margin: 10px 0px;
		display: block;
		font-size: 23rpx;
		color: #666;
		overflow: hidden;
		line-height: 30px;
	}

	.phone_login_ul_li.ip {
		border: 1px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
	}

	.phone_login_ul_li.ip input {
		width: 99%;
		border: none;
		height: 38px;
		font-size: 23rpx;
		text-indent: 10px;
		border-radius: 2px;
	}

	.phone_login_ul_li.ip_btn input {
		width: 100%;
		border-radius: 2px;
		background-color: green;
		color: #fff;
		font-size: 24px;
		border: none;
		height: 40px;
		line-height: 40px;
		-webkit-appearance: none;
	}

	.phone_login_ul_li.wx_btn input {
		width: 100%;
		border-radius: 2px;
		background-color: green;
		color: #fff;
		font-size: 24rpx;
		border: none;
		height: 40px;
		line-height: 40px;
		-webkit-appearance: none;
	}

	.phone_login_ul_li.qt_btn {
		text-align: center;
	}

	.phone_login_ul_li.qt_btn image {
		width: 278rpx;
		height: 278rpx;
		margin: 39rpx;
		font-size: 24rpx;
		border: none;
		-webkit-appearance: none;
	}

	.phone_login_ul_li.yzm img {
		margin-left: 10px;
		float: left;
		margin-top: 5px;
	}

	.phone_login_ul_li.bd_red {
		border-color: #EB512D;
	}

	.phone_login_ul_li.yz {
		color: #EB512D;
		text-indent: 10px;
		margin: 0px;
	}

	.phone_login_ul.yz_step2_li {
		width: 100%;
		float: left;
	}

	.phone_login_ul.yz_step2_li.sele_li {
		width: 50%;
	}

	.phone_login_ul.yz_step2 .tel_yz {
		width: 50%;
	}

	.phone_login_ul_li select {
		padding: 3px;
		font-size: 21rpx;
		width: 90%;
		border: 1px solid #ddd;
		float: left;
		color: #666;
	}

	.phone_login_ul_li select option {
		padding: 3px;
	}

	.phone_login_ul_li.tel_yz a {
		width: 90%;
		text-align: center;
		height: 30px;
		line-height: 30px;
		background-color: #ccc;
		border-radius: 2px;
		float: left;
		background-image: -moz-linear-gradient(top, #eeeeee, #dddddd);
		border: 1px solid #ddd;
	}

	.deal {
		width: 96%;
		margin-top: 45px;
		font-size: 21rpx;
		line-height: 24px;
		padding: 0px 2%;
		color: #666;
	}

	.phone_login_ul_li.text textarea {
		width: 98%;
		border: 1px solid #ddd;
		background-color: #fff;
		font-size: 23rpx;
		height: 50px;
		resize: none;
	}

	.phone_login_ul_li.score .red {
		font-size: 23rpx;
		padding: 0px 2px;
	}

	.phone_login_ul_li .code {
		width: 45%;
		border: 1px solid #bbb;
		background-color: #ccc;
		height: 38px;
		line-height: 38px;
		text-align: center;
		float: right;
		border-radius: 2px;
		color: #fff;
	}

	.phone_login_ul_li.att {
		border: 1px solid #EB512D;
		color: #EB512D;
		font-size: 21rpx;
		border-radius: 2px;
		padding: 5px;
		padding-left: 30px;
	}

	.code_img {
		margin-top: 2px;
		width: 100%;
		height: 38px;
		background-color: #fdfdfd;
		border: 1px solid #f0f0f0;
		color: #333;
		font-size: 23rpx;
		font-weight: bold;
		letter-spacing: 5px;
		line-height: 38px;
		text-indent: 5px;
		text-align: center;
	}
</style>