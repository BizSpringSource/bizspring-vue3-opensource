<template>
	<view class="content">
		<!--管理端首页头部-->
		<view class="header">
			<!--店铺标题部分-->
			<view class="storeinfo flex-row ai-c">
			</view>
		</view>
	</view>
</template>

<script>
import {
	refreshToken
} from '@/utils/login.js'
import bizRequest from '@/utils/request.js'
import unifooter from '../../components/footer/uni-footer';
var app = getApp();

export default {
	data() {
		return {
			speed: 50,
			is_height:true,
			bg_color: '#fefefe',
		};
	},
	components: {
		unifooter
	},
	created() {
				const userInfoStorage = uni.getStorageSync('userInfoStorage');
				if (userInfoStorage) {
					let rf_token = userInfoStorage.refresh_token;
					refreshToken(rf_token).then(response => {
						if (response.data.user) {
							app.setAuthorization('Bearer ' + response.data.access_token);
							app.setUserId(response.data.user.id);
							app.setUsername(response.data.user.username);
							app.globalData.userInfo = response.data.user;
							app.loginSetStorage(response.data);
							uni.switchTab({
								url: '/pages/index/index'
							})
						} else {
							uni.removeStorageSync('userInfoStorage');
							app.setAuthorization(null);
							app.setUserId(null);
							app.setUsername(null);
							app.globalData.userInfo = null;						
							uni.reLaunch({
								url:'/pages/index/login'
							})
						}
						// resolve();
					}).catch(error => {
						console.error("App.vue.login==>error", error);
						uni.showToast({
								title: '系统未知错误,请反馈给管理员',
							duration: 2000
						});
					})
				}else{
					uni.removeStorageSync('userInfoStorage');
					app.setAuthorization(null, "");
					app.setUserId(null, "");
					app.setUsername(null, "");
					app.globalData.userInfo = null;
					uni.reLaunch({
						url:'/pages/index/login'
					})
				}
	        },
	onLoad(options) {
	},
	methods: {
	}
};
</script>
<style lang="scss">
page {
	background: #f5f5f5;
}

</style>
