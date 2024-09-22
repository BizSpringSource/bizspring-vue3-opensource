<template>
	<view class="content">
		<view class="mix-tree-list">
			<block v-for="(item, index) in treeList" :key="index">
				<view  
					class="mix-tree-item"
					:style="[{
							paddingLeft: item.rank*15 + 'px',
							zIndex: item.rank*-1 +50
						}]"
					:class="{
							border: treeParams.border === true,
							show: item.show,
							last: item.lastRank,
							showchild: item.showChild
						}"
					@click.stop="treeItemTap(item, index)"
				>
					<image class="mix-tree-icon" :src="item.lastRank ? treeParams.lastIcon : item.showChild ? treeParams.currentIcon : treeParams.defaultIcon"></image>
					{{item.name}}
					<navigator class="crud" :url="'../../store/store_goods_category/add?id='+item.id+'&name='+item.name+'&level='+item.rank" >新增</navigator>
					<navigator class="crud"  :url="'../../store/store_goods_category/edit?id='+item.id+'&name='+item.name+'&level='+item.rank" >编辑</navigator>
					<view @tap="treeItemDeleteTap(item,index)" class="crud" >删除</view>
				</view>
			</block>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			list: {
				type: Array,
				default(){
					return [];
				}
			},
			params: {
				type: Object,
				default(){
					return {}
				}
			}
		},
		data() {
			return {
				treeList: [],
				treeParams: {
					defaultIcon: '/static/mix-tree/defaultIcon.png',
					currentIcon: '/static/mix-tree/currentIcon.png',
					lastIcon: '',
					border: false
				}
			}
		},
		watch: {
			list(list){
				this.treeParams = Object.assign(this.treeParams, this.params);
				this.treeList=[];
				this.renderTreeList(list);
			}
		},
		methods: {
			//扁平化树结构
			renderTreeList(list=[], rank=0, parentId=[]){
				list.forEach(item=>{
					this.treeList.push({
						id: item.id,
						name: item.name,
						parentId,  // 父级id数组
						rank,  // 层级
						showChild: false,  //子级是否显示
						show: rank === 0  // 自身是否显示
					})
					if(Array.isArray(item.children) && item.children.length > 0){
						let parents = [...parentId];
						parents.push(item.id);
						this.renderTreeList(item.children, rank+1, parents);
					}else{
						this.treeList[this.treeList.length-1].lastRank = true;
					}
				})
			},
			// 点击
			treeItemTap(item){
				let list = this.treeList;
				let id = item.id;
				// if(item.lastRank === true){
					//点击最后一级时触发事件
					this.$emit('treeItemClick', item);
					// return;
				// }
				item.showChild = !item.showChild;
				list.forEach(childItem=>{
					if(item.showChild === false){
						//隐藏所有子级
						if(!childItem.parentId.includes(id)){
							return;
						}
						if(childItem.lastRank !== true){
							childItem.showChild = false;
						}
						childItem.show = false;
					}else{
						if(childItem.parentId[childItem.parentId.length-1] === id){
							childItem.show = true;
						}
					}
				})
			},
			treeItemDeleteTap(item){
				let list = this.treeList;
				let id = item.id;
				// if(item.lastRank === true){
					//点击最后一级时触发事件
					this.$emit('treeItemDeleteClick', item);
					this.treeList=[];
					// return;
				// }
				// item.showChild = !item.showChild;
				// list.forEach(childItem=>{
				// 	if(item.showChild === false){
				// 		//隐藏所有子级
				// 		if(!childItem.parentId.includes(id)){
				// 			return;
				// 		}
				// 		if(childItem.lastRank !== true){
				// 			childItem.showChild = false;
				// 		}
				// 		childItem.show = false;
				// 	}else{
				// 		if(childItem.parentId[childItem.parentId.length-1] === id){
				// 			childItem.show = true;
				// 		}
				// 	}
				// })
			}
		}
	}
</script>

<style>
	.mix-tree-list{
		display: flex;
		flex-direction: column;
		padding-left: 30upx;
	}
	.mix-tree-item{
		display: flex;
		align-items: center;
		font-size: 34upx;
		color: #333;
		height: 0;
		opacity: 0;
		transition: .2s;
		position: relative;
	}
	.mix-tree-item.border{
		border-bottom: 1px solid #eee;
	}
	.mix-tree-item.show{
		height: 105upx;
		opacity: 1;
	}
	.mix-tree-icon{
		width: 26upx;
		height: 26upx;
		margin-right: 8upx;
		opacity: .9;
	}
	
	.mix-tree-item.showchild:before{
		transform: rotate(90deg);
	}
	.mix-tree-item.last:before{
		opacity: 0;
	}
	.mix-tree-item .crud{
		font-size: 26rpx;
		color: #10a4ff;
		margin-left:30rpx ;
	}
</style>
