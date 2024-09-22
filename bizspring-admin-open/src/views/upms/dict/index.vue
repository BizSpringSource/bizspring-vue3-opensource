<template>
  <div class="execution">
    <basic-container>
      <el-row :gutter="10">
        <el-col :span="this.clickObj ? 12 : 24">
          <el-card class="box-card">
            <avue-crud ref="crud" v-model:page="page" :data="tableData" :permission="permissionList"
              :table-loading="tableLoading" :option="tableOption"  v-model="form" @on-load="getPage"
              @row-update="handleUpdate" @row-save="handleSave" @search-change="searchChange" @sort-change="sortChange"
              @refresh-change="refreshChange" @row-del="handleDel">
              <template v-slot:menu="scope">
                <el-link type="primary" :icon="ElIconSClaim"   plain @click="handleItem(scope.row, scope.index)">键值列表</el-link>
              </template>
            </avue-crud>
          </el-card>
        </el-col>
        <el-col :span="12" v-if="this.clickObj">
          <el-card class="box-card">
            <template v-slot:header>
              <div class="clearfix">
                <h3>
                  <el-icon><el-icon-document-copy /></el-icon>
                  键值列表，所属类型：{{ this.clickObj.type }}
                </h3>
                <el-button style="float: right; padding: 3px 0" link @click="handleItem()">关闭</el-button>
              </div>
            </template>
            <avue-crud ref="crud2" v-model="form2" v-model:page="page2" :data="tableData2"
              :permission="permissionList" :before-open="beforeOpen2" :option="tableOption2"
              :table-loading="tableLoading2" @on-load="getPage2" @row-update="handleUpdate2" @row-save="handleSave2"
              @row-del="handleDel2" @refresh-change="refreshChange2" @sort-change="sortChange2">
            </avue-crud>
          </el-card>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import {
  DocumentCopy as ElIconDocumentCopy,
  Notification as ElIconSClaim,
} from '@element-plus/icons-vue'
import {
  getPage,
  addObj,
  putObj,
  delObj,
  getPage2,
  addObj2,
  putObj2,
  delObj2,
} from '@/api/upms/dict'
import { tableOption2, tableOption } from '@/const/crud/upms/dict'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      clickObj: null,
      form: {},
      dialogFormVisible: false,
      tableData: [],
      tableOption: tableOption,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      },
      tableLoading: false,
      form2: {},
      tableData2: [],
      page2: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      },
      tableLoading2: false,
      tableOption2: tableOption2,
      ElIconSClaim:shallowRef(ElIconSClaim),
      ElIconDocumentCopy:shallowRef(ElIconDocumentCopy)
    }
  },
  name: 'dict',
  created() { },
  mounted: function () { },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['sys:dict:add'] ? true : false,
        delBtn: this.permissions['sys:dict:del'] ? true : false,
        editBtn: this.permissions['sys:dict:edit'] ? true : false,
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
      this.$confirm('是否确认删除此数据', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(function () {
          return delObj(row)
        })
        .then(() => {
          this.tableData = []
          this.getPage(this.page)
          _this.$message({
            showClose: true,
            message: '已删除',
            type: 'success',
          })
        })
        .catch(function () { })
    },
    /**
     * @title 数据更新
     * @param row 为当前的数据
     * @param index 为当前更新数据的行数
     * @param done 为表单关闭函数
     *
     **/
    handleUpdate: function (row, index, done, loading) {
      putObj(this.filterForm(row))
        .then(() => {
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
      addObj(this.filterForm(row))
        .then(() => {
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
    searchChange(form, done) {
      this.getPage(this.page, this.filterForm(form))
      done()
    },
    /**
     * 刷新回调
     */
    refreshChange(val) {
      this.getPage(this.page)
    },

    handleItem: function (row) {
      if (row) {
        this.clickObj = row
        this.page2.currentPage = 1
        this.getPage2(this.page2)
      } else {
        this.clickObj = null
      }
    },
    beforeOpen2(done) {
      this.form2['dictId'] = this.clickObj.id
      this.form2['type'] = this.clickObj.type
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
          },
          params,
          this.paramsSearch2,
          {
            dictId: this.clickObj.id,
          }
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
      var _this = this
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
     * @title 数据更新
     * @param row 为当前的数据
     * @param index 为当前更新数据的行数
     * @param done 为表单关闭函数
     *
     **/
    handleUpdate2: function (row, index, done, loading) {
      putObj2(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '更新成功',
            type: 'success',
          })
          done()
          this.getPage2(this.page2)
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
    handleSave2: function (row, done, loading) {
      addObj2(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '保存成功',
            type: 'success',
          })
          done()
          this.getPage2(this.page2)
        })
        .catch(() => {
          loading()
        })
    },
    /**
     * 刷新回调
     */
    refreshChange2(val) {
      this.getPage2(this.page2)
    },
  },
}
</script>
