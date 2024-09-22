<template>
	<view>
		<view class="classlist w100 box-si-border padd-3">
			<mix-tree :list="objsList" @treeItemDeleteClick="treeItemDeleteClick" @treeItemClick="treeItemClick">
			</mix-tree>
		</view>
	</view>
</template>

<script>
	import {
		del,
		list
	} from "@/api/store/store_goods_category.js"
	import mixTree from '@/components/mix-tree/mix-tree';

	var app = getApp();

	export default {
		components: {
			mixTree
		},
		data() {
			return {
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				objsList: [],
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					goodsCategoryId: null,
					brandId: null,
					startPrice: null,
					endPrice: null,
					keyword: null,
					storeId: null,
				},
			};
		},
		onLoad(options) {
		},
		onShow() {
			this.store_goods_category_list();
		},
		methods: {
			/**
			 * 查询列表 list
			 */
			store_goods_category_list() {
				this.objsList=[];
				list(this.queryParams).then(response => {					
					this.objsList = response.data.data;
					this.searchLoading = false;
				});
			},

			treeItemClick(item) {
				let {
					id,
					name,
					parentId
				} = item;
			},

			treeItemDeleteClick(item) {
				let {
					id,
					name,
					parentId
				} = item;
				var that = this;
				uni.showModal({
					title: '提示',
					content: '是否确认删除',
					success: function(response) {
						let params = {
							"storeGoodsCategoryId": id
						}
						del(params).then(response_del => {
							that.store_goods_category_list();
						});
					}
				});
			}
		}
	};
</script>

<style lang="scss">
	.item {
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 20rpx;
		margin-bottom: 8rpx;
	}

	.item_sub_class {
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 10rpx;
		margin-bottom: 8rpx;
		border-top: #eeeeee 2rpx solid;
		text-indent: 60rpx;
		color: $color-hui-8;
	}

	.bottom {
		height: 120rpx;
		width: 100%;

		.add {
			position: fixed;
			width: 94%;
			left: 3%;
			bottom: 10rpx;
			height: 90rpx;
			line-height: 90rpx;
			color: #ffffff;
			background: $default-theme-color;
		}
	}

	.tanchuang {
		.tit {
			height: 90rpx;
			line-height: 90rpx;
			text-align: center;
		}

		.xzfl {
			height: 90rpx;
			line-height: 90rpx;
		}

		.ipt {
			height: 50rpx;
			line-height: 50rpx;
			box-sizing: border-box;
			margin: 30rpx;
		}

		.iptarea {
			height: 180rpx;
			line-height: 60rpx;
			border: #eeeeee 2rpx solid;
			border-radius: 8rpx;
			box-sizing: border-box;
			padding: 0 30rpx;
			margin-bottom: 30rpx;
		}
	}

	.input_line {
		height: 110rpx;
		line-height: 110rpx;
		box-sizing: border-box;
		padding: 30 30rpx;
	}
</style>
