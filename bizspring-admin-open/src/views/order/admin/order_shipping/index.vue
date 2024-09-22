<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="118px">
      <el-form-item label="编号" prop="sn">
        <el-input maxlength="32" v-model="queryParams.sn"
          placeholder="请输入编号" clearable
          @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="运单号" prop="trackingNo">
        <el-input maxlength="32" v-model="queryParams.trackingNo" placeholder="请输入运单号" clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="收货人" prop="consignee">
        <el-input maxlength="32" v-model="queryParams.consignee" placeholder="请输入收货人" clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input maxlength="15" v-model="queryParams.phone" placeholder="请输入电话" clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="地区" prop="region">
        <el-input maxlength="100" v-model="queryParams.region"
          placeholder="请输入地区" clearable
          @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input maxlength="100" v-model="queryParams.address" placeholder="请输入地址" clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="邮编" prop="zipCode">
        <el-input maxlength="8" v-model="queryParams.zipCode" placeholder="请输入邮编" clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="ElIconSearch"   @click="handleQuery">
          搜索
        </el-button>
        <el-button :icon="ElIconRefresh"   @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain :icon="ElIconDelete"   :disabled="multiple" @click="handleDelete">删除
        </el-button>
      </el-col>
      <el-col  :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="order_shippingList" @selection-change="handleSelectionChange" :header-cell-style="{ 
      background:'#f8f8f9',height:'50px',color:'#606266'}" :rowStyle="{ height:'50px'}">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="sn" />
      <el-table-column label="配送方式" align="center" prop="shippingMethod" />
      <el-table-column label="运单号" align="center" prop="trackingNo"  show-overflow-tooltip/>
      <el-table-column label="地区" align="center" prop="region"  show-overflow-tooltip/>
      <el-table-column label="邮编" align="center" prop="zipCode" />
      <el-table-column label="收货人" align="center" prop="consignee" />
      <el-table-column label="电话" align="center" prop="phone" />
      <el-table-column label="物流公司" align="center" prop="expressCompany"  show-overflow-tooltip/>
      <el-table-column label="订单" align="center" prop="orderId"  show-overflow-tooltip/>
      <el-table-column label="创建日期" align="center" prop="createdTime" width="180">
        <template v-slot="scope">
          <span>{{
            parseTime(scope.row.createdTime, '{y}-{m}-{d} {h}:{i}:{s}')
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
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
  Delete as ElIconDelete,
} from '@element-plus/icons-vue'
import {
  listOrder_shipping,
  delOrder_shipping,
} from '@/api/order/admin/order_shipping'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
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
      // 订单发货表格数据
      order_shippingList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        sn: null,
        trackingNo: null,
        consignee: null,
        phone: null,
        region: null,
        address: null,
        zipCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      ElIconSearch: shallowRef(ElIconSearch),
      ElIconRefresh: shallowRef(ElIconRefresh),
      ElIconDelete: shallowRef(ElIconDelete),
    }
  },
  name: 'Order_shipping',
  components: {},
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['order:order_shipping:add'] ? true : false,
        delBtn: this.permissions['order:order_shipping:del'] ? true : false,
        editBtn: this.permissions['order:order_shipping:edit'] ? true : false,
        viewBtn: this.permissions['order:order_shipping:get'] ? true : false,
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {}
    },
    /** 查询订单发货列表 */
    getList() {
      this.loading = true
      listOrder_shipping(this.queryParams)
        .then((response) => {
          this.loading = false
          console.log('listOrder_shipping', response)
          this.order_shippingList = response.data.data.content
          this.total = response.data.data.total
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        sn: null,
        id: null,
        createdTime: null,
        updatedTime: null,
        consignee: null,
        expressCompany: null,
        phone: null,
        region: null,
        address: null,
        zipCode: null,
        orderId: null,
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNumber = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      if (this.$refs['queryForm']) {
        this.$refs['queryForm'].resetFields()
      }
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除订单发货数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delOrder_shipping(ids)
        })
        .then(() => {
          this.getList()
          this.msgSuccess(
            '删除成功'
          )
        })
        .catch(() => { })
    },
  },
}
</script>
<style scoped>
:deep(.el-input__inner){
  width: 200px;
}
</style>
