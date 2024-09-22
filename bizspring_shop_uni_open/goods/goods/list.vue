<template>
	<view class="phone_main">
		<!--商品列表开始-->
		<view class="groupListBox">
			<view v-for="(item, index) in objsList" :key="index" class="group_list">
				<navigator class="group_list_a" :url="'../../goods/goods/detail?goodsId=' + item.id">
					<view class="group_list_dl">
						<view class="group_list_dl_dt">
							<image class="group_list_dl_dt_img" style="width: 100%;" :src="item.thumbnail||'/static/images/default_thumbnail.jpg'" @error="onErrorImg(item)"
								mode="widthFix"></image>
						</view>
						<view class="group_list_dl_dd">
							<view class="group_list_dl_dd_h3">
								<text class="group_list_dl_dd_h3_b">{{item.name}}</text>
							</view>
							<view class="group_list_dl_dd_span">市场价：
								<text class="group_list_dl_dd_span_b">¥{{item.marketPrice}}</text>
								<text>，</text>
							</view>
							<view class="group_list_dl_dd_span m-b-20">销售价：
								<text class="group_list_dl_dd_span_strong">¥{{item.price}}</text>
							</view>
						</view>
					</view>
				</navigator>
			</view>
		</view>
		<view class="loading" id="loading" style="display: none;">
			<view>
				<view>
					<image :src="'/resources/style/system/front/wap/images/loading.gif'" width="16"
						height="16"></image>
				</view>
				<view>正在加载....</view>
			</view>
		</view>
		<view class="loading" id="loading_none" style="display: block;">
			<view>
				<view>没有更多了</view>
			</view>
		</view>
		<view class="loadingWxApplet" :hidden="!searchLoading">正在载入更多...</view>
		<view class="loadingWxApplet" :hidden="!searchLoadingComplete">没有更多了</view>
	</view>
</template>
<script>
	import {
		list,
	} from "@/api/goods/goods.js"

	//获取应用实例  
	var app = getApp();
	export default {
		data() {
			return {
				//排序字段
				orderType: "",
				//排序 asc正 desc反
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				searchLoadingComplete: false,
				//“没有数据”的变量，默认false，隐藏 
				goodsId: 0,
				//数据结构：以一组一组来进行设定
				objs: "",
				objsList: [],
				loadType: "load",
				queryParams: {
					pageNumber: '1',
					pageSize: '10',
					goodsCategoryId: '',
					brandId: null,
					keyword: null,
					storeId: null,
				},
				goodsPropertys: [],
				filterWindowId: '',
			};
		},
		components: {},
		props: {},
		onLoad(option) {
			if (option.id) {
				this.queryParams.goodsCategoryId = option.id;
			}
			if (option.orderType) {
				this.queryParams.orderType = option.orderType;
			}
			this.goods_list();
		},
		// 属性选择
		onShow: function() {
		},
		// 下拉刷新触发
		onPullDownRefresh(val) {
			this.queryParams.pageNumber = 0;
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
				uni.showLoading({
					title: "Loading..."
				})
				list(this.goodsPropertys, this.queryParams).then(response => {
					uni.hideLoading();
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
					this.filterWindowId = this.queryParams.goodsCategoryId;
				});
			},
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_thumbnail.jpg");
			},
		}
	};
