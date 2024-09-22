<template>
	<view>
		<uni-forms ref="form" label-width="80px" :rules="rules" :model="formData" validate-trigger="bind"
			err-show-type="toast">
			<!--注册页-->
			<view class="phone_form">
				<view class="phone_form_ul bg_white">
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="收货人:" name="consignee">
							<input name="trueName" class="uni-item-value" type="text" maxlength="20" id="userName"
								v-model="formData.consignee" placeholder="请输入收货人"></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="电话:" name="phone">
							<input name="mobile" class="uni-item-value " v-model="formData.phone" type="number"
								maxlength="15" id="userName" placeholder="请输入电话"  ></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item label="邮编:" name="zipCode">
							<input name="mobile" type="number" class="uni-item-value " v-model="formData.zipCode"
								maxlength="20" id="userName" placeholder="请输入邮编" ></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="地区:" name="regionId">
							<picker class="uni-item-value " v-if="multiArray" mode="multiSelector" :range-key="'name'"
								@columnchange="changeRegionMultiColumn" @change="changeRegionMulti" :range="multiArray"
								:value="multiIndex">
								<view class="picker">
									{{multiArray[0][multiIndex[0]].name||'所在地区'}},{{multiArray[1][multiIndex[1]].name||''}},{{multiArray[2][multiIndex[2]].name||''}}
								</view>
							</picker>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item required label="地址:" name="address">
							<input class="uni-item-value " name="region_info" type="text" v-model="formData.address"
								maxlength="20" id="userName" placeholder="请输入地址" ></input>
						</uni-forms-item>
					</view>
					<view class="padd-3 flex-row ai-c pd-t-20 line-height-90">
						<uni-forms-item label="是否默认:" name="isDefault">
							<switch class="uni-item-value" :checked="formData.isDefault" name="default_flag"
								@change="changeDefaultAddr">
							</switch>
						</uni-forms-item>
					</view>
					<view class="phone_form_ul_li wx_btn">
						<button style="text-align: center;" class="btu" @tap="submitForm" >确 定</button>
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
		// del
	} from '@/api/upms/address.js'

	var app = getApp();
	export default {
		data() {
			return {
				index: 0,
				multiArray: [
					['北京'],
					['北京'],
					['东城区']
				],
				multiIndex: [0, 0, 0],
				formData: {
					isDefault: true
				},
				addressId: "",
				regionId: "",

				rules: {
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
					regionId: {
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

				},
			};
		},

		components: {},
		props: {},
		onLoad(options) {
			this.addressId = options.id;
			if (this.addressId && this.addressId != "") {
				this.address_edit();
			} else {
				this.address_add();
			} //调用应用实例的方法获取全局数据
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
			address_add: function() {
				//获取用户订单数据
				add().then(response => {
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
			onShow: function() {
				// this.address_add();
			},
			address_edit: function() {
				uni.showLoading({
					title: 'Loading...'
				})
				edit({
					'id': this.addressId
				}).then(response => {
					uni.hideLoading();
					this.formData = response.data.data.address;
					// this.order_update_init();

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

			changeDefaultAddr() {
				this.formData.isDefault = !this.formData.isDefault;
			},

			/** 提交按钮 */
			submitForm() {
				this.$refs['form'].validate().then(res => {
					if (this.formData.id != null) {
						this.address_update();
					} else {
						this.address_save();
					}
				}).catch(err => {
					console.log('errPlus', err);
				})
			},

			address_save() {
				save(this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 3000,
						icon: "none"
					});
					uni.navigateBack({
						url: "../../upms/address/address"
					})
				});
			},
			address_update() {
				update(this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 3000,
						icon: "none"
					});
					uni.navigateBack({
						url: "../../upms/address/address"
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
	    font-size: 23rpx;
	    color: #606266;
	    height: 36px;
	    padding: 0 12px 0 0;
	    vertical-align: middle;
	    flex-shrink: 0;
	    box-sizing: border-box;
	}
</style>
<style>
	.content {
		font-size: 22rpx;
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
		font-size: 28rpx !important;
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
		font-size: 23rpx;
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
		font-size: 23rpx;
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
		font-size: 23rpx;
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
		font-size: 21rpx;
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
		font-size: 21rpx;
		line-height: 24px;
		padding: 0px 2%;
		color: #666;
	}

	.phone_form_ul_li.text textarea {
		width: 98%;
		border: 1px solid #ddd;
		background-color: #fff;
		font-size: 23rpx;
		height: 50px;
		resize: none;
	}

	.phone_form_ul_li.score .red {
		font-size: 24px;
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
		font-size: 21rpx;
		border-radius: 2px;
		padding: 5px;
		padding-left: 30px;
		/* background: url("../../../static/images/att.png") no-repeat 5px center; */
	}
</style>