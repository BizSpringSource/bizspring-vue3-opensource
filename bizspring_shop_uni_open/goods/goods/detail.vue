<template>
	<view>
		<view class="details_index">
			<view class="details_top">
				<view class="details_photo" id="details_photo">
					<view class="deta_photo">
						<view class="tempWrap" style="overflow: hidden; position: relative;">
							<view class="page-section page-section-spacing swiper">

								<view class="btn-favorite">
									<view @tap="goods_favorite_add" class="btn-box">
										<text class="iconfont icon-gerenguanzhu"></text>
										<text>收藏</text>
									</view>
								</view>
								<swiper :indicator-dots="indicatorDots" class="swiper_group" style="height:750rpx;"
									:autoplay="autoplay" :interval="interval" :duration="duration">
									<swiper-item v-for="(item, index) in obj.goodsImages" :key="index">
										<image v-if="item" class="details_photo_img" :src="item.large"></image>
									</swiper-item>
								</swiper>
							</view>
						</view>
					</view>
				</view>
				<view v-if="obj" class="details_price">
					<view class="details_price_title">
						<span class="ziying">{{msg['Store_Type_'+store.type]}}</span>
						{{obj.name}}
					</view>
				</view>
			</view>
		</view>
		<!--商品规格-->
		<view class="details_box">
			<view class="stand">
				<view id="show_spec" class="line2">
					<view class="fl" decode="true">
						<text>已选</text>
					</view>
					<view class="fc">
						<text>{{skuSelectedTab}}</text>
					</view>
					<view class="fr" @tap="showbox" data-name="guigeshow">
						<text class="iconfont icon-gengduo"></text>
					</view>
				</view>
				<view id="show_spec" class="line2" v-if="address&&address.region">
					<view class="fl" decode="true">
						<text>送至</text>
					</view>
					<view class="fc">
						<text>{{address.region.parent.parent.regionName}}{{address.region.parent.regionName}}{{address.region.regionName}}</text>
					</view>
				</view>
				<view id="show_spec" class="line2" style="margin-bottom: 0;">
					<view class="fl" decode="true">
						<text>服务</text>
					</view>
					<view class="fc">
						<image
							src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAsCAYAAAAehFoBAAADj0lEQVRYR92ZPWgUQRTH39xuAiZ7d1olERstcgmoGDytA5aKCrY2inaK4geiiKKFIviBKFhoYWkjKNiKKaz0JKKFuRSKICQWYu52L0Jye0/mzcxmb5LN7uYu5sY0w82+3fnPL29m3nvDIOUfbstv4K+49cYh3rIG7uMtAm6n38D6wp9EwJ+y/xPZZdgr3mbtzHPq/1z5nUYCS2NMA3a6YBwdtYno9IfzRATxsiAKPUtPFr+F+xVx3Z4BzJIds/cS8YmZsSTwYgkbI/hPIb+Zz3iO+cLXEEaELwoyiOwRby2WecbbnoGRj/R8bKze5MPyPzQ7Nb6D9zfAPyx9fj/Zs66jbSFsjGAldB78t5LURkn2JW+dbuvISlZ3Ev9MYrPIh40RrBZXdbr0rslnER6Sj016J8n3pE/qvpqEThKbAFimsYfG7dv5NLw2AsLGCa4OZS8SAcQbYZ/Nlr2DSci0ahPlisDYJf7t3IR7U+oSQxkjWB213pz/I0zJBmsr/72uXGk6uVolqb8fSVYaqn3f6bY2ibUlYwNjBFeHs8doMg18LH3kPq3Osne63TTD34ski+wO6WBIMQwCnKL3Muw49Rsn2C04L+RMDvDWAmsXb3vLldJqEI4jm5t0z/Fxa4V8kbc++O/DuxYzTnC10PtVzEBkCs5AMU+/tairVdpJyapx1EHmTZUqwgNk5mKcYLfg1MIzyJVrW5ai6Q6tH6XNBOp3adGCfYZ2k5hMIS1ZfWwdKPfh/1swYv2NXLXL5mStklWkWya85oLTLrolDpom0jbid05Hz1hAnmBqn43bdYLkd6o0L2xFNs6ME7zSgyOKNALMSHqUC6Ylq8i3/aRbM8H6wGmJLHo/OKpE1JXUZxftv4PZ29TH8Cy1UdFaxwuOCuCDCD9hdbE2nKMamQ84QGS/uE/idoLwc7UrwK/xLO/XE4rYjKNjBatZukPOFVrUCNfkSUaVnn+VNQc6tPicMbgqYhbvutQlTI0TnLSQksYv09i6g84DsW3DCdmO01roL+4msjI+T135SSMijW1qwerjC1FW/bXoY1QnZgBtrV6qcerg3yOiAJRTqpihC2yqrel1kWWql4YI1kmvVgUeAKjOoO4+kAH5bDdadDsVVXGKvONY7Qp82wUr0kH2Ol2iKiIgXAiTUXbB3YfMbhf6RTYefYsEt/hzp78oqqYx2XriWyTPFMH61tTxF4udJvgvaBFJhuIjj/8AAAAASUVORK5CYII="
							mode="widthFix" style="width: 30rpx; margin-right: 10rpx"></image>
						<text>7天无理由退换</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 商品详情 -->
		<view class="details_box">
			<view class="stand">
				<view id="show_introduce" style="width: 100%; margin-bottom: 100rpx;">
					<view class="biaoti3">商品详情</view>
					<jyf-parser :html="obj.introduction"></jyf-parser>
				</view>
			</view>
		</view>
		<!--底部导航-->
		<view class="pintuan_foot">
			<navigator v-if="showStoreCenter==='true'" :url="'../../store/store/index?id='+obj.storeId" class="sc">
				<image src="../../static/images/dianpu.png" style="width: 36rpx;height: 36rpx;"></image>
				<view class="t">店铺</view>
			</navigator>
			<navigator url="../../pages/cart/list" open-type="switchTab" class="cart">
				<text class="iconfont icon-gouwuche gwc"></text>
				<text>购物车</text>
			</navigator>
			<!-- 普通商品 -->
			<view class="ptj" >
				<view @tap="cart_add_handler" class="add_car">加入购物车</view>
				<view @tap="buy_handler" class="atonce">立即购买</view>
			</view>
		</view>
		<!--规格弹窗-->
		<view class="showbg" v-if="guigeshow"></view>
		<view class="showbox" v-if="guigeshow">
			<view class="guanbi" @tap="guanbi" data-name="guigeshow">
				<text class="iconfont icon-guanbi"></text>
			</view>
			<view class="guige">
				<image mode="widthFix" :src="obj.thumbnail" class="img">
				</image>
				<view>
					<view class="price">
						<text class="t1">￥</text>
						<text class="t2">{{sku.price}}</text>
					</view>
					<view v-if="skuSelectedTab" class="color-gray2">已选：{{skuSelectedTab}}</view>
					<view v-if="sku.sn" class="kucun">库存：{{sku.stock}} 编号：{{sku.sn}}</view>
				</view>
			</view>
			<view class="dl" v-for="(item, index) in obj.specificationItems">
				<view class="dt">{{item.name}}</view>
				<view class="dd">
					<view class="taglist">
						<text v-if="itm.isSelected" v-for="(itm, idx) in item.entries" :key="idx"
							@tap="skuShowTab(index,idx,itm)"
							:class="idxs[index]==idx ? 'tag on': 'tag'">{{itm.value}}</text>
					</view>
				</view>
			</view>
			<view class="dl shuliang">
				<view class="dt">数量</view>
				<view class="dd">
					<text class="jian" @tap="bindMinus"> - </text>
					<text class="num">{{formData.quantity}}</text>
					<text class="jia" @tap="bindPlus"> + </text>
				</view>
			</view>
			<view class="bottom">

				<view class="ptj">
					<view @tap="cart_add_handler" class="add_car">加入购物车</view>
					<view @tap="buy_handler" class="atonce">立即购买</view>
				</view>
			</view>
		</view>
	</view>
