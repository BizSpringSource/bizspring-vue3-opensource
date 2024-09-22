<!--<SendSmsCode ref="smsCodeForm" @validatePhone="回调检验方法"/> -->
<template>
  <el-form ref="smsForm" :rules="smsRules" :model="smsForm" :label-width="lableWidth" status-icon>
    <el-form-item prop="phone" :label="phoneLabel">
      <el-input v-model="smsForm.phone" auto-complete="off" placeholder="请填写手机号">
        <template #prefix>
          <i class="icon-iconmobile"></i>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item v-if="codeShow" prop="code" :label="codeLabel">
      <el-input v-model="smsForm.code" auto-complete="off" placeholder="请填写验证码">
        <template #prefix>
          <i class="icon-verification-code"></i>
        </template>
        <template #append>
          <span :class="[{ display: msgKey }]" class="msg-text" @click="handleSend">
            发送验证码
          </span>
        </template>
      </el-input>
    </el-form-item>
  </el-form>
</template>

<script>
import { sendSmsCode } from '@/api/login'
import { validatenull } from '@/util/validate'

const MSGINIT = '发送验证码',
  MSGSCUCCESS = '${time}'+'秒后重发',
  MSGTIME = 60

export default {
  name: 'SendSmsCode',
  props: {
    phoneLabel: {
      type: String,
      default: null
    },
    exist: {
      type: Boolean,
      default: false
    },
    codeLabel: {
      type: String,
      default: null
    },
    codeShow: {
      type: Boolean,
      default: true
    },
    lableWidth: {
      type: String,
      default: null
    },
    modelValue: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    const validatePhone = (rule, value, callback) => {
      this.$emit('validatePhone', rule, value, callback)
    }
    const validateCode = (rule, value, callback) => {
      if (validatenull(value) || value.length !== 4) {
        callback(new Error('请填写4位数验证码'))
      } else {
        callback()
      }
    }
    return {
      msgText: MSGINIT,
      msgTime: MSGTIME,
      msgKey: false,
      smsForm: {
        phone: '',
        code: ''
      },
      smsRules: {
        phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
        code: [{ required: true, trigger: 'blur', validator: validateCode }]
      }
    }
  },
  watch: {
    smsForm: {
      handler(val) {
        this.$emit('"update:modelValue"', val)
      },
      deep: true,
      immediate: true
    },
    modelValue: {
      handler(val) {
        this.smsForm = val
      },
      deep: true,
      immediate: true
    }
  },
  methods: {
    validate(callback) {
      this.$refs.smsForm.validate((valid) => {
        callback(valid)
      })
    },
    handleSend() {
      // 判断是否可以发送（时间限制）
      if (this.msgKey) return
      // 发送验证码
      this.$refs.smsForm.validateField('phone', (valid) => {
        if (valid) {
          this.smsForm.exist = this.exist
          sendSmsCode(this.smsForm).then((response) => {
            if (response.data.data) {
              this.$message.success('验证码已发送')
              this.$emit('smsForm', this.smsForm)
              this.timeCacl()
            } else {
              this.$message.error(response.data.msg)
            }
          })
        }
      })
    },
    timeCacl() {
      // 计时避免重复发送
      this.msgText = MSGSCUCCESS.replace('${time}', this.msgTime)
      this.msgKey = true
      const time = setInterval(() => {
        this.msgTime--
        this.msgText = MSGSCUCCESS.replace('${time}', this.msgTime)
        if (this.msgTime === 0) {
          this.msgTime = MSGTIME
          this.msgText = MSGINIT
          this.msgKey = false
          clearInterval(time)
        }
      }, 1000)
    }
  }
}
</script>

<style>
.msg-text {
  display: block;
  width: 120px;
  font-size: 12px;
  text-align: center;
  cursor: pointer;
}

.msg-text.display {
  color: #ccc;
}
</style>
