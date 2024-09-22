<template>
	<view class="payment" style="padding-left: 0rpx;">
		<view class="pay_detail">
			<view style="display:flex;flex-direction:row;">
				<view style="width:15%;">
				</view>
				<view style="width:75%;">
					<view class="paybot">
						<view style="line-height:180rpx;">
							<text>应付金额：</text>
							<text style="font-size:80rpx;color:orange;">¥{{totalPrice}}</text>
						</view>
						<view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="payfor">
			<view class="banktitle">
				<text style="color:gray;font-size:26rpx;">请选择支付方式</text>
			</view>
			<view class="bankcar">
				<view class="list" style="padding:0rpx;">
					<view>
						<button type="default" class="list-item pay-list-item"
							style="text-align:left;border-color:#ffffff;background-repeat:no-repeat;background-image:url(/static/images/weixin_pay.png);'"
							@tap="payment_outLinePay()">
							<view style="margin-left: 148rpx;">系统支付</view>
						</button>
					</view> 
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	import {
		payment,
	} from "@/api/order/order.js"
	import {
		load,
		testPostPay,
		index,
		pay,
	} from "@/api/order/payment.js"
	import {
		baseURL
	} from '@/config/config.js'
	

	var app = getApp();
	export default {
		data() {
			return {
				ids: "",
				orderSn: "",
				loadingHidden: false,
				totalPrice: 0,
				orders: [],
				orderSns: [],
				paymentItemListForm: {
					paymentItemList: []
				}, //支付项
				online: null,
				defaultPayment: null,
				payments: null,
				paymentPattern: null,
				paymentLog: null,
			};
		},
		components: {},
		props: {},
		onLoad(options) {
			this.orderSns = options.orderSns;
			this.payment_payment();
			//等待登录成功	
			var that = this;
			//调用应用实例的方法获取全局数据			
			that.ids = options.ids;
			that.totalPrice = options.totalPrice;
			that.loadingHidden = false;
		},
		methods: {
			payment_payment: function() {
				payment({
					"orderSns": this.orderSns
				}).then(response => {
					uni.hideLoading();
					this.totalPrice = response.data.data.amount;
					this.orders = response.data.data.orders;
					this.orderSns = response.data.data.orderSns;
					this.online = response.data.data.online;
					this.defaultPayment = response.data.data.defaultPayment;
					this.payments = response.data.data.payments;
					let paymentItem = {};
					for (let i = 0; i < this.orders.length; i++) {
						paymentItem = {};
						paymentItem.type = "ORDER_PAYMENT";
						paymentItem.amount = this.orders[i].amount;
						paymentItem.orderSn = this.orders[i].sn;
						this.paymentItemListForm.paymentItemList.push(paymentItem);
					}
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			payment_outLinePay: function() {
				load(
					this.paymentItemListForm, {
						"paymentId": this.defaultPayment.id,
						"rePayUrl": "test"
					}).then(response => {
					uni.hideLoading();
					this.paymentLog = response.data.data.paymentLog;
					this.payment_testpostpay();
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},

			payment_testpostpay: function() {
				testPostPay({
					"extra": '支付',
				}, this.paymentLog.sn).then(response => {
					uni.hideLoading();
					uni.showToast({
						title: '支付成功',
						duration: 2000
					});
					setTimeout(function() {
						uni.reLaunch({
							url: '../../pages/member/index'
						});
					}, 3000);

				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},



		},
	};
</script>
<style>
	.paybot_a {
		color: #6a77b6;
		font: normal;
	}

	.bankone .banksp {
		height: 30px;
		line-height: 30px;
		margin: 10px 10px;
	}

	.payment {
		width: 100%;
		margin-top: 10rpx;
		overflow: hidden;
		color: #222;
	}

	.payment_h1 {
		font-size: 24rpx;
		height: 50px;
		line-height: 50px;
	}

	.pay_table {
		width: 100%;
		margin-bottom: 20px;
		background-color: #fafafa;
	}

	.pay_table_td {
		padding: 10px 5px;
		border: 1px dashed #eeeeee;
	}

	.pay_table_td_img {
		vertical-align: middle;
	}

	.pay_table .pay_style {
		width: 100px;
		padding: 5px 15px;
		border: 2px solid #e4e4e4;
		text-align: center;
		background-color: #FFF;
	}

	.pay_table .this {
		border: 2px solid #F00;
		color: #F00;
	}

	.pay_btn {
		width: 100%;
		margin-top: 10px;
		float: left;
	}

	.pay_btn_input {
		background-color: #D90600;
		width: 100%;
		float: left;
		text-align: center;
		height: 40px;
		line-height: 40px;
		font-size: 24rpx;
		color: #FFF;
	}

	.pay_detail {
		width: 100%;
		float: left;
		padding: 5px 5px;
	}

	.pay_detail_dl {
		width: 100%;
		float: left;
	}

	.pay_detail_dl_dt {
		width: 25%;
		float: left;
	}

	.pay_detail_dl_dd {
		width: 75%;
		float: left;
	}

	.pay_detail_dl_dd_li {
		line-height: 30px;
	}
</style>