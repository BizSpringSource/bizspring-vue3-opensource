<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <div style="text-align: center; color: red; margin-bottom: 10px">
        bizspring商城
      </div>
      <avue-crud @on-load="getPage" :option="tableOption" :data="tableData" :permission="permissionList"
        :table-loading="tableLoading" :before-open="beforeOpen" @search-change="searchChange"
        @refresh-change="refreshChange" @row-update="handleUpdate" @row-save="handleSave" @row-del="handleDel">
        <template v-slot:menu="scope">
          <el-link type="primary" :icon="ElIconSuitcase" v-if="permissions['sys:tenant:edit']"
            @click="handlePermission(scope.row, scope.index)">管理员角色权限</el-link>
        </template>
        <template v-slot:status="scope">
          <el-tag   effect="light" :type="scope.row.status == '0' ? 'success' : 'danger'">{{ scope.label
          }}</el-tag>
        </template>
      </avue-crud>
      <el-dialog title="分配权限" v-model="dialogPermissionVisible" :v-loading="loading">
        <el-tree class="filter-tree" :data="treeData" :default-checked-keys="checkedKeys" :check-strictly="false"
          node-key="id" highlight-current :props="defaultProps" show-checkbox ref="menuTree"
          :filter-node-method="filterNode" default-expand-all>
        </el-tree>
        <template v-slot:footer>
          <div class="dialog-footer">
            <el-button type="primary" @click="updatePermession(id, roleCode)" v-loading="dialogLoading">更 新
            </el-button>
          </div>
        </template>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { Suitcase as ElIconSuitcase } from '@element-plus/icons-vue'
import { addObj, delObj, getPage, getObj, putObj } from '@/api/upms/tenant'
import { fetchRoleTreeTenant, permissionUpdTenant } from '@/api/upms/role'
import { tableOption } from '@/const/crud/upms/tenant'
import { fetchAllMenuTree } from '@/api/upms/menu'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      dialogPermissionVisible: false,
      tableOption: tableOption,
      tableData: [],
      checkedKeys: [],
      tableLoading: false,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [], //升序字段
        descs: 'create_time', //降序字段
      },
      paramsSearch: {},
      defaultProps: {
        label: 'name',
        value: 'id',
      },
      treeData: [],
      dialogLoading: false,
      ElIconSuitcase: shallowRef(ElIconSuitcase),
    }
  },
  name: 'tenant',
  created() { },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:tenant:add'] ? true : false,
        delBtn: this.permissions['sys:tenant:del'] ? true : false,
        editBtn: this.permissions['sys:tenant:edit'] ? true : false,
        viewBtn: this.permissions['sys:tenant:get'] ? true : false,
      }
    },
  },
  methods: {
    getPage(page, params) {
      this.tableLoading = true
      getPage(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
            descs: this.page.descs,
            ascs: this.page.ascs,
          },
          params,
          this.paramsSearch
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records
          this.page.total = response.data.data.total
          this.page.currentPage = page.currentPage
          this.page.pageSize = page.pageSize
          this.tableLoading = false
        })
        .catch(() => {
          this.tableLoading = false
        })
    },
    handleDel: function (row, index) {
      var _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.id)
        })
        .then((data) => {
          _this.$message({
            showClose: true,
            message: '删除成功',
            type: 'success',
          })
          this.getPage(this.page)
        })
        .catch(function (err) { })
    },
    /**
     * @title 数据更新
     * @param row 为当前的数据
     * @param index 为当前更新数据的行数
     * @param done 为表单关闭函数
     *
     **/
    handleUpdate: function (row, index, done, loading) {
      putObj(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '修改成功',
            type: 'success',
          })
          done()
          this.getPage(this.page)
        })
        .catch(() => {
          loading()
        })
    },
    /**
     * @title 数据添加
     * @param row 为当前的数据
     * @param done 为表单关闭函数
     *
     **/
    handleSave: function (row, done, loading) {
      addObj(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '添加成功',
            type: 'success',
          })
          done()
          this.getPage(this.page)
        })
        .catch(() => {
          loading()
        })
    },
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
    },
    /**
     * 刷新回调
     */
    refreshChange(page) {
      this.getPage(this.page)
    },
    handlePermission(row) {
      this.tableLoading = true
      fetchRoleTreeTenant(row.id)
        .then((response) => {
          this.checkedKeys = response.data.data.listMenuVO
          this.checkedDysRole = response.data.data.sysRole
          return fetchAllMenuTree()
        })
        .then((response) => {
          this.treeData = response.data.data
          // 解析出所有的太监节点
          this.checkedKeys = this.resolveAllEunuchNodeId(
            this.treeData,
            this.checkedKeys,
            []
          )
          this.dialogPermissionVisible = true
          this.id = this.checkedDysRole.id
          this.roleCode = this.checkedDysRole.roleCode
          this.tableLoading = false
        })
    },
    /**
     * 解析出所有的节点id
     * @param json 待解析的json串
     * @param idArr 原始节点数组
     * @param temp 临时存放节点id的数组
     * @return 太监节点id数组
     */
    resolveAllEunuchNodeId(json, idArr, temp) {
      for (let i = 0; i < json.length; i++) {
        const item = json[i]
        // 存在子节点，递归遍历;不存在子节点，将json的id添加到临时数组中
        if (item.children && item.children.length !== 0) {
          this.resolveAllEunuchNodeId(item.children, idArr, temp)
        } else {
          temp.push(idArr.filter((id) => id === item.id))
        }
      }
      return temp
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    updatePermession(id, roleCode) {
      this.dialogLoading = true
      this.menuIds = ''
      this.menuIds = this.$refs.menuTree
        .getCheckedKeys()
        .join(',')
        .concat(',')
        .concat(this.$refs.menuTree.getHalfCheckedKeys().join(','))
      permissionUpdTenant({
        tenantId: this.checkedDysRole.tenantId,
        roleId: id,
        menuId: this.menuIds,
      })
        .then(() => {
          this.dialogLoading = false
          this.dialogPermissionVisible = false
          fetchAllMenuTree()
            .then((response) => {
              this.form = response.data.data
              return fetchRoleTreeTenant(this.checkedDysRole.tenantId)
            })
            .then((response) => {
              this.checkedKeys = response.data.data.listMenuVO
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000,
              })
            })
        })
        .catch(() => {
          this.dialogLoading = false
        })
    },
    beforeOpen(done, type) {
      window.openType = type
      done()
    },
  },
}
</script>