</script>
<style>
	.goodsTopTile {
		position: fixed;
		left: 0;
		top: 0;
		background: #ffffff;
		overflow: hidden;
		display: flex;
		width: 100%;
		align-items: center;
		padding-bottom: 10rpx;
	}

	.back {
		width: 30px;
		text-align: center;
		margin-right: 10px;
	}

	.back .iconfont {
		font-size: 28rpx !important;
	}

	.search {
		padding: 1% 3%;
		background: #ff4081;
	}

	.search_line_phcolor {
		color: #a8a8a8;
		font-size: 23rpx;
	}

	.search_line {
		display: flex;
		align-items: center;
		justify-content: center;
		padding-left: 75rpx;
		top: 0px;
		width: 100%;
		height: 100rpx;
		background-color: #1aaacd;
		z-index: 1;
	}

	.search_line_a {
		width: 550rpx;
		height: 75%;
		border: 1px solid #eee;
		display: block;
		margin-left: 10rpx;
		padding-right: 10rpx;
		border-radius: 50px;
		overflow: hidden;
	}

	.search_line_input {
		height: 100%;
		border: 0px;
		border-radius: 50px;
		margin-left: 20rpx;
	}

	.goodsTopTile .icon-sousuo- {
		font-size: 28rpx !important;
		line-height: 60rpx;
		margin-right: 20rpx;
		color: #656565;
	}


	.search_line_b {
		width: 75rpx;
		top: 10px;
		margin-left: 10px;
		font-style: normal;
		font-weight: lighter;
		text-align: center;
	}

	.search_line_img {
		text-align: center;
		margin-top: 20rpx;
		width: 25px;
		height: 25px;
		padding-right: 20px;
	}

	.search input {
		width: 75%;
		border-radius: 13px;
		background: #fff;
		border: none;
		font-size: 21rpx;
		padding: 1% 2.5%;
		margin-right: 5px;
	}

	.search button {
		line-height: 30px;
		background: none;
		text-align: center;
		border: none;
		padding: 3px;
	}

	.search button::after {
		content: none;
	}

	.searchcion {
		width: 24px;
		height: 24px;
		text-align: center;
	}

	.cont {
		width: 94%;
		padding: 3%;
	}


	.group_t {
		display: flex;
		width: 100%;
		height: 100rpx;
		background-color: #ffffff;
	}

	.group_t_ul_this {
		display: flex;
		flex: 1;
		height: 80rpx;
		align-items: center;
		justify-content: center;
		flex-flow: row;
		color: red;
		border-bottom: 4rpx solid #ff0000;
	}

	.group_t_ul {
		display: flex;
		flex: 1;
		height: 80rpx;
		align-items: center;
		justify-content: center;
		flex-flow: row;
		border-bottom: 1rpx solid #c0c0c0;
	}

	.group_top_ul_li {
		display: flex;
		align-items: center;
		font-size: 22rpx;
	}

	.group_top ul li_this {
		border-bottom: 1px solid #f00;
	}


	.group_top_ul_li_img {
		width: 30rpx;
		height: 30rpx;
		flex-flow: row;
	}

	.group_top_ul_li_i {
		flex-flow: row;
	}

	.group_top ul li a i img {
		float: left;
		margin-top: 1px;
	}

	.groupListBox {
		width: 94%;
		margin: auto;
		overflow: hidden;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		padding-top: 20rpx;
	}

	.group_list {
		width: calc(50% - 10rpx);
		overflow: hidden;
		margin-bottom: 20rpx;
		background: #ffffff;
		border-radius: 10rpx;
	}

	.group_list_a {
		overflow: hidden;
	}

	.group_list_dl {
		overflow: hidden;
		display: flex;
		flex-direction: column;
	}

	.group_list_dl_dt {
		width: 100%;
		overflow: hidden;
	}

	.group_list_dl_dt_img {
		width: 90%;
		height: auto;
		margin: 16rpx;
	}

	.group_list_dl_dd {
		width: 100%;
		box-sizing: border-box;
		padding: 8rpx 15rpx;
		flex-direction: row;
	}

	.group_list_dl_dd_h3 {
		flex-wrap: wrap;
		font-size: 24rpx;
		font-weight: bold;
		color: #000;
	}

	.group_list_dl_dd_h3_i {
		font-size: 21rpx;
		padding: 0px 5px;
		overflow: hidden;
		border-radius: 2px;
		text-align: center;
		line-height: 17px;
		color: #fff;
		margin-right: 5px;
	}

	.group_list_dl_dd_h3_b {
		line-height: 20px;
		height: 40px;
		font-size: 21rpx;
		overflow: hidden;
	}

	.group_list_dl_dd_span {
		flex-direction: column;
		margin-top: 7px;
		font-size: 21rpx;
		color: #7a7a7a;
	}

	.group_list_dl_dd_span_strong {
		font-size: 24rpx;
		color: #f30;
		margin-right: 10px;
		font-weight: normal;
		font-family: Arial, Helvetica, sans-serif;
	}

	.group_list_dl_dd_span_b {
		text-decoration: line-through;
	}

	.group_list_dl_dd_em {
		font-size: 21rpx;
		color: #7a7a7a;
		width: 100%;
		display: flex;
		justify-content: space-between;
		padding-bottom: 15rpx;
		align-items: flex-end;
	}

	.group_list_dl_dd_em .gouwuche {
		font-size: 27rpx !important;
		color: #ff0000;
	}


	.group_list_dl_dd_em_b {
		font-size: 23rpx;
		color: #fa4903;
	}

	.blackshow {
		width: 100%;
		height: 100vh;
		background: rgba(2, 2, 2, 0.54);
		position: fixed;
		left: 0;
		top: 0;
		z-index: 1;
	}

	.shaixuam {
		width: 100%;
		position: fixed;
		right: 0;
		bottom: 0;
		z-index: 11;
		background: #ffffff;
		border-top-left-radius: 30rpx;
		border-bottom-left-radius: 30rpx;
		box-sizing: border-box;
		padding: 20rpx;
	}

	.sx-box {
		width: 100%;
		overflow: hidden;
		margin-bottom: 30rpx;
	}

	.sx-box .tit {
		font-size: 23rpx;
		font-weight: bold;
		height: 62rpx;
		line-height: 62rpx;
	}

	.sx-box .sx-1 {
		display: flex;
		justify-content: space-between;
	}

	.sx-box .sx-1 .ipt {
		width: calc(50% - 30rpx);
		height: 62rpx;
		background: #f1f1f1;
		border-radius: 40rpx;
		box-sizing: border-box;
		padding: 0 20rpx;
	}

	.center {
		text-align: center;
	}

	.sx-box .sx-2 {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}

	.sx-box .sx-2 .li {
		width: calc(33.33% - 10rpx);
		height: 62rpx;
		background: #f1f1f1;
		border-radius: 40rpx;
		box-sizing: border-box;
		padding: 0 20rpx;
		text-align: center;
		line-height: 62rpx;
		font-size: 22rpx;
		margin-bottom: 20rpx;
		overflow-x: hidden;
	}

	.sx-box .sx-2 .on {
		border: #ff0000 2rpx solid;
		color: #ff0000;
	}

	.sx-foot {
		height: 100rpx;
		width: 74%;
		display: flex;
		justify-content: space-between;
		position: fixed;
		bottom: 0;
		left: 20%;
	}

	.sx-foot .reset {
		border: #ff0000 2rpx solid;
		border-radius: 40rpx;
		height: 70rpx;
		line-height: 70rpx;
		text-align: center;
		width: calc(50% - 10rpx);
		color: #ff0000;
	}

	.sx-foot .queding {
		border-radius: 40rpx;
		height: 70rpx;
		line-height: 70rpx;
		text-align: center;
		width: calc(50% - 10rpx);
		background: #ff0000;
		color: #ffffff;
	}

	.guanbi {
		position: absolute;
		color: #848484;
		right: 0;
		top: 0rpx;
		width: 90rpx;
		height: 50rpx;
		text-align: center;
		line-height: 50rpx;
		padding-top: 20rpx;
	}

	.guanbi .iconfont {
		font-size: 26rpx !important;
	}
</style>
