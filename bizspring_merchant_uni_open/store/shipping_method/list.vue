<template>
	<view>
		<view class="topkongbai"></view>
			<view v-for="(item,index) in objsList"  :key="index" class="listbox bg_white w100 box-si-border padd-3 m-b-10">
			<view class="item flex-row">
				<view class="proinfo">
					<view class="title">名称: {{item.name}}</view>
					<view class="tag">
						<view class="w50">首重(kg): {{item.defaultFreight.firstWeight||''}}</view>
						<view class="w50">续重(kg): {{item.defaultFreight.continueWeight||''}}</view>
						<view class="w50">首重价格(￥): {{item.defaultFreight.firstPrice||''}}</view>
						<view class="w50">续重价格(￥): {{item.defaultFreight.continuePrice||''}}</view>
						<view class="w50">介绍:{{item.description||''}} </view>
					</view>
				</view>
			</view>
			<view class="caozuo flex-row juc-sb">
				<navigator  :url="'../../store/shipping_method/index?id='+item.id" class="btn m-r-30 ">默认运费</navigator>
				<navigator  :url="'../../store/region_freight/list?id='+item.id" class="btn">地区运费</navigator>
			</view>
		</view>
		<view class="more color-gray2 text-align-center">没有更多了</view>
	</view>
</template>

<script>
	import {
		list,
	} from "@/api/store/shipping_method.js"
	var app = getApp();

	export default {
		data() {
			return {
				searchLoading: false,
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					sortProperty:'created_time',
					sortDirection:'DESC',
					goodsCategoryId: null,
					brandId: "",
					keyword: "",
					storeId: "",
				},
			};
		},
		onLoad(option) {
		},
		onShow() {
			this.shipping_method_list();
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.shipping_method_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.shipping_method_list();
		},
		methods: {
			/**
			 * 查询列表 list
			 */
			shipping_method_list() {
				list(this.queryParams).then(response => {
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
		height: 0rpx;
		border: #e0e0e0 2rpx solid;
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
				width:100%;
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
						.type_in{
							color: green;
							padding-left: 13rpx;
						}
						.type_out{
							color: #ff6600;
							padding-left: 13rpx;
						}
					}
				}

				.price {
					color: #ff6600;
					font-size: 24rpx;
				}
			}
		}

		.caozuo {
			padding-bottom: 20rpx;

			.btn {
				padding: 10rpx 10rpx;
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
	
	.btn-two1{
		font-size: 26rpx;
		height: 100rpx;
		line-height: 100rpx;
		width: 44%;
		left: 3%;
		margin: 30rpx auto;
		background: $default-theme-color;
		border-radius: 20rpx;
	}
	
	.btn-two2{
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
