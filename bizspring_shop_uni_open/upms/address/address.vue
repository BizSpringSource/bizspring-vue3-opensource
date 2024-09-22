<template>
	<view style="display:flex;flex-direction:column;margin-bottom:100rpx;">
		<radio-group @change="radioChange">
			<view v-for="(item, index) in objsList" :key="index"  
				style="background-color: #ffffff;display:flex;flex-direction:row;border-width: 2rpx; border-color: #f0e0e0;border-style: solid;border-radius: 15rpx;margin:10rpx;padding:25rpx 15rpx;">
				<view style="width:95%;font-size:22rpx;"  @click="address_selected(item)"   >
					<view class="gray">{{item.regionName}}{{item.address}}</view>
					<view class="gray" style="font-size:24rpx;">{{item.consignee}} {{item.phone}}
						<label v-if="item.isDefault==1"
							style="color:red;font-size:24rpx;padding:10rpx;margin:10rpx;">default</label>
					</view>
				</view>
				<navigator :url="'../../upms/address/address_add?id=' + item.id + '&editflag=edit'"
					style="width:15%;display: flex;align-items: center;vertical-align: center;justify-content: center;">
					<image style="width:50rpx;height:50rpx;" src="/static/images/edit.png"></image>
				</navigator>
				<view @tap="address_delete(item.id)"
					style="width:15%;display: flex;align-items: center;vertical-align: center;justify-content: center;">
					<image style="width:50rpx;height:50rpx;" src="/static/images/dleicon.png"></image>
				</view>
			</view>
		</radio-group>
		<view class="car_pay order_tt">
			<navigator style="text-align:center;" url="../../upms/address/address_add?editflag=add">新增地址</navigator>
		</view>
		<view class="loadingWxApplet" :hidden="!searchLoading">正在载入更多...</view>
		<view class="loadingWxApplet" :hidden="!searchLoadingComplete">没有更多了</view>
	</view>
</template>

<script>
	import {
		list,
		// bizspring-open
		// add,
		// save,
		// edit,
		// update,
		del
	} from "@/api/upms/address.js"
	
	
	//城市选择
	var app = getApp();
	export default {
		data() {
			return {
				// 设置加载的第几次，默认是第一次
				pageSize: 10,
				searchLoading: false,
				//"上拉加载"的变量，默认false，隐藏  
				searchLoadingComplete: false,
				//“没有数据”的变量，默认false，隐藏 
				objsData: [],
				region: 0,
				current: -1,
				addressId: "",
				objsList: [],
				loadType: 'load',
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
				},
				selectFlag:false
			};
		},
		components: {},
		props: {},
		onLoad(options) {
			this.selectFlag = options.selectFlag?options.selectFlag:false;
		},
		onShow: function() {
			this.address_list();
		},
		methods: {
			radioChange: function(evt) {
				for (let i = 0; i < this.objsData.length; i++) {
					if (this.objsData[i].id == evt.detail.value) {
						this.current = i;
						this.addressId = this.objsData[i].id;
						this.addressChecked();
					}
				}
			},
			
			/**
			 * 查询列表 list
			 */
			address_selected(item) {
				if(this.selectFlag){					
					uni.$emit('addressLoadSuccess',item);
					uni.navigateBack();
				}
			},

			/**
			 * 查询列表 list
			 */
			address_list() {
				uni.showLoading({
					title: 'Loading...'
					})
				list(this.queryParams).then(response => {
					//下拉加载
					if (this.loadType === "reach") {
						const newObjsList = response.data.data.content;
						newObjsList.forEach(item => this.objsList.push(item));
						this.searchLoading = false;
						//加载
					} else if (this.loadType === "load") {
						this.objsList = response.data.data.content;
						//下拉刷新
					} else if (this.loadType === "pullDown") {
						uni.stopPullDownRefresh();
					}
					if(!this.objsList || 0==this.objsList.length){
						uni.$emit('addressLoadSuccess',null);
					}
					if(this.objsList && 1==this.objsList.length){
						uni.$emit('addressLoadSuccess',this.objsList[0]);
					}
					
				});
			},

			address_delete: function(id) {
				uni.showLoading({
					title: 'Loading...'
				})
				del({
					'id': id
				}).then(response => {
					uni.hideLoading();
					uni.showToast({
						title: '已删除',
						icon: 'loading',
						duration: 1500
					});
					let that=this;
					setTimeout(function() {	
						that.loadType = "load";
						that.queryParams.pageNumber = 1;
						that.address_list();
					}, 2000);
				}).catch(error => {
					uni.hideLoading();
					console.error("error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},
			
		}
	};
</script>
<style>
	.test {
		background-color: red;
	}

	.order_tt {
		background: #ff0000;
		font-size: 22rpx;
		color: #ffffff;
	}
</style>
