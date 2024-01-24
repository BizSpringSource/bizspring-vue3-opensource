<template>
  <div>
    <!-- 导入对话框 -->
    <el-dialog
      v-model="upload.open"
      :title="title"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="headers"
        :action="baseUrl + url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>

        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-button
              v-if="tempUrl"
              text
              type="primary"
              :underline="false"
              style="font-size:12px;vertical-align: baseline;"
              @click="downExcelTemp"
            >下载模板
            </el-button>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!--校验失败错误数据-->
    <el-dialog v-model="errorVisible" title="校验失败数据">
      <el-table :data="errorData">
        <el-table-column
          property="lineNum"
          label="行号"
          width="100"
        ></el-table-column>
        <el-table-column
          property="errors"
          label="错误描述"
          show-overflow-tooltip
        >
          <template #="scope">
            <el-tag
              v-for="error in scope.row.errors"
              :key="error"
              type="danger"
            >{{ error }}
            </el-tag
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import store from '@/store'

export default {
  name: 'ExcelUpload',
  components: {},
  props: {
    url: {
      type: String,
      default: null
    },
    title: {
      type: String,
      default: null

    },
    tempUrl: {
      type: String,
      default: null

    },
    tempName: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      upload: {
        open: false,
        isUploading: false
      },
      errorVisible: false,
      errorData: []
    }
  },
  computed: {
    headers: function() {
      return {
        Authorization: 'Bearer ' + store.getters.access_token
      }
    }
  },
  methods: {
    downExcelTemp() {
      this.downBlobFile(this.tempUrl, {}, this.tempName)
    },
    handleFileUploadProgress() {
      this.upload.isUploading = true
    },
    handleFileError() {
      this.$message.error('上传失败,数据格式不合法!')
      this.upload.open = false
    },
    handleFileSuccess(response) {
      this.upload.isUploading = false
      this.upload.open = false
      this.$refs.upload.clearFiles()
      // 校验失败
      if (response.code === 1) {
        this.$message.error('导入失败，以下数据不合法')
        this.errorVisible = true
        this.errorData = response.data
        this.$refs.upload.clearFiles()
      } else {
        this.$message.success('导入成功')
        // 刷新表格
        this.$emit('refreshDataList')
      }
    },
    submitFileForm() {
      this.$refs.upload.submit()
    },
    show() {
      this.upload.isUploading = false
      this.upload.open = true
    }
  }
}
</script>
