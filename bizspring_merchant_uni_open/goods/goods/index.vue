<template>
	<view>
		<uni-forms ref="form" label-width="80px" :rules="rules" :model="formData" err-show-type="toast"
			validate-trigger="bind">
			<view class="w100 bg_white pd-b-10 pd-t-10 m-b-10">
				<view class="box padd-3 m-b-10">
					<uni-forms-item label-width="200px" required label="商品轮播图"
						class="uni-item" name="goodsImages">
					</uni-forms-item>
					<view class="piclist">
						<htz-image-upload :max="19" v-model="imagesData" mediaType="all" :chooseNum="9"
							:sourceType="['album','camera']" :compress="false" :quality="80"
							:formData="{'ceshi': '123'}" :remove="true" @imgDelete="imgDelete" @uploadFail="uploadFail"
							@chooseSuccess="chooseSuccess">
						</htz-image-upload>
					</view>
				</view>
			</view>

			<view class="w100 bg_white m-top-20 pd-b-10 ">
				<view class="box padd-3">
					<view class="flex-row ai-c  pd-t-20  border-bottom1  line-height-90">
						<uni-forms-item label-width="110px" required label="商品分类"
							class="uni-item" name="goodsCategoryId">
							<view class="uni-item-value" v-if="selectListItem&&selectListItem.length>0"
								@tap="toChoose(dprop)">
								<view v-for="(item,index) in selectListItem"
									style="margin: 2px;display: inline-block;line-height: 48rpx;" :key="index">
									<view v-if="item&&item.path&&item.path.length>0" v-for="(row,k) in item.path"
										style="display: inline-block;" :key="k">
										{{row.name}}-
									</view>
									{{item.name}}
								</view>
							</view>
							<view v-else class="uni-item-value" @tap="toChoose(dprop)">
								<text>未选择</text>
							</view>
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c  pd-t-20   line-height-90">
						<uni-forms-item label-width="110px" label="店铺商品分类:"
							class="uni-item">
							<view class="uni-item-value" v-if="selectStoreListItem&&selectStoreListItem.length>0"
								@tap="toChooseStore(dprop)">
								<view class="color-gray2" v-for="(item,index) in selectStoreListItem"
									style="margin: 2px;display: inline-block;" :key="index">
									<view v-if="item&&item.path&&item.path.length>0">
										<view v-for="(row,k) in item.path" style="display: inline-block;" :key="k">
											{{row.name}}-
										</view>
										{{item.name||'请选择'}}
									</view>
								</view>
							</view>
							<view class="uni-item-value" v-else @tap="toChooseStore(dprop)">
								<text>未选择</text>
							</view>
						</uni-forms-item>
					</view>
				</view>
			</view>

			<view class="w100 bg_white m-top-20 ">
				<view class="box padd-3 ">
					<view class="flex-row ai-c  pd-t-20  border-bottom1  line-height-90">
						<uni-forms-item required label="名称:" class="uni-item" name="name">
							<input type="text" class="uni-item-value" name="title" maxlength="100"
								v-model="formData.name"
								placeholder="请输入名称" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c  pd-t-20  border-bottom1  line-height-90">
						<uni-forms-item label="副标题:" :error-message="false" class="uni-item">
							<input type="text" class="uni-item-value" maxlength="100" v-model="formData.caption"
								name="mallnum" placeholder="请输入副标题"
								style="width: calc(100% - 200rpx);" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c  pd-t-20   line-height-90">
						<uni-forms-item label="品牌:" class="uni-item">
							<picker @change="bindBrandsChange" :value="brandsIdx" range-key="name" :range="brands">
								<view class="uni-item-value">
									{{brands[brandsIdx]&&brands[brandsIdx].name?brands[brandsIdx].name:''}}</view>
							</picker>
						</uni-forms-item>
					</view>
				</view>

				<view v-if="!formData.id" class="box padd-3">
					<view class="flex-row ai-c  pd-t-20 border-top1  line-height-90">
						<uni-forms-item required label="编号:" class="uni-item" name="sn">
							<input class="uni-item-value" type="text" maxlength="30" v-model="formData.sn"
								@blur="checkSn" name="mallnum"
								placeholder="请输入编号" />
						</uni-forms-item>
					</view>
				</view>
			</view>

			<view class="w100 bg_white m-top-20 pd-b-20 pd-t-20">
				<navigator :url="'edit_detail?html='+encodeURIComponent(formData.introduction)" class="box padd-3">
					<view class="flex-row ai-c m-b-20 line-height-90">
						<uni-forms-item required label="介绍:" class="uni-item"
							name="introduction">
						</uni-forms-item>
						<view class="face flex-row ai-c juc-sb">
							<text style="margin-left:auto; margin-right:0;" class="iconfont icon-angle-right"></text>
						</view>
					</view>
					<view class="flex-row ai-c line-height-90">
						<view class="uni-item-value bg_default height-80"
							:url="'edit_detail?html='+encodeURIComponent(formData.introduction)">
							<input type="text" class="detail_text " v-model="formData.introduction"
								placeholder="介绍" />
						</view>
					</view>
				</navigator>
			</view>
			<view class="w100 bg_white m-top-20 ">
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item required label="销售价:" class="uni-item" name="price">
							<input class="uni-item-value" type="digit" v-model="formData.price" maxlength="11"
								name="mallnum" placeholder="请输入销售价" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item label="成本价:" class="uni-item">
							<input type="digit" maxlength="11" name="mallnum" v-model="formData.cost"
								placeholder="请输入成本价"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item label="市场价:" class="uni-item" name="marketPrice">
							<input type="digit" maxlength="11" name="mallnum" v-model="formData.marketPrice"
								placeholder="请输入市场价"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
			</view>
			<view class="w100 bg_white ">
			</view>
			<view class="w100 bg_white ">
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item label="单位:" class="uni-item">
							<input type="text" maxlength="3" name="mallnum" v-model="formData.unit"
								placeholder="请输入单位"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item label="重量(kg):" class="uni-item">
							<input type="number" maxlength="12" name="mallnum" v-model="formData.weight"
								placeholder="请输入重量"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
			</view>
		</uni-forms>
		<uni-forms ref="formPlus" :rules="rulesPlus" label-width="90px" :model="skuForm.sku" err-show-type="toast"
			validate-trigger="bind">
			<view class="w100 bg_white ">
				<view class="box padd-3 m-b-10 ">
					<view class="flex-row ai-c pd-t-20   line-height-90">
						<uni-forms-item required label="库存:" label-width="80px" class="uni-item"
							name="stock">
							<input type="number" maxlength="9" name="mallnum" v-model="skuForm.sku.stock"
								placeholder="请输入库存"
								class="uni-item-value" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c pd-t-20  line-height-90">
						<uni-forms-item label-width="100px" label="是否需要物流:"
							style="line-height: 26rpx;" class="uni-item">
							<switch class="uni-item-value" :checked="formData.isDelivery" style="transform:scale(0.8)"
								@change="isDeliveryChange" />
						</uni-forms-item>
					</view>
				</view>
			</view>
			<view class="w100 bg_white pd-b-10 pd-t-10 m-top-20">

				<view class="box padd-3">
					<view class="flex-row ai-c  pd-t-20  border-bottom1 line-height-90">
						<uni-forms-item label="关键词:" class="uni-item">
							<input class="uni-item-value" type="text" maxlength="50" name="mallnum"
								v-model="formData.keyword"
								placeholder="请输入关键词" />
						</uni-forms-item>
					</view>
				</view>
				<view class="box padd-3">
					<view class="flex-row ai-c m-b-20 pd-t-20 line-height-90">
						<uni-forms-item label="备注:" class="uni-item">
							<input class="uni-item-value" type="text" maxlength="50" name="mallnum"
								v-model="formData.remark"
								placeholder="请输入备注" />
						</uni-forms-item>
					</view>
				</view>
			</view>
		</uni-forms>
		<view class="footer">
			<view @tap="submitForm" class="btn-add btn-full">保存</view>
		</view>
	</view>
