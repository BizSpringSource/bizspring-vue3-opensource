<template>
	<view>
		<view v-for="(item,index) in objsList" :key="index" class="listbox bg_white w100 box-si-border padd-3 m-b-10">
			<navigator class="item flex-row" :url="'../../goods/goods/detail?goodsId='+item.id">
				<view class="img">
					<image class="pic" 
						:src="item.goodsImages&&item.goodsImages[0]&&item.goodsImages[0].thumbnail?item.goodsImages[0].thumbnail:'/static/images/default_thumbnail.jpg'" />
				</view>
				<view class="proinfo">
					<view class="title">{{item.name}}</view>
					<view class="tag">
						<view class="price">￥{{item.price}}</view>
						<view class="w50 color-blue font-weight-bold ">{{msg['Goods_Type_'+item.type]}}</view>
					</view>
				</view>
			</navigator>
			<view class="caozuo flex-row juc-sb">
				<view class="btn" @tap="goods_del(item.id)">删除</view>
				<navigator :url="'../../goods/goods/index?id='+item.id" class="btn">编辑</navigator>
			</view>
		</view>
		<view class="more color-gray2 text-align-center">没有更多了</view>
		<view class="footer">
			<navigator class="btn-add btn-full" url="../../goods/goods/index">添加</navigator>
		</view>

	</view>
</template>

<script>
	import {
		list, //商品列表
		del
	} from "@/api/goods/goods.js"
	var app = getApp();

	export default {
		data() {
			return {
			    msg:{
			        'Goods_Type_GENERAL':'普通商品'
			    },
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				objsList: [],
				loadType: "load",
				queryParams: {
					sortProperty: 'created_time',
					sortDirection: 'DESC',
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: null,
					brandId: "",
					keyword: "",
					storeId: "",
					orderType:'',
					isMarketable:null,
				},
			};
		},
		onLoad(option) {
		},
		onShow() {
			this.goods_list();
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
						this.objsList=[];
						this.objsList = response.data.data.data.content;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},
			/**
			 * 删除
			 */
			goods_del(id) {
				let params={'ids':[id]};
				del(params).then(response => {
					uni.showToast({
						title: '已删除',
						duration: 3000,
						icon: "none"
					});
					this.queryParams.pageNumber=1;
					this.loadType = "load";
					this.goods_list();
				});
			},
			search(res) {
				uni.showToast({
					title:res.value,
					icon: 'none'
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
		height: 112rpx;
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
				width: calc(100% - 200rpx);

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
					font-size: 22rpx;

					.w50 {
						width: 50%;
					}
				}

				.price {
					font-weight: bold;
					color: #ff6600;
				}
			}
		}

		.caozuo {
			padding-bottom: 20rpx;
			margin-left: 30rpx;
			justify-content: flex-end;
			.btn {
				margin-left: 40rpx;
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
</style>
