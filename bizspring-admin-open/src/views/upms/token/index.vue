<template>
  <div class="execution">
    <basic-container>
      <avue-crud ref="crud" v-model:page="page" :data="tableData" :table-loading="tableLoading" :option="tableOption"
        :permission="permissionList" @on-load="getPage" @sort-change="sortChange" @refresh-change="refreshChange"
        @row-del="handleDel">
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { delObj, getPage } from '@/api/upms/token'
import { tableOption } from '@/const/crud/upms/token'
import { mapGetters } from 'vuex'

export default {
  name: 'token',
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
        delBtn: this.permissions['sys:token:del'] ? true : false,
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
      this.$confirm('是否强制下线用户:' + row.username, '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row.access_token)
        })
        .then((data) => {
          _this.$message({
            showClose: true,
            message: '已删除',
            type: 'success',
          })
        })
        .catch(function (err) { })
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
