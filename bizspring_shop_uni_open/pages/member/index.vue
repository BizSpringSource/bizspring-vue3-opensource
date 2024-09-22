<template>
	<view class="page">
		<view class="userinfo">
			<view class="userlogo">
				<image :src="userInfo.avatar" @error="onErrorImg" class="userinfo-avatar" mode="cover">
				</image>
				<view class="rbox">
					<view class="nick">{{userInfo.username}}</view>
					<navigator url="../../upms/login/index" :style="'display: ' + authHidden + ';'">
						<button v-show="canIUse" style="line-height:38rpx;width:150rpx;">登 录
						</button>
					</navigator>
					<view class="jindu">
						<text class="jd1"></text>
					</view>
				</view>
			</view>
			<view class="line2">
				<navigator class="li" url="../../upms/goods_favorite/list">
					<text v-if="datas.favoriteGoodsSize" class="num">{{datas.favoriteGoodsSize}}</text>
					<text class="tit">商品收藏</text>
				</navigator>
				<navigator class="li" url="../../upms/store_favorite/list">
					<text v-if="datas.favoriteStoreSize" class="num">{{datas.favoriteStoreSize}}</text>
					<text class="tit">店铺收藏</text>
				</navigator>
			</view>
		</view>
		<!--高级vip-->
		<view class="memberbox m_vip">
			<view class="m_left">
				<image style="width:40rpx ;height: 48rpx;"
					src="https://qiniuio.bizspring.cn/mobile/static/images/icon-vip.png" mode="widthFix">
				</image>
				<text style="color: #805223;">购物省钱</text>
			</view>
		</view>
		<!--订单详情-->
		<view class="dingdan">
			<view class="qbdd mt10">
				<text class="wodetit">我的订单</text>
				<navigator class="more" url="../../order/order/list?currentTab=0&otype=" hover-class="none">
					<text>查看全部订单</text>
					<text class="iconfont icon-youjiantou"></text>
				</navigator>
			</view>
			<view class="sc df tc bte ">
				<navigator class="icon" url="../../order/order/list?currentTab=1&otype=order_submit" hover-class="none">
					<text class="iconfont icon-daifukuan ic1"></text>
					<text class="text">待付款</text>
				</navigator>
				<navigator class="icon" url="../../order/order/list?currentTab=2&otype=order_pay" hover-class="none">
					<text class="iconfont icon-daifahuo"></text>
					<text class="text">待发货</text>
				</navigator>
				<navigator class="icon" url="../../order/order/list?currentTab=3&otype=order_shipping"
					hover-class="none">
					<text class="iconfont icon-daishouhuo"></text>
					<text class="text">待收货
					</text>
				</navigator>
			</view>
		</view>
		<view class="qita">
			<navigator class="list" url="../../upms/address/address">
				<text class="tit">收货地址</text>
				<text class="iconfont icon-youjiantou"></text>
			</navigator>
		</view>
	</view>
</template>
<script>
	import {
		show_store_center,
		load_member
	} from '@/api/upms/profile.js'

	const date = new Date();
	const years = [];
	const months = [];
	const days = [];
	var app = getApp();
	export default {
		data() {
			return {
				datas: [],
				userInfo: [],
				canIUse: true,
				authHidden: 'block',
				years,
				year: date.getFullYear(),
				months,
				month: 2,
				days,
				day: 2,
				value: [9999, 1, 1],
				isDaytime: true,
				mainheight: "",
				showStoreCenter:false
			};
		},
		components: {},
		props: {},
		onLoad(options) {
		},
		onShow: function(options) {
			var uInfo = uni.getStorageSync('userInfoStorage');
			if (uInfo) {
				this.userInfo = uInfo.user;
				this.load_member(uInfo.user.id);
				this.authHidden = 'none';
				this.canIUse = false;
			}else{
				setTimeout(function() {
					uni.reLaunch({
						url: '../../upms/login/index'
					});
				}, 100);	
			}
			let that=this;
			uni.getSystemInfo({				
				success(res) {
					that.mainheight = res.windowHeight - 180;
				}
			});
			this.load_show_store_center();
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
			//刷新Token	
			load_show_store_center() {
				show_store_center().then(response => {
					this.showStoreCenter = response.data.data.showStoreCenter;
				})
			},
			load_member(id) {
				load_member({
					id: id
				}).then(response => {
					this.userInfo = response.data.data.member;
				})
			},
			
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_thumbnail.jpg");
				this.$set(item, 'logo', "/static/images/default_thumbnail.jpg");
			},

		}
	};
