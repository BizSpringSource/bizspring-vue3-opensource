<template>
  <basic-container>
    <iframe ref="iframe" :src="src" class="iframe" />
  </basic-container>
</template>

<script>
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
export default {
  name: 'AvueIframe',
  data() {
    return {}
  },
  computed: {
    src() {
      return this.$route.query.url
    }
  },
  watch: {
    $route: function () {
      this.load()
    }
  },
  created() {
    NProgress.configure({ showSpinner: false })
  },
  mounted() {
    this.load()
  },
  methods: {
    // 显示等待框
    show() {
      NProgress.start()
    },
    // 隐藏等待狂
    hide() {
      NProgress.done()
    },
    // 加载组件
    load() {
      this.show()
      //超时3s自动隐藏等待狂，加强用户体验
      let time = 3
      const timeFunc = setInterval(() => {
        time--
        if (time === 0) {
          this.hide()
          clearInterval(timeFunc)
        }
      }, 1000)
      this.iframeInit()
    },
    //iframe窗口初始化
    iframeInit() {
      const iframe = this.$refs.iframe
      const clientHeight = document.documentElement.clientHeight - 150
      if (!iframe) return
      iframe.style.height = `${clientHeight}px`
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {
          this.hide()
        })
      } else {
        iframe.onload = () => {
          this.hide()
        }
      }
    }
  }
}
</script>

<style lang="scss">
.iframe {
  width: 100%;
  height: 100%;
  border: 0;
  overflow: hidden;
  box-sizing: border-box;
}
</style>
