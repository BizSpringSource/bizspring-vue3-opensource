<template>
	<view class="content">
		<!--管理端首页头部-->
		<view class="header">
			<view class="w100 box-si-border padd-3 flex-row counts-box">
				<navigator url="../../order/order/list?typeIndex=1" class="count" >
					<text class="num">{{countPendingPayment}}</text>
					<text class="name">待付款</text>
				</navigator>
				<navigator url="../../order/order/list?typeIndex=2" class="count" >
					<text class="num">{{countPaymentOrder}}</text>
					<text class="name">待发货</text>
				</navigator>
				<navigator url="../../order/order/list?typeIndex=10" class="count" >
					<text class="num">{{countPaymentOrder}}</text>
					<text class="name">支付订单</text>
				</navigator>
				<navigator url="../../upms/favorite/list?typeIndex=0"  class="count" >
					<text class="num">{{goodsFavoriteCount}}</text>
					<text class="name">收藏商品</text>
				</navigator>
				<navigator url="../../upms/favorite/list?typeIndex=1"  class="count" >
					<text class="num">{{storeFavoriteCount}}</text>
					<text class="name">收藏店铺</text>
				</navigator>
			</view>
			<view class="shrink">
			</view>
		</view>
		<!--body内容部分-->
		<view class="w100 box-si-border padd-3 body pd-t-30" style="background: #f5f5f5">
			<!--公告-->
			<uni-notice-bar scrollable single="true" v-if="gonggao" :text="gonggao" showIcon="true" background-color="#f5f5f5" :speed="speed" show-close></uni-notice-bar>
			<!--主导航-->
			<view class="navlis">
				<swiper class="swiper" :indicator-dots="false">
					<swiper-item v-for="(item,index) in nav_list" :key="index">
						<uni-grid :column="5" :highlight="true" @change="change" :showBorder="false">
							<uni-grid-item v-for="(val, k) in item" :index="k" :key="k">
								<view class="grid-item-box" :data-url="val.url" @tap="navHandler">
									<image :src="val.icon" class="image" mode="aspectFill"/>
									<text class="text">{{val.title}}</text>
								</view>
							</uni-grid-item>
						</uni-grid>
					</swiper-item>
				</swiper>
			</view>
			
			<!--店铺数据-->
			<view class="w100 border-r-10 bg_white box-si-border m-top-20" style="padding: 15rpx;">
				<view class="flex-row ai-c juc-sb">
					<text class="title font-size-16">店铺数据</text>
				</view>
				<view class="flex-row ai-c store_count">
					<view   class="item" >
						<text class="name">支付金额</text>
						<text class="num">{{sumThatdayPaymentTotal}}</text>
						<text class="yester">昨日: {{sumYesterdayPaymentTotal}}</text>
					</view>
					<view  class="item" >
						<text class="name">今日下单</text>
						<text class="num">{{countThatdayPlaceOrder}}</text>
						<text class="yester">昨日: {{countYesterdayPlaceOrder}}</text>
					</view>
					<view  class="item" >
						<text class="name">支付金额</text>
						<text class="num">{{countThatdayPlaceOrderMember}}</text>
						<text class="yester">昨日: {{countYesterdayPlaceOrderMember}}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		index		
	} from '@/api/upms/profile.js'
	import {
		index as merchant_index   //商品收藏 收藏店铺
	} from '@/api/upms/index.js'
	import {
		count as order_count,
	} from '@/api/order/order.js'
	
	
var app = getApp();

