<template>
  <div v-loading="loading" :style="'height:' + height">
    <iframe :src="src" frameborder="no" style="width: 100%; height: 100%" scrolling="auto" />
  </div>
</template>

<script>
export default {
  name: 'Druid',
  data() {
    return {
      src: '',
      height: document.documentElement.clientHeight - 94.5 + 'px;',
      loading: true,
    }
  },
  created() {
    let src = this.publicGlobalUrl + '/upms/druid/index.html'
    this.src = src
  },
  mounted: function () {
    setTimeout(() => {
      this.loading = false
    }, 230)
    const that = this
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + 'px;'
    }
  },
}
</script>
