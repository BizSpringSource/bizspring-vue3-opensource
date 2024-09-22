<template>
	<view>
		<uni-forms ref="form" label-width="110px" :model="formData" validate-trigger="bind"
			err-show-type="toast">
			<view class="phone_form">
				<view class="phone_form_ul bg_white">
					<view class="padd-3 flex-row border-bottom1 ai-c pd-t-20 line-height-90">
						<uni-forms-item style="line-height: 80rpx;" label="配送方式:">
							<input disabled="disabled" class="uni-item-value " v-model="shippingMethod.name" />
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
							<input name="mobile" class="uni-item-value " v-model="formData.continueWeight" type="number"
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
							<input name="continuePrice" type="number" class="uni-item-value "
								v-model="formData.continuePrice" maxlength="20" id="continuePrice"
								placeholder="请输入续重价格"></input>
						</uni-forms-item>
					</view>
					<view class="phone_form_ul_li wx_btn">
						<button style="text-align: center;" class="btu" @tap="submitForm" >保存</button>
					</view>
				</view>
			</view>
		</uni-forms>
	</view>
</template>

<script>
	import {
		edit,
		update,
	} from "@/api/store/shipping_method.js"

	var app = getApp();
	export default {
		data() {
			return {
				formData: {
					firstWeight: null,
					continueWeight: null,
					firstPrice: null,
					continuePrice: null,
					shippingMethodId: null,
				},
				shippingMethod: null,
				id: null,
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
			this.id = options.id? options.id:null;
			this.formData.shippingMethodId = this.id;
			this.shipping_method_edit();
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
			shipping_method_edit: function() {
				uni.showLoading({
					title: 'Loading...'
				})
				edit({
					'id': this.id
				}).then(response => {
					uni.hideLoading();
					this.formData = response.data.data.obj.defaultFreight?response.data.data.obj.defaultFreight:this.formData;
					this.shippingMethod = response.data.data.obj;
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '用户名不存在或密码错误',
						duration: 2000
					});
				})
			},

			/** 提交按钮 */
			submitForm() {
				this.$refs['form'].validate().then(res => {
					this.shipping_method_update();
				}).catch(err => {
					console.log('errPlus', err);
				})
			},

			shipping_method_update() {
				update(this.formData).then(response => {
					uni.showToast({
						title: '保存成功',
						duration: 3000,
						icon: "none"
					});
					let pages = getCurrentPages();
					var prevPage = pages[pages.length - 2];
					uni.navigateBack({
						url: "list",
						delta: 1,
						success(event) {
							prevPage.$vm.shipping_method_list();
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
		background: $default-theme-color;
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
		/* background: url("../../../static/images/att.png") no-repeat 5px center; */
	}
</style>