<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="名称" prop="name">
        <el-input maxlength="50" v-model="queryParams.name" placeholder="请输入名称"
          clearable   @keyup.enter="handleQuery" />
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
        <el-button type="primary" plain :icon="ElIconPlus"   @click="handleAdd">添加
        </el-button>
      </el-col>
           <el-col  :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table row-key="id" lazy border :load="loadChildrens"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" v-loading="loading" :data="regionList" :header-cell-style="{
      background:'#f8f8f9',height:'50px',color:'#606266'}" :rowStyle="{ height:'50px'}">
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconPlus" @click="handleAdd(scope.row)">添加</el-link>
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">编辑</el-link>
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改地区对话框 -->
    <el-dialog :title="title" v-model="open" width="550px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" maxlength="50" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="上级地区" prop="parentId">
          <el-input :disabled="true" maxlength="50" placeholder="form.parentName" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="form.sort" maxlength="3"
            placeholder="请输入排序" />
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
  Edit as ElIconEdit,
  Delete as ElIconDelete,
} from '@element-plus/icons-vue'
import {
  listRegion,
  delRegion,
  addRegion,
  saveRegion,
  editRegion,
  updateRegion,
  loadChildsRegion,
} from '@/api/upms/region'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 地区表格数据
      regionList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        name: null,
        sortProperty:'sort',  //排序字段
        sortDirection:'ASC', 	// * 递增  ASC, 递减 DESC
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      addRegion: {},
      ElIconSearch:shallowRef(ElIconSearch),
      ElIconRefresh:shallowRef(ElIconRefresh),
      ElIconPlus:shallowRef(ElIconPlus),
      ElIconDelete:shallowRef(ElIconDelete),
      ElIconEdit:shallowRef(ElIconEdit),
    }
  },
  name: 'Region',
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['upms:region:add'] ? true : false,
        delBtn: this.permissions['upms:region:del'] ? true : false,
        editBtn: this.permissions['upms:region:edit'] ? true : false,
        viewBtn: this.permissions['upms:region:get'] ? true : false,
      }
    },
  },
  methods: {
    setRules() {},
    /** 查询地区列表 */
    getList() {
      this.regionList = []
      this.loading = true
      listRegion(this.queryParams).then((response) => {
        this.loading = false
        this.regionList = response.data.data.content
        // this.total = response.data.data.total;

      })
    },
    loadChildrens(tree, treeNode, resolve) {
      console.log('tree', tree)
      console.log('treeNode', treeNode)
      console.log('resolve', resolve)
      loadChildsRegion(tree.id).then((response) => {
        resolve(response.data.data)
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
        createdTime: null,
        updatedTime: null,
        version: null,
        sort: null,
        fullName: null,
        level: null,
        name: null,
        treePath: null,
        parentId: null,
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
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      if (null != row) {
        this.addRegion = row
        this.form.parentId = row.id
        this.form.parentName = row.name
        this.open = true
        this.title = '添加地区'
      }

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true
      this.reset()
      this.setRules()
      const id = row.id || this.ids
      editRegion(id).then((response) => {
        this.loading = false
        this.setRules()
        console.log('editRegion', row)
        this.form = row
        this.open = true
        this.title = '编辑地区'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        console.log('regionSubmitForm', this.form)
        if (valid) {
          this.open = false
          this.loading = true
          if (this.form.id != null) {
            updateRegion(this.form).then((response) => {
              this.loading = false
              this.msgSuccess(
                '编辑成功'
              )
              this.getList()
            })
          } else {
            saveRegion(this.form).then((response) => {
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
      const id = row.id
      this.$confirm(
        '是否确认删除地区数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delRegion(id)
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
