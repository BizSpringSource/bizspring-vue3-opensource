<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <el-tabs @tab-click="switchTab" v-model="switchStatus" tabPosition="left">
        <el-tab-pane label="基本信息" name="userManager">
          <el-form :model="ruleForm" ref="ruleForm" label-width="100px" v-if="switchStatus === 'userManager'">
            <el-form-item :labelWidth="150" label="用户名" prop="username">
              <el-input width="200px" link maxlength="30" v-model="ruleForm.username" disabled></el-input>
            </el-form-item>
            <el-form-item :labelWidth="150" label="机构" prop="organId">
              <avue-input-tree v-model="ruleForm.organId" type="tree" placeholder="请选择机构" :dic="treeOrganData"
                :props="organProps" disabled></avue-input-tree>
            </el-form-item>
            <el-form-item :labelWidth="150" label="手机号码" prop="mobile">
              <el-row>
                <el-col :span="15" style="padding-right: 10px">
                  <el-input v-model="mobile" maxlength="11" placeholder="绑定手机号" disabled></el-input>
                </el-col>
                <el-col :span="5" v-if="mobile">
                  <el-button @click="showBindPhone('3')">解绑</el-button>
                </el-col>
                <el-col :span="5" v-if="!mobile">
                  <el-button @click="showBindPhone('2')">绑定手机</el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item :labelWidth="150" label="邮箱" prop="email">
              <el-input v-model="ruleForm.email" maxlength="30" placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item label="">
              <div class="text-center">
                <userAvatar v-on:setAvatar="setAvatar" :user="ruleForm" />
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">确 定
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="passwordManager">
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
            v-if="switchStatus === 'passwordManager'">
            <el-form-item :labelWidth="150" label="原密码" prop="password">
              <el-input type="password" show-password maxlength="30" v-model="ruleForm.password"></el-input>
            </el-form-item>
            <el-form-item :labelWidth="150" label="密码" prop="newpassword1">
              <el-input type="password" show-password maxlength="30" v-model="ruleForm.newpassword1"></el-input>
            </el-form-item>
            <el-form-item  :labelWidth="150" label="确认密码" prop="newpassword2">
              <el-input type="password" show-password maxlength="30" v-model="ruleForm.newpassword2"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">确 定
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="操作日志" name="logManager">
          <avue-crud ref="crud" v-model:page="page" :data="tableData" :table-loading="tableLoading" :option="tableOption"
            :permission="permissionList" @on-load="getPage" @search-change="searchChange" @refresh-change="refreshChange"
            @sort-change="sortChange" @row-del="handleDel">
          </avue-crud>
        </el-tab-pane>
        <el-tab-pane label="登录日志" name="loginlogManager">
          <avue-crud ref="crud" v-model:page="page2" :data="tableData2" :table-loading="tableLoading2"
            :option="tableOption2" :permission="permissionList2" @on-load="getPage2" @search-change="searchChange2"
            @refresh-change="refreshChange2" @sort-change="sortChange2" @row-del="handleDel2">
          </avue-crud>
        </el-tab-pane>
      </el-tabs>
      <el-dialog :title="doType == '2' ? '绑定手机' : '解绑'" v-model="dialogBindPhone" :close-on-click-modal="false"
        width="30%">
        <codePhone :type="doType" :mobile="mobile" @handleDone="handleBindPhone" />
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import store from '@/store'
import { fetchTree } from '@/api/upms/organ'
import { delObj, getPage } from '@/api/upms/log'
import { tableOption } from '@/const/crud/upms/log'
import { tableOption as tableOption2 } from '@/const/crud/upms/loginlog'
import { getPage as getPage2, delObj as delObj2 } from '@/api/upms/loginlog'
import { editObj, bindPhone } from '@/api/upms/user'
import codePhone from '@/components/code-phone/main.vue'
import userAvatar from './userAvatar.vue'

