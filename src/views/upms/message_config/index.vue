<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col  :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="message_configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="message['MessageConfig_type']" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column :label="message['MessageConfig_isMailEnabled']" align="center" prop="isMailEnabled"
        :formatter="isMailEnabledFormat" />
      <el-table-column :label="message['MessageConfig_isSmsEnabled']" align="center" prop="isSmsEnabled"
        :formatter="isSmsEnabledFormat" />
      <el-table-column :label="message['common_action']" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">{{ message['common_edit']
          }}</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改消息配置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item :label="message['MessageConfig_type'] + ': '" prop="type">
          <p>{{ message['MessageConfig_Type_' + form.type] }}</p>
        </el-form-item>
        <el-form-item :label="message['common_setting'] + ': '">
          <el-switch v-model="form.isMailEnabled" width="46" :active-text="message['MessageConfig_isMailEnabled']">
          </el-switch>
        </el-form-item>
        <el-form-item label=" ">
          <el-switch v-model="form.isSmsEnabled" width="46" :active-text="message['MessageConfig_isSmsEnabled']">
          </el-switch>
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">{{
            message['common_ok']
          }}</el-button>
          <el-button @click="cancel">{{ message['common_cancel'] }}</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Edit as ElIconEdit } from '@element-plus/icons-vue'
import {
  listMessage_config,
  getMessage_config,
  delMessage_config,
  addMessage_config,
  saveMessage_config,
  editMessage_config,
  updateMessage_config,
} from '@/api/upms/message_config'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      message: JSON.parse(sessionStorage.getItem('messages')),
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
      // 消息配置表格数据
      message_configList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: '',
        id: null,
        createdDate: null,
        isMailEnabled: true,
        lastModifiedDate: null,
        isSmsEnabled: true,
        version: null,
      },
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Message_config',
  components: {},
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        //addBtn: this.permissions['upms:message_config:add'] ? true : false,
        //delBtn: this.permissions['upms:message_config:del'] ? true : false,
        editBtn: this.permissions['upms:message_config:edit'] ? true : false,
        //viewBtn: this.permissions['upms:message_config:get'] ? true : false
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {
        type: [
          {
            required: true,
            message:
              this.message['MessageConfig_type'] +
              this.message['common_notNull'],
            trigger: 'change',
          },
        ],
        isMailEnabled: [
          {
            required: true,
            message:
              this.message['MessageConfig_isMailEnabled'] +
              this.message['common_notNull'],
            trigger: 'blur',
          },
        ],
        isSmsEnabled: [
          {
            required: true,
            message:
              this.message['MessageConfig_isSmsEnabled'] +
              this.message['common_notNull'],
            trigger: 'blur',
          },
        ],
      }
    },
    /** 查询消息配置列表 */
    getList() {
      this.loading = true
      listMessage_config(this.queryParams)
        .then((response) => {
          console.log('listMessage_config', response)
          this.message_configList = response.data.data
          // this.total = response.data.data.total;
          this.loading = false
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
        type: null,
        id: null,
        createdDate: null,
        isMailEnabled: true,
        lastModifiedDate: null,
        isSmsEnabled: true,
        version: null,
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
    //是否启用邮件
    isMailEnabledFormat(row, column) {
      if (row.isMailEnabled) {
        return '√'
      } else {
        return '×'
      }
    },
    //是否启用短信
    isSmsEnabledFormat(row, column) {
      if (row.isSmsEnabled) {
        return '√'
      } else {
        return '×'
      }
    },
    // 类型 type 字典翻译
    typeFormat(row, column) {
      return this.message['MessageConfig_Type_' + row.type]
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = this.message['common_add'] + this.message['MessageConfig']
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.setRules()
      //console.log("editMessage_config",row);
      this.form = row
      this.open = true
      this.title = this.message['common_edit'] + this.message['MessageConfig']
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.form.id != null) {
            updateMessage_config(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  this.message['common_edit'] + this.message['common_success']
                )
                this.open = false
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            saveMessage_config(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  this.message['common_add'] + this.message['common_success']
                )
                this.open = false
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        this.message['common_confirmDeletion'] +
        this.message['MessageConfig'] +
        this.message['common_data'] +
        '?',
        this.message['common_warning'],
        {
          confirmButtonText: this.message['common_ok'],
          cancelButtonText: this.message['common_cancel'],
          type: 'warning',
        }
      )
        .then(function () {
          return delMessage_config(ids)
        })
        .then(() => {
          this.getList()
          this.msgSuccess(
            this.message['common_delete'] + this.message['common_success']
          )
        })
        .catch(() => { })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        'upms/message_config/export',
        {
          ...this.queryParams,
        },
        `upms_message_config.xlsx`
      )
    },
  },
}
</script>
