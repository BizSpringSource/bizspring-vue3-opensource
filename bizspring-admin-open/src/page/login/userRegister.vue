<template>
  <el-form ref="registerForm" class="login-form" status-icon :rules="registerRules" :model="registerForm" label-width="0"
     >
    <el-form-item prop="email">
      <el-input v-model="registerForm.username" auto-complete="off" placeholder="请输入邮箱"
        @keyup.enter.native="handleRegister">
        <template #prefix>
          <i class="icon-mail-setting"></i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="username">
      <el-input v-model="registerForm.username" auto-complete="off" placeholder="请填写用户名"
        @keyup.enter.native="handleRegister">
        <template #prefix>
          <i class="icon-user-line"></i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="registerForm.password" type="password" auto-complete="off" show-password placeholder="请填写密码"
        @keyup.enter.native="handleRegister">
        <template #prefix>
          <i class="icon-password"></i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="password2">
      <el-input v-model="registerForm.password2" type="password" auto-complete="off" show-password placeholder="请填写密码"
        @keyup.enter.native="handleRegister">
        <template #prefix>
          <i class="icon-password"></i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item prop="organname">
      <el-input v-model="registerForm.organname" auto-complete="off" placeholder="请输入机构"
        @keyup.enter.native="handleRegister">
        <template #prefix>
          <i class="icon-brand"></i>
        </template>
      </el-input>
    </el-form-item>
    <SendSmsCode ref="smsCodeForm" v-model="registerForm" @validatePhone="validatePhone" />
    <el-form-item>
      <el-button type="primary" class="login-submit" @click.native.prevent="handleRegister">确 定
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { registerUser } from '@/api/login'
import { mapGetters } from 'vuex'
import SendSmsCode from '@/components/Sms/index.vue'
import { checkPassword, checkPhone, validateUsername } from '@/const/crud/admin/user'

export default {
  name: 'userRegister',
  components: { SendSmsCode },
  data() {
    return {
      registerForm: {
        email: '',
        username: '',
        password: '',
        password2: '',
        // phone: '',
        // code: '',
        organname: ''
      },
      checked: false,
      registerRules: {
        username: [
          {
            min: 6,
            max: 20,
            message: '长度在6至20个字符',
            trigger: 'blur'
          },
          {
            validator: validateUsername,
            trigger: 'blur'
          }
        ],
        password: [{ validator: checkPassword, trigger: 'blur' }],
        password2: [{ validator: checkPassword, trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: "blur" }],
        organname: [{ required: true, message: '请输入机构', trigger: "blur" }]
      }
    }
  },
  created() {
  },
  computed: {
    ...mapGetters(['tagWel'])
  },
  methods: {
    validatePhone(rule, value, callback) {
      checkPhone(rule, value, callback)
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.$refs.smsCodeForm.validate((valid2) => {
            if (valid2) {
              registerUser(this.registerForm)
                .then(() => {
                  this.$message.success('注册成功')
                  this.$emit('ok')
                })
            }
          })
        }
      })
    },
  }
}
</script>

<style></style>
