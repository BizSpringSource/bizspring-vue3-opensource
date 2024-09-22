<template>
	<view class="section">
		<!--  自定义标题栏-->
		<view class="goodsTopTile"
			:style="'height: ' + (appGlobal.navJiaonang.height+5) + 'px; padding-top:' + appGlobal.navTopHeight + 'px;'">
			<view class="back" @tap="goback">
				<text class="iconfont icon-fanhui"></text>
			</view>
			<view class="search-1"
				:style="'height: ' + appGlobal.navJiaonang.height + 'px; width:calc(100% - ' + (appGlobal.navJiaonang.width+70) + 'px);'">
				<input type="text" maxlength="20" placeholder-class="search_line_phcolor" class="search_line_input" placeholder="商品搜索"
					name="keyword" id="keyword" @blur="keywordChange"></input>
				<text class="iconfont icon-sousuo-"></text>
			</view>
		</view>
		<view class="content"
			:style="'padding-top: ' + (appGlobal.navTopHeight + appGlobal.navJiaonang.height +10) + 'px;'">
			<view class="cateBox">
				<!-- 左侧类型 -->
				<scroll-view scroll-y="true" class="left">
					<view v-for="(item, index) in objsList" :key="index" @tap="tapType(index)"
						:class="'left-item ' + item.status">
						{{item.name}}
					</view>
				</scroll-view>
				<!-- 右侧数据 -->
				<scroll-view scroll-y="true" class="right">
					<view class="typeTree">
						<view v-for="(data, index) in treeChildren" :key="index" class="brand">
							<view v-if="data" class="item">
								<navigator :url="'../../goods/goods/list?id=' + data.id + '&orderType=TOP_DESC'" style="padding-bottom: 10px;display: block;font-weight: 600;font-size: 23rpx;">
									{{data.name}}</navigator>
								<view v-for="(dat, indx) in data.children" :key="indx"
									style="min-width: 50px;line-height: 35px;float: left;margin-right: 10px;">
									<navigator :url="'../../goods/goods/list?id=' + dat.id + '&orderType=TOP_DESC'"
										style="font-size: 21rpx;font-weight: 400;text-decoration: none;color:#777777">
										{{dat.name}}</navigator>
								</view>
							</view>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>
	</view>
</template>
<script module="subutils" lang="wxs" src="../../utils/subutils.wxs"></script>
<script>
	import {
		index
	} from "@/api/goods/goods_category.js"
	//获取应用实例  
	var app = getApp();
	export default {
		data() {
			return {
				appGlobal: app.globalData,
				orderType: "",
				objsList: [],
				treeChildren: [],
			};
		},
		components: {},
		props: {},
		onLoad(option) {
		},
		onShow: function() {
			this.goods_category_index();
		},
		methods: {
			goods_category_index() {
				index().then(response => {
					this.objsList = response.data.data;
					this.treeChildren = this.objsList[0].children;
					this.objsList[0].status = "active";
					// resolve();
				}).catch(error => {
					console.error("login-index err：", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			//返回
			goback: function() {
				uni.switchTab({
					url: '/pages/index/index'
				});
			},

			tapType: function(index) {

				this.treeChildren = this.objsList[index].children;
				for (let i = 0; i < this.objsList.length; i++) {
					if (i == index) {
						this.objsList[i].status = "active";
					} else {
						this.objsList[i].status = "";
					}
				}

			},
		}
	};
</script>
<style>
	.section {
		position: absolute;
		width: 100%;
		height: 100%;
		overflow: hidden;
	}

	.left {
		position: absolute;
		left: 0;
		top: 0;
		height: 100%;
		width: 200rpx;
		background: #F6F6F6;
		text-align: center;
		font-size: 23rpx;
	}

	.left .active {
		background: #FFF;
		position: relative;
		z-index: 2;
	}

	.left-item {
		padding: 30rpx 20rpx;
		text-align: center;
		color: #6d6d6d;
		font-size: 23rpx;
	}

	.right {
		margin-left: 200rpx;
		height: 100%;
		overflow: hidden;
		width: auto;
		background: #fff;
	}

	.right text {
		font-size: 23rpx
	}

	.typeTree {
		padding-top: 10px;
		width: 100%
	}

	.typeTree .item {
		text-align: left;
		display: inline-block;
		vertical-align: top;
	}

	.typeTree .brand,
	.typeTree .category {
		flex-wrap: wrap;
		overflow: hidden;
		display: inline-block;
		width: 90%;
		margin-left: 6%
	}

	.typeTree .title {
		padding: 10px 0;
		display: block;
		color: #333;
	}

	.typeTree image {
		width: 100px;
		margin: 0 auto;
	}

	.typeTree .ad-banner .img {
		width: 100% !important;
	}

	.typeTree .brand image {
		height: 100px;
	}

	.typeTree .category image {
		height: 85px;
	}

	.typeTree .category {
		padding-bottom: 10px;
	}

	.item text {
		padding-bottom: 10px;
		display: block;
		font-weight: 600;
	}

	.cateTitle {
		text-align: center;
		line-height: 60rpx;
	}

	.cateTitle .icn {
		font-size: 28rpx;
		color: #dddddd;
	}

	.cateTitle .tt {
		font-size: 23rpx;
		color: #575656;
		font-weight: bold
	}

	.ad-banner {
		width: 94%;
		margin: auto;
		overflow: hidden;
	}

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

	.content {
		width: 100%;
		height: 100vh;
		overflow: hidden;
		box-sizing: border-box;
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

	.cateBox {
		width: 100%;
		height: 100vh;
		overflow: hidden;
		border-top: #e8e8e8 2rpx solid;
		position: absolute;
	}
</style>
