<template>
	<view class="content">
		<uni-forms ref="form" label-width="100px" :rules="rules" :model="orderShippingForm" validate-trigger="bind"
			err-show-type="toast">
			<view class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
				<view class="box padd-3">
					<view class="flex-row ai-c m-b-20 pd-t-10 line-height-90">
						<view class="coltitle"><text>编号</text>
							<text class="color-red">*</text>
						</view>
						<text style="width: calc(100% - 200rpx);" class="color-gray1">{{obj.sn}}</text>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c m-b-20 pd-t-10 line-height-90">
						<view class="coltitle"><text>创建日期</text>
							<text class="color-red">*</text>
						</view>
						<text style="width: calc(100% - 200rpx);" class="color-gray1">{{obj.createdTime}}</text>
					</view>
				</view>
			</view>

			<view class="w100 bg_white pd-b-10 pd-t-10 m-top-30">
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="配送方式" class="uni-item" name="shippingMethod">
							<view class="flex-row ai-c uni-item-value" data-type="mobile">
								<picker @change="bindShippingMethodsChange" :value="shippingMethodsIdx" range-key="name"
									:range="shippingMethods">
									<view class="color-gray1">{{shippingMethods&&shippingMethods[shippingMethodsIdx]&&shippingMethods[shippingMethodsIdx].name?shippingMethods[shippingMethodsIdx].name:''}}</view>
								</picker>
							</view>
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="物流公司" class="uni-item" name="expressCompany">
							<picker class="uni-item-value" @change="bindExpressCompanysChange" :value="expressCompanysIdx"
								range-key="name" :range="expressCompanys">
								<view class="color-gray1">{{expressCompanys&&expressCompanys[expressCompanysIdx]&&expressCompanys[expressCompanysIdx].name?expressCompanys[expressCompanysIdx].name:''}}</view>
							</picker>
						</uni-forms-item>
					</view>
				</view>

				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="运单号" class="uni-item" name="trackingNo">
							<input type="text" maxlength="30" v-model="orderShippingForm.trackingNo" placeholder="请输入运单号"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="物流费用" class="uni-item" name="freight">
							<input type="digit" maxlength="11" v-model="orderShippingForm.freight" placeholder="请输入物流费用"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
			</view>
			<view class="w100 bg_white pd-b-10 pd-t-10 m-top-30">
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="收货人" class="uni-item" name="consignee">
							<input type="text" maxlength="20" v-model="orderShippingForm.consignee" placeholder="请输入收货人"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="电话" class="uni-item" name="phone">
							<input type="text" maxlength="15" v-model="orderShippingForm.phone" placeholder="请输入电话"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="地区" class="uni-item" name="region">
							<view v-if="regions" class="uni-item-value">
								<picker mode="multiSelector" :range-key="'name'" @columnchange="changeRegionMultiColumn"
									@change="changeRegionMulti" :range="multiArray" :value="multiIndex">
									<view class="picker">
										{{multiArray[0][multiIndex[0]].name}},{{multiArray[1][multiIndex[1]].name}},{{multiArray[2][multiIndex[2]].name}}
									</view>
								</picker>
							</view>
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20 border-bottom1 line-height-90">
						<uni-forms-item required label="地址" class="uni-item" name="address">
							<input type="text" maxlength="100" v-model="orderShippingForm.address" placeholder="请输入地址"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>

				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  line-height-90">
						<uni-forms-item label="邮编" class="uni-item" name="zipCode">
							<input type="number" maxlength="12" v-model="orderShippingForm.zipCode" placeholder="请输入邮编"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
			</view>

			<view class="w100 bg_white pd-b-10 pd-t-10 m-b-30">
				<view class="box padd-3">
					<uni-forms-item label="备注" class="uni-item" name="remark">
						<view class="flex-row ai-c pd-t-20 line-height-90">
							<textarea style="height: 75rpx;width: 80%;line-height: 1;" maxlength="100"
								v-model="orderShippingForm.remark" placeholder="请输入备注"></textarea>
						</view>
					</uni-forms-item>
				</view>
			</view>

			<view class="kongbai"></view>
			<view class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
				<view class="box padd-3">
					<view class="flex-row ai-c m-b-20 pd-t-10 line-height-90">
						<label class="radio">
							<radio value="r1" :checked="is_check" @tap="checkedradio(is_check)" color="#ff6600"></radio>
						</label>
						<view class="tt2 flex-row juc-sb">
							<text>已阅读并同意</text>
							<navigator url="../../pages/utils/agreement" style="color: #0c72ec">用户服务协议 、隐私政策
							</navigator>
						</view>
					</view>
				</view>
			</view>

		</uni-forms>
		<view class="footer">
			<view @tap="submitForm" class="btn-add btn-full">发 货</view>
		</view>
	</view>
