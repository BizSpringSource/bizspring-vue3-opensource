<template>
	<view>
		<!--商家头部信息-->
		<view class="w100 box-si-border padd-3 bg_white m-b-20">
			<view class="flex-row juc-sb store-top ai-c">
				<view class="flex-row pd-t-20 pd-b-20 ai-c">
					<image :src="obj.logo" class="store-logo m-r-20"></image>
					<view class="flex-col">
						<text class="font-size-18 font-weight-600">{{obj.name}}</text>
						<text class="font-size-16 color-gray2">{{obj.introduction||''}}</text>
					</view>
				</view>
				<view class="follow">
					<view @tap="store_favorite_add" class="btn-box m-b-20">
						<text class="iconfont icon-gerenguanzhu"></text>
						<text>店铺收藏</text>
					</view>
				</view>
			</view>
		</view>
		<!--商品列表区域-->
		<view class="w100 box-si-border padd-3 bg_white mallbox">
			<view class="ceiling bg_white pd-b-20">
			</view>
			<!--商品列表 一行两列显示方式-->
			<view class="product flex-row juc-sb w100 box-si-border padd-3 pd-t-30">
				<navigator class="pro-item m-b-30 pd-b-20" v-for="(item,index) in objsList" :key="index"
					:url="'../../goods/goods/detail?goodsId='+item.id">
					<view class="imgbox">
						<image v-if="item.thumbnail" :src="item.thumbnail" class="img"></image>
					</view>
					<view class="title m-top-20">{{item.name}}</view>
					<view class="coupon">{{item.caption||''}}</view>
					<view class="info">
						<text class="m-r-20 font-size-17">￥{{item.price}}</text>
					</view>
				</navigator>
			</view>
		</view>
		<!--分享按钮-->
		<uni-popup ref="popup" type="share">
			<uni-popup-share title="分享" @select="select"></uni-popup-share>
		</uni-popup>
		<uni-popup ref="popcontent" type="center" >
			<view>
				<image src="/static/share.jpg"  mode="widthFix"></image>
			</view>
		</uni-popup>

	</view>
</template>

<script>
	import {
		store_list //商品列表
	} from "@/api/goods/goods.js"
	import {
		index //商家详情
	} from "@/api/store/store.js"
	import {
		add
	} from "@/api/upms/store_favorite.js"

	var app = getApp();
	export default {
		data() {
			return {
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: '1',
					brandId: "",
					keyword: "",
					storeId: "",
				},
				id: '',
				obj: {}, //店铺信息
			};
		},
		onLoad(options) {
			this.id = options.id;
			this.store_index();
			this.store_goods_list();
		},

		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.store_goods_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.store_goods_list();
		},
		methods: {
			store_index() {
				index(this.id).then(response => {
					//店铺
					this.obj = response.data.data.store;
				});
			},			
			store_favorite_add() {
				add({"storeId":this.id}).then(response => {
					let statusCode=response.statusCode;
					let message=response.data.message;
					if(200==statusCode){
						uni.showToast({
							title: "已收藏",
							duration: 2000
						});
					}else{
						uni.showToast({
							title: this.messages[message],
							duration: 2000
						});
					}
					
				});
			},
			/**
			 * 查询列表 list
			 */
			store_goods_list() {
				store_list(this.queryParams).then(response => {
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
			select(e) {
				if (e.index == 0) {
					this.$refs.popcontent.open('top');
				}
			}

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
			padding: 10rpx 20rpx;
			font-size: 24rpx;
			border-radius: 10rpx;
			display: flex;
			align-items: center;

			.iconfont {
				font-size: 24rpx !important;
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
			border: #ff0000 2rpx solid;
			padding: 6rpx 15rpx;
			border-radius: 10rpx;
			color: #ff0000;
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
				color: #ff0000;
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
				font-size: 23rpx;
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
				font-size: 21rpx;
				color: #ff0000;
			}

			.info {
				width: 100%;
				box-sizing: border-box;
				padding: 0 15rpx;
				font-size: 24rpx;
				color: #ff0000;
			}
		}
	}
</style>
