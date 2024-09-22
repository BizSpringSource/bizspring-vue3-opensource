<template>
	<view>
		<view class="w100 bg_white pd-b-10 pd-t-10 top">
		<scroll-view :scroll-x="true" style="white-space: nowrap;">
			<view class="tab flex-row">
				<view @tap="tapOrderStatus(0)" :class="'item ' + (tapIndex==0 ? 'on' : '')">全部</view>
				<view @tap="tapOrderStatus(1)" :class="'item ' + (tapIndex==1 ? 'on' : '')">待付款</view>
				<view @tap="tapOrderStatus(10)" :class="'item ' + (tapIndex==10 ? 'on' : '')">已支付</view>
				<view @tap="tapOrderStatus(2)" :class="'item ' + (tapIndex==2 ? 'on' : '')">待处理</view>
				<view @tap="tapOrderStatus(3)" :class="'item ' + (tapIndex==3 ? 'on' : '')">待发货</view>
			</view>
		</scroll-view>
		</view>
		<view class="topkongbai"></view>
		<view v-for="(item,index) in objsList" :key="index"
			class="listbox bg_white w100 box-si-border padd-3 m-b-10">
			<view  class="item flex-row" style="display: flex;justify-content: space-between;flex-wrap: wrap;">
				<view>
					{{item.memberName}}
				</view>
				<view style="color: #10a4ff">
					{{msg["Order_Status_"+item.status]}}
				</view>
			</view>
			<navigator :url="'../../order/order/view?id='+item.id"  v-for="(itm,idx) in item.orderItems" :key="idx" class="item flex-row">
				<view class="img">
					<image class="pic" :src="itm.thumbnail"></image>
				</view>
				<view class="proinfo">
					<view class="title">{{itm.name}} 
						<text style="margin-left: 30rpx;" v-if="itm.specifications&&itm.specifications.length>0">[</text><text v-for="(it,idxi) in itm.specifications" :key="idxi" >{{" "+it+" "}}</text><text  v-if="itm.specifications&&itm.specifications.length>0">]</text>
					</view>
					<view class="tag">
						<view class="w50">x {{itm.quantity}}</view>
					</view>
					<view>￥{{itm.price}}</view>
				</view>
			</navigator>
			<view class="item flex-row" style="padding:0;display: flex;justify-content: space-between;flex-wrap: wrap;">
				<view>
					<view>{{item.createdTime}}</view>
				</view>
				<view>
					<view class="price">合计: ￥{{item.price}}</view>
				</view>
			</view>
			<view class="item flex-row" style="display: flex;justify-content: space-between;flex-wrap: wrap;">
				<view style="color: #888888;">
					<view>地址:{{item.regionName||''}} {{item.address||''}}</view>
				</view>
			</view>
			<view class="caozuo flex-row juc-sb">
				<navigator  v-if="item.status == 'PENDING_SHIPMENT'" :url="'../../order/order/shipping?id='+item.id"    class="btn">发 货</navigator>
				<view class="btn"  v-if="item.status == 'RECEIVED'" @tap="order_complete(item.id)">完 成</view>
				<view class="btn"  v-if="item.status == 'PENDING_SHIPMENT' || item.status == 'SHIPPED' || item.status == 'RECEIVED'"  @tap="order_fail(item.id)">失 败</view>
			</view>
		</view>
		<view class="more color-gray2 text-align-center">没有更多了</view>

	</view>
</template>

<script>
	import {
		view,
		complete,
		fail,
		list,
		list_paymented_order
	} from "@/api/order/order.js"
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
                    'Order_Status_DENIED':'无法发货'
			    },
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				tapIndex: 0,
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: null,
					brandId: null,
					isOutOfStock: null,
					keyword: null,
					storeId: null,
					status:null
				},
				typeIndex:0,
			};
		},
		 onLoad(option) {
			//可能要添加时间选项
			if(option.typeIndex){
				this.typeIndex = option.typeIndex?option.typeIndex:null;				
			}
			this.tapOrderStatus(this.typeIndex);
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.order_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.order_list();
		},
		methods: {
			/**
			 * 查询列表 list
			 */
			order_list() {
				list(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.data.content;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},
			
			/**
			 * 查询列表 list
			 */
			order_paymented_list() {
				list_paymented_order(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.data.content;
						this.brandOptions = response.data.data.brands;
						this.goodsCategoryOptions = response.data.data.goodsCategories;
						this.searchLoading = false;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},
			
			/**
			 * 订单失败
			 */
			order_fail(id) {
				fail({
					'id': id
				}).then(response => {
					uni.hideLoading();
					uni.showToast({
						title: '操作成功',
						duration: 2000,
						icon: "none"
					});
					setTimeout(() => {
						this.loadType = "load";
						this.queryParams.pageNumber = 1;
						this.order_list();
					}, 2000)
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			/**
			 * 订单完成
			 */
			order_complete(id) {
				complete({
					'id': id
				}).then(response => {
					uni.hideLoading();
					uni.showToast({
						title: '操作成功',
						duration: 2000,
						icon: "none"
					});
					setTimeout(() => {
						this.loadType = "load";
						this.queryParams.pageNumber = 1;
						this.order_list();
					}, 2000)
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			/**
			 * 搜索
			 */
			search(res) {
				uni.showToast({
					title: '搜索：' + res.value,
					icon: 'none'
				});
			},

			tapOrderStatus(i) {
				let index = i;
				//全部
				if (index == 0) {
					this.tapIndex = 0;
					delete this.queryParams['status'];
				}
				// 待支付
				if (index == 1) {
					this.tapIndex = 1;
					this.queryParams.status = 'PENDING_PAYMENT';
				}
				//待处理
				if (index == 2) {
					this.tapIndex = 2;
					this.queryParams.status = 'PENDING_REVIEW';
				}
				//待发货
				if (index == 3) {
					this.tapIndex = 3;
					this.queryParams.status = 'PENDING_SHIPMENT';
				}
				if (index == 10) {
					this.tapIndex = 10;
					this.loadType = "load";
					this.queryParams.pageNumber = 1;
					this.order_paymented_list();			
				}else{					
					this.loadType = "load";
					this.queryParams.pageNumber = 1;
					this.order_list();					
				}
				
			}
		}
	};
</script>

<style lang="scss">
	uni-page-body {
		background-color: #f7f7f7;
		color: #2D2D2D;
	}

	.top {
		position: fixed;
		left: 0;
		z-index: 10;
		width: 100%;
		overflow: hidden;
	}

	.topkongbai {
		height: 115rpx;
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
		width: 100%;
		display: flex;		

		.item {
			margin-right: 15rpx;
			margin-left: 15rpx;
			text-align: center;
			padding: 0 15px;
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
					justify-content: space-between;
					flex-wrap: wrap;
					padding: 20rpx 0;
					color: #888888;
					font-size: 22rpx;

					.w50 {
						width: 50%;
					}
				}
			}
		}

		.price {
			font-weight: bold;
			color: #ff6600;
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

	.more {
		font-size: 24rpx;
		line-height: 60rpx;
	}

	.footer {
		height: 100rpx;
		width: 100%;

		.btn-add {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
			text-align: center;
		}
	}
</style>
