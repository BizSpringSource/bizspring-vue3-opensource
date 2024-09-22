<template>
  <div class="user">
    <basic-container>
      <el-row :span="24">
        <el-col :xs="24" :sm="24" :md="24" class="user__main">
          <avue-crud :option="option" ref="crud" v-model="form" v-model:page="page" :permission="permissionList"
            :table-loading="listLoading" :before-open="beforeOpen" @on-load="getPage" @sort-change="sortChange"
            @search-change="searchChange" @refresh-change="refreshChange" @row-update="handleUpdate"
            @row-save="handleSave" @row-del="handleDel" :data="list">
            <template v-slot:isEnabled="scope">
              <el-tag   effect="light" :type="scope.row.isEnabled == '1' ? 'success' : 'danger'">{{
                scope.label
              }}</el-tag>
            </template>
            <template v-slot:menu="scope">
              <el-link type="primary" v-if="permissions['sys:user:password']" :icon="ElIconKey"   plain
                @click="editPassword(scope.row, scope.index)">修改密码</el-link>
              <el-link type="primary" v-if="permissions['sys:loginlog:index']" :icon="ElIconInfo"   plain
                @click="showLoginlog(scope.row, scope.index)">登录日志</el-link>
            </template>
          </avue-crud>
        </el-col>
      </el-row>
      <el-dialog :title="'修改用户 '+'【' + selectRow.username + '】'+' 密码'" v-model="dialogVisible" width="40%" :v-loading="listLoading">
        <avue-form ref="form"  v-model="form2" :option="option2"  @submit="subPassword">
        </avue-form>
      </el-dialog>
      <el-dialog :title="'登录日志【' + selectRow.username + '】'" v-model="dialogVisible2" width="60%" :v-loading="listLoading">
        <avue-crud v-model:page="page2" :data="tableData2" :table-loading="tableLoading2" :option="tableOption2"
          :permission="permissionList2" @on-load="getPage2" @search-change="searchChange2"
          @refresh-change="refreshChange2" @sort-change="sortChange2" @row-del="handleDel2">
        </avue-crud>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { Key as ElIconKey, Calendar as ElIconInfo } from '@element-plus/icons-vue'
