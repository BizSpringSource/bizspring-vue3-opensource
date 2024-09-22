<template>
	<view>
		<view class="topkongbai"></view>
			<view v-for="(item,index) in objsList"  :key="index" class="listbox bg_white w100 box-si-border padd-3 m-b-10">
			<view class="item flex-row">
				<view class="proinfo">
					<view class="title">地区: {{item.region.fullName}}</view>
					<view class="tag">
						<view class="w50">首重(kg): {{item.firstWeight}}</view>
						<view class="w50">续重(kg): {{item.continueWeight}}</view>
						<view class="w50">首重价格(￥): {{item.firstPrice}}</view>
						<view class="w50">续重价格(￥): {{item.continuePrice}}</view>
					</view>
					
				</view>
			</view>
			<view class="caozuo flex-row juc-sb">
				<navigator  :url="'../../store/region_freight/index?id='+item.id"
					class="btn  ">编辑</navigator>
			</view>
		</view>
		<view class="more color-gray2 text-align-center">没有更多了</view>
		<view class="footer">
			<navigator class="btn-add btn-full" :url="'../../store/region_freight/index?shippingMethodId='+queryParams.shippingMethodId" >添加</navigator>
		</view>
	</view>
</template>

<script>
	import {
		add,
		list,
	} from "@/api/store/region_freight.js"
	var app = getApp();

	export default {
		data() {
			return {
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				objsList: [],
				loadType: "load",
				queryParams:{
					pageNumber: 1,
					pageSize: 10,
					sortProperty:'created_time',
					sortDirection:'DESC',
					shippingMethodId:null,
				},
			};
		},
		onLoad(option) {
			this.queryParams.shippingMethodId = option.id?option.id:null;
			this.region_freight_list();
		},
		onShow() {			
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 1;
			this.loadType = "pullDown";
			// 重新请求
			this.region_freight_list();
		},
		// 上拉加载触发
		onReachBottom() {
			// 改变状态为加载中
			this.searchLoading = true;
			// 页码发生变化
			++this.queryParams.pageNumber;
			this.loadType = "reach";
			// 加载更多
			this.region_freight_list();
		},
		methods: {
			/**
			 * 查询列表 list
			 */
			region_freight_list() {
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
					// font-weight: bold;
					color: #ff6600;
					font-size: 24rpx;
				}
			}
		}

		.caozuo {
			padding-bottom: 20rpx;

			.btn {
				margin-left: auto;
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
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
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
