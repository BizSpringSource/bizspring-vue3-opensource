<template>
	<view class="page">
		<view class="gwclist">
			<view v-if="objsList" v-for="(item, index) in objsList" :key="index">
				<view class="shop df">
					<view class="checkbox">
						<checkbox :checked="item.checked" @tap="bindCheckbox(item.skuId,index,!item.checked)">
						</checkbox>
					</view>
					<image class="sh_slt" :src="item.skuThumbnail"  @error="onErrorImg(item)" >
					</image>
					<view class="df_1">
						<view class="sp_text">
							<navigator :url="'../../goods/goods/detail?goodsId=' + item.goodsId">
								<view class="sp_tit ovh1">{{item.skuName}}
									<text style="margin-left: 30rpx;"
										v-if="item.specifications&&item.specifications.length>0">[</text><text
										v-for="(it,idx) in item.specifications">{{" "+it+" "}}</text><text
										v-if="item.specifications&&item.specifications.length>0">]</text>
								</view>
							</navigator>
							<view class="sp_jg">¥：{{item.price}}</view>
							<view style="display:flex;flex-direction:row;">
								<view style="display:flex;flex-direction:row;width:180rpx;border:2rpx solid #eee;">
									<view
										style="font-size:27rpx;width:62rpx;height:52rpx;text-align:center; line-height: 42rpx;"
										@tap="bindMinus(item.skuId,item.quantity)">
										-
									</view>
									<view
										style="width:70rpx;height:52rpx; line-height: 52rpx; border-width:0 2rpx 0 2rpx;border-style:solid;border-color:#eee;text-align:center;">
										{{item.quantity}}
									</view>
									<view style="width:62rpx;height:42rpx; line-height: 52rpx; text-align:center;"
										@tap="bindPlus(item.skuId,item.quantity)">
										+
									</view>
								</view>
								<navigator style="margin-left:170rpx;" :data-id="item.id" @tap="cart_del(item.skuId)">
									<image class="dle" src="/static/images/del.png"></image>
								</navigator>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!--样式-->
		<view class="container carts-list" v-if="objsList==undefined||objsList.length==0">
			<view class="pp">
				<image class="kong" src="/static/images/cart.jpg" mode="aspectFill"></image>
				<view class="cla">您的购物车是空的，立即去商城逛逛</view>
			</view>
		</view>
		<view class="cart_foot car_pay order_tt">
			<view class="total">
				<checkbox style="margin-right:20rpx;" :checked="selectedAllStatus" @tap="bindSelectAll">全选</checkbox>
				合计金额：
				<text class="red" id="order_pay_fee_all">¥{{cartSubTotal}}</text>
			</view>
			<navigator v-if="objsList&&0<objsList.length" class="paymen_btn" :url="'../../order/order/checkout?total=' + total">结算</navigator>
		</view>
	</view>
