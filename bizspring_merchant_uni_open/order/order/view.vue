<template>
	<view>
		<form @submit="formsubmit">
			<view class="choosebox">
				<view class="kongbai"></view>
				<view class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
					<!-- 发货状态 -->
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-20">
							<view class="coltitle"><text>编号 :</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{order.sn}}</text>
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-20 pd-t-10 line-height-90">
							<view class="coltitle"><text>状态 :</text></view>
							<text class="font-size-20 color-green font-weight-bold">{{msg["Order_Status_"+order.status]}}</text>
						</view>
					</view>
					<view class="kongbai"></view>
					<view v-if="order.shippingMethodName">
						<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
							<text class="font-weight-bold font-size-17">发货信息</text>
						</view>
						<view v-if="order.orderShipping" class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>运单号:</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.orderShipping.trackingNo}}</text>
							</view>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30  m-top-30 ">
								<view class="coltitle"><text>配送方式 :</text>
								</view>
								<text class="font-size-16 color-blue">{{order.shippingMethodName}}</text>
							</view>
						</view>
					</view>
					<view class="kongbai"></view>
					<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
						<text class="font-weight-bold font-size-17">收货信息</text>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 m-top-30">
							<view class="coltitle"><text>收货人 :</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{order.consignee||''}}</text>
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle">
								<text>地址 :</text>
							</view>
							<text>{{order.regionName}}{{order.address}}</text>
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle"><text>电话 :</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{order.phone||''}}</text>
						</view>
					</view>
					<view class="kongbai"></view>
					<view class="listbox bg_white w100 box-si-border padd-3 m-b-10">
						<view class="item flex-row"
							style="display: flex;justify-content: space-between;flex-wrap: wrap;">
							<view class="color-blue">
								{{order.memberName}}
							</view>
							<view>
								{{msg["Order_Type_" + order.type]}}
							</view>
							<view class="color-green">
								{{msg["Order_Status_"+order.status]}}
							</view>
						</view>
						<view  v-for="(itm,index) in order.orderItems" class="item flex-row">
							<view class="img">
								<image class="pic" :src="itm.thumbnail"></image>
							</view>
							<view class="proinfo">
								<view class="title">{{itm.name}}
								</view>
								<view class="juc-sb flex-row m-top-20 m-b-20 color-gray2" >
									<view><text v-if="itm.specifications&&itm.specifications.length>0">[</text><text v-for="(it,idx) in itm.specifications" >{{" "+it+" "}}</text><text  v-if="itm.specifications&&itm.specifications.length>0">]</text></view>
									<view>x {{itm.quantity}}</view>
								</view>
								<view class="tag color-gray2">￥{{itm.price}}</view>
							</view>
						</view>
						
						<view v-if="order.offsetAmount&&order.offsetAmount!=0" class="flex-row ai-c m-b-30 m-top-30"
							style="padding:0;display: flex;justify-content:flex-end;flex-wrap: wrap;">
							<view class="col-right">
								<text>调整金额 :</text>
								<text>{{order.offsetAmount}}</text>
							</view>
						</view>

						<view class="flex-row ai-c m-b-30 m-top-30"
							style="padding:0;display: flex;justify-content: space-between;flex-wrap: wrap;">
							<view>
								<text>运费:￥{{order.freight}}</text>
							</view>
							<view class="col-right">
								<text style="padding: 0rpx;"
									class="font-weight-bold color-red font-size-16">合计:￥{{order.price}}</text>
							</view>
						</view>
					</view>
					
					<view class="kongbai"></view>
					<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
						<text class="font-weight-bold font-size-17">订单记录</text>
					</view>
					<view v-for="(item,index) in order.orderLogs" class="box padd-3">
						<view class="flex-row ai-c m-b-20 m-top-30">
							<view class="coltitle m-r-20"><text>{{msg["OrderLog_Type_"+item.type]}} :</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{item.createdTime}}</text>
						</view>
					</view>
					<view class="kongbai"></view>
					<view class="footer" v-if="order.status=='PENDING_REVIEW'" >
						<view class="btn-add btn-two1" @tap="order_review(true)" >审核通过</view>
						<view class="btn-add btn-two2" @tap="order_review(false)" >审核不通过</view>
					</view>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	import {
		view,
		review
	} from '@/api/order/order.js'
	import {
		baseURL
	} from '@/config/config.js'

	var app = getApp();

	export default {
		data() {
			return {
			    msg:{
                    'Order_Status_PENDING_PAYMENT':'等待付款',
                    'Order_Status_PENDING_REVIEW':'等待处理',
                    'Order_Status_PENDING_SHIPMENT':'等待发货',
                    'Order_Status_SHIPPED':'已发货',
                    'Order_Status_RECEIVED':'已收货',
                    'Order_Status_COMPLETED':'已完成',
                    'Order_Status_FAILED':'已失败',
                    'Order_Status_CANCELED':'已取消',
                    'Order_Status_DENIED':'无法发货',
                    'Order_Type_GENERAL':'普通订单',
                    'OrderLog_Type_CREATE':'订单创建',
                    'OrderLog_Type_MODIFY':'订单修改',
                    'OrderLog_Type_CANCEL':'订单取消',
                    'OrderLog_Type_REVIEW':'订单处理',
                    'OrderLog_Type_PAYMENT':'订单收款',
                    'OrderLog_Type_SHIPPING':'订单发货',
                    'OrderLog_Type_RECEIVE':'订单收货',
                    'OrderLog_Type_COMPLETE':'订单完成',
                    'OrderLog_Type_FAIL':'订单失败'
			    },

				orderId: '',
				order: {},
				orderLogs: [],
				reply: {},
				formData: {
					quantity: '',
					remark: ''
				}
			};
		},
		components: {},
		onLoad(e) {
			this.orderId = e.id;
			this.order_view();
		},
		methods: {
			/**
			 * 详情
			 */
			order_view: function() {
				if (this.orderId != '') {
					uni.showLoading({
						title: 'Loading...'
					})
					view({
						'id': this.orderId
					}).then(response => {
						uni.hideLoading();
						this.order = response.data.data.order;
						this.order['orderLogs'] = response.data.data.orderLogs;
						this.order['orderShipping'] = response.data.data.orderShipping;
					}).catch(error => {
						uni.hideLoading();
						console.error("error", error);
						uni.showToast({
							title: '系统未知错误,请反馈给管理员',
							duration: 2000
						});
					})
				}
			},
			/**
			 * 审核
			 */
			order_review: function(passed) {
				if (this.orderId != '') {
					uni.showLoading({
						title: 'Loading...'
					})
					review({
						'id': this.orderId,
						'passed':passed
					}).then(response => {
						uni.hideLoading();
						uni.showToast({
							title: '操作成功',
							duration: 3000,
							icon: "none"
						});
						setTimeout(() => {
							let pages = getCurrentPages();
							var prevPage = pages[pages.length - 2];
							uni.navigateBack({
								url: "list",
								delta: 1,
								success(event) {
									prevPage.$vm.order_list();
								}
							})
							
						}, 3000)
					}).catch(error => {
						uni.hideLoading();
						console.error("error", error);
						uni.showToast({
							title: '系统未知错误,请反馈给管理员',
							duration: 2000
						});
					})
				}
			},
			

		}
	};