export default {
  components: {
    codePhone,
    userAvatar,
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (this.ruleForm.password !== '') {
        if (value !== this.ruleForm.newpassword1){
          callback(new Error('两次输入密码不一致');
        }else{
          callback();
        }
      } else {
        callback();
      }
    }
    return {
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
        ascs: [],
        descs: 'created_time',
      },
      tableLoading: false,
      tableOption: tableOption,
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
      treeOrganData: [],
      organProps: {
        label: 'name',
        value: 'id',
      },
      optionAvatar: {
        emptyBtn: false,
        submitBtn: false,
        labelWidth: 120,
        column: [
          {
            prop: 'avatar',
            type: 'upload',
            listType: 'picture-img',
            action: '/upms/file/upload?fileType=image&path=user',
            propsHttp: {
              url: 'link',
            },
            loadText: '附件上传中，请稍等',
            span: 24,
            tip: '只能上传jpg/png文件，且不超过500kb',
          },
        ],
      },
      switchStatus: '',
      show: false,
      headers: {
        Authorization: 'Bearer ' + store.getters.access_token,
      },
      mobile: null,
      ruleForm: {
        username: '',
        password: '',
        newpassword1: '',
        newpassword2: '',
        avatar: '',
        email: '',
      },
      rules: {
        password: [
          {
            required: true,
            min: 6,
            message: '原密码不能为空且不少于6位',
            trigger: 'change',
          },
        ],
        newpassword1: [
          { required: true, min: 6, message: '不少于6位', trigger: 'change' },
        ],
        newpassword2: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
      },
      dialogBindPhone: false,
      doType: null,
    }
  },
  created() {
    this.ruleForm.id = this.userInfo.id
    this.ruleForm.organId = this.userInfo.organId
    this.ruleForm.username = this.userInfo.username
    this.mobile = this.userInfo.mobile
    this.ruleForm.email = this.userInfo.email
    this.ruleForm.avatar =
      this.userInfo.avatar != null ? this.userInfo.avatar : ''
    this.switchStatus = 'userManager'
    this.handleOrgan()
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        delBtn: this.permissions['sys:log:del'] ? true : false,
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
  methods: {
    handleBindPhone(loginForm, type) {
      bindPhone(
        Object.assign(
          {
            id: this.ruleForm.id,
            doType: type,
          },
          loginForm
        )
      ).then((response) => {
        if (response.data.status == '200') {
          let message = ''
          if (type == '2') {
            this.mobile = loginForm.mobile
            message = '绑定成功'
          } else {
            this.mobile = null
            message = '解绑成功'
          }
          this.dialogBindPhone = false
          this.$store.dispatch('GetUserInfo')
          this.$notify({
            title: '成功',
            message: message,
            type: 'success',
            duration: 2000,
          })
        }
      })
    },
    showBindPhone(doType) {
      this.doType = doType
      this.dialogBindPhone = true
    },
    handleOrgan() {
      fetchTree().then((response) => {
        this.treeOrganData = response.data.data
      })
    },
    switchTab(tab, event) {
      this.switchStatus = tab.name
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true
          editObj(this.ruleForm)
            .then((response) => {
              this.loading = false
              if (response.data.data) {
                this.$notify({
                  title: '成功',
                  message: '更新成功',
                  type: 'success',
                  duration: 2000,
                })
                // 修改密码之后强制重新登录
                if (this.switchStatus === 'passwordManager') {
                  this.$store.dispatch('LogOut').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                  })
                }
              } else {
                this.$notify({
                  title: '失败',
                  message: response.data.message,
                  type: 'error',
                  duration: 2000,
                })
              }
            })
            .catch(() => {
              this.loading = false
              this.$notify({
                title: '失败',
                message: '更新失败',
                type: 'error',
                duration: 2000,
              })
            })
        } else {
          return false
        }
      })
    },
    setAvatar(evenAvatar) {
      console.log('setAvatar', evenAvatar)
      this.ruleForm.avatar = evenAvatar
    },
    // handleAvatarSuccess(res, done, loading) {
    //   this.ruleForm.avatar = res.url;
    //   done()
    // },
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
            createId: this.userInfo.id,
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
          return delObj(row.id)
        })
        .then((data) => {
          this.getPage(this.page)
          _this.$message({
            showClose: true,
            message: '已删除',
            type: 'success',
          })
        })
        .catch(function (err) { })
    },
    /**
     * 搜索回调
     */
    searchChange(form, done) {
      this.getPage(this.page, form)
      done()
    },
    /**
     * 刷新回调
     */
    refreshChange(page) {
      this.getPage(this.page)
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
            createId: this.userInfo.id,
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
}
</script>

<style>
.avatar-upload-preview {
  position: absolute;
  top: 50%;
  transform: translate(50%, -50%);
  width: 200px;
  height: 200px;
  border-radius: 50%;
  box-shadow: 0 0 4px #ccc;
  overflow: hidden;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
