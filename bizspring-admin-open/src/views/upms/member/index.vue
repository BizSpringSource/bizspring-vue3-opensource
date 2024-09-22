<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-form-item label="用户名" prop="username">
        <el-input maxlength="50" v-model="queryParams.username"
          placeholder="请输入用户名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="ElIconSearch" @click="handleQuery">
          搜索
        </el-button>
        <el-button :icon="ElIconRefresh" @click="resetQuery">重置</el-button>
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

    <el-table v-loading="loading" :data="memberList" @selection-change="handleSelectionChange" :header-cell-style="{
      background: '#f8f8f9', height: '50px', color: '#606266'
    }" :rowStyle="{ height: '50px' }">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户名" align="center" prop="username" />
      <el-table-column label="邮箱" align="center" prop="email" />
      <el-table-column label="手机" align="center" prop="mobile" />
      <el-table-column label="创建时间" align="center" prop="createdTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">编辑</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改商家管理对话框 -->
    <el-dialog :title="title" v-model="open" width="550px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="用户名" prop="username">
          <el-input :disabled="isdisabled" v-model="form.username" maxlength="30"
            placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" show-password maxlength="20"
            placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="passwordConfirm">
          <el-input v-model="form.passwordConfirm" show-password maxlength="20" placeholder="请输入确认密码" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" maxlength="30" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机" prop="mobile">
          <el-input v-model="form.mobile" maxlength="15"
            placeholder="请输入手机" />
        </el-form-item>
        <el-form-item label="设置">
          <el-switch v-model="form.isEnabled" width="46" active-text="是否启用">
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
  listMember,
  delMember,
  addMember,
  saveMember,
  editMember,
  updateMember,
} from '@/api/upms/member'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'
import ImageUpload from '@/components/ImageUpload/index.vue';
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
      // 商家管理表格数据
      memberList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        username: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      isdisabled: false,
      imgsrc: '',
      idxs: [],
      dataTimeValue: [],
      imageValue: [],
      uploadUrl: '/upms/file/upload?fileType=image&path=admin-upms-member',
      ElIconSearch: shallowRef(ElIconSearch),
      ElIconRefresh: shallowRef(ElIconRefresh),
      ElIconPlus: shallowRef(ElIconPlus),
      ElIconDelete: shallowRef(ElIconDelete),
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Member',
  components: { ImageUpload },
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['upms:member:add'] ? true : false,
        delBtn: this.permissions['upms:member:del'] ? true : false,
        viewBtn: this.permissions['upms:member:get'] ? true : false,
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {
        username: [
          {
            required: true,
            message:'用户名不能为空',
            trigger: 'blur',
          },
        ],
        password: [
          {
            required: true,
            message:'密码不能为空',
            trigger: 'blur',
          },
        ],
        passwordConfirm: [
          {
            required: true,
            message:'确认密码不能为空',
            trigger: 'blur',
          },
        ],
        email: [
          {
            required: true,
            message:'邮箱不能为空',
            trigger: 'blur',
          },
          {
            type: 'email',
            message: 'E-mail格式错误',
            trigger: ['blur', 'change'],
          },
        ],
        mobile: [
          {
            required: true,
            message:'手机不能为空',
            trigger: 'blur',
          },
          {
            pattern: /[^a-zA-Z]$/,
            message: '手机格式错误',
            trigger: ['blur', 'change'],
          },
        ],
      }
    },
    /** 查询商家管理列表 */
    getList() {
      this.loading = true
      listMember(this.queryParams)
        .then((response) => {
          this.loading = false
          console.log('listMember', response)
          this.memberList = response.data.data.content
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
        id: null,
        username: null,
        email: null,
        password: null,
        passwordConfirm: null,
        mobile: null,
        isEnabled: true,
        tenantId: null,
        createdTime: null,
        updatedTime: null,
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
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.password != this.form.passwordConfirm) {
            this.msgError(
              '密码、确认密码不相同'
            )
            return
          }
          if (this.form.id != null) {
            let data = {
              id: this.form.id,
              password: this.form.password,
              email: this.form.email,
              mobile: this.form.mobile,
              isEnabled: this.form.isEnabled,
            }
            updateMember(data).then((response) => {
              this.msgSuccess(
                '编辑成功'
              )
              this.open = false
              this.getList()
            })
          } else {
            saveMember(null, this.form).then((response) => {
              this.msgSuccess(
                '添加成功'
              )
              this.open = false
              this.getList()
            })
          }
        }
      })
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.loading = true
      this.reset()
      this.isdisabled = false
      this.title = '添加会员'
      addMember()
        .then((response) => {
          this.loading = false
          this.open = true
        })
        .finally(() => {
          this.loading = false
        })
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true
      this.reset()
      this.isdisabled = true
      editMember(row.id)
        .then((response) => {
          console.log('editMember  ===   >>>', response)
          this.loading = false
          this.setRules()
          this.form = response.data.data.data ? response.data.data.data : null
          this.form.password = null
          this.open = true
          this.title = '编辑会员'
        })
        .finally(() => {
          this.loading = false
        })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除用户数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delMember(ids)
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