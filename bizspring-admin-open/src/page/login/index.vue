<template>
  <div class="login-container">
    <div class="login-weaper  animated bounceInDown">
      <div class="login-left animated fadeInLeft">
        <div class="login-info">
          <h2 class="login-info-title">BizSpring跨境电商</h2>
        </div>
      </div>
      <div class="login-border animate__animated animate__fadeInRight">
        <div class="login-main">
          <userLogin v-if="activeName === 'user'"></userLogin>
          <codeLogin v-else-if="activeName === 'code'"></codeLogin>
          <user-register v-else-if="activeName === 'register'" @ok="activeName = 'user'"></user-register>
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
      // this.loadLanguage();
    },
  computed: {
    ...mapGetters(['website'])
  },
  methods:{



  }
}
</script>
