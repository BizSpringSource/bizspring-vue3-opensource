<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="名称" prop="name">
        <el-input maxlength="50" v-model="queryParams.name" placeholder="请输入名称"
          clearable @keyup.enter="handleQuery" style="width: 194px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="ElIconSearch" @click="handleQuery">
          搜索
        </el-button>
        <el-button :icon="ElIconRefresh" @click="resetQuery">
          重置
        </el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain :icon="ElIconPlus" @click="handleAdd">添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :icon="ElIconDelete" :disabled="multiple" @click="handleDelete">删除
        </el-button>
      </el-col>
      <el-col :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="storeList" @selection-change="handleSelectionChange" :header-cell-style="{
      background: '#f8f8f9', height: '50px', color: '#606266'
    }" :rowStyle="{ height: '50px' }">
      <el-table-column type="selection" align="center" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="手机" align="center" prop="mobile" />
      <el-table-column label="到期日期" align="center" prop="endDate" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="isEnabled">
        <template #default="scope">
          <el-switch v-model="scope.row.isEnabled" disabled/>
        </template>
      </el-table-column>
      <el-table-column label="商家" align="center" prop="merchantName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width"  fixed="right">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">编辑</el-link>
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改店铺对话框 -->
    <el-dialog :title="title" v-model="open" width="550px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="商家" prop="merchantId" >
          <el-autocomplete v-model="merchantIdSearch" :fetch-suggestions="querySearch" placeholder="请输入内容"
            @select="handleSelect" @change="blueInput" :trigger-on-focus="false"></el-autocomplete>
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" maxlength="50" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="logo">
          <imageUpload v-model="form.logo" maxlength="100" :uploadImgUrl="uploadUrl"
            v-on:handleImagesUrl="setImagesUrl" />
        </el-form-item>
        <el-form-item label="E-mail" prop="email">
          <el-input v-model="form.email" maxlength="30" placeholder="请输入E-mail" />
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="form.mobile" maxlength="15"
            placeholder="请输入手机" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" maxlength="15" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" maxlength="100"
            placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="邮编" prop="zipCode">
          <el-input v-model="form.zipCode" maxlength="8"
            placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="简介" prop="introduction">
          <el-input v-model="form.introduction" maxlength="200" type="textarea" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="关键词" prop="keyword">
          <el-input v-model="form.keyword" maxlength="50"
            placeholder="请输入关键词" />
        </el-form-item>
        <el-form-item label="设置">
          <el-switch v-model="form.isEnabled" width="46" :active-text="是否启用">
          </el-switch>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  Search as ElIconSearch,
  Refresh as ElIconRefresh,
  Plus as ElIconPlus,
  Delete as ElIconDelete,
  Edit as ElIconEdit,
} from '@element-plus/icons-vue'
import {
  listStore,
  delStore,
  addStore,
  saveStore,
  editStore,
  updateStore,
  merchantSelect,
} from '@/api/store/admin/store'
import ImageUpload from '@/components/ImageUpload/index.vue'
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
      // 店铺表格数据
      storeList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 9,
        name: null,
        isEnabled: null,
        hasExpired: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      merchantIdSearch: '',
      isEnableds: ['true', 'false'],
      uploadUrl: '/upms/file/upload?fileType=image&path=admin-store-store',
      ElIconSearch: shallowRef(ElIconSearch),
      ElIconRefresh: shallowRef(ElIconRefresh),
      ElIconPlus: shallowRef(ElIconPlus),
      ElIconDelete: shallowRef(ElIconDelete),
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Store',
  components: {
    ImageUpload,
  },

  created() {
    this.getList()
    this.setRules()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['store:store:add'] ? true : false,
        delBtn: this.permissions['store:store:del'] ? true : false,
        viewBtn: this.permissions['store:store:get'] ? true : false,
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {
        name: [
          {
            required: true,
            message:'名称不能为空',
            trigger: 'blur',
          },
        ],
        email: [
          {
            required: true,
            message:'E-mail不能为空',
            trigger: 'blur',
          },
        ],
        mobile: [
          {
            required: true,
            message:'手机不能为空',
            trigger: 'blur',
          },
        ],
        endDate: [
          {
            required: true,
            message:'到期日期不能为空',
            trigger: 'blur',
          },
        ],
        isEnabled: [
          {
            required: true,
            message:'是否启用不能为空',
            trigger: 'blur',
          },
        ],
        merchantId: [
          {
            required: true,
            message:'商家不能为空',
            trigger: 'blur',
          },
        ],
      }
    },
    querySearch(queryString, callback) {
      var list = [{}]
      this.form.merchantId=null;
      if (queryString && queryString.length > 0) {
        let queryParams = {
          keyword: queryString,
          count: 999,
        }
        merchantSelect({
          keyword: queryString,
          count: 999,
        }).then((response) => {
          var list = response.data.data;
          // 如果list.length等于0，则代表没有匹配到结果。手动给list添加一条提示信息
          if (list.length == '0') {
            this.msgError("未检索到商家");
            list.push({
              id: '-1',
              value: '无匹配结果',
            })
            // 调用 callback 返回建议列表的数据
            callback(list)
          } else {
            this.msgSuccess('商家');
            list = list.map((item) => {
              return {
                value: `${item.username}`,
                id: `${item.id}`,
              }
            })
            // 调用 callback 返回建议列表的数据
            callback(list)
          }
        })
      }
    },
    handleSelect(item) {
      console.log('item:', item)
      this.form.merchantId = item.id?item.id:null;
      this.merchantIdSearch = item.value?item.value:null;
    },

    blueInput(e){
      console.log("blueInput e, merchantId merchantIdSearch",e,this.form.merchantId,this.merchantIdSearch);
        if(!e){
          this.form.merchantId=null;
        }
        if(!this.form.merchantId){
          this.merchantIdSearch=null;
        }
    },
    /** 查询店铺列表 */
    getList() {
      this.loading = true
      listStore(this.queryParams)
        .then((response) => {
          this.loading = false
          this.storeList = response.data.data.data.content
          this.total = response.data.data.data.total
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
        id: null,
        name: null,
        logo: null,
        email: null,
        mobile: null,
        phone: null,
        address: null,
        zipCode: null,
        introduction: null,
        keyword: null,
        endDate: null,
        isEnabled: true,
        createdTime: null,
        updatedTime: null,
        merchantId: null,
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNumber = 1
      if (this.queryParams.storeCategoryId == "")
        this.queryParams.storeCategoryId = null
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
    /** 新增按钮操作 */
    handleAdd() {
      this.loading = true;
      addStore().then((response) => {
        this.loading = false;
        let resData = response.data.data
        console.log('resData:', resData)
        this.reset()
        this.open = true
        this.title = '添加店铺'
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.loading = true;
      const id = row.id || this.ids
      editStore(id).then((response) => {
        this.loading = false;
        this.form = response.data.data.store
        var resData = response.data.data
        this.merchantIdSearch = resData.merchant
          ? resData.merchant.username
          : ''
        this.open = true
        this.title = '编辑店铺'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.open = false
          this.loading = true
          if (this.form.id != null) {
            updateStore(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  '编辑成功'
                )
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            console.log(this.form)
            saveStore(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  '添加成功'
                )
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          }
        }
      })
    },
    setImagesUrl(url) {
      this.form.logo = url
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除店铺数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delStore(ids)
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
:deep(.el-select .el-input) {
  width: 195px;
  display: flex;
}
.el-form--inline .el-form-item {
  display: inline-flex;
  vertical-align: middle;
  margin-right: 8px;
}
</style>


