<template>
	<view class="content">
		<view class="icon" :style="{color: color, fontSize: fontSize}" v-html="type" @click="toclick"></view>
	</view>
</template>

<script>
	export default {
		props: {
			type: {
				type: String,
				default: '&#xe644;'
			},
			color: {
				type: String,
				default: '#666666'
			},
			fontSize: {
				type: String,
				default: '34rpx'
			}
		},
		methods: {
			toclick() {
				this.$emit('click');
			}
		}
	}
</script>

<style scoped>
	.content{
		display: flex;
		align-items: center;
		justify-content: center;
	}
	@font-face {
		font-family: 'biz';
		/** 阿里巴巴矢量图标库的字体库地址，可以替换自己的字体库地址 **/
		src: url('https://at.alicdn.com/t/font_1491431_6m7ltjo8wi.ttf') format('truetype');
	}

	.icon {
		font-family: biz !important;
		font-size: 34rpx;
	}

</style>
