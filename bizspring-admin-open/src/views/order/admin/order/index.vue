<template>
	<div class="app-container">
		<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
			<el-form-item label="编号" prop="sn">
				<el-input maxlength="32" v-model="queryParams.sn"
					placeholder="请输入编号" clearable
					@keyup.enter="handleQuery" />
			</el-form-item>
			<el-form-item label="收货人" prop="consignee">
				<el-input maxlength="50" v-model="queryParams.consignee"
					placeholder="请输入收货人" clearable
					@keyup.enter="handleQuery" />
			</el-form-item>
			<el-form-item label="地址" prop="address">
				<el-input maxlength="100" v-model="queryParams.address"
					placeholder="请输入地址" clearable
					@keyup.enter="handleQuery" />
			</el-form-item>
			<el-form-item label="邮编" prop="zipCode">
				<el-input maxlength="8" v-model="queryParams.zipCode"
					placeholder="请输入邮编" clearable
					@keyup.enter="handleQuery" />
			</el-form-item>
			<el-form-item label="电话" prop="phone">
				<el-input maxlength="15" v-model="queryParams.phone"
					placeholder="请输入电话" clearable
					@keyup.enter="handleQuery" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" :icon="ElIconSearch" @click="handleQuery">
					搜索
				</el-button>
				<el-button :icon="ElIconRefresh" @click="resetQuery">重置</el-button>
			</el-form-item>
		</el-form>

		<el-row :gutter="10" class="mb8">
			<el-col :span="6">
				<right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
			</el-col>
		</el-row>

		<el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange" :header-cell-style="{
      background: '#f8f8f9', height: '50px', color: '#606266'
    }" :rowStyle="{ height: '50px' }">
			<el-table-column type="selection" width="55" align="center" />
			<el-table-column label="编号" align="center" prop="sn" />
			<el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
			<el-table-column label="会员" align="center" prop="memberName" />
			<el-table-column label="店铺" align="center" prop="storeName" show-overflow-tooltip />
			<el-table-column label="订单金额" align="center" prop="amount" />
			<el-table-column label="支付方式名称" align="center" prop="paymentPatternName" />
			<el-table-column label="配送方式名称" align="center" prop="shippingMethodName" />
			<el-table-column label="收货人" align="center" prop="consignee" />
			<el-table-column label="电话" align="center" prop="phone" />
			<el-table-column label="创建时间" align="center" prop="createdTime" width="180">
				<template v-slot="scope">
					<span>{{
            parseTime(scope.row.createdTime, '{y}-{m}-{d} {h}:{i}:{s}')
          }}</span>
				</template>
			</el-table-column>
		</el-table>

		<pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
			v-model:limit="queryParams.pageSize" @pagination="getList" />
	</div>
</template>

<script>
	import {
		Search as ElIconSearch,
		Refresh as ElIconRefresh,
		Edit as ElIconEdit,
		Delete as ElIconDelete
	} from '@element-plus/icons-vue'
	import {
		listOrder,
	} from '@/api/order/admin/order'
	import {
		mapGetters
	} from 'vuex'
	import {
		shallowRef
	} from 'vue'

	export default {
		data() {
			return {
                msg:{
                    'Order_Status_PENDING_PAYMENT':'等待付款',
                    'Order_Status_PENDING_REVIEW':'等待处理',
                    'Order_Status_PENDING_SHIPMENT':'等待发货',
                    'Order_Status_SHIPPED':'已发货',
                    'Order_Status_RECEIVED':'已收货',
                    'Order_Status_COMPLETED':'已完成',
                    'Order_Status_FAILED':'已失败',
                    'Order_Status_CANCELED':'已取消',
                    'Order_Status_DENIED':'无法发货'
                },
				// 遮罩层
				loading: true,
				// 选中数组
				ids: [],
				// 非单个禁用
				single: true,
				// 非多个禁用
				multiple: true,
				// 显示搜索条件
				showSearch: true,
				// 总条数
				total: 0,
				// 订单表格数据
				orderList: [],
				// 是否显示弹出层
				open: false,
				// 查询参数
				queryParams: {
					pageNumber: 1,
					pageSize: 10,
					sn: null,
					memberName: null,
					consignee: null,
					address: null,
					zipCode: null,
					phone: null,
				},
				// 表单参数
				form: {},
				// 表单校验
				rules: {},
				order: [],
				methods: [],
				paymentPatterns: [],
				shippingMethods: [],
				ElIconSearch: shallowRef(ElIconSearch),
				ElIconRefresh: shallowRef(ElIconRefresh),
				ElIconEdit: shallowRef(ElIconEdit),
				ElIconDelete: shallowRef(ElIconDelete),
			}
		},
		name: 'Order',
		components: {},
		created() {
			this.getList()
		},
		computed: {
			...mapGetters(['permissions']),
			permissionList() {
				return {
					addBtn: this.permissions['order:order:add'] ? true : false,
					delBtn: this.permissions['order:order:del'] ? true : false,
					editBtn: this.permissions['order:order:edit'] ? true : false,
					viewBtn: this.permissions['order:order:get'] ? true : false,
				}
			},
		},
		methods: {
			/** 查询订单列表 */
			getList() {
				this.loading = true
				listOrder(this.queryParams)
					.then((response) => {
						this.loading = false
						console.log('listOrder', response)
						this.orderList = response.data.data.data.content
						this.total = response.data.data.data.total
						this.loading = false
					})
					.finally(() => {
						this.loading = false
					})
			},
			// 表单重置
			reset() {
				this.form = {
					id: null,
					sn: null,
					createdTime: null,
					status: 0,
					amount: null,
					consignee: null,
					address: null,
					zipCode: null,
					phone: null,
				}
			},
			/** 搜索按钮操作 */
			handleQuery() {
				this.queryParams.pageNumber = 1
				this.getList()
			},
			/** 重置按钮操作 */
			resetQuery() {
				if (this.$refs.queryForm) {
					this.$refs.queryForm.resetFields()
				}
				this.handleQuery()
			},
			// 多选框选中数据
			handleSelectionChange(selection) {
				this.ids = selection.map((item) => item.id)
				this.single = selection.length !== 1
				this.multiple = !selection.length
			},
			// 方式 method 字典翻译
			statusFormat(row, column) {
				return this.msg['Order_Status_' + row.status]
			},
		},
	}
</script>
<style scoped>
	:deep(.el-select .el-input) {
		width: 195px;
		display: flex;
	}

	.el-input {
		width: 194px;
	}
</style>