<template>
	<view>
		<uni-forms ref="form" label-width="110px" :rules="rules" :model="formData" validate-trigger="bind"
			err-show-type="toast">
			<view class="phone_form">
				<view class="phone_form_ul bg_white">
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="地区:" name="regionId">
							<picker :disabled="null!=formData.id" class="uni-item-value " v-if="multiArray" mode="multiSelector" :range-key="'name'"
								@columnchange="changeRegionMultiColumn" @change="changeRegionMulti" :range="multiArray"
								:value="multiIndex">
								<view class="picker">
									{{multiArray[0][multiIndex[0]].name||''}},{{multiArray[1][multiIndex[1]].name||''}},{{multiArray[2][multiIndex[2]].name||''}}
								</view>
							</picker>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="首重(kg):" name="firstWeight">
							<input name="firstWeight" class="uni-item-value" type="text" maxlength="20" id="firstWeight"
								v-model="formData.firstWeight" placeholder="请输入首重"></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="续重(kg):" name="continueWeight">
							<input name="continueWeight" class="uni-item-value " v-model="formData.continueWeight" type="number"
								maxlength="15" id="continueWeight" placeholder="请输入续重"></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="首重价格(￥):" name="firstPrice">
							<input name="firstPrice" type="number" class="uni-item-value " v-model="formData.firstPrice"
								maxlength="20" id="firstPrice" placeholder="请输入首重价格"></input>
						</uni-forms-item>
					</view>

					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="续重价格(￥):" name="continuePrice">
							<input class="uni-item-value " name="continuePrice" type="text" v-model="formData.continuePrice"
								maxlength="20" id="continuePrice" placeholder="请输入续重价格"></input>
						</uni-forms-item>
					</view>
					<view class="footer">
						<view class="btn-add btn-full"  @tap="submitForm" >保存</view>
					</view>
				</view>
			</view>
		</uni-forms>
	</view>
</template>