</template>

<script>
	import {
		view,
		shipping,
	} from '@/api/order/order.js'
	import htzImageUpload from '@/components/htz-image-upload/htz-image-upload.vue'
	import {
		baseURL
	} from '@/config/config.js'

	var app = getApp();
	export default {
		data() {
			return {
				orderId: '',
				obj: {},
				shippingMethodsIdx: 0,
				expressCompanysIdx: 0,
				regions: [],
				index: 0,
				multiArray: [
					['北京'],
					['北京'],
					['东城区']
				],
				multiIndex: [0, 0, 0],
				shippingMethods: [],
				expressCompanys: [],
				orderShippingForm: {
					sn: '', //编码 -
					shippingMethod: '', //配送方式名称
					expressCompany: '', //快递公司
					expressCompanyUrl: '', //快递公司网址
					expressCompanyCode: '', //快递公司代码
					trackingNo: '', //运单号
					freight: 0, //物流费用
					consignee: '', //收货人
					region: '', //地区
					address: '', //地址
					zipCode: '', //邮编
					phone: '', //电话
					remark: '', //备注
					orderId: '', //订单 -
					orderShippingItems: [], //发货项 -
				},
				formData: {
					orderId: '',
					regionId: '',
					regionName: '',
					shippingMethodId: '',
					shippingMethodName: '',
					consignee: '',
					address: '',
					zipCode: '',
					phone: '',
					remark: '',
				},

				is_check: false,

				rules: {
					shippingMethod: {
						rules: [{
							required: true,
							errorMessage: '配送方式不能为空'
						}]
					},
					expressCompany: {
						rules: [{
							required: true,
							errorMessage: '物流公司不能为空'
						}]
					},
					trackingNo: {
						rules: [{
							required: true,
							errorMessage: '运单号不能为空'
						}]
					},
					freight: {
						rules: [{
							required: true,
							errorMessage: '物流费用不能为空'
						}]
					},
					consignee: {
						rules: [{
							required: true,
							errorMessage: '收货人不能为空'
						}]
					},
					phone: {
						rules: [{
							required: true,
							errorMessage: '电话不能为空'
						}]
					},
					region: {
						rules: [{
							required: true,
							errorMessage: '地区不能为空'
						}]
					},
					address: {
						rules: [{
							required: true,
							errorMessage: '地址不能为空'
						}]
					},
				}
			};
		},
		components: {
			htzImageUpload,
		},

		onLoad(e) {
			this.orderId = e.id;
			this.order_view();
		},
		onReady() {
			this.$refs.form.setRules(this.rules);
		},
		methods: {

			/** 提交按钮 */
			submitForm() {
				this.$refs['form'].validate().then(res => {
					this.order_shipping();
				}).catch(err => {
					console.log('err', err);
				})
			},

			order_shipping() {
				if (!this.is_check) {
					uni.showToast({
						title: '用户服务协议 、隐私政策',
						duration: 3000,
						icon: "none"
					});
					return;
				}
				this.orderShippingForm.sn = this.obj.sn;
				this.orderShippingForm.orderId = this.obj.id;
				this.orderShippingItemData();
				//formData 赋值
				this.formData.orderId = this.orderId;
				this.formData.consignee = this.orderShippingForm.consignee;
				this.formData.address = this.orderShippingForm.address;
				this.formData.zipCode = this.orderShippingForm.zipCode;
				this.formData.phone = this.orderShippingForm.phone;
				this.formData.remark = this.orderShippingForm.remark;


				shipping(this.orderShippingForm, this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 2000,
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
					}, 2000)
				})
			},

			orderShippingItemData() {
				let orderShippingItems = [];
				for (let i = 0; i < this.obj.orderItems.length; i++) {
					let orderItem = this.obj.orderItems[i];
					let shippingItem = {};
					shippingItem.sn = orderItem.sn;
					shippingItem.skuId = orderItem.skuId;
					shippingItem.quantity = orderItem.quantity;
					shippingItem.isDelivery = orderItem.isDelivery;
					shippingItem.specifications = orderItem.specifications;
					this.orderShippingForm.orderShippingItems.push(shippingItem);
				}
			},

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
						this.obj = response.data.data.order;
						this.shippingMethods = response.data.data.shippingMethods;
						this.expressCompanys = response.data.data.expressCompanys;
						this.obj['orderLogs'] = response.data.data.orderLogs;
						this.obj['orderShipping'] = response.data.data.orderShipping;
						let event = {
							detail: {
								value: 0
							}
						}
						this.bindShippingMethodsChange(event);
						this.bindExpressCompanysChange(event);
						this.order_update_init();

						let regionId = this.obj.regionId;
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
									
									this.orderShippingForm.region = this.multiArray[0][this.multiIndex[0]].name + ',' + this.multiArray[1][this.multiIndex[1]].name + ',' + this.multiArray[2][this.multiIndex[2]].name;
									this.formData.regionName = this.orderShippingForm.region;
									
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
											
											this.orderShippingForm.region = this.multiArray[0][this.multiIndex[0]].name + ',' + this.multiArray[1][this.multiIndex[1]].name + ',' + this.multiArray[2][this.multiIndex[2]].name;
											this.formData.regionName = this.orderShippingForm.region;
											
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
													
													this.orderShippingForm.region = this.multiArray[0][this.multiIndex[0]].name + ',' + this.multiArray[1][this.multiIndex[1]].name + ',' + this.multiArray[2][this.multiIndex[2]].name;
													this.formData.regionName = this.orderShippingForm.region;
													
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
				this.formData.regionId = this.obj.regionId != null ? this.obj.regionId : null;
				this.formData.paymentPatternId = this.obj.paymentPatternId != null ? this.obj.paymentPatternId : null;
				this.formData.shippingMethodId = this.obj.shippingMethodId != null ? this.obj.shippingMethodId : null;
				this.formData.freight = this.obj.freight != null ? this.obj.freight : 0;
				this.formData.tax = this.obj.tax != null ? this.obj.tax : 0;
				this.formData.offsetAmount = this.obj.offsetAmount != null ? this.obj.offsetAmount : 0;
				this.formData.rewardPoint = this.obj.rewardPoint != null ? this.obj.rewardPoint : null;
				this.formData.consignee = this.obj.consignee != null ? this.obj.consignee : null;
				this.formData.address = this.obj.address != null ? this.obj.address : null;
				this.formData.zipCode = this.obj.zipCode != null ? this.obj.zipCode : null;
				this.formData.phone = this.obj.phone != null ? this.obj.phone : null;
				this.formData.invoiceTitle = this.obj.invoiceTitle != null ? this.obj.invoiceTitle : null;
				this.formData.invoiceTaxNumber = this.obj.invoiceTaxNumber != null ? this.obj.invoiceTaxNumber : null;
				this.formData.remark = this.obj.remark != null ? this.obj.remark : null;
			},

			bindShippingMethodsChange(e) {
				this.shippingMethodsIdx = e.detail.value;
				this.orderShippingForm.shippingMethod = this.shippingMethods[e.detail.value].name;
				this.formData.shippingMethodId = this.shippingMethods[e.detail.value].id,
					this.formData.shippingMethodName = this.shippingMethods[e.detail.value].name;
			},

			bindExpressCompanysChange(e) {
				this.expressCompanysIdx = e.detail.value;
				this.orderShippingForm.expressCompany = this.expressCompanys[e.detail.value].name; //快递公司
				this.orderShippingForm.expressCompanyUrl = this.expressCompanys[e.detail.value].url; //快递公司网址
				this.orderShippingForm.expressCompanyCode = this.expressCompanys[e.detail.value].code; //快递公司代码
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
				this.orderShippingForm.region = this.multiArray[0][this.multiIndex[0]].name + ',' + this.multiArray[1][
					this.multiIndex[1]
				].name + ',' + this.multiArray[2][this.multiIndex[2]].name;
				this.formData.regionName = this.orderShippingForm.region;
			},
			checkedradio: function(value) {
				this.is_check = !value;
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
		font-size: 24rpx;
		color: #606266;
		height: 36px;
		padding: 0 12px 0 0;
		vertical-align: middle;
		flex-shrink: 0;
		box-sizing: border-box;
		line-height: 30rpx;
	}
	
