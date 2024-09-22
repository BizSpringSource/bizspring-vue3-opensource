<template>
	<view>
		<view class="w100 bg_white padd-3 box-si-border">
			<view class="listbox  border-bottom1   bg_white w100 box-si-border m-b-10">
				<view class="item flex-row">
					<view class="img">
						<image class="pic" :src="store.logo" @error="onErrorImg(store)" ></image>
					</view>
					<view class="proinfo">
						<view class="title m-top-20 ">{{user.username}}
						</view>
						<view class="tag color-gray2  ">手机 : {{user.mobile}}</view>
					</view>
				</view>
			</view>
			<view class="topkongbai"></view>
			<view class="flex-row border-bottom1 juc-sb ai-c height-100">
				<text class="font-weight-500">邮箱</text>
				<view class="flex-row ai-c" data-type="usernick">
					<text class="color-gray1">{{user.email}}</text>
				</view>
			</view>
		</view>
		<!-- 底部按钮 -->
		<view class="blank"></view>
	</view>
</template>

<script>
	import {
		index,
	} from '@/api/upms/profile.js'
	const app = getApp();
	export default {
		data() {
			return {
				store:{},
				user:{},
				type: 'username',
			};
		},
		components: {
		},
		onLoad(options) {
		},
		onShow() {
				this.profile_index();
		},
		methods: {

			/**
			 * 详情
			 */
			profile_index: function() {
				uni.showLoading({
					title: 'Loading...'
				})
				index().then(response => {
					uni.hideLoading();
					this.store = response.data.data.store;
					this.user = response.data.data.merchant;

				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
				})
			},

			onErrorImg(item) {
				this.$set(item, 'thumbnail', "/static/images/default_logo.png");
				this.$set(item, 'logo', "/static/images/default_logo.png");
			},
		}
	};
</script>

<style lang="scss">
	.height-100 {
		height: 120rpx;

		.iconfont {
			font-size: 28rpx !important;
			color: #a2a2a2;
			margin-left: 20rpx;
		}

		.face {
			height: 80rpx;
			overflow: hidden;

			.img {
				width: 80rpx;
				height: 80rpx;
				border-radius: 80rpx;
			}

			.face-icon {
				font-size: 70rpx !important;
				color: #b8b8b8;
			}
		}
	}

	.tanchuang {
		.tit {
			height: 90rpx;
			line-height: 90rpx;
			text-align: center;
		}

		.ipt {
			height: 90rpx;
			line-height: 90rpx;
			border: #eeeeee 2rpx solid;
			border-radius: 8rpx;
			box-sizing: border-box;
			padding: 0 20rpx;
			margin-bottom: 30rpx;
		}

		.iptarea {
			height: 180rpx;
			line-height: 60rpx;
			border: #eeeeee 2rpx solid;
			border-radius: 8rpx;
			box-sizing: border-box;
			padding: 0 20rpx;
			margin-bottom: 30rpx;
			width: 100%;
		}
	}

	.blank {
		width: 100%;
		height: 100rpx;
	}

	.btn-full {
		position: fixed;
		bottom: 100rpx;
		left: 3%;
	}


	.listbox {
		.item {
			padding: 20rpx 0;
			box-sizing: border-box;

			.img {
				width: 120rpx;
				height: 120rpx;
				overflow: hidden;
				margin: 22rpx 22rpx 22rpx 0;

				.pic {
					width: 120rpx;
					height: 120rpx;
					border-radius: 120rpx;
				}
			}

			.proinfo {
				width: calc(100% - 165rpx);

				.title {
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
					}
				}
			}
		}

		.price {
			font-weight: bold;
			color: #ff6600;
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
</style>
