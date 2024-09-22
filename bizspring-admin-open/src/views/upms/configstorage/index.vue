<template>
  <div class="execution">
    <basic-container>
      <avue-crud ref="crud" v-model:page="page" :data="tableData" :permission="permissionList"
        :table-loading="tableLoading" :option="tableOption" v-model="form" @on-load="getPage"
        @refresh-change="refreshChange" @row-update="handleUpdate" @row-save="handleSave" @row-del="handleDel"
        @sort-change="sortChange" @search-change="searchChange">
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import {
  getPage,
  addObj,
  putObj,
  delObj,
} from '@/api/upms/configstorage'
import { tableOption } from '@/const/crud/upms/configstorage'
import { mapGetters } from 'vuex'

export default {
  name: 'sysconfigstorage',
  data() {
    return {
      form: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [], //升序字段
        descs: [], //降序字段
      },
      paramsSearch: {},
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
        addBtn: this.permissions['sys:sysconfigstorage:add'] ? true : false,
        delBtn: this.permissions['sys:sysconfigstorage:del'] ? true : false,
        editBtn: this.permissions['sys:sysconfigstorage:edit'] ? true : false,
        viewBtn: this.permissions['sys:sysconfigstorage:get'] ? true : false,
      }
    },
  },
  methods: {
    searchChange(params, done) {
      params = this.filterForm(params)
      this.paramsSearch = params
      this.page.currentPage = 1
      this.getPage(this.page, params)
      done()
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
    /**
     * @title 数据删除
     * @param row 为当前的数据
     * @param index 为当前删除数据的行数
     *
     **/
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
        .then((response) => {
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
        .then((response) => {
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
  },
}
</script>
