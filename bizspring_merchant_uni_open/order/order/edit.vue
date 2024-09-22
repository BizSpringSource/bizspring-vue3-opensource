<template>
	<view>
		<form @submit="formsubmit">
			<view class="choosebox">
				<view class="kongbai"></view>
				<view class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
					<!-- 发货状态 -->
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-20">
							<view class="coltitle"><text>编号:</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{order.sn}}</text>
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-20 pd-t-10 line-height-90">
							<view class="coltitle"><text>状态 :</text></view>
							<text
								class="font-size-20 color-green font-weight-600">{{msg["Order_Status_"+order.status]}}</text>
						</view>
					</view>
					<view class="kongbai"></view>
					<view v-if="order.shippingMethodName">
						<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
							<text
								class="font-weight-bold font-size-17">发货信息</text>
							<text class="iconfont icon-angle-right"></text>
						</view>
						<view v-if="order.orderShipping" class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>运单号 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.orderShipping.sn}}</text>
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
					<view class="flex-row  border-bottom1 juc-sb ai-c height-100 padd-2">
						<text class="font-weight-bold  font-size-17">收货信息</text>
					</view>


					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 m-top-30">
							<view class="coltitle"><text>收货人 :</text></view>
							<input type="text" maxlength="20" v-model="formData.consignee"
								placeholder="请输入收货人"
								style="width: calc(100% - 200rpx);" />
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle"><text>电话 :</text>
							</view>
							<input type="number" maxlength="15" v-model="formData.phone" name="mallnum"
								placeholder="请输入电话"
								style="width: calc(100% - 200rpx);" />
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle"><text>地区 :</text></view>
							<view v-if="regions" class="phone_form_ul_li ip ">
								<picker mode="multiSelector" :range-key="'name'" @columnchange="changeRegionMultiColumn"
									@change="changeRegionMulti" :range="multiArray" :value="multiIndex">
									<view class="picker" style="padding-left:15rpx;">
										{{multiArray[0][multiIndex[0]].name}},{{multiArray[1][multiIndex[1]].name}},{{multiArray[2][multiIndex[2]].name}}
									</view>
								</picker>
							</view>
						</view>
					</view>
					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle">
								<text>地址 :</text>
							</view>
							<input type="text" maxlength="50" v-model="formData.address" name="mallnum"
								placeholder="请输入地址"
								style="width: calc(100% - 200rpx);" />
						</view>
					</view>

					<view class="box padd-3">
						<view class="flex-row ai-c m-b-30 ">
							<view class="coltitle"><text>附言 :</text>
							</view>
							<input type="text" v-model="formData.remark" name="mallnum"
								placeholder="请输入附言"
								style="width: calc(100% - 200rpx);" />
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
						<view v-for="(itm,index) in order.orderItems" class="item flex-row">
							<view class="img">
								<image class="pic" :src="itm.thumbnail"></image>
							</view>
							<view class="proinfo">
								<view class="title">{{itm.name}}
								</view>
								<view class="juc-sb flex-row m-top-20 m-b-20 color-gray2">
									<view><text v-if="itm.specifications&&itm.specifications.length>0">[</text><text
											v-for="(it,idx) in itm.specifications">{{" "+it+" "}}</text><text
											v-if="itm.specifications&&itm.specifications.length>0">]</text></view>
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
								<text>支付方式 :{{order.paymentPatternName||""}}</text>
							</view>
							<view class="col-right"><text>兑换积分 :
									{{order.exchangePoint}}</text></view>
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
					<!-- 开票信息 -->
					<view v-if="order.invoice" class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
						<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
							<text
								class="font-weight-bold font-size-17">发票信息</text>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>抬头 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.invoice.title}}</text>
							</view>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>税号 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.invoice.taxNumber}}</text>
							</view>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>税金 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.tax}}</text>
							</view>
						</view>
					</view>
					<view class="kongbai"></view>
					<view v-if="order.refundAmount>0||order.refundableAmount>0"
						class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
						<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
							<text class="font-weight-bold font-size-17">退款信息</text>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>已退货数量 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.returnedQuantity}}</text>
							</view>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>应退金额 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.refundableAmount}}</text>
							</view>
						</view>
						<view class="box padd-3">
							<view class="flex-row ai-c m-b-30 m-top-30">
								<view class="coltitle"><text>退款金额 :</text>
								</view>
								<text type="text" style="width: calc(100% - 200rpx);">{{order.refundAmount}}</text>
							</view>
						</view>

					</view>
					<view class="kongbai"></view>
					<view class="flex-row border-bottom1 juc-sb ai-c height-100 padd-2">
						<text class="font-weight-bold font-size-17">订单记录</text>
					</view>
					<view v-for="(item,index) in order.orderLogs" class="box padd-3">
						<view class="flex-row ai-c m-b-20 m-top-30">
							<view class="coltitle"><text>{{msg["OrderLog_Type_"+item.type]}} :</text>
							</view>
							<text type="text" style="width: calc(100% - 200rpx);">{{item.createdTime}}</text>
						</view>
					</view>
					<view class="kongbai"></view>
					<view class="kongbai"></view>
					<view class="kongbai"></view>
					<view class="footer">
						<view @tap="order_update" class="btn-add btn-full">保存</view>
					</view>
				</view>
			</view>
		</form>
	</view>
