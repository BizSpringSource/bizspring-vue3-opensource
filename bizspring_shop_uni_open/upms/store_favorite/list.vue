<template>
	<view>
		<view class="shoucang" v-for="(item, index) in objsList" :key="index">
			<navigator :url="'../../store/store/index?id='+item.storeId" ><image  class="img"	:src="item.storeLogo" mode="widthFix"></image></navigator>
			<view class="box1">
				<navigator :url="'../../store/store/index?id='+item.storeId" class="tit">{{item.storeName}}</navigator>
				<view class="price">
					<navigator :url="'../../store/store/index?id='+item.storeId" style="color: #888888;">编号: {{item.storeId}}</navigator>
					<text  @tap="store_favorite_delete" :data-store_favorite="item" class="iconfont icon-shanchu"></text>
				</view>
			</view>
		</view>
		<view class="loadingWxApplet" :hidden="!searchLoading">正在载入更多...</view>
		<view class="loadingWxApplet" :hidden="!searchLoadingComplete">没有更多了</view>
	</view>
</template>

<script>
	
	import { del,list }  from "@/api/upms/store_favorite.js"
	
	var app = getApp();
	export default {
		data() {
			return {
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				searchLoadingComplete: false,
				//“没有数据”的变量，默认false，隐藏 
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: 0,
					pageSize: 10,
				},
			};
		},

		components: {},
		props: {},
		onLoad() {
			var that = this;
		},
		onShow:function(){
			this.store_favorite_list();
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
			 /**
			  * 查询列表 list
			  */
			store_favorite_list() {
				list(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.content;
						this.brandOptions = response.data.data.brands;
						this.searchLoading = false;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
				});
			},
			store_favorite_delete: function(e) {
				var that =this;
				var data = e.currentTarget.dataset.store_favorite;
				uni.showModal({
					title: '提示',
					content: '是否确认删除',
					success: function(response) {
						if(response.confirm){
							del(data.id+',').then(response_del => {						
								that.store_favorite_list();															
							});
						}
					}
				});
			},
			
		}
	};
</script>
<style>
	.shoucang {
		background: #ffffff;
		box-sizing: border-box;
		width: 100%;
		overflow: hidden;
		margin-bottom: 20rpx;
		display: flex;
		padding: 30rpx;
	}
	
	.shoucang .img {
		width: 130rpx;
		height: 130rpx;
		overflow: hidden;
		margin-right: 20rpx;
	}
	
	.shoucang .box1 {
		width: calc(100% - 150rpx);
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}
	
	.shoucang .box1 .tit {
		font-size: 24rpx;
	}
	
	.shoucang .box1 .price {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}
	
	.shoucang .box1 .price .iconfont {
		font-size: 27rpx !important;
	}
</style>
