<template>
  <div class="login-container">
    <div class="login-weaper  animated bounceInDown">
      <div class="login-left animated fadeInLeft">
        <div class="login-info">
          <h2 class="login-info-title">BizSpring电商平台</h2>
          <ul class="login-info-list">
            <li class="login-info-item">
              <el-link type="primary" href="http://www.bizspring.cn">前去官网</el-link>
            </li>
          </ul>
        </div>
      </div>
      <div class="login-border animate__animated animate__fadeInRight">
        <div class="login-main">
          <userLogin v-if="activeName === 'user'"></userLogin>
          <codeLogin v-else-if="activeName === 'code'"></codeLogin>
          <user-register v-else-if="activeName === 'register'" @ok="activeName = 'user'"></user-register>
          <div class="login-menu">
            <a href="#" @click.stop="activeName = 'user'">账号密码</a>
            <a href="#" @click.stop="activeName = 'code'">短信登录</a>
            <a v-if="website.register" href="#" @click.stop="activeName = 'register'">用户注册</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import '@/styles/login.scss'
import userLogin from './userlogin.vue'
import codeLogin from './codelogin.vue'
import userRegister from '@/page/login/userRegister.vue'
import { mapGetters } from 'vuex'
import request from '@/router/axios'

export default {
  name: 'login',
  components: {
    userLogin,
    codeLogin,
    userRegister
  },
  data() {
    return {
      activeName: 'user'
    }
  },
  created() {
    this.loadLanguage();
  },
  computed: {
    ...mapGetters(['website'])
  },
  methods: {
    loadLanguage() {
      request({
        url: '/upms/i18n/message?terminal=admin&language=zh_CN',
        method: 'get'
      }).then(response => {
        if (response.status == '200') {
          let messages = response.data.data.i18n;
          sessionStorage.setItem("messages", JSON.stringify(messages));
        } else {
          this.$message.error(response.data.message)
        }
      })
    }


  }
}
</script>
