<template>
	<view class="uni-footer">
		<view class="blank"></view>
		<view class="navlistbox" :style="'background:'+bg_color+';'">
			<view class="item" :data-url="item.url" @click="footerClick" v-for="(item,index) in navlist" :key="index">
				<text :class="item.icon"></text>
				<text class="name">{{item.name}}</text>
			</view>
		</view>
	</view>
</template>

<script>

	/**
	 * Section 标题栏
	 * @description 标题栏
	 * @property {String} type = [line|circle] 标题装饰类型
	 * 	@value line 竖线
	 * 	@value circle 圆形
	 * @property {String} title 主标题
	 * @property {String} subTitle 副标题
	 */

	export default {
		name: 'unifooter',
		props: {
			navlist: {
				type: Array,
				default: []
			},
			bg_color: {
				type: String,
				default: '#ffffff'
			},
		},
		data() {
			return {}
		},
		methods: {
			footerClick(e){
				this.$emit("navClick",e.currentTarget.dataset.url)//通过this.$emit(获取自定义对象，后面为子组件传递给父页面的参数)
			}
			
		}
	}
</script>
<style lang="scss" >
	$uni-primary: #2979ff !default;
	.uni-footer{
		width: 100%;
		height: 110rpx;
		overflow: hidden;
		.blank{
			height: 120rpx;
			width: 100%;
			overflow: hidden;
		}
		.navlistbox{
			width: 100%;
			height: 110rpx;
			position: fixed;
			left: 0;
			bottom: 0;
			display: flex;
			justify-content: space-between;
			align-items: center;
			.item{
				display: flex;
				flex: 1;
				text-align: center;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				.iconfont{
					font-size: 20px !important;
					margin-bottom: 8rpx;
				}
				.name{
					font-size: 28rpx;
				}
			}
		}
	}
</style>