<script>
	import {
		add,
		save,
		edit,
		update,
	} from "@/api/store/region_freight.js"

	var app = getApp();
	export default {
		data() {
			return {
				prevPage:null,
				index: 0,
				multiArray: [
					['北京'],
					['北京'],
					['东城区']
				],
				multiIndex: [0, 0, 0],
				formData: {
					id:null,
					firstWeight:null,
					continueWeight:null,
					firstPrice:null,
					continuePrice:null,
					shippingMethodId:null,
					storeId:null,
					regionId:null,
					regionName:null,
				},
				queryParams: {
					id: null,
					shippingMethodId:null,
				},
				id:null,
				regionId: null,
				shippingMethod:null,
				rules: {
					firstWeight: {
						rules: [{
							required: true,
							errorMessage: '首重不能为空'
						}]
					},
					continueWeight: {
						rules: [{
							required: true,
							errorMessage: '续重不能为空'
						}]
					},
					firstPrice: {
						rules: [{
							required: true,
							errorMessage: '首重价格不能为空'
						}]
					},
					continuePrice: {
						rules: [{
							required: true,
							errorMessage: '续重价格不能为空'
						}]
					},

				},
			};
		},

		components: {},
		props: {},
		onLoad(options) {
			let pages = getCurrentPages();
			this.prevPage = pages[pages.length - 2];
			this.queryParams.shippingMethodId = options.shippingMethodId?options.shippingMethodId:null;
			this.formData.shippingMethodId = this.queryParams.shippingMethodId;
			this.queryParams.id = options.id?options.id:null;
			this.formData.id = this.queryParams.id;
			if (this.formData.id && this.formData.id != "") {
				this.region_freight_edit();
			} else {
				this.region_freight_add();
			} 
		},
		onReady() {
			this.$refs.form.setRules(this.rules);
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
			region_freight_add: function() {
				//获取用户订单数据
				add(this.queryParams).then(response => {
					this.shippingMethod=response.data.data.shippingMethod ? response.data.data.shippingMethod:null;
					var regions = response.data.data.regions;
					var multiArray = new Array();
					var array1 = [];
					var array2 = [];
					var array3 = [];
					var index = 0;
					for (var i = 0; i < regions.length; i++) {
						if (regions[i].level == 0) {
							array1[index] = regions[i];
							index++;
						}
					}
					if (array1[0] && array1[0].children) {
						array2 = array1[0].children;
					}
					if (array1[0] && array2[0].children) {
						array3 = array2[0].children;
					}
					multiArray[0] = array1;
					multiArray[1] = array2;
					multiArray[2] = array3;
					this.formData.regionId = multiArray[2][0].id;
					this.multiArray = multiArray;
					this.formData.regionName = this.multiArray[0][this.multiIndex[0]].name + "," + this
						.multiArray[1][this.multiIndex[1]].name + "," + this.multiArray[2][this.multiIndex[2]]
						.name;
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '用户名不存在或密码错误',
						duration: 2000
					});
				})
			},
			region_freight_edit: function() {
				uni.showLoading({
					title: 'Loading...'
				})
				edit(this.queryParams).then(response => {
					uni.hideLoading();
					this.formData = response.data.data.obj;
					let regionId = this.formData.regionId;
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
						title: '用户名不存在或密码错误',
						duration: 2000
					});
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
				this.formData.regionName = this.multiArray[0][this.multiIndex[0]].name + "," + this.multiArray[1][this
					.multiIndex[1]
				].name + "," + this.multiArray[2][this.multiIndex[2]].name;
			},


			/** 提交按钮 */
			submitForm() {
				this.$refs['form'].validate().then(res => {
					if (this.formData.id != null) {
						this.region_freight_update();
					} else {
						this.region_freight_save();
					}
				}).catch(err => {
					console.log('errPlus', err);
				})
			},

			region_freight_save() {
				save(this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 3000,
						icon: "none"
					});		
					let that=this;
					uni.navigateBack({
						url: "list",
						delta: 1,
						success(event) {
							that.prevPage.$vm.region_freight_list();
						}
					})
				});
			},
			region_freight_update() {
				update(this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 3000,
						icon: "none"
					});
					let that=this;
					uni.navigateBack({
						url: "list",
						delta: 1,
						success(event) {
							that.prevPage.$vm.region_freight_list();
						}
					})
				})
			},
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
		font-size: 20px !important;
		color: #a2a2a2;
		margin-left: 10px;
	}

	.phone_hd .home {
		top: 10px;
	}

	.phone_form {
		width: 96%;
		overflow: hidden;
		padding: 0px 2%;
		margin-top: 10px;
		margin-bottom: 15px;
	}

	.phone_form_ul_li {
		margin: 10px 0px;
		display: block;
		font-size: 24rpx;
		color: #666;
		overflow: hidden;
		line-height: 30px;
	}

	.phone_form_ul_li.ip {
		border-bottom: 1px solid rgba(246, 246, 246, 0.88);
		background-color: #fff;
		border-radius: 2px;
	}

	.phone_form_ul_li.ip input {
		width: 99%;
		border: none;
		height: 38px;
		font-size: 24rpx;
		text-indent: 10px;
		border-radius: 2px;
	}

	.phone_form_ul_li.ip_btn input {
		width: 100%;
		border-radius: 2px;
		background-color: red;
		color: #fff;
		font-size: 24rpx;
		border: none;
		height: 40px;
		line-height: 40px;
		-webkit-appearance: none;
	}

	.phone_form_ul_li.wx_btn {
		width: 100%;
		border-radius: 2px;
		background-color: #ff0000;
		color: #fff;
		border: none;
		-webkit-appearance: none;
		border-radius: 10rpx;
		color: #ffffff;
	}

	.phone_form_ul_li.wx_btn .btu {
		border: none !important;
		background: none !important;
		color: #ffffff !important;
	}

	.phone_form_ul_li.qt_btn {
		text-align: center;
	}

	.phone_form_ul_li.qt_btn image {
		width: 278rpx;
		height: 278rpx;
		margin: 39rpx;
		font-size: 24rpx;
		border: none;
		-webkit-appearance: none;
	}

	.phone_form_ul_li.yzm input {
		width: 70%;
		float: left;
		border: 1px solid #ddd;
		background-color: #fff;
		border-radius: 2px;
		font-size: 24rpx;
		text-indent: 10px;
		height: 38px;
		float: left;
	}

	.phone_form_ul_li.yzm img {
		margin-left: 10px;
		float: left;
		margin-top: 5px;
	}

	.phone_form_ul_li.bd_red {
		border-color: #EB512D;
	}

	.phone_form_ul_li.yz {
		color: #EB512D;
		text-indent: 10px;
		margin: 0px;
	}

	.phone_form_ul.yz_step2_li {
		width: 100%;
		float: left;
	}

	.phone_form_ul.yz_step2_li.sele_li {
		width: 50%;
	}

	.phone_form_ul.yz_step2 .tel_yz {
		width: 50%;
	}

	.phone_form_ul_li select {
		padding: 3px;
		font-size: 22rpx;
		width: 90%;
		border: 1px solid #ddd;
		float: left;
		color: #666;
	}

	.phone_form_ul_li select option {
		padding: 3px;
	}

	.phone_form_ul_li.tel_yz a {
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
		font-size: 22rpx;
		line-height: 24px;
		padding: 0px 2%;
		color: #666;
	}

	.phone_form_ul_li.text textarea {
		width: 98%;
		border: 1px solid #ddd;
		background-color: #fff;
		font-size: 24rpx;
		height: 50px;
		resize: none;
	}

	.phone_form_ul_li.score .red {
		font-size: 24rpx;
		padding: 0px 2px;
	}

	.phone_form_ul_li .code {
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

	.phone_form_ul_li.att {
		border: 1px solid #EB512D;
		color: #EB512D;
		font-size: 22rpx;
		border-radius: 2px;
		padding: 5px;
		padding-left: 30px;
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