</script>

<style lang="scss">
	.height-80 {
		height: 95rpx;

		.iconfont {
			font-size: 28rpx !important;
			color: #a2a2a2;
			margin-left: 20rpx;
		}

		.face {
			height: 80rpx;
			overflow: hidden;

			.img {
				width: 80rpx;
				height: 80rpx;
				border-radius: 80rpx;
			}

			.face-icon {
				font-size: 70rpx !important;
				color: #b8b8b8;
			}
		}
	}
	
	.btn-two1{
		font-size: 26rpx;
		height: 100rpx;
		line-height: 100rpx;
		width: 44%;
		left: 3%;
		margin: 30rpx auto;
		background: $default-theme-color;
		border-radius: 20rpx;
	}
	
	.btn-two2{
		font-size: 26rpx;
		height: 100rpx;
		line-height: 100rpx;
		right: 3%;
		width: 44%;
		margin: 30rpx auto;
		background: rgb(255, 154, 67);
		border-radius: 20rpx;
	}

	.color-red {
		padding: 10rpx;
	}

	.piclist {
		display: flex;
		flex-wrap: wrap;
		margin-bottom: 30rpx;

		.upbox {
			width: 20vw;
			height: 20vw;
			margin: 10rpx;
			border: $color-hui-e 2rpx solid;
			color: $color-hui-8;
			font-size: $uni-font-size-base;
			border-radius: 10rpx;

			.iconfont {
				font-size: $uni-font-size-title;
			}

			.img {
				width: 20vw;
				height: 20vw;
			}
		}
	}


	.footer {
		height: 100rpx;
		width: 100%;

		.btn-add {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			color: #ffffff;
			bottom: 0;
			text-align: center;
		}
	}

	.coltitle {
		width: 30%;
	}

	.col-right {
		width: 35%;
	}

	.col-group {
		width: 62%;
	}

	.col-group-lab {
		width: 100%;
		display: flex;
		flex-direction: row;
		font-size: 24rpx;
	}

	.col-group-lab label {
		width: 100%;
	}

	.col-checkbox-line {
		width: 100%;
		line-height: 70rpx;
		text-align: right;
	}

	.col-checkbox-line text {
		width: 70%;
	}

	.col-checkbox-line checkbox {
		width: 20%;
	}

	.params {
		width: calc(200% - 200rpx);
	}

	.set-params {
		text-align: right;
		width: calc(100% - 200rpx);
	}

	.kongbai {
		height: 10rpx;
		width: 100%;
		background: #f7f7f7;
	}

	.specification-key {
		width: calc(100% - 200rpx);
		padding-left: 10rpx;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.specification-value {
		width: calc(160% - 200rpx);
		padding-left: 10rpx;
		border: solid 2rpx #d0d0d0;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.specification-del {
		width: calc(35% - 200rpx);
	}

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

	.choosebox {
		width: 100%;
		background: #ffffff;
		border-radius: 20rpx 20rpx 0 0;
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
		font-size: 24rpx;
		font-weight: bold;
		text-align: center;
	}

	.showbox .dl {
		padding: 20rpx;
		overflow: hidden;
	}

	.showbox .dt {
		font-weight: bold;
		padding-bottom: 20rpx;
	}


	.top {
		position: fixed;
		left: 0;
		z-index: 10;
		width: 100%;
		overflow: hidden;
	}

	.topkongbai {
		height: 215rpx;
	}

	.mall_search {
		width: 94%;
		margin: auto;
		height: 70rpx;
		border-radius: 10rpx;
		background: #eeeeee;
		line-height: 70rpx;

		.iconfont {
			color: #888888;
			margin-right: 20rpx;
		}

		.ipt {
			height: 70rpx;
			line-height: 70rpx;
		}
	}

	.tab {
		height: 80rpx;
		line-height: 80rpx;

		.item {
			flex: 1;
			text-align: center;
		}

		.on {
			position: relative;
		}

		.on::after {
			content: '~';
			position: absolute;
			font-size: 1rpx;
			height: 5rpx;
			width: 60rpx;
			background: $default-theme-color;
			bottom: 0;
			overflow: hidden;
			left: calc(50% - 30rpx);
		}
	}

	.listbox {
		.item {
			padding: 20rpx 0;
			box-sizing: border-box;

			.img {
				width: 180rpx;
				height: 180rpx;
				overflow: hidden;
				margin-right: 20rpx;

				.pic {
					width: 180rpx;
					height: 180rpx;
					border-radius: 10rpx;
				}
			}

			.proinfo {
				width: calc(100% - 200rpx);

				.title {
					overflow: hidden;
					white-space: nowrap;
					text-overflow: ellipsis;
					width: 100%;
				}

				.tag {
					display: flex;
					justify-content: flex-end;
					flex-wrap: wrap;
					padding: 20rpx 0;
					font-size: 22rpx;
					.w50 {
						width: 50%;
					}
				}
			}
		}

		.caozuo {
			padding-bottom: 20rpx;

			.btn {
				padding: 10rpx 40rpx;
				border: #eeeeee 2rpx solid;
				border-radius: 10rpx;
				font-size: 23rpx;
				color: #666666;
			}
		}
	}

	.height-100 {
		height: 100rpx;

		.iconfont {
			font-size: 28rpx !important;
			color: #a2a2a2;
			margin-left: 20rpx;
		}

		.face {
			height: 80rpx;
			overflow: hidden;

			.img {
				width: 80rpx;
				height: 80rpx;
				border-radius: 80rpx;
			}

			.face-icon {
				font-size: 70rpx !important;
				color: #b8b8b8;
			}
		}
	}
</style>
