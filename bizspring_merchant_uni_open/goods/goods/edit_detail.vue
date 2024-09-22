<template>
	<view style="background-color: #ffffff;">
		<bizEdit placeholder="请输入介绍" :html="initHtml"  @editOk="editOk" uploadFileUrl="" ></bizEdit>
	</view>
</template>

<script>
var app = getApp();	
import bizEdit from '@/components/biz-edit/biz-edit.vue';
export default {
	data() {
		return {
			initHtml:'',
		}
	},
	components: {
		bizEdit
	},
	onLoad(options){
		let goodsDetail=options.html;
		this.initHtml=decodeURIComponent(goodsDetail);
	},
	methods: {
		editOk(res) {
			let pages = getCurrentPages();//获取所有页面栈实例列表
				uni.$emit('introductionSuccess',res.html)
				uni.navigateBack();
		}
	}
}
</script>

<style>
</style>
