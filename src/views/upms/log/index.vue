<template>
  <div class="log">
    <basic-container>
      <avue-crud ref="crud" v-model:page="page" :data="tableData" :table-loading="tableLoading" :option="tableOption"
        :permission="permissionList" @on-load="getPage" @search-change="searchChange" @refresh-change="refreshChange"
        @sort-change="sortChange" @row-del="handleDel">
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { delObj, getPage } from '@/api/upms/log'
import { tableOption } from '@/const/crud/upms/log'
import { mapGetters } from 'vuex'

export default {
  name: 'log',
  data() {
    return {
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],
        descs: 'create_time',
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
        delBtn: this.permissions['sys:log:del'] ? true : false,
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
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
        this.page.currentPage = page.currentPage
        this.page.pageSize = page.pageSize
        this.tableLoading = false
      })
    },
    handleDel: function (row, index) {
      var _this = this
      this.$confirm('是否确认删除?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.id)
        })
        .then((data) => {
          this.getPage(this.page)
          _this.$message({
            showClose: true,
            message: '删除成功',
            type: 'success',
          })
        })
        .catch(function (err) { })
    },
    /**
     * 搜索回调
     */
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
  },
}
</script>