</template>
<script>
	import {
		detail,
	} from "@/api/goods/goods.js"
	import {
		add
	} from "@/api/order/cart.js"
	import {
		show_store_center
	} from '@/api/upms/profile.js'
	import {
		add as add_favorite
	} from "@/api/upms/goods_favorite.js"
	var app = getApp();
	export default {
		data() {
			return {
			    msg:{
                    'Store_Type_GENERAL':'普通',
                    'Store_Type_SELF':'自营'
			    },
				address: [],
				goodsId: "",
				indicatorDots: true,
				duration: 1000,
				page: 2,
				index: 2,
				// 滑动
				datas: [],
				background: ['demo-text-1', 'demo-text-2', 'demo-text-3'],
				vertical: true,
				autoplay: true,
				interval: 2000,
				guigeshow: false,
				formData: {
					skuId: null,
					quantity: 1,
					currentCartId: ''
				},
				goodsId: '',
				skuId: '',
				obj: {},
				idxs: [],
				skuSelectedTab: '',
				skus: [],
				sku: {},
				cartQuantity: 0,
				currentCart: {},
				store: {},
				goodsLabels: [],
				showStoreCenter: false,
				notJoinGroup: false,
				messages:{
					'Store_Type_GENERAL':'普通',
					'Store_Type_SELF':'自营'
				}
			};
		},
		components: {},
		props: {},
		onLoad(options) {
			//商品详情load 后,这里暂时调用
			this.goodsId = options.goodsId ? options.goodsId : null;
			this.skuId = options.skuId ? options.skuId : null;
			this.goods_detail();
			this.cartQuantity = uni.getStorageSync('cartQuantity');
			let currentCart = uni.getStorageSync('currentCart');
			if (currentCart) {
				this.currentCart = JSON.parse(currentCart);
			}
			this.formData.currentCartId = this.currentCart.id;
			this.load_show_store_center();
		},
		onShow: function() {
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
			load_show_store_center() {
				show_store_center().then(response => {
					this.showStoreCenter = response.data.data.showStoreCenter;
				})
			},
			goods_detail() {
				uni.showLoading({
					title: 'Loading...'
				})
				detail({
					"goodsId": this.goodsId,
					"skuId": this.skuId
				}).then(response => {
					let data = response.data;
					uni.hideLoading();
					this.obj = data.data.goods;
					this.store = data.data.store;
					this.skus = data.data.skus;
					if (0 <= this.obj.specificationItems && 1 == this.skus.length) {
						this.formData.skuId = this.skus[0].id;
					}
					this.defaultSkuSelect();
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			// 倒计时
			goods_favorite_add() {
				add_favorite({
					"goodsId": this.obj.id
				}).then(response => {
					let statusCode = response.statusCode;
					let message = response.data.message;
					if (200 == statusCode) {
						uni.showToast({
							title: '已收藏',
							duration: 2000,
							icon: 'none'
						});
					} else {
						uni.showToast({
							title: message,
							duration: 2000
						});
					}
				});
			},
			//默认sku选中
			defaultSkuSelect: function() {				
				for(let i=0;i<this.skus.length;i++){
					if(this.skus[i].isDefault){
						this.sku=this.skus[i];
						this.formData.skuId = this.sku.id;
					}
				}
			
				for (let i = 0; i < this.obj.specificationItems.length; i++) {
					let entries = this.obj.specificationItems[i].entries;
					for (let x = 0; x < entries.length; x++) {
						
						if(this.sku.specificationValues[i].value==entries[x].value){
							this.$set(this.idxs, i,x);
						}
					}
				}
			},
			//颜色
			skuShowTab: function(index, idx, item) {
				this.$set(this.idxs, index, idx);
				this.skuSelectedTab = '';
				let selectedSpecItems = [];
				for (let i = 0; i < this.obj.specificationItems.length; i++) {
					let entries = this.obj.specificationItems[i].entries;

					for (let x = 0; x < entries.length; x++) {
						if (this.idxs[i] == x) {
							this.skuSelectedTab += entries[x].value + "  ";
							selectedSpecItems.push(entries[x]);
						}
					}
				}
				// 这做判断  skus  中  同时 包含  selectedSpecItems 的id  和 value的sku
				for (let i = 0; i < this.skus.length; i++) {
					let sku = this.skus[i];
					let isTrue = false;
					if (sku.specificationValues.length == selectedSpecItems.length) {
						for (let x = 0; x < sku.specificationValues.length; x++) {
							let specificationValue = sku.specificationValues[x];
							if (x == 0) {
								isTrue = true;
							}
							let selectedSpecItem = selectedSpecItems[x];
							if (specificationValue.id == selectedSpecItem.id && specificationValue.value ==
								selectedSpecItem.value) {
								isTrue = isTrue && true;
							} else {
								isTrue = isTrue && false;
							}
						}
						if (isTrue) {
							this.formData.skuId = sku.id;
							this.sku = sku;
							break;
						}
					}
				}
			},
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_thumbnail.jpg");
				this.$set(item, 'logo', "/static/images/default_thumbnail.jpg");
			},
			//尺寸
			guanbi: function(e) {
				var that = this;
				var name = e.currentTarget.dataset.name;
				if (name == 'guigeshow') {
					that.guigeshow = false;
				}
			},
			showbox: function(e) {
				var that = this;
				var name = e.currentTarget.dataset.name;

				if (name == 'guigeshow') {
					that.guigeshow = true;
				}
			},
			cart_add_handler: function(e) {
				if (!(this.formData.skuId && this.formData.skuId.length > 0)) {
					if (this.guigeshow) {
						uni.showToast({
							title: "请选择规格",
							duration: 2000
						});
					}
					this.guigeshow = true;
				} else {
					this.cart_add();
				}
			},
			buy_handler: function(e) {
				this.notJoinGroup = false;
				if (!(this.formData.skuId && this.formData.skuId.length > 0)) {
					this.guigeshow = true;
				} else {
					//购买逻辑
					uni.navigateTo({
						url: '../../order/order/checkout?skuId=' + this.formData.skuId + '&quantity=' + this
							.formData.quantity
					})
				}
			},

			cart_add: function(e) {
				var uInfo = uni.getStorageSync('userInfoStorage');
				if (uInfo) {
					//添加到购物车
					uni.showLoading({
						title: "Loading..."
					})
					add(this.formData).then(response => {
						uni.hideLoading();
						let currentCart = response.data.data.currentCart;
						if (currentCart) {
							this.cartQuantity = response.data.data.quantity;
							this.formData.currentCartId = currentCart.id;
							uni.setStorageSync("currentCart", JSON.stringify(currentCart));
							uni.setStorageSync("cartQuantity", this.cartQuantity);
							this.guigeshow = false;
							uni.showToast({
								title: '已加入购物车',
								duration: 2000
							});
						}
					}).catch(error => {
						uni.hideLoading();
						console.error("error", error);
						uni.showToast({
							title: '系统未知错误,请反馈给管理员',
							duration: 2000
						});
					})
				} else {
					setTimeout(function() {
						uni.reLaunch({
							url: '../../upms/login/index'
						});
					}, 100);
				}

			},

			bindMinus: function() {
				this.formData.quantity -= 1;
			},
			bindPlus: function() {
				this.formData.quantity += 1;
			},

		}
	};
</script>

<style scoped>
	/deep/ img {
	    display: block;
	}
	
	/deep/ .details_photo span{
		position: inherit;
	}
	
</style>

<style>
	page {
		font-size: 22rpx;
	}


	.btn-favorite {
		position: fixed;
		right: 2%;
		color: #ffffff;
		/* top: 30rpx; */
		background: deepskyblue;
		text-align: center;
		margin-left: 14rpx;
		padding: 10rpx 8rpx;
		border-radius: 10rpx;
		font-size: 23rpx;
		z-index: 999;
	}


	.cont {
		margin-top: 20rpx;
		height: calc(100% - 90rpx);
		overflow: hidden;
		line-height: 54rpx;
	}

	.parameter-tbody {
		vertical-align: middle;
		border-color: inherit;
		width: 100%;
		line-height: 30px;
		font-size: 21rpx;
		border-collapse: collapse;
	}

	.parameter-tr {
		display: flex;
		vertical-align: inherit;
		border-color: inherit;
		border-collapse: collapse;
	}

	.parameter-group {
		padding-left: 20rpx;
		width: 100%;
		text-align: left;
		background-color: #f1f1f1;
		border: 1upx solid #e4e4e4;
		border-collapse: collapse;
	}

	.parameter-th {
		justify-content: flex-start;
		width: 25%;
		padding: 0px 10px;
		text-align: right;
		border: 1upx solid #e4e4e4;
	}

	.parameter-td {
		justify-content: flex-end;
		width: 65%;
		padding: 0px 10px;
		border: 1upx solid #e4e4e4;
	}

	.qgprice {
		width: 100%;
		overflow: hidden;
		background: linear-gradient(90deg, #f23256, #ff0000);
		display: flex;
		justify-content: space-between;
	}

	.qgprice .rgt {
		width: 200rpx;
		background: #ffeae9;
		padding: 10rpx 0;
		text-align: center;
	}

	.qgprice .rgt .sj text {
		background: #f23256;
		padding: 0 12rpx;
		border-radius: 10rpx;
		font-size: 20rpx;
		color: #ffffff;
	}

	.qgprice .lft {
		width: calc(100% - 200rpx);
		padding: 10rpx;
		color: #ffffff;
		box-sizing: border-box;
	}

	.qgprice .lft .pr1 {
		font-size: 26rpx;
		font-weight: bold;
	}

	.qgprice .lft .pr2 {
		font-size: 21rpx;
		display: flex;
	}

	.qgprice .lft .pr2 .ms {
		font-size: 20rpx;
		border-radius: 20rpx;
		border: #ffffff 2rpx solid;
		margin-right: 10rpx;
	}


	.pintuanguize {
		box-sizing: border-box;
		margin-top: 20rpx;
		padding: 20rpx;
		background: #ffffff;
		overflow: hidden;
	}

	.pintuanguize .tit {
		font-weight: bold;
		margin-bottom: 20rpx;
	}

	.pintuanguize .gzbox {
		display: flex;
	}

	.pintuanguize .gzbox .li {
		display: flex;
		flex-direction: column;
		align-items: center;
		flex: 1;
		font-size: 21rpx;
		color: #686868;
	}

	.pintuanguize .gzbox .li .t1 {
		border: #ddd 2rpx solid;
		border-radius: 40rpx;
		margin-bottom: 15rpx;
		width: 40rpx;
		height: 40rpx;
		text-align: center;
		line-height: 40rpx;
	}

	.pintuanguize .gzbox .li .t1:after {
		content: '';
		width: 100%;
		height: 2rpx;
		background: #dddddd;
	}

	.pintuantuijian {
		box-sizing: border-box;
		margin-top: 20rpx;
		padding: 20rpx;
		background: #ffffff;
		overflow: hidden;
	}

	.pintuantuijian .tit {
		font-weight: bold;
		margin-bottom: 20rpx;
	}

	.pintuantuijian .box {
		width: 100%;
		height: 310rpx;
		white-space: nowrap;
	}

	.pintuantuijian .box .item {
		width: 200rpx;
		height: 310rpx;
		display: inline-block;
		margin-right: 20rpx;
	}

	.pintuantuijian .box .item image {
		width: 100%;
		height: 200rpx;
		border-radius: 8rpx;
	}

	.pintuantuijian .box .item .tit {
		font-size: 21rpx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		font-weight: normal;
	}

	.pintuantuijian .box .item .info {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.pintuantuijian .box .item .info .price {
		color: #ff0000;
	}

	.pintuantuijian .box .item .info .btn {
		color: #ffffff;
		background: #ff0000;
		padding: 4rpx 14rpx;
		border-radius: 8rpx;
		font-size: 20rpx;
	}


	.miaoList {
		margin: 20rpx 0 120rpx 0;
		padding: 0 20rpx;
		overflow: hidden;
	}

	.scroll {
		width: 100%;
		overflow: hidden;

	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.scroolbox {
		overflow: hidden;
		display: flex;
	}

	.msitem {
		width: 220rpx;
		height: 360rpx;
		margin-right: 20rpx;
		overflow: hidden;
	}

	.msitem .img {
		width: 200rpx;
		overflow: hidden;
	}

	.msitem .img .image {
		width: 100%;
	}

	.msitem .title1 {
		font-size: 23rpx;
		width: 100%;
		overflow: hidden;
		height: 80rpx;
	}

	.msitem .price {
		display: flex;
		align-items: center;
	}

	.msitem .price .msjia {
		color: #ff0000;
		font-size: 23rpx;
		margin-right: 10rpx;
	}

	.msitem .price .msjia .ic {
		font-size: 20rpx;
	}

	.msitem .price .yuanjia {
		font-size: 20rpx;
		color: #8e8e8e;
		text-decoration: line-through;
	}


	.details_index {
		padding-bottom: 0px;
		width: 100%;
		overflow: hidden;
		margin-top: 0 !important;
	}

	.details_top {
		width: 100%;
		background: #fff;
		overflow: hidden;
		border-radius: 0 0 20rpx 20rpx;
		border: none !important;
	}

	.details_photo {
		width: 100%;
		height: 750rpx;
		margin-left: auto;
		margin-right: auto;
	}

	.deta_photo {
		width: 100%;
		height: 100%;
		margin-left: auto;
		margin-right: auto;
		text-align: center;
		overflow: hidden;
	}

	.details_photo_img {
		width: 100%;
		height: 100%;
	}
	
	.details_photo span {
	    position: inherit;
	}

	.details_photo_span {
		font-size: 21rpx;
		color: #444;
		position: inherit;
		top: 0px;
		left: 5px;
	}

	.details_top_section {
		width: 100%;
		padding-top: 10px;
		padding-bottom: 10px;
		overflow: hidden;
	}

	.details_price {
		overflow: hidden;
		box-sizing: border-box;
		width: 100%;
		padding: 20rpx;
	}

	.details_price_price {
		width: 100%;
		display: flex;
		align-items: center;
	}

	.details_price_price .price1 {
		color: #ff0000;
		font-weight: bold;
	}

	.details_price_price .price1 .store_price {
		font-size: 21rpx;
	}

	.details_price_price .price1 .xianjia {
		font-size: 28rpx;
	}

	.details_price_price .price2 {
		font-size: 23rpx;
		text-decoration: line-through;
		margin-left: 30rpx;
		color: #999999;
	}

	.details_price_price .yishounum {
		font-size: 23rpx;
		margin-left: 30rpx;
		color: #999999;
	}

	.details_price .goodTag {
		display: flex;
		padding: 15rpx 0;
	}

	.details_price .goodTag .tag {
		padding: 8rpx 15rpx;
		background: #ff0000;
		color: #ffffff;
		font-size: 20rpx;
		border-radius: 4rpx;
		margin-left: 15rpx
	}

	.details_price_title {
		font-size: 24rpx;
		font-weight: bold;
		padding: 10rpx 15rpx;
	}

	.details_price_title .ziying {
		padding: 4rpx 10rpx;
		background: #ff0000;
		color: #ffffff;
		font-size: 20rpx;
		border-radius: 10rpx;
		margin-right: 20rpx;
	}

	.details_price_span {
		display: block;
		float: left;
	}

	.details_price_ul_li {
		width: 100%;
		float: left;
	}


	.details_price_ul_details_price_ads {
		font-size: 23rpx;
		color: #999;
		margin-top: 5px;
		padding-left: 10px;
	}

	.details_price_ul_details_price_ads_b {
		margin-right: 20px;
	}

	.details_star {
		display: block;
		width: 27px;
		height: 27px;
		background-size: 100%;
		margin-top: 4px;
		float: right;
		margin-right: 5px;
	}

	.details_star_img {
		margin-top: -1px;
	}

	.details_box {
		background: #fff;
		width: 100%;
		margin: auto;
		overflow: hidden;
		border-radius: 20rpx;
		margin-top: 10rpx;
		border: none !important;
	}

	.details_box .stand {
		width: 97%;
		margin: auto;
		margin-bottom: 20rpx;
		border: none !important;
	}

	.details_box .stand:last-child {
		width: 97%;
		margin: auto;
		margin-bottom: 20rpx;
		border: none !important;
	}

	.details_box .stand .fl {
		font-size: 23rpx;
		font-weight: bold;
	}

	.details_box .stand .fr {
		padding-left: 10rpx;
	}

	.details_box .stand .fc {
		float: left;
		margin-left: 20rpx;
		display: flex;
		align-items: center;
	}

	#show_introduce {
		overflow: hidden;
	}

	#show_introduce image {
		width: 100% !important;
	}

	.details_name_a {
		display: block;
		overflow: hidden;
	}

	.details_name_h3 {
		border-bottom: 1px dashed #ddd;
		height: 30px;
		line-height: 30px;
		font-size: 24rpx;
		color: #000;
		padding-bottom: 5px;
		font-weight: lighter;
	}

	.details_name_p {
		line-height: 20px;
		color: #666;
		margin-top: 10px;
		font-size: 23rpx;
	}

	.standard_active {
		width: 100%;
		float: left;
		overflow: hidden;
	}

	.standard_bottom {
		width: 100%;
		float: left;
		border-top: 1px dashed #ddd;
		margin-top: 10px;
	}

	.standard_bottom_li {
		width: 100%;
		margin-bottom: 5px;
		margin-top: 10px;
		float: left;
	}

	.standard_bottom_li_strong {
		font-size: 23rpx;
		color: #333;
		font-weight: lighter;
	}

	.standard_check_a {
		display: block;
		float: left;
		height: 30px;
		width: 90px;
		margin-right: 5px;
		line-height: 30px;
		text-align: center;
		font-size: 23rpx;
		color: #333;
		border: 1px solid #ddd;
		margin-bottom: 10px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
	}

	.standard_check_this {
		border: 1px solid #f00;
		background-size: 15%;
	}

	.standard_num_span {
		float: left;
		font-weight: lighter;
		color: #333;
		font-size: 23rpx;
		line-height: 27px;
	}

	.details_num {
		float: left;
		height: 30px;
		border: 1px solid #ddd;
		border-right: 0px;
		margin-right: 10px;
	}

	.details_num_i {
		border-right: 1px solid #ddd;
		background: #f1f1f1;
		width: 30px;
		height: 30px;
		text-align: center;
		display: block;
		float: left;
	}

	.details_num_i_img {
		margin-top: 7px;
	}

	.details_num_input {
		border: 0px;
		border-right: 1px solid #ddd;
		height: 28px;
		text-align: center;
		line-height: 28px;
		font-size: 24rpx;
		color: #333;
		width: 50px;
		float: left;
	}

	.standard_num_em {
		font-size: 23rpx;
		color: #666;
		line-height: 27px;
		font-style: normal;
	}

	.details_evaluate_ul_li {
		height: 30px;
		width: 100%;
		padding-top: 10px;
		padding-bottom: 10px;
		font-size: 23rpx;
		line-height: 30px;
	}

	.details_evaluate_ul_details_eli {
		border-bottom: 1px dashed #ddd;
	}

	.details_evaluate_ul_li_a {
		display: block;
		overflow: hidden;
	}

	.details_evaluate_ul_li_b {
		float: right;
		margin-right: 20px;
		font-size: 23rpx;
		color: #666;
	}

	.details_evaluate_ul_li_b_i {
		margin-right: 10px;
		float: left;
	}

	.details_evaluate_ul_li_b_i_em {
		float: left;
		width: 16px;
		height: 16px;
		overflow: hidden;
		margin-right: 5px;
		margin-top: 7px;
	}

	.details_evaluate_ul_li_b_i_em_img {
		float: left;
	}

	.details_evaluate_ul_li_span {
		float: left;
		font-size: 24rpx;
		color: #000;
		height: 30px;
	}

	.details_evaluate_ul_li_strong {
		font-size: 23rpx;
		font-weight: lighter;
		color: #666;
		padding-left: 10px;
	}

	.details_shop_a {
		display: block;
		overflow: hidden;
		line-height: 27px;
		font-size: 23rpx;
	}

	.details_shop_span {
		float: right;
		font-size: 21rpx;
		border: 1px solid #ddd;
		height: 27px;
		line-height: 27px;
		padding-right: 30px;
		padding-left: 20px;
	}

	.details_total {
		width: 100%;
		background: #ffffff;
		position: fixed;
		height: 50px;
		bottom: 0px;
		padding-top: 5px;
		box-shadow: 0px -1px 2px rgba(218, 218, 218, 0.3);
		display: flex;
	}

	.details_total .add_car {
		height: 35px;
		line-height: 35px;
		width: 39%;
		margin: 5px 1%;
		text-align: center;
		color: #fff;
		background: #ffc707;
		border-radius: 50rpx;
		border: none !important;
	}

	.details_total .atonce {
		height: 35px;
		line-height: 35px;
		width: 39%;
		margin: 5px 1%;
		text-align: center;
		color: #fff;
		background: #f91f28;
		border-radius: 50rpx;
	}

	.details_total_details_enjoy {
		width: 12%;
		margin: 5px 3%;
		height: 35px;
		text-align: center;
		background-size: 100%;
		float: right;
		display: block;
		position: relative;
	}

	.details_total_details_enjoy .gwc {
		font-size: 40rpx !important;
		color: #9c9b9b;
	}

	.gwc {
		font-size: 26rpx !important;
		color: #ff0000;
	}

	.details_total_details_enjoy_img {
		width: 70rpx;
		height: 70rpx;
	}

	.details_total_car_num {
		border: 2px solid #fff;
		position: absolute;
		background: #c90000;
		color: #fff;
		width: 18px;
		height: 18px;
		line-height: 18px;
		overflow: hidden;
		top: -5px;
		right: -10px;
		border-radius: 50%;
		font-size: 20rpx;
		text-align: center;
	}

	.details_top_name {
		padding: 5px;
		overflow: hidden;
		line-height: 24px;
		height: 48px;
		overflow: hidden;
		border-top: 1px solid #eee;
	}

	.goods_introduce_img {
		width: 750rpx;
		height: 750rpx;
	}

	.m_vip {
		display: flex;
		padding: 20rpx;
		margin-top: 20rpx;
		align-items: center;
		justify-content: space-between;
		border-radius: 60rpx;
		background: #f7f8fc;
	}

	.m_vip .m_left {
		display: flex;
		align-items: center;
		font-size: 22rpx;
	}

	.m_vip .m_left text {
		margin-left: 15rpx;
	}

	.m_vip .m_right {
		background: #2a2724;
		font-size: 21rpx;
		border-radius: 30rpx;
		color: #e3c49e;
		padding: 8rpx 15rpx;
	}

	.m_vip .m_right .iconfont {
		font-size: 20rpx !important;
	}

	.stand .youhui1 {
		width: 100%;
		box-sizing: border-box;
		padding: 20rpx 20rpx 0 80rpx;
		float: left;
	}

	.stand .youhui1 .yhq1 {
		background: #fee9e6;
		color: #f44c36;
		padding: 6rpx 14rpx;
		border-radius: 4rpx;
		margin-right: 20rpx;
		float: left;
	}

	.stand .youhui1 .yhq2 {
		border-radius: 8rpx;
		border: 2rpx solid #f44c36;
		color: #f44c36;
		position: relative;
		overflow: hidden;
		padding: 0 16rpx;
		float: left;
	}

	.stand .youhui1 .yy {
		overflow: hidden;
		padding-top: 7rpx;
		margin-left: 20rpx;
		float: left;
	}

	.stand .youhui1 .yhq2:before {
		content: "";
		position: absolute;
		top: 50%;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		width: 16rpx;
		height: 16rpx;
		border-radius: 50%;
		border: 2rpx solid #f44c36;
		background-color: #fff;
		z-index: 3;
		left: -10rpx;
	}

	.stand .youhui1 .yhq2:after {
		content: "";
		position: absolute;
		top: 50%;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		width: 16rpx;
		height: 16rpx;
		border-radius: 50%;
		border: 2rpx solid #f44c36;
		background-color: #fff;
		z-index: 3;
		right: -10rpx;
	}

	.stand .line2 {
		margin-bottom: 20rpx;
		border-bottom: 2rpx solid rgba(243, 243, 243, 0.73);
		padding-bottom: 40rpx;
	}

	.bor_bottom {
		border-bottom: #f1f1f1 2rpx solid;
	}

	.pd20 {
		padding-bottom: 20rpx;
	}

	.pinlun .plitem {
		width: 100%;
		display: flex;
		padding: 20rpx 0;
	}

	.pinlun .plitem .pl1 {
		color: #acacac;
	}

	.pinlun .plitem .p2 {}

	.biaoti3 {
		text-align: center;
		padding: 10rpx 0;
		font-weight: bold;
	}

	.box3 {
		padding: 0 20rpx;
		overflow: hidden;
		display: flex;
		justify-content: space-between;
	}

	.show3 {
		width: calc(33.33% - 10rpx);
		overflow: hidden;
	}

	.show3 .img3 {
		width: 100%;
	}

	.show3 .tit3 {
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden;
	}

	.show3 .price3 {
		color: #ff0000;
	}

	.show3 .price3 .t1 {
		font-size: 20rpx;
	}

	.show3 .price3 .t2 {
		font-weight: bold;
	}


	.pintuan_foot {
		width: 100%;
		background: #ffffff;
		height: 100rpx;
		display: flex;
		position: fixed;
		left: 0;
		bottom: 0;
		z-index: 12;
	}

	.pintuan_foot .sc {
		width: calc(18% - 2rpx);
		display: flex;
		flex-direction: column;
		align-items: center;
		border-right: #eaeaea 2rpx solid;
		border-top: #eaeaea 2rpx solid;
		justify-content: center;
	}

	.pintuan_foot .cart {
		width: 18%;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		position: relative;
		border-top: #eaeaea 2rpx solid;
	}

	.pintuan_foot .cart .car_num {
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
		border-top: #eaeaea 2rpx solid;
	}

	.pintuan_foot .ddgm {
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #ffffff;
		background: #ffc707;
		color: #ffffff;
		height: 64%;
		border-radius: 50rpx;
		line-height: 32rpx;
		font-size: 20rpx;
		width: 48%;
		font-weight: bold;
		text-align: center;
	}

	.pintuan_foot .group {
		display: flex;
		flex-direction: column;
		align-items: center;
		background: #ff0000;
		color: #ffffff;
		height: 64%;
		border-radius: 50rpx;
		line-height: 32rpx;
		width: 48%;
		font-size: 22rpx;
		font-weight: bold;
		text-align: center;
	}

	.pintuan_foot .ptj {
		width: 64%;
		display: flex;
		justify-content: space-between;
		border-top: #eaeaea 1px solid;
	}

	.pintuan_foot .ptjg {
		align-items: center;
		width: 64%;
		display: flex;
		justify-content: space-between;
		height: 100%;
		vertical-align: middle;
		color: #ffffff;
		border-top: #eaeaea 1px solid;
	}

	.pintuan_foot .pts {
		width: 64%;
		display: flex;
		justify-content: center;
		color: #ffffff;
		border-top: #eaeaea 1px solid;
	}

	.pintuan_foot .add_car {
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

	.pintuan_foot .atonce {
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

	.pintuan_foot .place {
		background: #ff0000;
		color: #ffffff;
		height: 70rpx;
		line-height: 70rpx;
		border-radius: 50rpx;
		width: calc(75% - 20rpx);
		font-weight: bold;
		text-align: center;
		margin-right: 10rpx;
		margin-top: 14rpx;
	}


	.bottom .cancel {
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

	.bottom .ddgm {
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #ffffff;
		background: #ffc707;
		color: #ffffff;
		height: 70rpx;
		border-radius: 70rpx;
		line-height: 32rpx;
		font-size: 22rpx;
		width: 45%;
		font-weight: bold;
		text-align: center;
	}

	.bottom .group {
		display: flex;
		flex-direction: column;
		align-items: center;
		background: #ff0000;
		color: #ffffff;
		height: 70rpx;
		border-radius: 70rpx;
		line-height: 32rpx;
		font-size: 22rpx;
		width: 45%;
		font-weight: bold;
		text-align: center;
	}

	.bottom .ptj {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}

	.bottom .ptjg {
		align-items: center;
		height: 100rpx;
		width: 100%;
		display: flex;
		justify-content: space-between;
		color: #ffffff;
	}

	.bottom .pts {
		width: 100%;
		display: flex;
		justify-content: space-between;
		color: #ffffff;
	}

	.bottom .add_car {
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

	.bottom .atonce {
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

	.bottom .place {
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
		font-size: 23rrpx;
		font-weight: bold;
		text-align: center;
	}

	.showbox .dl {
		padding: 20rpx;
		overflow: hidden;
	}

	.showbox .couponcode_title {
		font-weight: bold;
		padding: 10rpx;
		margin-left: 15rpx;
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
		width: 100%;
	}

	.taglist {
		display: flex;
	}

	.taglist .tag {
		padding: 10rpx 24rpx;
		background-color: #f2f2f2;
		border-radius: 30rpx;
		text-align: center;
		margin-right: 20rpx;
	}

	.taglist .on {
		background-color: #fcedeb;
		color: #f92028;
	}

	.youhuiquan .yhq {
		display: flex;
		overflow: hidden;
		position: relative;
	}

	.youhuiquan .yhq:before {
		content: "";
		position: absolute;
		top: 0;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		width: 30rpx;
		height: 30rpx;
		border-radius: 50%;
		border: 2rpx solid #f2d2d2;
		background-color: #fff;
		z-index: 3;
		left: 222rpx;
	}

	.youhuiquan .yhq:after {
		content: "";
		position: absolute;
		bottom: -30rpx;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		width: 30rpx;
		height: 30rpx;
		border-radius: 50%;
		border: 2rpx solid #f2d2d2;
		background-color: #fff;
		z-index: 3;
		left: 222rpx;
	}

	.youhuiquan .yhq .left {
		width: 240rpx;
		height: 180rpx;
		background: #f52923;
		border-radius: 20rpx 0 0 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		color: #ffffff;
	}

	.youhuiquan .yhq .left_grey {
		width: 240rpx;
		height: 180rpx;
		background: #999999;
		border-radius: 20rpx 0 0 20rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		color: #ffffff;
	}

	.youhuiquan .yhq .right {
		width: calc(100% - 240rpx);
		height: 180rpx;
		background: #fbf2f2;
		border: 2rpx solid #f2d2d2;
		border-radius: 0 20rpx 20rpx 0;
	}

	.youhuiquan .right .lin1 {
		padding: 0rpx 10rpx 0rpx 40rpx;
	}

	.youhuiquan .right .hyq {
		background: rgba(245, 41, 35, .6);
		color: #fff;
		font-size: 20rpx;
		padding: 2rpx 10rpx;
		margin-right: 20rpx;
		border-radius: 4rpx;
	}

	.guige {
		display: flex;
		padding: 20rpx;
	}

	.guige .img {
		width: 160rpx;
		height: 160rpx;
		border-radius: 8rpx;
		overflow: hidden;
		margin-right: 20rpx;
	}

	.guige .price {
		color: #ff0000;
		font-weight: bold;
		padding-bottom: 20rpx;
	}

	.guige .price .t1 {
		font-size: 20rpx;
	}

	.guige .price .t2 {
		font-size: 28rpx;
	}

	.guige .kucun {
		padding-top: 20rpx;
		color: #adadad;
	}

	.showbox .shuliang {
		display: flex;
		justify-content: space-between;
	}

	.shuliang .dd {
		display: flex;
		line-height: 50rpx;
	}

	.shuliang .jian,
	.shuliang .jia {
		width: 50rpx;
		height: 50rpx;
		border: #eaeaea 2rpx solid;
		text-align: center;
	}

	.shuliang .jian {
		border-radius: 5rpx 0 0 5rpx;
		background: rgba(241, 241, 241, 0.85);
	}

	.shuliang .jia {
		border-radius: 0 5rpx 5rpx 0;
	}

	.shuliang .num {
		width: 80rpx;
		text-align: center;
		border-top: #eaeaea 2rpx solid;
		border-bottom: #eaeaea 2rpx solid;
		height: 50rpx;
	}

	.bottom {
		display: flex;
		justify-content: space-between;
		position: absolute;
		bottom: 8rpx;
		left: 0;
		width: 100%;
		box-sizing: border-box;
		padding: 0 20rpx;
	}

	.bottom .add_car {
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

	.bottom .atonce {
		background: #ff0000;
		color: #ffffff;
		height: 70rpx;
		line-height: 70rpx;
		border-radius: 50rpx;
		width: calc(50% - 10rpx);
		font-weight: bold;
		text-align: center;
		margin-top: 14rpx;
	}

	.box {
		width: 94%;
		background: #ffffff;
		box-sizing: border-box;
		padding: 20rpx;
		margin: auto;
		margin-top: 20rpx;
		border-radius: 20rpx;
	}

	.dl {
		overflow: hidden;
	}

	.dl .dt {
		padding-bottom: 20rpx;
		font-weight: bold;
		position: relative;
	}

	.dl .dt:before {
		position: absolute;
		top: 35%;
		left: 0;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
		content: "";
		width: 8rpx;
		height: 30rpx;
		background: -webkit-gradient(linear, left top, left bottom, from(#f91f28), to(rgba(255, 79, 46, .35)));
		background: linear-gradient(180deg, #f91f28, rgba(255, 79, 46, .35));
	}

	.dl .dd {
		display: flex;
		flex-wrap: wrap;
	}

	.dl .dd .tab {
		width: calc(33.33% - 15rpx);
		margin: 10rpx 5rpx;
		color: #000;
		background-color: #fdf0ef;
		border-radius: 20rpx;
		text-align: center;
		padding: 10rpx 0;
	}

	.dl .dd .on {
		color: #f91f28;
		background-color: #fdf0ef;
	}

	.nocont {
		text-align: center;
		display: flex;
		flex-direction: column;
		align-items: center;
		color: #c7c7c7;
	}

	.nocont .img {
		width: 200rpx;
		height: 200rpx;
	}

	.sh_slt {
		width: 96rpx;
		height: 96rpx;
		overflow: hidden;
		border-radius: 96rpx;
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

	.sp_tit_score {
		width: 70%;
		overflow: hidden;
		font-size: 23rpx;
	}

	.star {
		width: 30rpx;
		height: 30rpx;
	}
</style>