import { shallowRef } from 'vue'
import {
  addObj,
  delObj,
  getPage,
  putObj,
  editPassword,
} from '@/api/upms/user'
import { fetchTree } from '@/api/upms/organ'
import { tableOption } from '@/const/crud/upms/user'
import { tableOption as tableOption2 } from '@/const/crud/upms/loginlog'
import { getPage as getPage2, delObj as delObj2 } from '@/api/upms/loginlog'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      form2: {},
      option2: {
        submitText:'确 定',
        emptyText:'取 消',
        column: [
          {
            label: '新密码',
            labelWidth:'150',
            span: 24,
            placeholder:'请输入新密码',
            prop: 'password',
            rules: [
              {
                required: true,
                min: 6,
                message:'不少于6位',
                trigger: 'change',
              },
            ],
          },
        ],
      },
      selectRow: {},
      dialogVisible: false,
      treeOption: {
        nodeKey: 'id',
        addBtn: false,
        menu: false,
        defaultExpandAll: true,
        props: {
          label: 'name',
          value: 'id',
        },
      },
      treeData: [],
      option: shallowRef(tableOption),
      checkedKeys: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条,
        isAsc: false, //是否倒序
      },
      list: [],
      listLoading: true,
      role: [],
      form: {},
      dialogVisible2: false,
      tableData2: [],
      page2: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],
        descs: 'created_time',
      },
      tableLoading2: false,
      tableOption2: tableOption2,
      ElIconKey: shallowRef(ElIconKey),
      ElIconInfo: shallowRef(ElIconInfo),
    }
  },
  name: 'sys_user',
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:user:add'] ? true : false,
        delBtn: this.permissions['sys:user:del'] ? true : false,
        editBtn: this.permissions['sys:user:edit'] ? true : false,
        viewBtn: this.permissions['sys:user:get'] ? true : false,
      }
    },
    permissionList2() {
      return {
        addBtn: this.permissions['sys:loginlog:add'] ? true : false,
        delBtn: this.permissions['sys:loginlog:del'] ? true : false,
        editBtn: this.permissions['sys:loginlog:edit'] ? true : false,
        viewBtn: this.permissions['sys:loginlog:get'] ? true : false,
      }
    },
  },
  watch: {},
  created() {
    this.init()
  },
  methods: {
    subPassword(form2, done) {
      form2.id = this.selectRow.id
      editPassword(form2)
        .then(() => {
          done()
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000,
          })
          this.dialogVisible = false
          this.form2.password = ''
        })
        .catch(() => {
          done()
        })
    },
    editPassword(row, index) {
      this.selectRow = row
      this.dialogVisible = true
    },
    init() {
      fetchTree().then((response) => {
        this.treeData = response.data.data
      })
    },
    nodeClick(data) {
      this.page.page = 1
      this.getPage(this.page, { organId: data.id })
    },
    sortChange(val) {
      let prop = val.prop
        ? val.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
        : ''
      if (val.order == 'ascending') {
        this.page.descs = []
        this.page.ascs = prop
      } else if (val.order == 'descending') {
        this.page.ascs = []
        this.page.descs = prop
      } else {
        this.page.ascs = []
        this.page.descs = []
      }
      this.getPage(this.page)
    },
    getPage(page, params) {
      this.listLoading = true
      getPage(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
            descs: this.page.descs,
            ascs: this.page.ascs,
          },
          params
        )
      ).then((response) => {
        this.list = response.data.data.records
        this.page.total = response.data.data.total
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
        this.listLoading = false
      })
    },
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
    },
    refreshChange(page) {
      this.getPage(this.page)
    },
    changeIsEnableValue(){
      if(this.form.isEnabled=='0'){
        this.form.isEnabled=false;
      }else{
        this.form.isEnabled=true;
      }
    },
    handleSave(row, done, loading) {
      this.changeIsEnableValue();
      addObj(this.form)
        .then(() => {
          this.getPage(this.page)
          this.$notify({
            title: '成功',
            message: '保存成功',
            type: 'success',
            duration: 2000,
          })
          done()
        })
        .catch(() => {
          loading()
        })
    },
    handleUpdate(row, index, done, loading) {
      this.changeIsEnableValue();
      putObj(this.form)
        .then(() => {
          this.getPage(this.page)
          done()
          this.$notify({
            title: '成功',
            message: '更新成功',
            type: 'success',
            duration: 2000,
          })
        })
        .catch(() => {
          loading()
        })
    },
    handleDel(row, index) {
      this.$confirm('此操作将永久删除该用户(用户名: '+ row.username +' ), 是否继续?',
         '提示',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        console.log('delObj(row.id)', row.id)
        delObj(row.id)
          .then(() => {
            this.list.splice(index, 1)
            this.$notify({
              title: '成功',
              message: '已删除',
              type: 'success',
              duration: 2000,
            })
          })
          .cache(() => {
            this.$notify({
              title: '失败',
              message: '删除失败',
              type: 'error',
              duration: 2000,
            })
          })
      })
    },
    beforeOpen(done, type) {
      window.openType = type
      done()
    },

    showLoginlog(row, index) {
      this.selectRow = row
      this.tableData2 = []
      this.page2.currentPage = 1
      this.dialogVisible2 = true
      this.getPage2(this.page2)
    },
    searchChange2(params, done) {
      params = this.filterForm(params)
      this.page2.currentPage = 1
      this.getPage2(this.page2, params)
      done()
    },
    sortChange2(val) {
      let prop = val.prop
        ? val.prop.replace(/([A-Z])/g, '_$1').toLowerCase()
        : ''
      if (val.order == 'ascending') {
        this.page2.descs = []
        this.page2.ascs = prop
      } else if (val.order == 'descending') {
        this.page2.ascs = []
        this.page2.descs = prop
      } else {
        this.page2.ascs = []
        this.page2.descs = []
      }
      this.getPage2(this.page2)
    },
    getPage2(page, params) {
      this.tableLoading2 = true
      getPage2(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
            descs: this.page2.descs,
            ascs: this.page2.ascs,
            createId: this.selectRow.id,
          },
          params
        )
      )
        .then((response) => {
          this.tableData2 = response.data.data.records
          this.page2.total = response.data.data.total
          this.page2.currentPage = page.currentPage
          this.page2.pageSize = page.pageSize
          this.tableLoading2 = false
        })
        .catch(() => {
          this.tableLoading2 = false
        })
    },
    /**
     * @title 数据删除
     * @param row 为当前的数据
     * @param index 为当前删除数据的行数
     *
     **/
    handleDel2: function (row, index) {
      let _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj2(row.id)
        })
        .then((data) => {
          _this.$message({
            showClose: true,
            message: '已删除',
            type: 'success',
          })
          this.getPage2(this.page2)
        })
        .catch(function (err) { })
    },
    /**
     * 刷新回调
     */
    refreshChange2(page) {
      this.getPage2(this.page2)
    },
  },
  emits: ['update:value'],
}
</script>

