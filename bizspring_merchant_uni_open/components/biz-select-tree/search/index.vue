<template>
	<view class="tmp-box">
		<view class='filterBox'>
			<view class='filter-input'>
				<text style="color: #b8b8b8;"  class="iconfont icon-sousuo filterImg"/>
				<input class="text" type='text' maxlenth="20" v-model="inputVal" confirm-type="搜索" @confirm='handleFllter' placeholder='搜索'></input>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				inputVal: "",

			};
		},
		methods: {
			handleFllter(e) {
				this.$emit("confirm", e.detail.value)
			},
			clears(){
				this.inputVal=""
			}
		},
	}
</script>

<style lang="scss" scoped>
	.filterBox {
		padding: 15rpx 32rpx;
		background-color: #fff;
		.filter-input {
			height: 80rpx;
			background-color: #eeeff0;
			border-radius: 40rpx;
			display: flex;
			align-items: center;
			
			padding-left: 40rpx;
			.filterImg {
				width: 32rpx;
				height: 32rpx;
				margin-right: 20rpx;
				margin-bottom: 5rpx;
			}
			.filterImgs {
				width: 32rpx;
				height: 32rpx;
				
			}
			.text {
				width: 84%;
				background-color: #eeeff0;
				font-size: 32rpx;
				color: #000;
			}
		}
	}
	@import url("../css/icon.css");
</style>
