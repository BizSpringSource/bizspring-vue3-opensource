<template>
  <span @click="handleOpen()">
    <!-- <el-badge :value="logsFlag ? '' : logsLen" :max="99">
      <i class="icon-log"></i>
    </el-badge> -->
    <el-dialog v-model="box" title="日志" width="60%" append-to-body>
      <el-button type="primary" icon="el-icon-upload" @click="send">上传到服务器</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="clear">清空本地日志</el-button>
      <el-table :data="logsList">
        <el-table-column prop="type" label="类型" width="80px">
        </el-table-column>
        <el-table-column prop="url" label="地址" show-overflow-tooltip width="180">
        </el-table-column>
        <el-table-column prop="message" show-overflow-tooltip label="内容">
        </el-table-column>
        <el-table-column prop="stack" show-overflow-tooltip label="错误堆栈">
        </el-table-column>
        <el-table-column prop="time" label="时间">
        </el-table-column>
      </el-table>
    </el-dialog>
  </span>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'top-logs',
  data() {
    return {
      box: false
    }
  },
  created() { },
  mounted() { },
  computed: {
    ...mapGetters(['logsList', 'logsFlag', 'logsLen'])
  },
  props: [],
  methods: {
    handleOpen() {
      this.box = true
    },
    send() {
      this.$confirm('确定上传本地日志到服务器', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取 消',
        type: 'warning'
      })
        .then(() => {
          this.$store.dispatch('SendLogs').then(() => {
            this.box = false
            this.$message({
              type: 'success',
              message: '提交成功'
            })
          })
        })
        .catch(() => { })
    },
    clear() {
      this.$confirm('确定清空本地日志记录', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$store.commit('CLEAR_LOGS')
          this.box = false
          this.$message({
            type: 'success',
            message: '清空成功'
          })
        })
        .catch(() => { })
    }
  }
}
</script>

<style lang="scss" scoped>
.code {
  font-size: 12px;
  display: block;
  font-family: monospace;
  white-space: pre;
  margin: 1em 0px;
}
</style>
