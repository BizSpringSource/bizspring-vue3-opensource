<template>
  <div></div>
</template>

<script>
  import request from '@/router/axios'
  import {mapGetters} from 'vuex'

  export default {
    name: 'authredirect',
    computed: {
      ...mapGetters(['tagWel'])
    },
    created() {
      const params = this.$route.query
      let code = params.code
      let url = this.publicGlobalUrl
      // 登录请求
      if (url.indexOf('LOGIN') > 0) {
        //QQ 微信强大 可以解析
        if (url.indexOf('WX') > 0 || url.indexOf('QQ') > 0) {
          let state = params.state.split("-")[0]
          let code = params.code
          // window.opener.location.href = `${window.location.origin}/#/login?state=${state}&code=${code}&time=` + new Date().getTime();
          window.opener.location.href = `${this.publicGlobalUrl}/#/login?state=${state}&code=${code}&time=` + new Date().getTime();
        } else if (url.indexOf('OSC') > 0) {
          let start = url.indexOf("=") + 1
          let end = url.indexOf("&state")
          code = url.substring(start, end)
          // window.opener.location.href = `${window.location.origin}/#/login?state=OSC&code=${code}&time=` + new Date().getTime();
          window.opener.location.href = `${this.publicGlobalUrl}/#/login?state=OSC&code=${code}&time=` + new Date().getTime()
        } else if (url.indexOf('GITEE') > 0) {
          let start = url.indexOf("=") + 1
          let end = url.indexOf("&state")
          code = url.substring(start, end)
          // window.opener.location.href = `${window.location.origin}/#/login?state=GITEE&code=${code}&time=` + new Date().getTime();
          window.opener.location.href = `${this.publicGlobalUrl}/#/login?state=GITEE&code=${code}&time=` + new Date().getTime()
        }
        window.close()
      } else {
        if (url.indexOf('WX') > 0 || url.indexOf('QQ') > 0) {
          let state = params.state.split("-")[0]
          let code = params.code
          request({
            url: '/upms/thirdparty/bind',
            method: 'post',
            params: {state, code}
          }).then(() => {
            this.$alert('社交账号绑定成功', '成功', {
              confirmButtonText: '确 定',
              callback: () => {
                window.close()
              }
            })
          })
        } else if (url.indexOf('OSC') > 0) {
          let state = 'OSC'
          this.bind(state, url)
        } else if (url.indexOf('GITEE') > 0) {
          let state = 'GITEE'
          this.bind(state, url)
        }
      }
    },
    methods: {
      bind: function (state, url) {
        let start = url.indexOf("=") + 1
        let end = url.indexOf("#")
        let code = url.substring(start, end)
        request({
          url: '/upms/thirdparty/bind',
          method: 'post',
          params: {state, code}
        }).then(() => {
          this.$alert('社交账号绑定成功', '成功', {
            confirmButtonText: '确 定',
            callback: () => {
              window.close()
            }
          })
        })
      }
    }
  }
</script>


<style>
</style>
