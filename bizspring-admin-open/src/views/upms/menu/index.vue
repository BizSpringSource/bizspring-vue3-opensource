<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <avue-crud ref="crud" v-model="form" @on-load="getPage" :option="tableOption" :data="tableData"
        :permission="permissionList" :table-loading="tableLoading" @refresh-change="refreshChange"
        @row-update="handleUpdate" @row-save="handleSave" @row-del="handleDel">
        <template v-slot:type="scope">
          <el-tag effect="light" :type="scope.row.type == '0'
            ? ''
            : scope.row.type == '1'
              ? 'danger'
              : 'warning'
            ">{{ scope.row.$type }}</el-tag>
        </template>
        <template v-slot:icon="scope">
          <i :class="scope.row.icon"></i>
        </template>
        <template v-slot:menu="scope">
          <el-link type="primary" v-if="permissions['sys:menu:add']" :icon="ElIconPlus" plain
            @click="addChildrenMenu(scope.row, scope.index)">添加下级菜单</el-link>
        </template>
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { Plus as ElIconPlus } from '@element-plus/icons-vue'
import { shallowRef } from 'vue'
import {
  addObj,
  delObj,
  fetchAllMenuTree,
  putObj,
} from '@/api/upms/menu'
import { tableOption } from '@/const/crud/upms/menu'
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      form: {},
      tableOption: tableOption,
      tableData: [],
      tableLoading: false,
      ElIconPlus: shallowRef(ElIconPlus),
    }
  },
  name: 'sysMenu',
  watch: {
    'form.type'() {
      let column4 = this.tableOption.column[4]
      let column5 = this.tableOption.column[5]
      let column6 = this.tableOption.column[6]
      let column7 = this.tableOption.column[7]
      if (this.form.type === '0') {
        column5.display = true
        column6.display = true
        column7.display = true
      }
      if (this.form.type === '1') {
        column5.display = false
        column6.display = false
        column7.display = false
      }
    },
  },
  created() { },
  computed: {
    ...mapGetters(['elements', 'permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:menu:add'] ? true : false,
        delBtn: this.permissions['sys:menu:del'] ? true : false,
        editBtn: this.permissions['sys:menu:edit'] ? true : false,
        viewBtn: this.permissions['sys:menu:get'] ? true : false,
      }
    },
  },
  methods: {
    getPage() {
      this.tableLoading = true
      fetchAllMenuTree()
        .then((response) => {
          this.tableData = response.data.data
          let children = this.tableData
          let parentIdDIC = [
            {
              id: '0',
              name: '顶级',
              parentId: '0',
              children: children,
            },
          ]
          this.$refs.crud.DIC.parentId = parentIdDIC
          this.tableLoading = false
        })
        .catch(() => {
          this.tableLoading = false
        })
    },
    handleDel: function (row, index) {
      var _this = this
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.id)
        })
        .then((data) => {
          _this.$message({
            showClose: true,
            message: '已删除',
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
            message: '更新成功',
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
            message: '保存成功',
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
     * 刷新回调
     */
    refreshChange(page) {
      this.getPage(this.page)
    },
    addChildrenMenu(row, index) {
      this.form.parentId = row.id
      this.$refs.crud.rowAdd()
    },
  },
}
</script>

