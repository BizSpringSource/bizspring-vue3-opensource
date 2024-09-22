<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="名称" prop="name">
        <el-input maxlength="30" v-model="queryParams.name" placeholder="请输入名称"
          clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="网址" prop="url">
        <el-input maxlength="100" v-model="queryParams.url" placeholder="请输入网址"
          clearable   @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="ElIconSearch"   @click="handleQuery">
          搜索
        </el-button>
        <el-button :icon="ElIconRefresh"   @click="resetQuery">重 置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain :icon="ElIconPlus"   @click="handleAdd">添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain :icon="ElIconDelete"   :disabled="multiple" @click="handleDelete">删除
        </el-button>
      </el-col>
      <el-col  :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="brandList" @selection-change="handleSelectionChange" :header-cell-style="{ 
      background:'#f8f8f9',height:'50px',color:'#606266'}" :rowStyle="{ height:'50px'}">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="logo" align="center" prop="logo">
        <template #default="scope">
          <img class="user-avatar" :src="scope.row.logo" />
        </template>
      </el-table-column>
      <el-table-column label="网址" align="left" prop="url"  show-overflow-tooltip/>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">编辑</el-link>
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改品牌对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="108px">
        <el-form-item label="名称" prop="name">
          <el-input maxlength="50" v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="logo" prop="logo">
          <el-input maxlength="100" v-model="form.logo" placeholder="请输入logo" />
        </el-form-item>
        <el-form-item label="网址" prop="url">
          <el-input v-model="form.url" maxlength="100" placeholder="请输入网址" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="form.sort" maxlength="3"
            placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="介绍" prop="introduction">
          <el-input v-model="form.introduction" maxlength="100" type="textarea" placeholder="请输入" />
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
  listBrand,
  delBrand,
  addBrand,
  saveBrand,
  editBrand,
  updateBrand,
} from '@/api/goods/admin/brand'
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
      // 品牌表格数据
      brandList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        name: null,
        url: null,
        sortProperty:'sort',  //排序字段
        sortDirection:'ASC' 	// * 递增  ASC, 递减 DESC
      },
      // 表单参数
      form: {
      },
      // 表单校验
      rules: {},
      types: [],
      ElIconSearch: shallowRef(ElIconSearch),
      ElIconRefresh: shallowRef(ElIconRefresh),
      ElIconPlus: shallowRef(ElIconPlus),
      ElIconDelete: shallowRef(ElIconDelete),
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Brand',
  components: {},
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['goods:brand:add'] ? true : false,
        delBtn: this.permissions['goods:brand:del'] ? true : false,
        viewBtn: this.permissions['goods:brand:get'] ? true : false,
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
        sort: [
          {
            type: 'number',
            message:'排序只允许输入数字',
            trigger: ['blur', 'change'],
          },
        ],
      }
    },
    /** 查询品牌列表 */
    getList() {
      this.loading = true
      listBrand(this.queryParams).then((response) => {
        this.loading = false
        console.log('listBrand', response)
        this.brandList = response.data.data.content
        this.total = response.data.data.total

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
        name: null,
        id: null,
        type: null,
        createdTime: null,
        logo: null,
        updatedTime: null,
        url: null,
        version: null,
        sort: null,
        introduction: null,
      }
      // this.resetForm("form");
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
    /** 新增按钮操作 */
    handleAdd() {
      this.loading = true
      this.reset()
      addBrand().then((response) => {
        this.loading = false
        console.log('addBrand', response)
        //add  返回数据
        this.types = response.data.data.types
        this.open = true
        this.title = '添加品牌'
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true
      this.reset()
      const id = row.id || this.ids
      editBrand(id).then((response) => {
        this.loading = false
        this.setRules()
        console.log('editBrand', response)
        this.form = response.data.data.data
        this.types = response.data.data.types
        this.open = true
        this.title = '编辑品牌'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.open = false
          this.loading = true
          if (this.form.id != null) {
            updateBrand(this.form).then((response) => {
              this.loading = false
              this.msgSuccess(
                '编辑成功'
              )
              this.getList()
            })
          } else {
            this.form.type='IMAGE';
            saveBrand(this.form).then((response) => {
              this.loading = false
              this.msgSuccess(
                '添加成功'
              )
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除品牌数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      ).then(function () {
          return delBrand(ids)
      }).then(() => {
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
.user-avatar {
  margin: 0 5px;
  padding: 2px;
  width: 40px;
  height: 40px;
  border-radius: 100%;
  box-sizing: border-box;
  border: 1px solid #eee;
  vertical-align: middle;
}
</style>