<template>
	<view>
		<view class="swiper-tab pd-t-20 pd-b-20 ">
			<view @tap="tapOrderStatus(0)" :class="'swiper-tab-list ' + (tapIndex==0 ? 'on' : '')">全部</view>
			<view @tap="tapOrderStatus(1)" :class="'swiper-tab-list ' + (tapIndex==1 ? 'on' : '')">待付款</view>
			<view @tap="tapOrderStatus(2)" :class="'swiper-tab-list ' + (tapIndex==2 ? 'on' : '')">待发货</view>
			<view @tap="tapOrderStatus(3)" :class="'swiper-tab-list ' + (tapIndex==3 ? 'on' : '')">待收货</view>
		</view>
		<view class="c_t60"></view>
		<view :current="currentTab" class="swiper-box" duration="300">
			<!-- 全部 -->
			<view class="search_no" v-if="!objsList">
				<view class="font_14">
					<image class="scimg" src="/static/images/search_no.png"></image>
				</view>
				<text>没有更多了</text>
			</view>
			<view v-for="(item, index) in objsList" :key="index"
				style="display:flex;flex-direction:column;"
				class="shop df m-top-30" :data-orderid="item.id">
				<view>
					<view class="font_12 red fl_r mr_5 mt_5">
						{{msg["Order_Status_"+item.status]}}
					</view>
					<view class="font_13 black fl_l mr_5 mt_10">
						{{item.createdTime}}
					</view>
				</view>
				<view class="m-b-30 pd-t-30" :style=" idx > 0 ?'display: flex;border-top: 2px solid #eee;':'display: flex;' " v-for="(itm, idx) in item.orderItems">
					<navigator :url="'../../goods/goods/detail?goodsId=' + itm.goodsId" >
						<image class="sh_slt" :src="itm.thumbnail"></image>
					</navigator>
					<view class="df_1">
						<view class="sp_text">
							<navigator :url="'../../goods/goods/detail?goodsId=' + itm.goodsId" hover-class="changestyle">
								<view class="sp_tit ovh1">{{itm.name}}
									<text style="margin-left: 30rpx;"
										v-if="itm.specifications&&itm.specifications.length>0">[</text><text
										v-for="(it,idx) in itm.specifications">{{" "+it+" "}}</text><text
										v-if="itm.specifications&&itm.specifications.length>0">]</text>
								</view>
							</navigator>
							<view style="margin:15rpx 0;text-align: right;" class="sp_neb">销售价：¥ {{itm.price}}
								</view>
							<view style="text-align: right;font-size: 21rpx" class="sp_jg">
								<view>数量：×{{itm.quantity}}</view>
							</view>
						</view>
					</view>
				</view>
				<view>
					<view   class="font_12 red fl_r mr_5 mt_5"  >
						￥{{item.amount}}
					</view>
					<view   class="font_12 gray fl_r mr_5 mt_5"  >
						实付金额:
					</view>					
				</view>
				
				<view>
					<navigator :url="'../../order/order/payment?orderSns=' + item.sn"  class="font_12 red fl_r mr_5 mt_5" v-if="item.status=='PENDING_PAYMENT'" >
						<button type="button">实付金额</button>
					</navigator>
					<view class="font_12 red fl_r mr_5 mt_5"
						v-if="item.type=='EXCHANGE'&&(item.status=='PENDING_PAYMENT'||item.status=='PENDING_REVIEW')"
						@tap="order_remove(item.id)" :data-orderid="item.id">
						<button type="button">订单取消</button>
					</view>
					<view class="font_12 red fl_r mr_5 mt_5"
						v-if="item.status=='SHIPPED'"
						@tap="order_receive(item.id)" :data-orderid="item.id">
						<button type="button">确认收货</button>
					</view>
				</view>
			</view>
			<view class="loadingWxApplet" :hidden="!searchLoading">正在载入更多...</view>
			<view class="loadingWxApplet" :hidden="!searchLoadingComplete">没有更多了</view>
		</view>
	</view>
</template>

