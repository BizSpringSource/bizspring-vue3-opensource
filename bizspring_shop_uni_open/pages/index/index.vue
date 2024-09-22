<template>
	<view class="content">
		<view class="phone_main">
			<!--顶部-->
			<view class="indexTop" style="height: 280rpx">
				<view class="indexTopBg" :style="'background-color: ' + topBgcolor + ';'"></view>
				<view class="indexTopBox">
					<view class="page-section page-section-spacing swiper">
						<swiper :indicator-dots="indicatorDots" class="swiper_group" :autoplay="autoplay"
							interval="5000" duration="500" @change="swiperchang">
							<swiper-item class="swiper_group_item" v-for="(item, index) in indexBanner" :key="index">
								<image :src="item.src" class="swiper_group_img"></image>
							</swiper-item>
						</swiper>
					</view>
				</view>
			</view>
			<!--导航菜单-->
			<view class="nav" style="background: #fff;">
				<swiper :indicator-dots="false" autoplay="true" interval="5000" duration="500" class="swiper-box">
					<swiper-item>
						<view class="nav_ul" v-if="rootGoodsCategories">
							<view v-for="(item, index) in rootGoodsCategories" :key="index" class="nav_ul_li">
								<navigator :url="'../../goods/goods/list?id='+item.id+'&orderType=TOP_DESC'" >
									<image
										:src="item.image">
									</image>
									<text>{{item.name}}</text>
								</navigator>
							</view>
						</view>
					</swiper-item>
				</swiper>
			</view>
			<navigator class="adBanner">
				<image src="https://qiniuio.bizspring.cn/mobile/static/images/banners/index02-En.jpg" mode="widthFix"
					class="ad"></image>
			</navigator>
			<view class="phone_floor" v-if="floorsList">
				<view class="piclist">
					<view v-for="(item, index) in floorsList" :key="index" class="piclist_nav">
						<navigator :url="'../../goods/goods/detail?goodsId=' + item.id">
							<image class="piclist_nav_img" :src="item.thumbnail||'/static/images/default_thumbnail.jpg'"
								@error="onErrorImg(item)"></image>
							<view class="re_name">{{subutils.substr(item.name,0,12)}}</view>
							<view class="price">
								<text class="pr1">¥{{item.price}}</text>
								<text class="pr2">¥{{item.marketPrice}}</text>
							</view>
						</navigator>
					</view>
				</view>
			</view>
			<view class="loadingMore"></view>
		</view>
		<view class="loadingWxApplet" :hidden="!searchLoading">正在载入更多...</view>
		<view class="loadingWxApplet" :hidden="!searchLoadingComplete">没有更多了</view>
	</view>