</template>

<script>
	import {
		view,
		update,
		regions_childs,
		list,
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
                    'Order_Type_GENERAL':'普通订单',
                    'Order_Status_PENDING_PAYMENT':'等待付款',
                    'Order_Status_PENDING_REVIEW':'等待处理',
                    'Order_Status_PENDING_SHIPMENT':'等待发货',
                    'Order_Status_SHIPPED':'已发货',
                    'Order_Status_RECEIVED':'已收货',
                    'Order_Status_COMPLETED':'已完成',
                    'Order_Status_FAILED':'已失败',
                    'Order_Status_CANCELED':'已取消',
                    'Order_Status_DENIED':'无法发货',
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
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				objsList: [],
				orderId: '',
				order: {},
				orderLogs: [],
				reply: {},
				regions: [],
				index: 0,
				multiArray: [
					['北京'],
					['北京'],
					['东城区']
				],
				multiIndex: [0, 0, 0],
				formData: {
					regionId: '',
					paymentPatternId: '',
					shippingMethodId: '',
					freight: 0,
					tax: 0,
					offsetAmount: 0,
					rewardPoint: '',
					consignee: '',
					address: '',
					zipCode: '',
					phone: '',
					invoiceTitle: '',
					invoiceTaxNumber: '',
					remark: '',
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
						this.order_update_init();

						let regionId = this.order.regionId;
						let regions = response.data.data.regions;
						let array1 = [];
						let array2 = [];
						let array3 = [];

						var index = 0; //如果是update
						for (let x = 0; x < regions.length; x++) {
							if (regions[x].level == 0) {
								array1[index] = regions[x];
								index++;
							}
						}
						let updatemultiIndex = [0, 0, 0];
						if (regionId) {
							this.multiArray[0] = array1;
							for (let i = 0; i < array1.length; i++) {
								if (array1[i].id == regionId) { //在第一层找到了
									updatemultiIndex[0] = i;
									this.multiIndex = updatemultiIndex;
									return;
								}
								if (array1[i].children) {
									array2 = array1[i].children; //在第二层查找
									this.multiArray[1] = array2;
									for (let j = 0; j < array2.length; j++) {
										if (array2[j].id == regionId) {
											updatemultiIndex[1] = j;
											updatemultiIndex[0] = i;
											this.multiIndex = updatemultiIndex;
											return;
										}
										if (array2[j].children) {
											array3 = array2[j].children; //在第三次查找
											this.multiArray[2] = array3;
											for (let f = 0; f < array3.length; f++) {
												if (array3[f].id == regionId) {
													updatemultiIndex[2] = f;
													updatemultiIndex[1] = j;
													updatemultiIndex[0] = i;
													this.multiIndex = updatemultiIndex;
													return;
												}
											}
										}
									}
								}
							}
						}
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

			order_update_init() {
				this.formData.regionId = this.order.regionId != null ? this.order.regionId : null;
				this.formData.paymentPatternId = this.order.paymentPatternId != null ? this.order.paymentPatternId : null;
				this.formData.shippingMethodId = this.order.shippingMethodId != null ? this.order.shippingMethodId : null;
				this.formData.freight = this.order.freight != null ? this.order.freight : 0;
				this.formData.tax = this.order.tax != null ? this.order.tax : 0;
				this.formData.offsetAmount = this.order.offsetAmount != null ? this.order.offsetAmount : 0;
				this.formData.rewardPoint = this.order.rewardPoint != null ? this.order.rewardPoint : null;
				this.formData.consignee = this.order.consignee != null ? this.order.consignee : null;
				this.formData.address = this.order.address != null ? this.order.address : null;
				this.formData.zipCode = this.order.zipCode != null ? this.order.zipCode : null;
				this.formData.phone = this.order.phone != null ? this.order.phone : null;
				this.formData.invoiceTitle = this.order.invoiceTitle != null ? this.order.invoiceTitle : null;
				this.formData.invoiceTaxNumber = this.order.invoiceTaxNumber != null ? this.order.invoiceTaxNumber : null;
				this.formData.remark = this.order.remark != null ? this.order.remark : null;
			},

			//保存商品
			order_update() {
				update(this.order, this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
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
				})
			},

			changeRegionMultiColumn: function(e) {
				var multiArray = this.multiArray;
				var multiIndex = [this.multiIndex[0], this.multiIndex[1], this.multiIndex[2]];
				switch (e.detail.column) {
					case 0:
						//选择第一列
						//给第二列
						if (multiArray[0][e.detail.value].children.length != 0) {
							multiIndex[0] = e.detail.value;
							multiIndex[1] = 0;
							multiArray[1] = multiArray[0][e.detail.value].children; //第二列仅有一个值，要给第三列重新赋值
							if (multiArray[0][e.detail.value].children.length >= 1) {
								multiArray[2] = multiArray[1][0].children;
								multiIndex[2] = 0;
							}
						} else {
							multiArray[1] = [];
							multiArray[2] = [];
						}
						break;
					case 1:
						//选择第二列      
						//给第三列赋值
						multiIndex[1] = e.detail.value;
						multiIndex[2] = 0;
						multiArray[2] = multiArray[1][e.detail.value].children;
						break;
					case 2:
						//选择第三列      
						multiIndex[2] = e.detail.value;
						break;
				}
				this.multiArray = multiArray;
				this.multiIndex = multiIndex;
			},
			changeRegionMulti: function(e) {
				var multiArray = this.multiArray;
				var multiIndex = e.detail.value;
				var regionId = "";
				if (multiArray[2][multiIndex[2]].id) {
					regionId = multiArray[2][multiIndex[2]].id;
				} else {
					if (multiArray[1][multiIndex[1]].id) {
						regionId = multiArray[1][multiIndex[1]].id;
					} else {
						regionId = multiArray[0][multiIndex[0]].id;
					}
				}

				this.multiIndex = e.detail.value;
				this.formData.regionId = regionId;
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

	.btn-two1 {
		font-size: 26rpx;
		height: 100rpx;
		line-height: 100rpx;
		width: 44%;
		left: 3%;
		margin: 30rpx auto;
		background: $default-theme-color;
		border-radius: 20rpx;
	}

	.btn-two2 {
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
		background-color: #ffffff;

		.btn-add {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
			text-align: center;
			z-index: 9;
		}

		.btn-add-box {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
			text-align: center;
			z-index: 99;
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