<script>
	import {
		list,
		receive,
		cancel
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
				searchLoadingComplete: false,
				// tab切换  
				currentTab: 0,
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: '1',
					brandId: null,
					isOutOfStock: null,
					keyword: null,
					storeId: null,
				},
				objsList: [],
				brandOptions: [],
				goodsCategoryOptions: [],
				tapIndex: 0,
				loadType: "load",
			};
		},

		components: {},
		props: {},
		onLoad(options) {
			this.tapIndex = parseInt(options.currentTab);
			if (this.currentTab == 5) {
			} else {
				this.tapOrderStatus(this.tapIndex);
			}
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
						this.brandOptions = response.data.data.brands;
						this.goodsCategoryOptions = response.data.data.goodsCategories;
						this.searchLoading = false;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},
			

			tapOrderStatus(i) {
				let index = i;
				if (index == 0) {
					this.tapIndex = 0;
					delete this.queryParams['status'];
				}
				if (index == 1) {
					this.tapIndex = 1;
					this.queryParams.status = 'PENDING_PAYMENT';
				}
				if (index == 2) {
					this.tapIndex = 2;
					this.queryParams.status = 'PENDING_SHIPMENT';
				}
				if (index == 3) {
					this.tapIndex = 3;
					this.queryParams.status = 'SHIPPED';
				}

				this.loadType = "load";
				this.queryParams.pageNumber = 1;
				this.order_list();
			},

			order_reload_data:function(i) {
				this.objsList = [];
				this.loadType = "load";
				this.queryParams.pageNumber = 1;
				this.order_list();
			},
			//取消订单
			order_remove: function(id) {
				var that = this;
				uni.showModal({
					title: '提示',
					content: '您确定要取消该订单吗？',
					success: function(res) {
						if (res.confirm) {
							cancel({
								"orderId": id
							}).then(response => {
								uni.hideLoading();
								that.order_reload_data();
							}).catch(error => {
								uni.hideLoading();
								console.error("error", error);
								uni.showToast({
									title: '系统未知错误,请反馈给管理员',
									duration: 2000
								});
							})
						}
					}
				});
			},
			//确认收货
			order_receive: function(id) {
				let that = this;
				uni.showModal({
					title: '提示',
					content: '您确认订单收货吗？',
					success: function(res) {
						if (res.confirm) {
							receive({
								"orderId": id
							}).then(response => {
								uni.hideLoading();
								that.order_reload_data();
							}).catch(error => {
								uni.hideLoading();
								console.error("error", error);
								uni.showToast({
									title: '系统未知错误,请反馈给管理员',
									duration: 2000
								});
							})
						}
					}
				});
			},

		}
	};
</script>
<style>
	.swiper-tab {
		width: 100%;
		height: 55rpx;
		border-bottom: 1px solid #eee;
		text-align: center;
		line-height: 30rpx;
		background: #fff;
		position: fixed;
		z-index: 999;
	}

	.swiper-tab-list {
		font-size: 21rpx;
		display: inline-block;
		width: 25%;
		color: #666;
	}

	.on {
		color: #dd2727;
		border-bottom: 5rpx solid #dd2727;
	}

	.swiper-box {
		display: block;
		height: 100%;
		width: 100%;
		overflow: hidden;
	}

	.clearbutton {
		background: #fff;
		display: inline-block;
		margin: 3% 3% 2% 3%;
		color: #dd2727;
		font-size: 21rpx;
		width: 94%;
	}

	.shop {
		background: #fff;
		padding: 4%;
	}

	.shop checkbox {
		margin-top: 27px;
	}

	.sh_slt {
		width: 200rpx;
		height: 200rpx;
		overflow: hidden;
		border-radius: 5px;
		margin-right: 4%;
		float: left;
	}

	.sp_text {
		float: left;
		line-height: 20px;
		width: 100%;
		text-align: left;
	}

	.sp_tit {
		width: 100%;
		overflow: hidden;
		font-size: 23rpx;
	}

	.sp_neb {
		width: 100%;
		overflow: hidden;
		font-size: 21rpx;
		color: #999;
	}

	.sp_jg {
		width: 100%;
		overflow: hidden;
		font-size: 23rpx;
		color: #fc0628;
	}

	.dle {
		color: #999;
		font-size: 21rpx;
		float: right;
	}

	.dle image {
		width: 18px;
		height: 18px;
		vertical-align: sub;
	}

	.jk_bottom {
		position: fixed;
		bottom: 0;
		background: #fff;
		width: 100%;
		display: inline-flex;
		border-bottom: 1px solid #eee;
		font-size: 23rpx;
		color: #999;
	}

	.jk_bottom checkbox {
		margin: 4% 0 4% 4%;
	}

	.jk_bottom .heji {
		margin-top: 5.5%;
		width: 30%;
		text-align: right;
	}

	.jk_bottom .all {
		margin-top: 5.5%;
		padding-left: 2%;
	}

	.jk_bottom .js_button {
		background: #dd2727;
		float: right;
		color: #fff;
		font-size: 16px;
		text-align: center;
		width: 40%;
		position: absolute;
		right: 0;
		line-height: 50px;
	}

	.c_t60 {
		clear: both;
		height: 1px;
		padding-top: 90rpx;
	}

	.blue {
		color: #42b1ff;
	}

	.mr_5 {
		margin-right: 5px;
	}

	.mt_5 {
		margin-bottom: 5px;
	}

	.mt_10 {
		margin-bottom: 25rpx;
	}

	button {
		background-color: #fff;
		line-height: 52rpx;
		text-align: center;
		border: 1rpx solid #ccc;
		width: 150rpx;
		height: 52rpx;
		border-radius: 26rpx;
		font-size: 24rpx;
		color: red;
	}
</style>
