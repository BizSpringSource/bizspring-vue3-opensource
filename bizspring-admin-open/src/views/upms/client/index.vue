<template>
  <div class="execution">
    <basic-container>
      <avue-crud ref="crud" v-model:page="page" :data="tableData" :table-loading="tableLoading" :option="tableOption"
        :permission="permissionList" @on-load="getPage" @refresh-change="refreshChange" @row-update="handleUpdate"
        @row-save="handleSave" @row-del="handleDel" @sort-change="sortChange">
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { addObj, delObj, getPage, putObj } from '@/api/upms/client'
import { tableOption } from '@/const/crud/upms/client'
import { mapGetters } from 'vuex'

export default {
  name: 'client',
  data() {
    return {
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
    }
  },
  created() { },
  mounted: function () { },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:client:add'] ? true : false,
        delBtn: this.permissions['sys:client:del'] ? true : false,
        editBtn: this.permissions['sys:client:edit'] ? true : false,
        viewBtn: this.permissions['sys:client:get'] ? true : false,
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
      this.tableLoading = true
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
        let tableData = response.data.data.records
        tableData.forEach(function (item, index) {
          item.authorizedGrantTypes = item.authorizedGrantTypes.split(',')
        })
        this.tableData = tableData
        this.page.total = response.data.data.total
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
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
      row.authorizedGrantTypes = row.authorizedGrantTypes.join(',')
      putObj(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '更新成功',
            type: 'success',
          })
          this.getPage(this.page)
          done()
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
      row.authorizedGrantTypes = row.authorizedGrantTypes.join(',')
      addObj(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '保存成功',
            type: 'success',
          })
          this.getPage(this.page)
          done()
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
  },
}
</script>
