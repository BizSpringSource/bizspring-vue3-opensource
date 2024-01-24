<template>
  <div style="width: 100%; text-align: center; background-color: white">
    <el-alert title="XXL_JOB后台已在新窗口打开，演示账号/密码：bizspring/123456，以下为内容截图" type="warning" center show-icon>
    </el-alert>
    <p>
      <el-image src="https://bizspring-blog.oss-cn-zhangjiakou.aliyuncs.com/git/20210120133516.png"
        style="width: 80%"></el-image>
    </p>
    <p>
      <el-image src="https://bizspring-blog.oss-cn-zhangjiakou.aliyuncs.com/git/20210120133442.png"
        style="width: 80%"></el-image>
    </p>
  </div>
</template>

<script>
export default {
  name: 'job',
  data() {
    return {}
  },
  created() {
    this.$alert('演示账号/密码：bizspring/123456', '提示', {
      confirmButtonText: '确定',
      callback: (action) => {
        let src = 'http://139.129.89.21:8080/xxl-job-admin'
        window.open(src, '_blank')
      },
    })
  },
  mounted: function () { },
}
</script>
