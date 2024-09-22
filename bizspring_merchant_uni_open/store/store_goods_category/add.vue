<template>
	<view>
		<form @submit="formsubmit">
			<view class="bg_white pd-b-10 pd-t-10 m-b-10 addbox">
					<view class="textarr">
						<textarea value="" v-model="formData.name" placeholder="请输入商品分类" class="area" />
					</view>
			</view>
			<view class="bg_white pd-b-10 pd-t-10 m-b-10 addbox">
				<view class="box padd-3">
					<view class="flex-row ai-c m-b-20 pd-t-10 juc-sb">
						<view>上级分类</view>
						<view class="color-gray1" >
							<text>{{parentName}}</text>
						</view>
					</view>
				</view>
			</view>
		</form>
		<view class="footer" @tap="formsubmit" ><view class="fabu">确 定</view></view>
	</view>
</template>

<script>
	
import { add,save }  from "@/api/store/store_goods_category.js";
var app = getApp();
	
export default {
	data() {
		return {
			selectListItem:[],
			storeGoodsCategoryTree:[],
			formData: {
					name: '',
					level: '',
					treePath:'',
			},
			parentName:null,
			parentId:null
		};
	},
	components: {
	},
	created() {
		uni.$on('selectSuccess',(data)=>{
			this.$set(this,'selectListItem',[...data.list]);
		})
	},
	onLoad(e) {
		this.parentId=e.id;
		this.parentName=e.name;
		this.formData.level=e.level;
		
	},
	methods: {
		toChoose(prop){
			this.clear();
			// #ifdef H5
				let items = encodeURIComponent(JSON.stringify(this.storeGoodsCategoryTree));
			// #endif
			// #ifdef MP-QQ||MP-WEIXIN
				let items = JSON.stringify(this.storeGoodsCategoryTree);
			// #endif
			uni.navigateTo({
				url:`choose_category?arr=${items}&prop=${JSON.stringify(prop)}`
			})
		},
		clear(){
			this.selectListItem=[];
		},
        //表单提交
        formsubmit(e){
			if (!this.formData.name) {
				uni.showToast({
					title: '分类名称不能为空',
					duration: 3000,
					icon: "none"
				});
				return;
			}
			save(this.formData,{"parentId":this.parentId}).then(response => {
				uni.showToast({
					title: '保存成功',
					duration: 3000,
					icon: "none"
				});
				let pages = getCurrentPages();
				var prevPage = pages[pages.length - 2];
				uni.navigateBack({
					url: "list",
					delta: 1,
					success(event) {
						prevPage.$vm.store_goods_category_list();
					}
				})
			});
        },
	}
};
</script>

<style lang="scss">
	.addbox{
		width: 94%;
		margin-left: 3%;
		border-radius: 20rpx;
	}
.piclist {
	display: flex;
	flex-wrap: wrap;
	justify-content: center;
	align-items: center;
	padding: 40rpx 0;
	border-bottom: $color-hui-e 2rpx solid;
	.upbox {
		width: 20vw;
		height: 20vw;
		margin: 10rpx;
		border: $color-hui-e 2rpx solid;
		color: $color-hui-8;
		font-size: $uni-font-size-base;
		.iconfont{
			font-size: $uni-font-size-title;
		}
		.img{
			width: 20vw;
			height: 20vw;
		}
	}
}
.textarr{
		padding: 20rpx;
		position: relative;
		.area{
			width: 100%;
			height: 200rpx;
		}
		.zishu{
			position: absolute;
			bottom: 20rpx;
			right: 20rpx;
		}
	}
.footer {
	height: 100rpx;
	width: 100%;
	.fabu {
		position: fixed;
		height: 90rpx;
		line-height: 90rpx;
		border-radius: 20rpx;
		width: 94%;
		left: 3%;
		background: $default-theme-color;
		color: #ffffff;
		text-align: center;
		bottom: 10rpx;
	}
}
.price-input-box{
	border: #dcdcdc 2rpx solid;
	height: 50rpx;
	line-height: 50rpx;
	display: flex;
	border-radius: 5rpx;
	.fuhao{
		font-weight: 600;
		background: #eeeeee;
		border-right: #dcdcdc 2rpx solid;
		width: 60rpx;
		text-align: center;
	}
	.ipt{
		width: calc(100% - 60rpx);
		height: 50rpx;
		line-height: 50rpx;
		box-sizing: border-box;
		padding: 0 15rpx;
	}
}
</style>