</template>

<script>
	import {
		add,
		edit,
		check_sn,
		save,
		update,
		upload_goods_image
	} from '@/api/goods/goods.js'
	import htzImageUpload from '@/components/htz-image-upload/htz-image-upload.vue'
	import {
		baseURL
	} from '@/config/config.js'

	var app = getApp();
	export default {
		data() {
			return {
				messages: app.globalData.messages,
				clas_idx: 0,
				dprop: { //单选模式
					label: 'name',
					children: 'children',
					multiple: false,
					nodes: true
				},
				selectListItem: [],
				selectStoreListItem: [],
				imagesData: [],

				checkedSn: false,
				brands: [],
				brandsIdx: 0,
				goodsCategoryTree: [],
				storeGoodsCategoryTree: [],
				formData: {
					id: null,
					//介绍
					introduction: "",
					// 编号
					sn: null,
					// 名称
					name: null,
					// 副标题
					caption: null,
					// 销售价
					price: null,
					// 成本价
					cost: null,
					// 市场价
					marketPrice: null,
					// 最大佣金
					unit: null,
					// 重量
					weight: null,
					// 是否需要物流
					isDelivery: true,
					// 备注
					remark: null,
					// 搜索关键词
					keyword: null,
					// 商品分类
					goodsCategoryId: null,
					goodsCategoryName: null,
					// 品牌
					brandId: null,
					type: null,
					// 店铺商品分类
					storeGoodsCategoryId: null,
					goodsImages: [],
				},
				skuForm: {
					sku: {
						'price': '',
						'cost': '',
						'marketPrice': '',
						'stock': '',
						'allocatedStock': '',
						'isDefault': true,
						'lockFlag': false,
						'goodsId': '',
					},
				},
				isUpdate: false,
				rules: {
					name: { //图片集合
						rules: [{
							required: true,
							errorMessage: '名称不能为空'
						}]
					},
					goodsImages: { //图片集合
						rules: [{
								format: 'array'
							},
							{
								validateFunction: function(rule, value, data, callback) {
									if (value.length < 1) {
										callback('请至少选择一个商品轮播图');
									}
									return true
								}
							}
						]
					},
					goodsCategoryId: {
						rules: [{
							required: true,
							errorMessage: '商品分类不能为空'
						}]
					},
					sn: {
						rules: [{
							required: true,
							errorMessage: '编号不能为空'
						}]
					},
					price: {
						rules: [{
							required: true,
							errorMessage:'销售价不能为空'
						}]
					},
					introduction: {
						rules: [{
							required: true,
							errorMessage: '介绍不能为空'
						}]
					}
				},
				rulesPlus: {
					stock: {
						rules: [{
							required: true,
							errorMessage: '库存不能为空'
						}, {
							format: 'number',
							errorMessage: '库存请输入数字'
						}]
					}

				},
			};
		},
		components: {
			htzImageUpload,
		},
		created() {
			//商品分类选择
			uni.$on('selectSuccess', (data) => {
				this.$set(this, 'selectListItem', [...data.list]);
				if (this.selectListItem && 0 < this.selectListItem.length) {
					this.goodsCategorySelected(this.selectListItem[0].id, true);
				}
			})
			//店铺商品分类选择
			uni.$on('selectSuccessStore', (data) => {
				this.$set(this, 'selectStoreListItem', [...data.list]);
				this.storeGoodsCategorySelected();
			})

			//商品详情编辑
			uni.$on('introductionSuccess', (data) => {
				this.$set(this.formData, 'introduction', data);
			});


		},
		onLoad(e) {
			if (e.id) {
				this.goods_edit(e.id);
				this.isUpdate = true;
			} else {
				this.goods_add();
			}
		},
		onReady() {
			this.$refs.form.setRules(this.rules);
			this.$refs.formPlus.setRules(this.rulesPlus);
		},
		methods: {

			/** 提交按钮 */
			submitForm() {
				this.$refs['form'].validate().then(res => {
					this.$refs['formPlus'].validate().then(res => {
						if (this.formData.id != null) {
							this.goods_update();
						} else {
							this.goods_save();
						}

					}).catch(err => {
						console.log('errPlus', err);
					})
				}).catch(err => {
					console.log('err', err);
				})
			},

			// 商品新增
			goods_add() {
				add().then(response => {
					this.brands = response.data.data.brands;
					this.goodsCategoryTree = response.data.data.goodsCategoryTree;
					this.storeGoodsCategoryTree = response.data.data.storeGoodsCategoryTree;
					this.formData.brandId = this.brands[this.brandsIdx].id;
				}).catch(error => {
					console.error("goods_add error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},

			//商品编辑
			goods_edit(id) {
				edit({
					"goodsId": id
				}).then(response => {
					this.formData = response.data.data.goods;
					this.formData.introduction = response.data.data.goods.introduction.replace('"', '\"');
					this.brands = response.data.data.brands;
					this.goodsCategoryTree = response.data.data.goodsCategoryTree;
					this.storeGoodsCategoryTree = response.data.data.storeGoodsCategoryTree;
					this.skuForm.sku = response.data.data.defaultSku;
					this.loadBrandIdx();
					this.loadEditImageList();
					this.goodsCategorySelected(this.formData.goodsCategoryId, false);
					this.formData.brandId = this.brands[this.brandsIdx].id;
					this.selectListItem = [response.data.data.checkedGoodsCategory];
					this.selectStoreListItem = [response.data.data.checkedStoreGoodsCategory];

				}).catch(error => {
					console.error("goods_add error", error);
					uni.showToast({
						title: '系统未知错误,请反馈给管理员',
						duration: 2000
					});
				})
			},

			//保存商品
			async goods_save(e) {
				let data = [];
				let param = {};
				this.skuForm.sku.price = this.formData.price; // 销售价
				this.skuForm.sku.cost = this.formData.cost; // 成本价
				this.skuForm.sku.marketPrice = this.formData.marketPrice; // 市场价
				//没有规格
				data = {
					"goodsForm": this.formData,
					"skuForm": this.skuForm,
				}

				await this.checkSn();
				if (!this.checkedSn) {
					return;
				}

				save(data, param).then(response => {
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
							prevPage.$vm.goods_list();
						}
					})
				});
			},
			//保存商品
			async goods_update(e) {

				let data = [];
				let param = {};
				this.skuForm.sku.price = this.formData.price; // 销售价
				this.skuForm.sku.cost = this.formData.cost; // 成本价
				this.skuForm.sku.marketPrice = this.formData.marketPrice; // 市场价
				//没有规格
				data = {
					"goodsForm": this.formData,
					"skuForm": this.skuForm,
				}

				update(data, param).then(response => {
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
							prevPage.$vm.goods_list();
						}
					})
				})
			},

			//上传失败
			uploadFail(err) {
				console.log('err', err)
			},
			//删除图片
			imgDelete(e) {
				let goodsImages = this.formData.goodsImages;
				this.formData.goodsImages = [];
				for (let i = 0; i < goodsImages.length; i++) {
					if (goodsImages[i].type == "IMAGE") {
						if (goodsImages[i].thumbnail != e.del) {
							this.formData.goodsImages.push(goodsImages[i]);
						}
					} else if (goodsImages[i].type == "MEDIA") {
						if (goodsImages[i].source != e.del) {
							this.formData.goodsImages.push(goodsImages[i]);
						}
					} else if (goodsImages[i].type == null) {
						if (goodsImages[i].thumbnail != e.del) {
							goodsImages[i].type = "IMAGE";
							this.formData.goodsImages.push(goodsImages[i]);
						}
					}
				}
			},
			//选择图片返回
			chooseSuccess(tempFilePaths, e) {
				this.imgUpload(tempFilePaths);
			},
			//上传文件
			imgUpload(tempFilePaths) {
				uni.showLoading({
					title: '上传中...',
				});
				let uploadImgs = [];
				tempFilePaths.forEach((item, index) => {
					uploadImgs.push(new Promise((resolve, reject) => {
						uni.uploadFile({
							url: baseURL +
								"/goods/merchant/goods/upload_goods_image?path=goods",
							filePath: item,
							name: 'file',
							header: app.globalData.headersFile,
							success: (uploadFileRes) => {
								resolve(uploadFileRes);
								if (uploadFileRes.statusCode == '200') {
									let _res = JSON.parse(uploadFileRes.data);
									let goodsImage = _res.data;
									if (goodsImage) {
										if (goodsImage.type == "IMAGE") {
											this.formData.goodsImages.push(goodsImage);
											this.imagesData.push(goodsImage.thumbnail);
										} else if (goodsImage.type == "MEDIA") {
											this.formData.goodsImages.push(goodsImage);
											this.imagesData.push(goodsImage.source);
										}
									}
								}
							},
							fail: (err) => {
								console.log(err);
								reject(err);
							},
							complete: () => {}
						});

					}))
				})
				Promise.all(uploadImgs) //执行所有需请求的接口
					.then((results) => {
						uni.hideLoading();
					})
					.catch((res, object) => {
						uni.hideLoading();
					});
			},

			//选择商品分类
			toChoose(prop) {
				// #ifdef H5
				let item = encodeURIComponent(JSON.stringify(this.selectListItem));
				// #endif
				// #ifdef MP-QQ||MP-WEIXIN
				let item = JSON.stringify(this.selectListItem);
				// #endif
				uni.navigateTo({
					url: `choose_category?arr=${item}&prop=${JSON.stringify(prop)}&from=category&treeNode=` +
						encodeURIComponent(JSON
							.stringify(this.goodsCategoryTree))
				})
			},
			//选择商品分类
			toChooseStore(prop) {
				// #ifdef H5
				let item = encodeURIComponent(JSON.stringify(this.selectStoreListItem));
				// #endif
				// #ifdef MP-QQ||MP-WEIXIN
				let item = JSON.stringify(this.selectStoreListItem);
				// #endif
				uni.navigateTo({
					url: `choose_category?arr=${item}&prop=${JSON.stringify(prop)}&from=storeCategory&treeNode=` +
						JSON.stringify(this.storeGoodsCategoryTree)
				})
			},
			//商品分类选择完成后触发事件
			goodsCategorySelected(goodsCategoryId, isNew) {
				this.formData.goodsCategoryId = goodsCategoryId;
			},
			checkSn() {
				return new Promise((resolve, reject) => {
					check_sn({
						"sn": this.formData.sn
					}).then(response => {
						if (response.data.data == false) {
							uni.showToast({
								title: '编码不可以用，或已被使用',
								icon: 'none'
							});
							this.checkedSn = false;
						} else {
							this.checkedSn = true;
						}
						resolve();
					});
				})
			},
			//更新imagesData
			loadEditImageList() {
				if (this.formData.goodsImages && this.formData.goodsImages.length > 0) {
					let goodsImages = this.formData.goodsImages;
					for (let i = 0; i < goodsImages.length; i++) {
						if (goodsImages[i].type == "IMAGE") {
							this.imagesData.push(goodsImages[i].thumbnail);
						} else if (goodsImages[i].type == "MEDIA") {
							this.imagesData.push(goodsImages[i].source);
						} else if (null == goodsImages[i].type) {
							this.imagesData.push(goodsImages[i].thumbnail || goodsImages[i].source);
						}
					}
				}
			},
			storeGoodsCategorySelected() {
				this.formData.storeGoodsCategoryId = this.selectStoreListItem[0].id;
			},
			bindBrandsChange(e) {
				this.brandsIdx = e.detail.value;
				this.formData.brandId = this.brands[e.detail.value].id;
			},
			loadBrandIdx() {
				for (let i = 0; i < this.brands.length; i++) {
					if (this.brands[i].id == this.formData.brandId) {
						this.brandsIdx = i;
					}
				}
			},
			isDeliveryChange: function(e) {
				this.formData.isDelivery = e.detail.value;
			},
		}
	};