</script>
<style>
	.me {
		height: 100%;
	}

	.userinfo {
		position: relative;
		width: 100%;
		height: 350rpx;
		background: #fd462f;
		color: #fff;
	}

	.userinfo .system {
		position: absolute;
		right: 40rpx;
		top: 20rpx;
	}

	.userinfo .system .iconfont {
		font-size: 28rpx !important;
	}

	.userinfo .userlogo {
		display: flex;
		align-items: center;
		margin: auto;
	}

	.userlogo .nick {
		font-size: 23rpx;
		font-weight: bold;
		margin-bottom: 15rpx;
	}

	.userlogo .rbox {
		overflow: hidden;
		width: calc(100% - 230rpx);
	}

	.userlogo .tag {
		width: 100%;
		overflow: hidden;
		height: 50rpx;
		margin-bottom: 10rpx;
	}

	.userlogo .tag text {
		background: rgba(215, 10, 10, 0.63);
		color: #ffffff;
		font-size: 20rpx;
		border-radius: 30rpx;
		padding: 6rpx 15rpx;
		margin-right: 20rpx;
	}

	.userlogo .jindu {
		width: 100%;
		height: 4rpx;
		background: rgba(255, 255, 255, 0.36);
		border-radius: 6rpx;
		overflow: hidden;
	}

	.userlogo .jindu .jd1 {
		width: 30rpx;
		background: #ffffff;
		display: block;
		height: 4rpx;
	}

	.userinfo-avatar {
		display: block;
		width: 120rpx;
		height: 120rpx;
		margin: 20rpx;
		margin-left: 50rpx;
		border-radius: 50%;
		border: 2px solid #fff;
		box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.2);
	}

	.userinfo-nickname {
		color: #fff;
		font-size: 23rpx;
	}

	.userinfo .line2 {
		width: 100%;
		margin: auto;
		display: flex;
		overflow: hidden;
		margin-top: 20rpx;
	}

	.userinfo .line2 .li {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		border-right: #ffffff 2rpx solid;
	}

	.userinfo .line2 .li .num {
		font-size: 23rpx;
		font-weight: bold;
	}

	.userinfo .line2 .li .tit {
		font-size: 23rpx;
	}

	.userinfo .line2 .li:last-child {
		border: none;
	}

	.userinfo .line2 .li .iconfont {
		font-size: 50rpx !important;
		margin-bottom: 10rpx;
	}

	.sc {
		background: #fff;
		width: 100%;
		font-size: 21rpx;
		line-height: 16px;
		color: #666;
		padding: 4% 0;
	}

	.sc image {
		width: 24px;
		height: 24px;
		display: inline-block;
	}

	.bte {
		border-top: 1px solid #eee;
		display: flex;
	}

	.bte .icon {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.bte .icon .iconfont {
		font-size: 50rpx !important;
		color: #ff0000;
		margin-bottom: 20rpx;
	}

	.bte .icon .ic1 {
		font-size: 56rpx !important;
	}

	.bte .icon .ic5 {
		font-size: 44rpx !important;
	}

	.bte .icon .text {
		font-size: 21rpx;
		color: #585757;
	}

	.qbdd {
		background: #fff;
		width: 92%;
		font-size: 21rpx;
		color: #666;
		padding: 4%;
		border-bottom: 1px solid #eee;
		display: flex;
		justify-content: space-between;
	}

	.qbdd .iconfont {
		font-size: 20rpx !important;
		margin-left: 10rpx;
	}

	.c33 {
		float: left;
		color: #000
	}

	contact-button {
		position: relative;
		left: -50px;
		opacity: 0;
	}

	.dingdan {
		width: 94%;
		margin: auto;
		overflow: hidden;
		background: #ffffff;
		border-radius: 20rpx;
	}

	.dingdan .wodetit {
		font-size: 23rpx;
		font-weight: bold;
	}

	.qita {
		width: 94%;
		margin: auto;
		margin-top: 20rpx;
		background: #ffffff;
		border-radius: 20rpx;
	}

	.qita .list {
		display: flex;
		justify-content: space-between;
		border-bottom: #dddddd 2rpx solid;
		padding: 30rpx 20rpx;
	}

	.qita .list:last-child {
		border: none;
	}

	.qita .list .tit {
		font-size: 23rpx;
		color: #585757;
	}

	.qita .list .iconfont {
		font-size: 22rpx !important;
		color: #b3b1b1;
	}

	/*猜你喜欢*/
	.cainixihuan {
		width: 94%;
		overflow: hidden;
		margin: auto;
	}

	.cainixihuan .biaoti {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 20rpx 0;
	}

	.cainixihuan .biaoti text {
		display: block;
	}

	.cainixihuan .biaoti .line {
		height: 2px;
		border-bottom: #b6b6b6 2rpx solid;
		width: 100rpx;
	}

	.cainixihuan .biaoti .wenzi {
		font-size: 24rpx;
		color: #7b7b7b;
		margin: 0 8rpx;
	}


	.phone_floor {
		width: 100%;
		overflow: hidden;
		margin: auto;
		padding-top: 20rpx;
		background: none !important;
		border: none !important;
	}

	.phone_floor h1 {
		padding: 10px;
		font-weight: normal;
		overflow: hidden;
		font-size: 23rpx;
	}

	.phone_floor h1 .fl {
		border-left: 3px solid #e22c37;
		padding-left: 8px;
		height: 12px;
		line-height: 12px;
		float: left;
		margin-top: 3px;
	}

	.phone_floor h1 .fr {
		float: right;
	}

	.phone_floor h1 .fr a {
		color: #666;
		font-size: 20rpx;
	}

	.piclist {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		justify-content: space-between;
	}

	.piclist_nav {
		width: calc(50% - 10rpx);
		height: 500rpx;
		border-radius: 20rpx;
		background-color: #ffffff;
		margin-bottom: 20rpx;
	}

	.piclist_nav_img {
		width: 100%;
		height: 362rpx;
	}

	.re_name {
		font-size: 23rpx;
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 15rpx;
	}

	.price {
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 15rpx;
	}

	.price .pr1 {
		font-size: 26rpx;
		color: #ff0000;
	}

	.price .pr2 {
		font-size: 20rpx;
		color: #959595;
		text-decoration: line-through;
		margin-left: 14rpx;
	}

	.memberbox {
		width: 94%;
		margin: auto;
		margin-top: 20rpx;
		background: #ffffff;
		border-radius: 20rpx;
		box-sizing: border-box;
	}

	.m_vip {
		display: flex;
		position: relative;
		margin-top: -45rpx;
		padding: 20rpx;
		margin-bottom: 20rpx;
		align-items: center;
		justify-content: space-between;
		box-shadow: 0 0 10rpx rgba(187, 187, 187, 0.67);
	}

	.m_vip .m_left {
		display: flex;
		align-items: center;
		font-size: 20rpx;
	}

	.m_vip .m_left text {
		margin-left: 15rpx;
	}

	.m_vip .m_right {
		background: #2a2724;
		font-size: 21rpx;
		border-radius: 30rpx;
		color: #e3c49e;
		padding: 8rpx 15rpx;
	}

	.m_vip .m_right .iconfont {
		font-size: 20rpx !important;
	}

	.m_tuiguang {
		display: flex;
		padding: 20rpx;
		justify-content: space-between;
		margin-bottom: 20rpx;
	}

	.m_tuiguang .m-tg-left {
		display: flex;
		width: calc(80% - 0rpx);
		height: 120rpx;
	}

	.m_tuiguang .m-tg-left::after {
		width: 34rpx;
		height: 112rpx;
		background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAB5CAYAAAA54wGJAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyNpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQwIDc5LjE2MDQ1MSwgMjAxNy8wNS8wNi0wMTowODoyMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChNYWNpbnRvc2gpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjEyNEM5RTA3RTA3RjExRUFBREZBODRFRkVBOEMyQUQyIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjEyNEM5RTA4RTA3RjExRUFBREZBODRFRkVBOEMyQUQyIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MTI0QzlFMDVFMDdGMTFFQUFERkE4NEVGRUE4QzJBRDIiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MTI0QzlFMDZFMDdGMTFFQUFERkE4NEVGRUE4QzJBRDIiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4lN/TrAAAB+0lEQVR42uSZiW7DIAyGawutzZ6ge/933NEDL5WChCIOY5uMLEiordR+8fEbMIXr9eOUGTBPOjUOLMDiV/ZwGZB4IOM7TdYi88HseGIFAq3hwMYfg5XLqqRAxmqWlWghlRZhpwZxhZ3SG1gKW5Rp8yw7pqjFLm+yOHQHkgYIlsKGHi5Dwk2QLg6pBZaGS8q4OgQr2YBWk2gp6j9JCrSIusVClqg526jYZbCoku5JgS0qxQxIw9Zy10rpuqfsa6P/J0DSiLnVwrMGSIn3b/O8WNVyDJ2kQMp8ZkGxsofQKmHnGhQTcaMK9FKCYiUhlJlTDoqMrTNlZYC+1/oUKhw0X9OvDAly+lwDabVUBbBffuxXMY8fPMVQl3EVMvH0idbYx1CXAVFF7DEcopg+HNO6kvsB+DXPW8llSpy+cnL6WYDsWi4BvwOs1N6eKqtQCMttmU3rYW7hva9hXAtTW+ljmeI9BaLpc7DaFUHKuhfsqT0SQySfp7bxaYKVTrAggXH7ZTas1AWA9JoACyAToOmBE6zO2FC4XjF32QQImn4FK5uU6Z2D6b8V4vYMtZdnLSdY826ULIGmsunWVqhdpl4uq7qq8ZPieialq2y6JUUcw9Sl49gxHBtIu3J53Fo2qxQ6pg4PAqStallUKdRT2PvZ9Q4CpGPGUH2F/yvAAHPXmlutoVdOAAAAAElFTkSuQmCC);
		background-size: auto;
		background-size: 100% 100%;
		content: "";
	}

	.m_tuiguang .m-tg-left .item {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-between;
		padding: 15rpx 0;
		font-size: 22rpx;
		color: #b0743a;
	}

	.m_tuiguang .m-tg-r {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-between;
		padding: 15rpx 0;
		font-size: 22rpx;
		color: #b0743a;
	}

	.m_tuiguang .m-tg-r .iconfont {
		font-size: 28rpx !important;
	}

	.m_tuiguang .m-tg-r .tt {
		color: #1d1d1d !important;
	}

	.m-style1 {
		padding: 20rpx;
		margin-top: 20rpx;
	}

	.m-style1 .tit {
		font-size: 23rpx;
		font-weight: bold;
		border-bottom: #e2e2e2 2rpx solid;
		padding-bottom: 20rpx;
		margin-bottom: 20rpx;
	}

	.m-style1 .itemlist {
		width: 100%;
		display: flex;
		overflow: hidden;
		flex-wrap: wrap;
	}

	.m-style1 .itemlist .item {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 25%;
		font-size: 22rpx;
		margin-bottom: 20rpx;
		padding-top: 25rpx;
	}

	.m-style1 .itemlist .item .img {
		width: 30%;
		margin-bottom: 20rpx;

	}

	/*弹出样式*/
	.showbg {
		width: 100%;
		height: 100vh;
		background: rgba(0, 0, 0, 0.51);
		position: fixed;
		top: 0;
		left: 0;
		z-index: 15;
	}

	.showbox {
		width: 100%;
		height: calc(100vh - 330rpx);
		background: #ffffff;
		border-radius: 20rpx 20rpx 0 0;
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 16;
	}

	.showbox .guanbi {
		position: absolute;
		right: 20rpx;
		top: 20rpx;
		background: rgba(153, 153, 153, 0.2);
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		border-radius: 40rpx;
	}

	.showbox .biaoti1 {
		padding: 20rpx;
		font-size: 21rpx;
		font-weight: bold;
		text-align: center;
	}

	.showbox .shezhi {
		padding: 20rpx;
		width: 100%;
		overflow: hidden;
		box-sizing: border-box;
		display: flex;
		font-size: 22rpx;
		align-items: center;
	}

	.shezhi .left {
		width: 160rpx;
		text-align: left;
	}

	.shezhi .weui-input {
		width: calc(100% - 180rpx);
		border: rgba(241, 241, 241, 0.87) 2rpx solid;
		border-radius: 5rpx;
		padding: 10rpx;
		color: #9e9e9e;
		font-size: 22rpx !important;
	}

	.page-body {
		width: 100%;
		position: absolute;
		left: 0;
		bottom: 0;
		background: #ffffff;
		height: 300rpx;
		box-shadow: 0 0 20rpx #c7c7c7;
		z-index: 999;
	}

	.selected-date,
	.xb {
		color: #9e9e9e;
		font-size: 22rpx !important;
	}

	.queding {
		position: absolute;
		left: 3%;
		bottom: 120rpx;
		background: #ff0000;
		text-align: center;
		color: #ffffff;
		width: 94%;
		padding: 15rpx 0;
		border-radius: 40rpx;
		z-index: 20;
	}

	.shezhi .dizhi {
		width: calc(100% - 180rpx);
		color: #9e9e9e;
		display: flex;
		align-items: center;
		font-size: 22rpx !important;
	}
</style>
