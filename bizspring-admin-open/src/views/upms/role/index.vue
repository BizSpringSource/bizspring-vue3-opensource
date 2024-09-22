<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <avue-crud :option="tableOption" :data="list" ref="crud" v-model:page="page" v-model="form"
        :table-loading="listLoading" :before-open="beforeOpen" :permission="permissionList" @on-load="getPage"
        @sort-change="sortChange" @search-change="searchChange" @refresh-change="refreshChange" @row-update="handleUpdate"
        @row-save="handleSave" @row-del="handleDel">
        <template v-slot:dsScopeForm="scope">
          <div v-if="form.dsType == 1">
            <el-tree class="filter-tree" :data="dsScopeData" :check-strictly="true" node-key="id" highlight-current
              :props="defaultProps" ref="scopeTree" :default-checked-keys="checkedDsScope" show-checkbox>
            </el-tree>
          </div>
        </template>
        <template v-slot:menu="scope">
          <el-link type="primary" :icon="ElIconSuitcase" v-if="permissions['sys:role:perm']"
            @click="handlePermission(scope.row, scope.index)">权限</el-link>
        </template>
      </avue-crud>
    </basic-container>
    <el-dialog title="分配权限" v-model="dialogPermissionVisible" :v-loading="listLoading">
      <el-tree class="filter-tree" :data="treeData" :default-checked-keys="checkedKeys" :check-strictly="false"
        node-key="id" highlight-current :props="defaultProps" show-checkbox ref="menuTree"
        :filter-node-method="filterNode" default-expand-all>
      </el-tree>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="updatePermession(id, roleCode)" v-loading="dialogLoading">更新
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Suitcase as ElIconSuitcase } from '@element-plus/icons-vue'
import {
  addObj,
  delObj,
  getPage,
  fetchRoleTree,
  permissionUpd,
  putObj,
} from '@/api/upms/role'
import { tableOption } from '@/const/crud/upms/role'
import { fetchTree } from '@/api/upms/organ'
import { fetchMenuTree } from '@/api/upms/menu'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      tableOption: tableOption,
      dsScopeData: [],
      treeData: [],
      checkedKeys: [],
      checkedDsScope: [],
      defaultProps: {
        label: 'name',
        value: 'id',
      },
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      },
      menuIds: '',
      list: [],
      listLoading: true,
      form: {},
      id: undefined,
      roleCode: undefined,
      rolesOptions: undefined,
      dialogPermissionVisible: false,
      dialogLoading: false,
      ElIconSuitcase:shallowRef(ElIconSuitcase),
    }
  },
  name: 'table_role',
  created() { },
  computed: {
    ...mapGetters(['elements', 'permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:role:add'] ? true : false,
        delBtn: this.permissions['sys:role:del'] ? true : false,
        editBtn: this.permissions['sys:role:edit'] ? true : false,
        viewBtn: this.permissions['sys:role:get'] ? true : false,
      }
    },
  },
  methods: {
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
      )
        .then((response) => {
          this.list = response.data.data.records
          this.page.total = response.data.data.total
          this.page.currentPage = page.currentPage
          this.page.pageSize = page.pageSize
          this.listLoading = false
        })
        .catch(() => {
          this.listLoading = false
        })
    },
    refreshChange(page) {
      this.getPage(this.page)
    },
    searchChange(param, done) {
      this.page.currentPage = 1
      this.getPage(this.page, this.filterForm(param))
      done()
    },
    beforeOpen(done, type) {
      window.openType = type
      fetchTree().then((response) => {
        this.dsScopeData = response.data.data
        if (this.form.dsScope) {
          this.checkedDsScope = this.form.dsScope.split(',')
        } else {
          this.checkedDsScope = []
        }
      })
      done()
    },
    handlePermission(row) {
      this.listLoading = true
      fetchRoleTree(row.id)
        .then((response) => {
          this.checkedKeys = response.data.data
          return fetchMenuTree()
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
          this.id = row.id
          this.roleCode = row.roleCode
          this.listLoading = false
        })
        .catch(function () {
          this.listLoading = false
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
    getNodeData(data, done) {
      done()
    },
    handleDel(row, index) {
      var _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.id)
        })
        .then(() => {
          this.getPage(this.page)
          this.list.splice(index, 1)
          _this.$message({
            showClose: true,
            message: '已删除',
            type: 'success',
          })
        })
        .catch(function () { })
    },
    handleSave(row, done, loading) {
      if (this.form.dsType === 1) {
        this.form.dsScope = this.$refs.scopeTree.getCheckedKeys().join(',')
      }
      addObj(this.form)
        .then(() => {
          this.getPage(this.page)
          done()
          this.$notify({
            title: '成功',
            message: '保存成功',
            type: 'success',
            duration: 2000,
          })
        })
        .catch(() => {
          loading()
        })
    },
    handleUpdate(row, index, done, loading) {
      if (this.form.dsType === 1) {
        this.form.dsScope = this.$refs.scopeTree.getCheckedKeys().join(',')
      }
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
    updatePermession(id, roleCode) {
      this.dialogLoading = true
      this.menuIds = ''
      this.menuIds = this.$refs.menuTree
        .getCheckedKeys()
        .join(',')
        .concat(',')
        .concat(this.$refs.menuTree.getHalfCheckedKeys().join(','))
      permissionUpd({
        roleId: id,
        menuId: this.menuIds,
      })
        .then(() => {
          this.dialogLoading = false
          this.dialogPermissionVisible = false
          fetchMenuTree()
            .then((response) => {
              return fetchRoleTree(id)
            })
            .then((response) => {
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success',
                duration: 2000,
              })
            })
        })
        .catch(() => {
          this.dialogLoading = false
        })
    },
  },
}
</script>