</template>
<script module="subutils" lang="wxs" src="../../utils/subutils.wxs"></script>
<script>
	import {
		getStore,
		setStore
	} from '@/utils/store.js'
	import {
		isURL
	} from '@/utils/validate.js'
	import {
		getUserInfo,
		loginByPhone,
		loginByThirdParty,
		loginByUsername,
		logout,
		refreshToken
	} from '@/utils/login.js'
	import util from '@/utils/util'
	import {
		securityKey
	} from '@/config/config.js'
	import {
		index
	} from "@/api/goods/goods.js"

	var app = getApp();
	export default {
		data() {
			return {
				// 设置加载的第几次，默认是第一次
				//返回数据的个数  
				//排序字段
				orderType: "",
				//排序 asc正 desc反
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				searchLoadingComplete: false,
				//“没有数据”的变量，默认false，隐藏 
				indicatorDots: true,
				interval: 5000,
				duration: 500,
				goodsData: [],
				page: 2,
				index: 2,
				brand: [],
				// 滑动
				background: ['demo-text-1', 'demo-text-2', 'demo-text-3'],
				indicatorDots: true,
				autoplay: true,
				topBgcolor: '#8A72E2',
				indexBanner: [{
						src: 'https://qiniuio.bizspring.cn/mobile/static/images/banner1-En.jpg',
						bgcolor: '#8A72E2',
					},
					{
						src: 'https://qiniuio.bizspring.cn/mobile/static/images/banner2-En.jpg',
						bgcolor: '#EE914C',
					},
					{
						src: 'https://qiniuio.bizspring.cn/mobile/static/images/banner3-En.jpg',
						bgcolor: '#4091ff',
					},
				],
				loading: false,
				code: {
					src: "/code",
					value: "",
					len: 4,
					type: "image"
				},
				floorsList: [],
				goodsCategorys: [],
				rootGoodsCategories: [],
				selectionGoodss: [], //甄选
				goodsCategorysWidth: 350,
				queryParams: {
					orderType: 'TOP_DESC',
				},
				nowHours: null,
				nowMinutes: null,
				nowSeconds: null,
				hours: 0, //时
				minutes: 59, //分
				seconds: 59, //秒
				countdown: '00:59:59', //倒计时
				timer: null, //重复执行
			};
		},

		components: {},
		props: {},
		onLoad(options) {
		},


		async onShow() {
			// this.languageParams.userId = app.globalData.userInfo ? app.globalData.userInfo.id : null;
			uni.setNavigationBarColor({
				backgroundColor: '#3974e8'
			});
			this.index_floor_goods();
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

			index_floor_goods: function() {
				uni.showLoading({
					title: "Loading..."
				})
				index(this.queryParams).then(response => {
					uni.hideLoading();
					this.floorsList = response.data.data.floorGoodss;
					this.rootGoodsCategories = response.data.data.rootGoodsCategories;
					this.selectionGoodss = response.data.data.selectionGoodss;
					this.goodsCategorys = response.data.data.goodsCategorys;
					this.goodsCategorysWidth = 160 * this.goodsCategorys.length;

				});
			},
			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_thumbnail.jpg");
				this.$set(item, 'logo', "/static/images/default_thumbnail.jpg");
			},
			switchUser: function() {
				uni.switchTab({
					url: '../user/user'
				});
			},
			swiperchang: function(e) {
				var that = this;
				var bannerIndex = e.detail.current;
				var indexBanner = that.indexBanner;

				for (var i in indexBanner) {
					if (bannerIndex == i) {
						that.topBgcolor = indexBanner[i].bgcolor;
					}
				}
			},
		}
	};
</script>
<style>
	.body {
		border-radius: 60rpx;
		margin-top: -50rpx;
		overflow: hidden;
	}

	/***首页-开始***/
	.indexTop {
		width: 100%;
		overflow: hidden;
		position: relative;
	}

	.indexTop .indexTopBox {
		position: absolute;
		width: 100%;
		z-index: 1;
		top: 0;
		left: 0;
		overflow: hidden;
	}

	.indexTop .indexTopBox .indexTitle {
		width: 100%;
		overflow: hidden;
		padding-left: 20px;
		height: 80rpx;
		line-height: 64rpx;
		font-size: 24rpx;
		color: #ffffff;
	}

	.indexTop .indexTopBox .search_line {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 94%;
		height: 104rpx;
		margin: auto;
	}

	.indexTop .indexTopBox .search_line_a {
		width: 100%;
		height: 60rpx;
		border-radius: 50px;
		overflow: hidden;
		display: flex;
		background: #ffffff;
	}

	.indexTop .indexTopBox .search_line_input {
		outline: none;
		width: calc(100% - 25px);
		border: 0px;
		font-size: 22rpx;
		color: #8d8d8d;
		border-radius: 50px;
		background: none !important;
	}

	.indexTop .indexTopBox .icon-sousuo- {
		font-size: 28rpx !important;
		line-height: 50rpx;
		margin-right: 20rpx;
		color: #fd462f;
	}

	.indexTop .indexTopBox .search_line_phcolor {
		color: #a1a1a1;
		caret-color: #a1a1a1;
		font-size: 20rpx;
	}

	.indexTop .indexTopBox .search_line_b {
		width: 75rpx;
		top: 10px;
		margin-left: 10px;
		font-style: normal;
		font-weight: lighter;
		text-align: center;
	}

	.indexTop .indexTopBox .search_line_img {
		text-align: center;
		width: 25px;
		height: 25px;
	}
/* <!-- bizspring-open --> */
/* 	.indexTop .indexTopBg {
		background: #fd462f;
		width: 600px;
		height: 260px;
		border-radius: 300px;
		position: absolute;
		z-index: 0;
		top: -116px;
		left: 50%;
		margin-left: -300px;
	} */