</style>
<style lang="scss">
	.content {
		font-size: 23rpx;
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

	.uni-item-value {
		line-height: 72rpx;
		height: 72rpx;
		width: 100%;
		color: #666666;
	}

	.color-red {
		padding: 10rpx;
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
					// font-weight: 600;
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
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
			text-align: center;
			z-index: 999999;
		}
	}

	.price-input-box {
		border: #dcdcdc 2rpx solid;
		height: 80rpx;
		line-height: 80rpx;
		display: flex;
		border-radius: 5rpx;
		width: calc(100% - 200rpx);

		.fuhao {
			font-weight: 600;
			background: #eeeeee;
			border-right: #dcdcdc 2rpx solid;
			width: 60rpx;
			text-align: center;
		}

		.ipt {
			width: calc(100% - 60rpx);
			height: 50rpx;
			line-height: 50rpx;
			box-sizing: border-box;
			padding: 0 15rpx;
		}

		.detail_text {

			display: -webkit-box;
			overflow: hidden;
			text-overflow: ellipsis;
			word-wrap: break-word;
			white-space: normal !important;
			-webkit-line-clamp: 1;
			line-clamp: 1;
			-webkit-box-orient: vertical;
			padding: 25rpx;

		}
	}

	.coltitle {
		width: 38%;
	}

	.col-checkbox-line {
		line-height: 70rpx;
	}

	.col-parameter {
		width: calc(75% - 200rpx);
	}

	.col-parameter-name {
		width: calc(100% - 200rpx);
	}

	.col-parameter-del {
		width: calc(43% - 200rpx);
	}

	.showbox-tit-btn {
		margin-left: 14rpx;
		padding: 10rpx 8rpx;
		border: #d0d0d0 2rpx solid;
		border-radius: 10rpx;
		font-size: 25rpx;
		color: #666666;
	}



	.parameter-key {
		width: calc(63% - 200rpx);
		padding-left: 10rpx;
		border: solid 2rpx #d0d0d0;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.parameter-value {
		width: calc(100% - 200rpx);
		padding-left: 10rpx;
		border: solid 2rpx #d0d0d0;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.parameter-del {
		width: calc(35% - 200rpx);
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

	.showbox .dd .t1 {
		color: #f44c36;
		background: #fee9e6;
		padding: 4rpx 10rpx;
		margin-right: 20rpx;
	}

	.showbox .dd .youhuiquan {
		overflow: hidden;
	}
</style>