</script>
<style scoped>
	/deep/ .uni-forms-item {
		position: relative;
		display: flex;
		margin-bottom: 20rpx;
		flex-direction: row;
		width: 100%;
	}

	/deep/ .uni-forms-item__label {
		display: flex;
		flex-direction: row;
		align-items: center;
		text-align: left;
		font-size: 24rpx;
		color: #606266;
		height: 36px;
		padding: 0 12px 0 0;
		vertical-align: middle;
		flex-shrink: 0;
		box-sizing: border-box;
		line-height: 30rpx;
	}
</style>
<style lang="scss">
	.content {
		font-size: 23rpx;
	}

	.uni-forms-item {
		position: relative;
		display: flex;
		margin-bottom: 22px;
		flex-direction: row;
		width: 100%;
	}

	.uni-item {
		width: 100%;
	}

	.coltitle {
		width: 90px;
		font-size: 23rpx;
		color: #666666;
	}

	.uni-item-value {
		line-height: 72rpx;
		height: 72rpx;
		width: 100%;
		color: #666666;
	}

	.detail_text {
		font-size: 23rpx;
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		word-wrap: break-word;
		white-space: normal !important;
		-webkit-line-clamp: 1;
		line-clamp: 1;
		-webkit-box-orient: vertical;
		color: #666666;
		text-align: center;
		width: 100%;
		height: 100%;
	}

	.picker {
		width: 100%;
	}

	.color-red {
		padding: 10rpx;
	}

	.piclist {
		display: flex;
		flex-wrap: wrap;
		margin-bottom: 30rpx;

		.upbox {
			width: 20vw;
			height: 20vw;
			margin: 10rpx;
			border: $color-hui-e 2rpx solid;
			color: $color-hui-8;
			font-size: $uni-font-size-base;
			border-radius: 10rpx;

			.iconfont {
				font-size: $uni-font-size-title;
			}

			.img {
				width: 20vw;
				height: 20vw;
			}
		}
	}

	.footer {
		height: 100rpx;
		width: 100%;

		.btn-add {
			position: fixed;
			height: 90rpx;
			line-height: 90rpx;
			left: 3%;
			color: #ffffff;
			bottom: 0;
			background: $default-theme-color;
			text-align: center;
			z-index: 999999;
		}
	}

	.price-input-box {
		border: #dcdcdc 2rpx solid;
		height: 80rpx;
		line-height: 80rpx;
		display: flex;
		border-radius: 5rpx;
		width: calc(100% - 200rpx);

		.fuhao {
			font-weight: 600;
			background: #eeeeee;
			border-right: #dcdcdc 2rpx solid;
			width: 60rpx;
			text-align: center;
		}

		.ipt {
			width: calc(100% - 60rpx);
			height: 50rpx;
			line-height: 50rpx;
			box-sizing: border-box;
			padding: 0 15rpx;
		}

		.detail_text {
			font-size: 23rpx;
			display: -webkit-box;
			overflow: hidden;
			text-overflow: ellipsis;
			word-wrap: break-word;
			white-space: normal !important;
			-webkit-line-clamp: 1;
			line-clamp: 1;
			-webkit-box-orient: vertical;
			padding: 25rpx;

		}
	}

	.col-checkbox-line {
		line-height: 70rpx;
	}

	.col-parameter {
		width: calc(75% - 200rpx);
	}

	.col-parameter-name {
		width: calc(100% - 200rpx);
	}

	.col-parameter-del {
		width: calc(43% - 200rpx);
	}

	.showbox-tit-btn {
		margin-left: 14rpx;
		padding: 10rpx 8rpx;
		border: #d0d0d0 2rpx solid;
		border-radius: 10rpx;
		font-size: 25rpx;
		color: #666666;
	}



	.parameter-key {
		width: calc(63% - 200rpx);
		padding-left: 10rpx;
		border: solid 2rpx #d0d0d0;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.parameter-value {
		width: calc(100% - 200rpx);
		padding-left: 10rpx;
		border: solid 2rpx #d0d0d0;
		height: 60rpx;
		margin-right: 20rpx;
		border-radius: 10rpx;
	}

	.parameter-del {
		width: calc(35% - 200rpx);
	}


	/*弹出样式*/
	.showbg {
		width: 100%;
		height: 100vh;
		background: rgba(0, 0, 0, 0.51);
		position: fixed;
		top: 0;
		left: 0;
		z-index: 15;
	}

	.showbox {
		width: 100%;
		height: calc(100vh - 330rpx);
		background: #ffffff;
		border-radius: 20rpx 20rpx 0 0;
		position: fixed;
		bottom: 0;
		left: 0;
		z-index: 16;
	}


	.showbox .guanbi {
		position: absolute;
		right: 20rpx;
		top: 20rpx;
		background: rgba(153, 153, 153, 0.2);
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		border-radius: 40rpx;
	}

	.showbox .biaoti1 {
		padding: 20rpx;
		font-size: 24rpx;
		font-weight: bold;
		text-align: center;
	}

	.showbox .dl {
		padding: 20rpx;
		overflow: hidden;
	}

	.showbox .dt {
		font-weight: bold;
		padding-bottom: 20rpx;
	}

	.showbox .dd .t1 {
		color: #f44c36;
		background: #fee9e6;
		padding: 4rpx 10rpx;
		margin-right: 20rpx;
	}

	.showbox .dd .youhuiquan {
		overflow: hidden;
	}
</style>