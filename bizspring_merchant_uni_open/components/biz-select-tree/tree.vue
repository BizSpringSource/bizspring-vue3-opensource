<template>
	<view>
		<view class="header">
			<search v-if="searchIf" ref="sea" @confirm="confirmSearch" />
			<view class="title">
				<scroll-view scroll-x style="width: 100%;white-space: nowrap;" :scroll-left="scrollLeft">
					<view v-for="(item,index) in tree_stack" class="inline-item" :key="index">
						<view class="inline-item" v-if="index==0" @click="backTree(item,-1)">
							<text :class="[(isActive(index)&&!isre)?'none':'active']">全部</text>
						</view>
						<view v-if="index==0&&isre" @click="backTree(item,-2)" :class="[(index==tree_stack.length-1&&isre)?'none':'active','inline-item']">
							<i class="iconfont icon-z043 iconclass" />
							搜索结果
						</view>
						<view class="inline-item" @click="backTree(item,index)" v-if="index!=0">
							<i class="iconfont icon-z043 iconclass" />
							<text :class="isActive(index)?'none inline-ite':'active'">
								{{item[props.label]}}
							</text>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>
		<view>
			<view class="container-list">
				<view class="common" v-for="(item, index) in tree" @click="handleClick(item,index)" :key="index">
					<label class="content">
						<view class="list-item" v-show="isCheck">
							<!-- 多选 -->
							<view class="checkbox" v-if="props.multiple" @click.stop="handleClick(item,-1)">
								<span v-if="isSelect(item)">
									<i v-if="item.bx&&newCheckList.length!=0" class="iconfont icon-banxuanzhongshousuo1-shi icons" />
									<i v-else class="iconfont icon-xuanzhong txt icon-selected" />
								</span>
								<i v-else-if="item.qx" class="iconfont icon-xuanzhong txt icon-selected" />
								<i v-else-if="item.bx" class="iconfont icon-banxuanzhongshousuo1-shi icons" />
								<i style="color: #b8b8b8;" v-else class="iconfont icon-weixuanzhong txt" />
							</view>
							<!-- 单选 -->
							<view class="checkbox" v-else-if="(props.nodes?item.children.length>0?false:true:true)" @click.stop="handleClick(item,-1)">
								<i v-if="radioSelect(item)" class="txt iconfont icon-selected" />
								<i style="color: #b8b8b8;" v-else class="txt iconfont icon-weixuanzhong1" />
							</view>
						</view>
						<view class="lable-text">{{item[props.label]}}</view>
						<view class="right"><i v-if="item.children.length>0" class="iconfont icon-z043 iconclass"></i></view>
					</label>
				</view>
			</view>
		</view>
		<view class="btn box_sizing">
			<button class="sureBtn" type="primary" @click="backConfirm">确认</button>
		</view>
	</view>
</template>

<script src="./code.js" type="text/javascript"></script>
<style lang="scss" scoped>
	@import './css/style.scss';
	@import url("./css/icon.css");
</style>
