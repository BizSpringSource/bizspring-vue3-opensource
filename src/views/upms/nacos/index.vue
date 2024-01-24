<template>
  <div style="width: 100%; text-align: center; background-color: white">
    <el-alert
      title="nacos后台已在新窗口打开，以下为内容截图"
      type="warning"
      center
      show-icon
      :closable="false"
    >
    </el-alert>
    <p>
      <el-image
        src="https://bizspring-blog.oss-cn-zhangjiakou.aliyuncs.com/git/20210319204007.png"
        style="width: 80%"
      ></el-image>
    </p>
  </div>
</template>

<script>
export default {
  name: 'nacos',
  data() {
    return {}
  },
  created() {
    let url = 'http://127.0.0.1:8848/nacos/index.html'
    window.open(url, '_blank')
  },
  mounted: function () {},
}
</script>
