<template>
  <el-form class="login-form" status-icon label-width="0">
    <SendSmsCode ref="smsCodeForm" v-model="smsCodeForm" :exist="true" @validatePhone="validatePhone" />
    <el-form-item>
      <el-button type="primary" class="login-submit" @click.native.prevent="handleLogin">确 定
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { mapGetters } from 'vuex'
import SendSmsCode from '@/components/Sms/index.vue'
import { isvalidatemobile } from '@/util/validate'


export default {
  name: 'Codelogin',
  components: { SendSmsCode },
  computed: {
    ...mapGetters(['tagWel'])
  },
  data() {
    return {
      smsCodeForm: {}
    }
  },
  methods: {
    validatePhone(rule, value, callback) {
      if (isvalidatemobile(value)[0]) {
        callback(new Error(isvalidatemobile(value)[1]))
      } else {
        callback()
      }
    },
    handleLogin() {
      this.$refs.smsCodeForm.validate((valid) => {
        if (valid) {
          this.$store.dispatch('LoginByPhone', this.smsCodeForm).then(() => {
            this.$router.push({ path: this.tagWel.value })
          })
        }
      })
    }
  }
}
</script>