/* <!-- bizspring-open --> */
	.indexTop .page-section {
		width: 100%;
		/* margin-left: 3%; */
		/* border-radius: 20rpx; */
	}

	.indexCat {
		width: 100%;
		overflow: hidden;
		display: flex;
		flex-flow: row;
		height: 80rpx;
	}

	.indexCat .vieleft {
		width: 94%;
		overflow: hidden;
		height: 60rpx;
		margin: auto;
	}

	.indexCat .cassName {
		width: 100rpx;
	}

	.vieleft .brand {
		width: 130rpx;
		height: 60rpx;
		line-height: 50rpx;
		float: left;
		padding: 0 15rpx;
		font-size: 23rpx;
		color: #ffffff;
	}

	.phone_main {
		width: 100%;
		height: 100%;
		margin: 0px auto;
		overflow: hidden;
	}

	.swiper-item {
		display: block;
		height: 150px;
	}

	.phone_top {
		width: 100%;
		position: relative;
	}

	.phone_top .close {
		width: 30px;
		height: 30px;
		position: absolute;
		top: 5px;
		right: 5px;
		z-index: 1;
	}

	.logo_line {
		padding: 5px 10px;
		height: 30px;
		line-height: 30px;
		overflow: hidden;
	}

	.logo_line img {
		max-width: 150px;
		float: left;
	}

	.logo_line span {
		float: right;
	}

	.logo_line .log_after {
		width: 90px;
		white-space: nowrap;
		float: right;
		font-size: 20rpx;
		text-align: right;
		text-overflow: ellipsis;
		-moz-text-overflow: ellipsis;
		overflow: hidden;
	}

	.logo_line .log {
		border: 1px solid #eee;
		text-align: center;
		font-size: 20rpx;
	}

	/*幻灯*/
	.focusBox {
		position: relative;
		width: 100%;
		height: 150px;
		overflow: hidden;
	}

	.focusBox .pic img {
		width: 100%;
		height: 150px;
		display: block;
	}

	.focusBox .hd {
		overflow: hidden;
		zoom: 1;
		position: absolute;
		bottom: 5px;
		left: 50%;
		margin-left: -50px;
		z-index: 3;
	}

	.focusBox .hd li {
		float: left;
		line-height: 15px;
		text-align: center;
		font-size: 20rpx;
		width: 25px;
		height: 5px;
		cursor: pointer;
		overflow: hidden;
		background: #919191;
		margin-left: 4px;
		filter: alpha(opacity=80);
		opacity: 0.8;
		-webkit-transition: All 0.5s ease;
		-moz-transition: All 0.5s ease;
		-o-transition: All 0.5s ease;
		text-indent: 999px;
	}

	.focusBox .hd .on {
		background: #fff;
		filter: alpha(opacity=100);
		opacity: 1;
	}


	/*导航*/
	.nav {
		width: 94%;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		margin-left: 3%;
		border-radius: 20rpx;
		padding: 5rpx 0;
	}

	.nav_ul {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 730rpx;
		margin: 0 auto;
		position: relative;
		left: 5rpx;
	}

	.nav_ul_li {
		width: 19.5%;
		height: 190rpx;
	}


	.nav_ul_li:not(:nth-child(3n)) {
		margin-right: 1rpx;
	}

	.nav_ul_li navigator {
		display: flex;
		flex-direction: column;
	}

	.nav_ul_li navigator image {
		width: 136rpx;
		height: 136rpx;
		margin: 0px auto;
	}

	.nav_ul_li navigator text {
		font-size: 21rpx;
		margin: 10rpx 0rpx;
		text-align: center;
	}

	.nav_ul_li view {
		border: 0rpx #fff none;
		background-color: #fff;
		display: flex;
		flex-direction: column;
	}

	.nav_ul_li view::after {
		border: none;
	}

	.nav_ul_li navigator image {
		width: 77rpx;
		height: 77rpx;
		margin: 0rpx auto;
	}

	.nav_ul_li view image {
		width: 95rpx;
		height: 95rpx;
		margin: 0rpx auto;
	}

	.nav_ul_li view text {
		font-size: 22rpx;
		margin: 12rpx 0rpx;
		text-align: center;
	}

	.swiper-box {
		height: 185rpx;
	}

	.swiper-box .wx-swiper-dots.wx-swiper-dots-horizontal {
		margin-bottom: 0;

	}

	.swiper-box .wx-swiper-dot {
		width: 30rpx;
		display: inline-flex;
		height: 10rpx;
		margin-left: 2rpx;
		justify-content: space-between;
		border-radius: 8rpx;
		margin-bottom: -10px;
	}

	.swiper-box .wx-swiper-dot::before {
		content: '';
		flex-grow: 1;
		background: rgba(255, 255, 255, 0.8);
		border-radius: 8rpx
	}

	.swiper-box .wx-swiper-dot-active::before {
		background: rgba(244, 0, 0, 0.8);
	}

	.newReDian {
		width: 96%;
		margin: auto;
		/* overflow: hidden; */
		display: flex;
		border-top: #ededed 2rpx solid;
		margin-top: 10rpx;
		padding-top: 20rpx;
		justify-content: space-between;
		align-items: center;
	}

	.newReDian .img {
		width: 180rpx;
		height: 60rpx;
		overflow: hidden;
	}

	.newReDian .img .pic {
		width: 100%;
	}

	.newReDian .textlist {
		width: calc(96% - 180rpx);
		margin-left: 20rpx;
	}

	.newReDian .textlist .swiper_news {
		width: 100%;
		height: 36rpx;
		line-height: 36rpx;
		font-size: 23rpx;
	}

	.newReDian .more {
		width: 40rpx;
		height: 60rpx;
		line-height: 60rpx;
		overflow: hidden;
	}

	.newReDian .more .iconfont {
		font-size: 26rpx !important;
		color: #ff0000;
	}

	/*广告banner*/
	.adBanner {
		width: 94%;
		margin: auto;
		overflow: hidden;
		margin-top: 20rpx;
	}

	.adBanner .ad {
		width: 100%;
		border-radius: 20rpx;
	}

	/*限时秒杀*/
	.miaoshaBox .miaoshaoTitle {
		margin-top: 20rpx;
		padding: 0 20rpx;
		overflow: hidden;
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.miaoshaBox .miaoshaoTitle .pic {
		width: 26%;
	}

	.miaoshaBox .miaoshaoTitle .pic .ms {
		width: 100%;
	}

	.miaoshaBox .daojishi {
		display: flex;
	}

	.miaoshaBox .daojishi .tim1 {
		font-weight: 500;
	}

	.miaoshaBox .daojishi .tim2 {
		background: #ff0000;
		color: #ffffff;
		padding: 4rpx 8rpx;
		border-radius: 8rpx;
	}

	.miaoshaBox .daojishi .tim3 {
		color: #ff0000;
		margin: 0 8rpx;
	}

	.miaoshaBox .tabTime {
		margin-top: 20rpx;
		padding: 0 20rpx;
		overflow: hidden;
		display: flex;
		align-items: center;
	}

	.miaoshaBox .tabTime .tab {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;
		border-bottom: #d0d0d0 2rpx solid;
		color: #999999;
		padding-bottom: 20rpx;
		font-size: 22rpx;
	}

	.miaoshaBox .tabTime .tab-on {
		color: #ff0000;
		padding-bottom: 12rpx;
		border-bottom: #ff0000 2rpx solid;
	}

	.miaoshaBox .tabTime .tab-on .time {
		font-weight: bold;
		font-size: 26rpx;
	}

	.miaoshaBox .tabTime .tab-on .name {
		font-size: 24rpx;
	}

	.miaoshaBox .miaoList {
		margin-top: 20rpx;
		padding: 0 20rpx;
		overflow: hidden;
	}

	.miaoshaBox .scroll {
		width: 100%;
		overflow: hidden;

	}

	::-webkit-scrollbar {
		width: 0;
		height: 0;
		color: transparent;
	}

	.miaoshaBox .scroolbox {
		overflow: hidden;
		display: flex;
	}

	.miaoshaBox .msitem {
		width: 220rpx;
		height: 360rpx;
		margin-right: 20rpx;
		overflow: hidden;
	}

	.miaoshaBox .msitem .img {
		width: 200rpx;
		overflow: hidden;
	}

	.miaoshaBox .msitem .img .image {
		width: 100%;
	}

	.miaoshaBox .msitem .title1 {
		font-size: 23rpx;
		width: 100%;
		overflow: hidden;
		height: 80rpx;
	}

	.miaoshaBox .msitem .price {
		display: flex;
		align-items: center;
	}

	.miaoshaBox .msitem .price .msjia {
		color: #ff0000;
		font-size: 33rpx;
		margin-right: 10rpx;
	}

	.miaoshaBox .msitem .price .msjia .ic {
		font-size: 18rpx;
	}

	.miaoshaBox .msitem .price .yuanjia {
		font-size: 18rpx;
		color: #8e8e8e;
		text-decoration: line-through;
	}

	.miaoshaBox .maorMs {
		text-align: center;
		width: 100%;
		line-height: 60rpx;
		margin-bottom: 20rpx;
	}

	.miaoshaBox .pintuan {
		margin-top: 20rpx;
		padding: 0 20rpx;
		overflow: hidden;
		display: flex;
		align-items: center;
	}

	.miaoshaBox .pintuan .t1 {
		font-size: 26rpx;
		font-weight: bold;
		color: #000000;
		margin-right: 20rpx;
	}

	.miaoshaBox .pintuan .t2 {
		font-size: 24rpx;
		color: #929292;
		margin-right: 10rpx;
	}

	.miaoshaBox .pintuan .t3 {
		font-size: 26rpx !important;
		color: #f80000;
		width: 50rpx;
	}

	.shopBox1 {
		background-repeat: no-repeat;
		background-position: top center;
		background-size: 100%;
	}

	.shopBox1 .link {
		width: 100%;
		height: 200rpx;
	}

	.shopBox1 .shopList {
		width: 100%;
		overflow: hidden;
		padding: 30rpx 20rpx;
		box-sizing: border-box;
	}

	.shopBox1 .scroolbox {
		overflow: hidden;
		display: flex;
		padding-bottom: 10rpx;
	}

	.shopBox1 .msitem {
		width: 220rpx;
		height: 360rpx;
		margin-right: 20rpx;
		overflow: hidden;
		border-radius: 20rpx;
		box-shadow: 0 0 4rpx #c7c7c7;
	}

	.shopBox1 .msitem .img {
		width: 220rpx;
		overflow: hidden;
	}

	.shopBox1 .msitem .img .image {
		width: 100%;
	}

	.shopBox1 .msitem .title1 {
		font-size: 23rpx;
		width: 100%;
		overflow: hidden;
		height: 80rpx;
		padding: 0 10rpx;
		box-sizing: border-box;
	}

	.shopBox1 .msitem .price {
		display: flex;
		align-items: center;
	}

	.shopBox1 .msitem .price .msjia {
		color: #ff0000;
		font-size: 23rpx;
		margin-right: 10rpx;
	}

	.shopBox1 .msitem .price .msjia .ic {
		font-size: 18rpx;
	}

	.shopBox1 .msitem .price .yuanjia {
		font-size: 18rpx;
		color: #8e8e8e;
		text-decoration: line-through;
	}

	.shopBox2 {
		background-repeat: no-repeat;
		background-position: top center;
		background-size: 100%;

	}

	.shopBox2 .link {
		width: 100%;
		height: 210rpx;
	}

	.shopBox2 .shopList {
		width: 100%;
		overflow: hidden;
		padding: 30rpx 20rpx;
		box-sizing: border-box;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}

	.shopBox2 .shopList .msitem {
		width: calc(33.3% - 15rpx);
		height: 360rpx;
		overflow: hidden;
		border-radius: 20rpx;
		box-shadow: 0 0 4rpx #c7c7c7;
		margin-bottom: 20rpx;
		background: #ffffff;
	}

	.shopBox2 .msitem .img {
		width: 100%;
		overflow: hidden;
	}

	.shopBox2 .msitem .img .image {
		width: 100%;
	}

	.shopBox2 .msitem .title1 {
		font-size: 23rpx;
		width: 100%;
		overflow: hidden;
		height: 80rpx;
		padding: 0 10rpx;
		box-sizing: border-box;
	}

	.shopBox2 .msitem .price {
		display: flex;
		align-items: center;
	}

	.shopBox2 .msitem .price .msjia {
		color: #ff0000;
		font-size: 21rpx;
		margin-right: 10rpx;
	}

	.shopBox2 .msitem .price .msjia .ic {
		font-size: 18rpx;
	}

	.shopBox2 .msitem .price .yuanjia {
		font-size: 18rpx;
		color: #8e8e8e;
		text-decoration: line-through;
	}

	.calssTab {
		width: 94%;
		margin: auto;
		display: flex;
		overflow: hidden;
		padding: 30rpx 0;
	}

	.calssTab .showTab {
		display: flex;
		flex: 1;
		flex-direction: column;
		text-align: center;
		align-items: center;
		border-right: 2rpx #dddddd solid;
	}

	.calssTab .showTab:last-child {
		border: none;
	}

	.calssTab .showTab .text1 {
		font-size: 26rpx;
		font-weight: bold;
		color: #333333;
	}

	.calssTab .showTab .text2 {
		font-size: 26rpx;
		color: #ffffff;
		font-weight: bold;
		background: #ff0000;
		padding: 2rpx 18rpx;
		border-radius: 10rpx;
	}

	.calssTab .showTab .text3 {
		font-size: 23rpx;
		color: #939393;
		padding: 2rpx 18rpx;
		border-radius: 20rpx;
	}


	.phone_floor {
		width: 94%;
		overflow: hidden;
		margin: auto;
		padding-top: 20rpx;
		background: none !important;
		border: none !important;
	}

	.phone_floor h1 {
		padding: 10px;
		font-weight: normal;
		overflow: hidden;
		font-size: 23rpx;
	}

	.phone_floor h1 .fl {
		border-left: 3px solid #e22c37;
		padding-left: 8px;
		height: 12px;
		line-height: 12px;
		float: left;
		margin-top: 3px;
	}

	.phone_floor h1 .fr {
		float: right;
	}

	.phone_floor h1 .fr a {
		color: #666;
		font-size: 20rpx;
	}

	.recom {
		width: 100%;
		overflow: hidden;
		position: relative;
		background: #f5f5f5;
		padding-bottom: 10px;
	}

	.ohbox {
		overflow: hidden;
		position: relative;
		min-width: 10px;
		left: 0px;
	}

	.piclist {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		justify-content: space-between;
	}

	.piclist_nav {
		width: calc(50% - 10rpx);
		border-radius: 20rpx;
		background-color: #ffffff;
		margin-bottom: 20rpx;
		overflow: hidden;
	}

	.piclist_nav_img {
		width: 100%;
		height: 362rpx;
	}

	.piclist_nav .tags {
		display: flex;
		padding-top: 20rpx;
		margin-bottom: 15rpx;
	}

	.piclist_nav .tags .jingxuan {
		padding: 5rpx 15rpx;
		background: #000000;
		color: #ffffff;
		border-radius: 8rpx;
		font-size: 20rpx;
		margin-left: 15rpx;
	}

	.piclist_nav .tags .fangxin {
		padding: 5rpx 15rpx;
		background: #ff0000;
		color: #ffffff;
		border-radius: 8rpx;
		font-size: 20rpx;
		margin-left: 15rpx;
	}

	.recom .list {
		overflow: hidden;
		zoom: 1;
		left: 50%;
		margin-left: -50px;
		position: absolute;
	}

	.recom .list li {
		width: 8px;
		height: 8px;
		border: 1px solid #ddd;
		background-color: #fff;
		float: left;
		display: inline;
		width: 15px;
		height: 15px;
		cursor: pointer;
		margin-left: 10px;
		line-height: 10000px;
		overflow: hidden;
	}

	.recom .list li.on {
		border-color: #e22c37;
		background-color: #e22c37;
	}

	.recom ul {
		width: 100%;
		overflow: hidden;
	}

	.recom ul li {
		width: 50%;
		height: 0;
		padding-bottom: 68%;
		display: block;
		margin: -1px;
		float: left;
		overflow: hidden;
		background: #fff;
		border: 1px solid #f2f2f2;
	}

	.recom ul a li {
		margin: 2px;
		overflow: hidden;
		float: left;
		position: relative;
		overflow: hidden;
	}

	.recom ul a li img {
		max-width: 130px;
	}

	.recom ul a li span {
		width: 100%;
		height: 30px;
		line-height: 30px;
		position: absolute;
		bottom: 0px;
		left: 0px;
		white-space: nowrap;
	}

	.recom ul a li span.bg {
		background-color: #000;
		filter: alpha(opacity=30);
		-moz-opacity: 0.3;
		-khtml-opacity: 0.3;
		opacity: 0.3;
	}

	.re_name {
		font-size: 23rpx;
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 15rpx;
	}

	.price {
		width: 100%;
		box-sizing: border-box;
		padding: 2rpx 8rpx;
	}

	.price .pr1 {
		font-size: 26rpx;
		color: #ff0000;
	}

	.price .pr2 {
		font-size: 18rpx;
		color: #959595;
		text-decoration: line-through;
		margin-left: 14rpx;
	}

	/*商城活动*/

	.phone_floor .phone_activity h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_activity {
		width: 100%;
		overflow: hidden;
	}

	.phone_activity ul li {
		width: 33.3%;
		float: left;
		display: block;
	}

	.phone_activity ul li a {
		padding: 10px;
		overflow: hidden;
		display: block;
		border-right: 1px solid #eee;
	}

	.phone_activity ul li:last-of-type a {
		border-right: 0px;
	}

	.phone_activity ul li a img {
		width: 100%;
	}

	.phone_activity ul li p {
		width: 100%;
		overflow: hidden;
		text-align: center;
	}

	.phone_activity ul li p.price {
		font-size: 20rpx;
		color: #666;
		height: 30px;
		line-height: 30px;
	}

	.phone_activity ul li p.price b {
		font-size: 26rpx;
		color: #e22c37;
		font-family: Arial, Helvetica, sans-serif;
	}

	.phone_activity ul li p.dis {
		background-color: #d8505c;
		color: #fff;
		font-size: 26px;
	}

	/*超值购*/
	.phone_floor .phone_group h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_group {
		width: 100%;
		overflow: hidden;
	}

	.phone_group ul li {
		width: 50%;
		display: block;
		float: left;
	}

	.phone_group ul li a {
		padding: 5px;
		overflow: hidden;
		float: left;
		border-right: 1px solid #eee;
		border-bottom: 1px solid #eee;
	}

	.phone_group ul li:nth-child(2n) a {
		border-right-color: #fff;
	}

	.phone_group ul li:nth-child(3) a {
		border-bottom: 0px;
	}

	.phone_group ul li:nth-child(4) a {
		border-bottom: 0px;
	}

	.phone_group ul li a img {
		width: 100%;
	}

	.phone_group ul li p {
		width: 100%;
		overflow: hidden;
		height: 30px;
		line-height: 30px;
	}

	.phone_group ul li p.g_name {
		font-size: 20px;
		color: #666;
		height: 24px;
		line-height: 24px;
	}

	.phone_group ul li p.price b {
		font-size: 24rpx;
		color: #e22c37;
	}

	.phone_group ul li p.price s {
		color: #999;
		font-size: 20rpx;
	}

	/*品牌街*/

	.phone_brand {
		margin: 0px 10px 5px 10px;
		overflow: hidden;
		padding-top: 1px;
		padding-left: 1px;
	}

	.phone_brand ul li {
		width: 50%;
		display: block;
		float: left;
	}

	.phone_brand ul li a {
		padding: 1px;
		display: block;
		border: 1px solid #eee;
		margin-top: -1px;
		margin-left: -1px;
	}

	.phone_brand ul li a img {
		width: 142px;
		height: 66px;
		margin: 0px auto;
		display: block;
	}

	/*精选市场*/
	.phone_floor .phone_menu h1 {
		border-bottom: 1px solid #eee;
	}

	.phone_menu {
		overflow: hidden;
		padding-bottom: 2%;
	}

	.phone_menu ul li {
		width: 47%;
		margin-left: 2%;
		float: left;
		display: block;
		margin-top: 2%;
	}

	.phone_menu ul li a {
		width: 100%;
		background-color: #f1f1f1;
		float: left;
		padding: 5px 0px;
	}

	.phone_menu ul li .fl {
		max-width: 65px;
		display: block;
		float: left;
		margin-left: 10px;
		line-height: 50px;
		font-size: 24rpx;
		white-space: nowrap;
	}

	.phone_menu ul li .fr {
		display: block;
		float: right;
		margin-right: 10px;
	}

	.phone_menu ul li .fr img {
		width: 50px;
		height: 50px;
		border-radius: 50%;
	}

	.swiper_group {
		height: 260rpx;
		width: 100%;
	}

	.swiper_group_item {
	}

	.swiper_group_img {
		width: 100%;
		height: 260rpx;
	}

	.loadingMore {
		width: 94%;
		margin: auto;
		padding: 30rpx 0;
		text-align: center;
	}

	.towAd {
		width: 94%;
		margin: auto;
		overflow: hidden;
		background: #ffffff;
		box-sizing: border-box;
		padding: 20rpx;
		margin-top: 20rpx;
		display: flex;
		border-radius: 20rpx;
		justify-content: space-between;
	}

	.towAd .item {
		width: calc(50% - 10rpx);
	}

	.towAd .item image {
		width: 100%;
	}

	.loading {
		width: 100%;
		padding-top: 40%;
		text-align: center;
	}
</style>