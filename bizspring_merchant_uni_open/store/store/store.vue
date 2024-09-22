<template>
	<view>
		<!--商家头部信息-->
		<view class="w100 box-si-border padd-3 bg_white m-b-20">
			<view class="flex-row juc-sb store-top ai-c">
				<view class="flex-row pd-t-20 pd-b-20 ai-c">
					<image :src="store.logo||'/static/images/default_logo.png'" @error="onErrorImg(store)" class="store-logo m-r-20"></image>
					<view class="flex-col">
						<text class="font-size-18 font-weight-600">{{store.name}}</text>
						<text class="font-size-16 color-gray2">{{msg['Store_Type_'+store.type]}}</text>
					</view>
				</view>
			</view>
		</view>
		<!--商品列表区域-->
		<view class="w100 box-si-border padd-3 bg_white mallbox">
			<!--商品列表 一行两列显示方式-->
			<view class="product flex-row juc-sb w100 box-si-border padd-3 pd-t-30">
				<view class="pro-item m-b-30 pd-b-20" v-for="(item,index) in objsList" :key="index" >
					<view class="imgbox">
						<image :src="item.thumbnail||'/static/images/default_thumbnail.jpg'" class="img"></image>
					</view>
					<view class="title m-top-20">{{item.name}}</view>
					<view class="coupon">{{item.caption}}</view>
					<view class="info">
						<text class="m-r-20 font-size-17">￥{{item.price}}</text>
					</view>
				</view>
			</view>
		</view>

	</view>
</template>

<script>
	import {
		list//商品列表
	} from "@/api/goods/goods.js"
	import {
		index		
	} from '@/api/upms/profile.js'	
	
	var app = getApp();

	export default {
		data() {
			return {
		    	msg:{
                    'Store_Type_GENERAL':'普通',
                    'Store_Type_SELF':'自营'
                },
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: null,
					brandId: "",
					keyword: "",
					storeId: "",
				},
				store:{},
			};
		},
		onLoad(options) {
		},
		onShow() {
			this.goods_list();
			this.store_index();
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.goods_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.goods_list();
		},
		methods: {
			
			store_index: function() {
				uni.showLoading({
					title: 'Loading...'
				})
				index().then(response => {
					uni.hideLoading();
					this.store = response.data.data.store;
					this.user = response.data.data.merchant;
			
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
				})
			},			
			/**
			 * 查询列表 list
			 */
			goods_list() {
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
			//商品选项卡点击事件
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_logo.png");
				this.$set(item, 'logo', "/static/images/default_logo.png");
			},

		}
	};
</script>

<style lang="scss">
	.store-top {
		margin-bottom: 20rpx;

		.store-logo {
			width: 160rpx;
			height: 160rpx;
			border: #eeeeee 2rpx solid;

		}

		.btn-box {
			border: $default-theme-color 2rpx solid;
			padding: 10rpx 20rpx;
			font-size: 22rpx;
			border-radius: 10rpx;
			display: flex;
			align-items: center;

			.iconfont {
				font-size: 22rpx !important;
				margin-right: 10rpx;
			}
		}
	}

	.coupon {
		overflow: hidden;
		line-height: 60rpx;
		text-overflow: ellipsis;
		white-space: nowrap;

		.item {
			border: #ff6600 2rpx solid;
			padding: 6rpx 15rpx;
			border-radius: 10rpx;
			color: #ff6600;
		}
	}

	.mallbox {
		padding: 20rpx 0;

		.title {
			border-bottom: #eeeeee 2rpx solid;
			line-height: 80rpx;
		}

		.tab {
			font-size: $uni-font-size-base;

			.tab-item {
				flex: 1;
				text-align: center;
			}

			.on {
				color: #ff6600;
			}
		}

		.product {
			flex-wrap: wrap;
		}

		.pro-item {
			width: calc(50% - 20rpx);
			border: #eeeeee 2rpx solid;
			border-radius: 10rpx;
			overflow: hidden;

			.imgbox {
				width: 100%;
				padding-top: 100%;
				position: relative;
				overflow: hidden;
				border-radius: 10rpx;

				.img {
					width: 100%;
					height: 100%;
					object-fit: cover;
					position: absolute;
					left: 0;
					top: 0;
					overflow: hidden;
				}
			}

			.title {
				width: 100%;
				box-sizing: border-box;
				padding: 0 15rpx;
				font-size: 24rpx;
				text-overflow: ellipsis;
				white-space: nowrap;
				overflow: hidden;
				font-weight: 500;
				border: none;
				line-height: 30rpx;
			}

			.coupon {
				width: 100%;
				box-sizing: border-box;
				padding: 0 15rpx;
				font-size: 22rpx;
				color: #ff0000;
			}

			.info {
				width: 100%;
				box-sizing: border-box;
				padding: 0 15rpx;
				font-size: 22rpx;
				color: #ff0000;
			}
		}
	}
</style>