export default {
	data() {
		return {
			userInfo:app.globalData.userInfo,
			speed: 50,
			gonggao: '价格即将上调，当前优惠不容错过',
			nav_list: [
				[
					{
						title: '主页',
						url: '../../store/store/store',
						icon: '/static/images/index_icons/shouye.png'
					},
					{
						title: '商品',
						url: '../../goods/goods/list',
						icon: '/static/images/index_icons/shangpin.png'
					},
					{
						title: '店铺商品分类',
						url: '../../store/store_goods_category/list',
						icon: '/static/images/index_icons/fenlei.png'
					},
					{
						title: '订单',
						url: '../../order/order/list',
						icon: '/static/images/index_icons/dingdan.png'
					},
					{
						title: '配送方式',
						url: '../../store/shipping_method/list',
						icon: '/static/images/index_icons/peisong.png'
					},
				]
			],			
			store:null,
			user:null,
			
			countPendingPayment:0,//待支付
			countPaymentOrder:0,//支付订单
			sumThatdayPaymentTotal:0,//今日支付
			sumYesterdayPaymentTotal:0,//昨日支付
			countThatdayPlaceOrder:0,//今日下单
			countYesterdayPlaceOrder:0,//昨日下单
			countThatdayPlaceOrderMember:0,//今日下单客户
			countYesterdayPlaceOrderMember:0,//昨日下单客户
			
			goodsFavoriteCount:0,//商品收藏
			storeFavoriteCount:0,//店铺收藏
			selectDate:null,

		};
	},
	components: {
	},
	 onLoad(options) {
	},
	onShow() {
		var uInfo = uni.getStorageSync('userInfoStorage');
		if (uInfo) {
			this.index_profile();
		}
		this.index_load();
	},
	methods: {
		/**
		 * 详情
		 */
		index_profile: function() {
			uni.showLoading({
				title: 'Loading...'
			})
			index().then(response => {
				uni.hideLoading();
				this.store = response.data.data.store;
				this.user = response.data.data.merchant;
		
			}).catch(error => {
				uni.hideLoading();
				console.error("error", error);
			})
		},
		
		
		index_load: function() {
			//商品收藏 收藏店铺
			merchant_index().then(response => {
				let data = response.data.data;
				this.goodsFavoriteCount = data.goodsFavoriteCount?data.goodsFavoriteCount :0;
				this.storeFavoriteCount = data.storeFavoriteCount?data.storeFavoriteCount :0;
			}).catch(error => {
				console.error("error", error);
			})
			
			order_count().then(response => {
				let data = response.data.data;
				this.countPendingPayment = data.countPendingPayment?data.countPendingPayment:0;
				this.countPaymentOrder = data.countPaymentOrder?data.countPaymentOrder :0;
				this.sumThatdayPaymentTotal = data.sumThatdayPaymentTotal?data.sumThatdayPaymentTotal :0;
				this.sumYesterdayPaymentTotal = data.sumYesterdayPaymentTotal?data.sumYesterdayPaymentTotal :0;
				this.countThatdayPlaceOrder = data.countThatdayPlaceOrder?data.countThatdayPlaceOrder :0;
				this.countYesterdayPlaceOrder = data.countYesterdayPlaceOrder?data.countYesterdayPlaceOrder :0;
				this.countThatdayPlaceOrderMember = data.countThatdayPlaceOrderMember?data.countThatdayPlaceOrderMember :0;
				this.countYesterdayPlaceOrderMember = data.countYesterdayPlaceOrderMember?data.countYesterdayPlaceOrderMember :0;
					
			}).catch(error => {
				console.error("error", error);
			})
			
		},
		change(e){
			this.selectDate=e.fulldate;
			if(this.selectDate){
				uni.navigateTo({
					url:'/marketing/activity/activity_index?selectDate='+this.selectDate
				})
			}
		},
		navHandler(e){
			uni.navigateTo({
				url:e.currentTarget.dataset.url
			})
		},
	}
};
</script>
<style lang="scss">
page {
	background: #f5f5f5;
}

.header {
	background: $default-theme-color;
	overflow: hidden;
	
	.storeinfo {
		width: 100%;
		box-sizing: border-box;
		padding: 56rpx 3%;
		color: #ffffff;
		position: relative;
		
		.logo {
			width: 80rpx;
			height: 80rpx;
			border-radius: 80rpx;
			overflow: hidden;
			margin-right: 20rpx;
			
			.img {
				width: 80rpx;
				height: 80rpx;
				border-radius: 80rpx;
			}
		}
		
		.info {
			font-size: 23rpx;
			
			.type {
				margin-left: 20rpx;
				background: #ffffff;
				color: $default-theme-color;
				padding: 2rpx 20rpx;
				border-radius: 20rpx 0 20rpx 0;
			}
		}
		
		.right_menu {
			position: absolute;
			right: 15rpx;
			top: 45rpx;
			
			.iconfont {
				font-size: 45rpx !important;
			}
		}
	}
	
	.counts-box {
		flex-wrap: wrap;
		overflow: hidden;
		.count {
			width: 20%;
			text-align: center;
			display: flex;
			flex-direction: column;
			color: #ffffff;
			padding: 20rpx 0;
			margin-bottom: 10rpx;
			
			.num {
				font-size: 28rpx;
				font-weight: 500;
			}
			
			.name {
				font-size: 23rpx;
				font-weight: normal;
				color: #ececec;
			}
		}
	}
	
	.shrink {
		text-align: center;
		color: #ffffff;
		margin-bottom: 60rpx;
		
		.iconfont {
			font-size: 50rpx !important;
		}
	}
}

.body {
	border-radius: 60rpx;
	margin-top: -50rpx;
	overflow: hidden;
}

.img-angle{
	width:50rpx;
	height:50rpx;
}

.uni-noticebar {
	padding: 0;
}

.grid-item-box {
	text-align: center;
	font-size: 24rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	.image {
		width: 58rpx;
		height: 58rpx;
	}
}
.swiper{
	height: 160rpx;
}
.uni-grid-item{
	margin-bottom: 20rpx !important;
}

.scroll-view_w{
	width: 100%;
	overflow: hidden;
	white-space: nowrap;
}
.scroll-view-item{
	border: #eeeeee 2rpx solid;
	border-radius: 20rpx;
	padding: 20rpx;
	width: 177rpx;
	text-align: center;
	display:inline-block;
	margin-right: 15rpx;
	background: #ffffff;
	font-size: 24rpx;
	.img{
		width: 50rpx;
		height: 50rpx;
		margin-right: 20rpx;
	}
}
.store_count{
	.item{
		margin-top:30rpx;
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		line-height: 46rpx;
		border-right: #eeeeee 2rpx solid;
		.name{
			font-size: 23rpx;
			color: #8f8f94;
		}
		.num{
			font-size: 26rpx;
			font-weight: 600;
		}
		.yester{
			font-size: 23rpx;
			color: #8f8f94;
		}
	}
	.item:last-child{
		border: none;
	}
}
.uni-calendar--hook{
	width: 100%;
	box-sizing: border-box;
	overflow: hidden;
}


</style>
