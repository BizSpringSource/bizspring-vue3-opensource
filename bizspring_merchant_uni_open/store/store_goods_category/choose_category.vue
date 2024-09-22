<template>
	<view>
		<biz-tree :checkList="checkList" :props="prop" @sendValue="confirm" :isCheck="true" :treeNone="tree">
		</biz-tree>
	</view>
</template>

<script>
	import BizTree from '@/components/biz-tree/tree.vue';
	import {
		add
	} from "@/api/store/store_goods_category.js";
	var app = getApp();

	export default {
		components: {
			BizTree
		},
		data() {
			return {
				tree: [],
				checkList: [],
				backList: this.checkList,
				prop: {},
				max: 5,		

			}
		},
		onLoad(option) {
			let {
				prop,
				arr
			} = option
			// #ifdef H5
			let checkList = JSON.parse(decodeURIComponent(arr));
			// #endif
			// #ifdef MP-QQ||MP-WEIXIN||APP-NVUE||APP-PLUS
			let checkList = JSON.parse(arr);
			// #endif
			this.prop = JSON.parse(prop);
			this.checkList = checkList;
			this.tree=this.checkList;
			
		},
		methods: {

			//获取选中的值
			confirm(val, back) {
				if (back) {
					this.backConfirm(val)
					return
				}
				this.backList = val;
			},

			// 返回上一页传参
			backConfirm(val) {
				uni.$emit('selectSuccess', {
					list: val
				})
				uni.navigateBack();
			},
			makeTreeNode(leval) {
				let treeNoneList = [];
				let levelSN='级';
				for (let k = 0; k < 100; k++) {
					treeNoneList.push({
						name: `${leval}${levelSN}-${k}`,
						id: this.guid(),
						user: true
					})
				}
				return treeNoneList
			},

			guid() {
				function S4() {
					return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
				}
				return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
			}
		}
	}
</script>