</template>
<script>

	import {
		get_current,
		modify,
		select,
		remove,
	} from "@/api/order/cart.js"
	
	var app = getApp();
	export default {
		data() {
			return {
				appGlobal: app.globalData,
				currentPage: 1,
				// 设置加载的第几次，默认是第一次  
				pageSize: 10,
				//返回数据的个数  
				orderBy: "",
				//排序字段
				orderType: "",
				//排序 asc正 desc反
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				searchLoadingComplete: false,
				//“没有数据”的变量，默认false，隐藏 
				objsList: [],
				page: 1,
				minusStatuses: ['disabled', 'disabled', 'normal', 'normal', 'disabled'],
				objsData: [],
				datas: [],
				selectedAllStatus: false,
				carts: [],
				toastHidden: false,
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 0,
					pageSize: 10,
				},
				currentCart: {},
				cartSubTotal: 0,
				recommendGoodss: [],
			};
		},
		components: {},
		props: {},
		onLoad(options) {
		},
		onShow: function() {
			var uInfo = uni.getStorageSync('userInfoStorage');
			if (uInfo) {
				this.cart_list();	
			}else{
				setTimeout(function() {
					uni.reLaunch({
						url: '../../upms/login/index'
					});
				}, 100);	
			}
		},
		methods: {
			/**
			 * 查询列表 list
			 */
			cart_list() {
				uni.showLoading({
					title: 'Loading...'
				})
				let currentCartStr= uni.getStorageSync('currentCart');
				if(currentCartStr){
					this.currentCart = JSON.parse(currentCartStr);
				}
				if (this.currentCart && this.currentCart.id) {
					get_current({
						"currentCartId": this.currentCart.id
					}).then(response => {
						uni.hideLoading();
						//下拉加载
						if (this.loadType === "reach") {
							const newObjsList = response.data.data.content;
							newObjsList.forEach(item => this.objsList.push(item));
							this.searchLoading = false;
							//加载
						} else if (this.loadType === "load") {
							this.objsList = response.data.data.cartItems;
							//下拉刷新
						} else if (this.loadType === "pullDown") {
							uni.stopPullDownRefresh();
						}
						this.cart_totol_sum();
					});
				}else{
					uni.hideLoading();
				}
				
				
			},
			cart_del: function(skuId) {
				let that = this;
				uni.showModal({
					title: '提示',
					content: '是否确认删除',
					success: function(res) {
						if (res.confirm) {
							remove({
								"skuId": skuId,
								"currentCartId": that.currentCart.id
							}).then(response => {
								that.loadType = "load";
								that.objsList = [];
								that.cart_list();
								that.cart_totol_sum()
								uni.showToast({
									title: '已删除',
								})
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
					fail: function() {
						// fail
					}
				});
			},
			//商品数量 -  减少
			bindMinus: function(skuId, quantity) {
				// 如果只有1件了，就不允许再减了 
				if (quantity > 1) {
					quantity--;
				}
				uni.showLoading({
					title: 'Loading...'
				})
				modify({
					"skuId": skuId,
					"quantity": quantity,
					"currentCartId": this.currentCart.id
				}).then(response => {
					uni.hideLoading();
					this.loadType = "load";
					this.objsList = [];
					this.cart_list();
					this.cart_totol_sum();
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			//商品数量 +  增加
			bindPlus: function(skuId, quantity) {
				quantity++;
				uni.showLoading({
					title: 'Loading...'
				})
				modify({
					"skuId": skuId,
					"quantity": quantity,
					"currentCartId": this.currentCart.id
				}).then(response => {
					uni.hideLoading();
					this.loadType = "load";
					this.objsList = [];
					this.cart_list();
					this.cart_totol_sum();

				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			cart_totol_sum: function() {
				var objsList = this.objsList; // 计算总金额
				var total = 0;
				for (var i = 0; objsList && i < objsList.length; i++) {
					if (objsList[i].checked) {
						total += objsList[i].subtotal;
					}
				} // 写回经点击修改后的数组
				this.cartSubTotal = total;
			},
			bindCheckbox: function(skuIds, index, checked) {
				if (index) {
					this.objsList[index].checked = checked;
				}
				uni.showLoading({
					title: 'Loading...'
				})
				select({
					"skuIds": skuIds,
					"checked": checked,
					"currentCartId": this.currentCart.id
				}).then(response => {
					uni.hideLoading();
					this.loadType = "load";
					this.objsList = [];
					this.cart_list();
					this.cart_totol_sum();
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			bindSelectAll: function(e) {
				var selectedAllStatus = !this.selectedAllStatus; // 取反操作
				var objsList = this.objsList;
				var skuIds = "";
				for (var i = 0; i < objsList.length; i++) {
					objsList[i].checked = selectedAllStatus;
					skuIds += objsList[i].skuId + ",";
				}
				this.selectedAllStatus = selectedAllStatus;
				this.bindCheckbox(skuIds, null, selectedAllStatus)
				this.cart_totol_sum();
			},
			
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_thumbnail.jpg");
				this.$set(item, 'logo', "/static/images/default_thumbnail.jpg");
			},
		}
	};
</script>
<style>
	/* #ifdef H5 */
	.cart_foot {
		width: 100%;
		background: #ffffff;
		height: 100rpx;
		display: flex;
		position: fixed;
		left: 0;
		bottom: 100rpx;
		z-index: 999999;
	}
	/* #endif */
	/* #ifndef H5 */
	.cart_foot {
		width: 100%;
		background: #ffffff;
		height: 100rpx;
		display: flex;
		position: fixed;
		left: 0;
		bottom: 0rpx;
		z-index: 999999;
	}
	/* #endif */
	.cart_foot .sc {
		width: calc(18% - 2rpx);
		display: flex;
		flex-direction: column;
		align-items: center;
		border-right: #eaeaea 2rpx solid;
		justify-content: center;
	}
	.cart_foot .cart {
		width: 18%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		position: relative;
	}
	.cart_foot .cart .car_num {
		border: 2px solid #fff;
		position: absolute;
		background: #ff0000;
		color: #fff;
		width: 18px;
		height: 18px;
		line-height: 18px;
		overflow: hidden;
		top: 2px;
		right: 5px;
		border-radius: 50%;
		font-size: 20rpx;
		text-align: center;
	}
	.cart_foot .ddgm {
		width: 32%;
		display: flex;
		flex-direction: column;
		align-items: center;
		background: #ff976a;
		justify-content: center;
		color: #ffffff;
	}
	.cart_foot .ptj {
		width: 64%;
		display: flex;
		justify-content: space-between;
	}
	.cart_foot .add_car {
		background: #ffc707;
		color: #ffffff;
		height: 70rpx;
		line-height: 70rpx;
		border-radius: 50rpx;
		width: calc(50% - 10rpx);
		font-weight: bold;
		text-align: center;
		margin-top: 14rpx;
	}
	.cart_foot .atonce {
		background: #ff0000;
		color: #ffffff;
		height: 70rpx;
		line-height: 70rpx;
		border-radius: 50rpx;
		width: calc(50% - 10rpx);
		font-weight: bold;
		text-align: center;
		margin-right: 10rpx;
		margin-top: 14rpx;
	}
	.clearbutton {
		background: #fff;
		display: inline-block;
		margin: 3% 3% 2% 3%;
		color: #FF8247;
		font-size: 23rpx;
		width: 94%;
	}
	.shop {
		background: #fff;
		overflow: hidden;
		width: 100%;
		box-sizing: border-box;
		padding: 15rpx;
		border-radius: 20rpx;
		margin-top: 20rpx;
	}
	.shop checkbox {
		margin-top: 27px;
	}
	.sh_slt {
		width: 80px;
		height: 80px;
		overflow: hidden;
		border-radius: 5px;
		margin-right: 4%;
		margin-left: 3%;
		float: left;
	}
	.sp_text {
		float: left;
		width: 100%;
	}
	.sp_tit {
		width: 100%;
		overflow: hidden;
		font-size: 23rpx;
	}
	.sp_neb {
		width: 100%;
		margin: 15rpx 0;
		overflow: hidden;
		font-size: 22rpx;
		color: #999;
	}
	.sp_jg {
		width: 100%;
		overflow: hidden;
		margin: 15rpx 0;
		font-size: 24rpx;
		color: #ff0000;
	}
	.dle {
		color: #999;
		font-size: 21rpx;
		float: right;
		margin-left: 10px;
		width: 35rpx;
		height: 35rpx;
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
		background: #FF8247;
		float: right;
		color: #fff;
		font-size: 24rpx;
		text-align: center;
		width: 40%;
		position: absolute;
		right: 0;
		line-height: 50px;
	}
	.bottom_clear {
		clear: clear;
		height: 1px;
		margin-top: 60px;
	}
	/*外部容器*/
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-between;
		box-sizing: border-box;
	}
	/*复选框样式*/
	.carts-list icon {
		margin-top: 60rpx;
		margin-right: 20rpx;
	}
	/*整体列表*/
	.carts-list {
		display: flex;
		flex-direction: column;
		padding: 20rpx 40rpx;
		background: #fff;
	}
	/*每行单元格*/
	.carts-item {
		display: flex;
		flex-direction: row;
		height: 150rpx;
		/*width属性解决标题文字太短而缩略图偏移*/
		width: 100%;
		border-bottom: 1px solid #eee;
		padding: 30rpx 0;
	}
	/*左部图片*/
	.carts-image {
		width: 150rpx;
		height: 150rpx;
	}
	.kong {
		width: 120rpx;
		height: 120rpx;
	}
	/*右部描述*/
	.carts-text {
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
	/*右上部分标题*/
	.carts-title {
		margin: 10rpx;
		font-size: 23rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		width: 201rpx;
	}
	/*右下部分价格与数量*/
	.carts-subtitle {
		font-size: 22rpx;
		color: darkgray;
		padding: 0 20rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}
	/*价格*/
	.carts-price {
		color: #f60;
	}
	/*底部按钮*/
	.carts-footer {
		width: 100%;
		height: 80rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		position: fixed;
		bottom: 0;
		border-top: 1px solid #eee;
		background: #ffffff;
		z-index: 999;
	}
	/*复选框*/
	.carts-footer icon {
		margin-left: 20rpx;
		margin-top: 10rpx;
		color: #fff;
	}
	/*全选字样*/
	.carts-footer text {
		font-size: 24rpx;
		margin-left: 8rpx;
		line-height: 10rpx;

	}
	/*立即结算按钮*/
	.carts-footer .button {
		line-height: 80rpx;
		text-align: center;
		width: 220rpx;
		height: 80rpx;
		background-color: #FF8247;
		color: white;
		font-size: 26rpx;
		font-weight: 700;
		border-radius: 0;
		border: 0;
	}
	.stepper {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		padding-top: 40rpx;
	}
	.stepper text {
		background-color: #f2f2f2;
		margin: 5px 0;
		width: 26px;
		height: 26px;
		line-height: 26px;
		text-align: center;
		font-weight: 900;
		color: #939393;
		border-radius: 6rpx;
		border: 1px solid #fff;
	}
	.stepper input {
		width: 43px;
		height: 25px;
		text-align: center;
		border-radius: 6rpx;
		border-top: 1px solid #f2f2f2;
		border-bottom: 1px solid #f2f2f2
	}
	.modal-close {
		margin-left: 200rpx;
		padding: 20px 0;
		color: #939393;
	}
	.page {

		padding-bottom: 60rpx;
	}
	.reds {
		color: red;

	}
	.pp {
		text-align: center;
		padding: 100rpx 0;
	}
	.cla {
		font-family: '微软雅黑';
		color: #ccc;

	}
	.gwclist {
		width: 96%;
		margin: auto;
		overflow: hidden;
	}
	.cainixihuan {
		width: 94%;
		overflow: hidden;
		margin: auto;
	}
	.cainixihuan .biaoti {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 20rpx 0;
	}
	.cainixihuan .biaoti text {
		display: block;
	}
	.cainixihuan .biaoti .line {
		height: 2px;
		border-bottom: #b6b6b6 2rpx solid;
		width: 100rpx;
	}
	.cainixihuan .biaoti .wenzi {
		font-size: 24rpx;
		color: #7b7b7b;
		margin: 0 8rpx;
	}
	.phone_floor {
		width: 100%;
		overflow: hidden;
		margin: auto;
		padding-top: 20rpx;
		background: none !important;
		border: none !important;
	}
	.phone_floor h1 {
		padding: 10px;
		font-weight: normal;
		overflow: hidden;
		font-size: 23rpx;
	}
	.phone_floor h1 .fl {
		border-left: 3px solid #e22c37;
		padding-left: 8px;
		height: 12px;
		line-height: 12px;
		float: left;
		margin-top: 3px;
	}

	.phone_floor h1 .fr {
		float: right;
	}

	.phone_floor h1 .fr a {
		color: #666;
		font-size: 21rpx;
	}

	.piclist {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		justify-content: space-between;
	}

	.piclist_nav {
		width: calc(50% - 10rpx);
		border-radius: 20rpx;
		background-color: #ffffff;
		margin-bottom: 20rpx;
		overflow: hidden;
		padding: 20rpx 0;
	}

	.piclist_nav_img {
		width: 100%;
		height: 362rpx;
	}

	.re_name {
		font-size: 23rpx;
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 15rpx;
	}

	.price {
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 15rpx;
	}

	.price .pr1 {
		font-size: 26rpx;
		color: #ff0000;
	}

	.price .pr2 {
		font-size: 20rpx;
		color: #959595;
		text-decoration: line-through;
		margin-left: 14rpx;
	}

	.car_pay {
		background-color: #ffffff !important;
		font-size: 23rpx;
	}
</style>
