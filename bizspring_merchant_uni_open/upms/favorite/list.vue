<template>
	<view>
		<view class="w100 bg_white pd-b-10 pd-t-10 top">
			<view class="tab flex-row">
				<view @tap="tapFavoriteType(0)" :class="'item ' + (tapIndex==0 ? 'on' : '')">商品收藏</view>
				<view @tap="tapFavoriteType(1)" :class="'item ' + (tapIndex==1 ? 'on' : '')">店铺收藏</view>
			</view>
		</view>
		<view class="topkongbai"></view>
		<view v-for="(item,index) in objsList" :key="index" class="listbox bg_white w100 box-si-border padd-3 m-b-10">
			<view class="item flex-row">
				<view class="proinfo" v-if="tapIndex==0">
					<view class="title">商品：{{item.goodsName}}</view>
					<view class="tag">
						<view class="w40">会员: {{item.memberName}}</view>
						<view class="w60">收藏时间: {{item.createdTime}}</view>
					</view>
				</view>
				<view class="proinfo" v-if="tapIndex==1">
					<view class="tag">
						<view class="w40">会员:{{item.memberName}}</view>
						<view class="w60">收藏时间:{{item.createdTime}}</view>
					</view>
				</view>
			</view>
		</view>
		<view class="more color-gray2 text-align-center">没有更多了</view>
	</view>
</template>

<script>
	import {
		list as store_favorite_list,
	} from "@/api/upms/store_favorite.js"
	import {
		list as goods_favorite_list,
	} from "@/api/upms/goods_favorite.js"
	var app = getApp();

	export default {
		data() {
			return {
				tapIndex: 0,
				searchLoading: false,
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					sortProperty: 'created_time',
					sortDirection: 'DESC',
					goodsCategoryId: null,
					brandId: "",
					keyword: "",
					storeId: "",
				},
				typeIndex: 0
			};
		},
		onLoad(option) {
			if (option.typeIndex) {
				this.typeIndex = option.typeIndex ? option.typeIndex : null;
				this.tapFavoriteType(this.typeIndex);
			} else {
				this.favorite_list();
			}
		},
		onShow() {
			this.favorite_list();
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.favorite_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.favorite_list();
		},
		methods: {

			favorite_list() {
				if (this.tapIndex == 0) {
					this.goods_favorite_list();
				}
				if (this.tapIndex == 1) {
					this.store_favorite_list();
				}
			},

			/**
			 * 查询列表 list
			 */
			goods_favorite_list() {
				goods_favorite_list(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.content;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},

			store_favorite_list() {
				store_favorite_list(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.content;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},

			tapFavoriteType(i) {
				let index = i;
				if (index == 0) {
					this.objsList =[];
					this.tapIndex = 0;
					this.loadType = "load";
					this.queryParams.pageNumber = 1;
					this.goods_favorite_list();
				}
				if (index == 1) {
					this.objsList =[];
					this.tapIndex = 1;
					this.loadType = "load";
					this.queryParams.pageNumber = 1;
					this.goods_favorite_list();
				}

			}

		}
	};
</script>

<style lang="scss">
	.top {
		position: fixed;
		left: 0;
		z-index: 10;
		width: 100%;
		overflow: hidden;
	}

	.topkongbai {
		height: 115rpx;
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
				width: 100%;
				.title {
					font-weight: 600;
					overflow: hidden;
					white-space: nowrap;
					text-overflow: ellipsis;
					width: 100%;
				}

				.tag {
					display: flex;
					justify-content: space-between;
					flex-wrap: wrap;
					padding: 20rpx 0;
					color: #888888;
					font-size: 23rpx;

					.w50 {
						width: 50%;
						margin-top: 10rpx;

						.type {
							color: #ff6600;
							padding-left: 13rpx;
						}
					}
					
					.w40 {
						width: 40%;
						margin-top: 10rpx;
					
						.type {
							color: #ff6600;
							padding-left: 13rpx;
						}
					}
					.w60 {
						width: 60%;
						margin-top: 10rpx;
					
						.type {
							color: #ff6600;
							padding-left: 13rpx;
						}
					}
				}

				.price {
					// font-weight: bold;
					color: #ff6600;
					font-size: 24rpx;
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

	.more {
		font-size: 24rpx;
		line-height: 60rpx;
	}

	.footer {
		height: 100rpx;
		width: 100%;

		.btn-add {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			color: #ffffff;
			bottom: 0;
			text-align: center;
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
</style>
