<!--
  - Copyright (C) 2018-2019
  - All rights reserved, Designed By www.bizspring.cn
  - 注意：
  - 本软件为www.bizspring.cn开发研制，未经购买不得使用
  - 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
  - 一经发现盗用、分享等行为，将追究法律责任，后果自负
-->
<template>
  <div class="code-phone">
    <el-form :rules="formRules"
             ref="form"
             :model="formData"
             label-width="0">
      <el-form-item prop="mobile">
        <el-input    maxlength="15"
                  v-model="formData.mobile"
                  :disabled="type == '3'"
                  placeholder="请输入手机号码">
          <i slot="prefix"
             class="icon-shouji"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input   maxlength="8"
                  v-model="formData.code"
                  @keyup.enter.native="handleDone"
                  placeholder="请输入验证码">
          <i slot="prefix"
             class="icon-yanzhengma"
             style="margin-top:6px;"></i>
          <template slot="append">
          <span @click="handleSend"
                class="msg-text"
                :class="[{display:msgKey}]">{{msgText}}</span>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button  
                   type="primary"
                   @click.native.prevent="handleDone"
                   class="code-phone-submit">{{type == '1' ? '登录' : '确 定'}}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { validatePhone } from "@/util/validate"
  import { sendCode } from "@/api/upms/phone"
  const MSGINIT = "发送验证码",
          MSGSCUCCESS = "${time}秒后可重发",
          MSGTIME = 60;
  export default {
    name: "codePhone",
    props: {
      //1、登录；2、绑定；3、解绑
      type: {
        type: String
      },
      mobile: {
        type: String
      },
    },
    data() {
      const validatorPhone = (rule, value, callback) => {
        if (validatePhone(value)[0]) {
          callback(new Error(validatePhone(value)[1]));
        } else {
          callback();
        }
      };
      const validatorCode = (rule, value, callback) => {
        if (value.length != 4) {
          callback(new Error("请输入4位数的验证码"));
        } else {
          callback();
        }
      };
      return {
        msgText: MSGINIT,
        msgTime: MSGTIME,
        msgKey: false,
        formData: {
          mobile: '',
          code: ''
        },
        formRules: {
          mobile: [{ required: true, trigger: 'blur', validator: validatorPhone }],
          code: [{ required: true, trigger: 'blur', validator: validatorCode }]
        }
      }
    },
    created() {
      this.formData.mobile = this.mobile
    },
    unmounted() {

    },
    mounted(){

    },
    beforeDestroy() {

    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods:{
      handleSend() {
        if (this.msgKey) return
        if(validatePhone(this.formData.mobile)[0]){
          this.$message.error("请输入正确的手机号")
          return
        }
        this.msgKey = true
        sendCode({
          mobile: this.formData.mobile,
          type: this.type
        }).then(response => {
          this.msgKey = false
          if (response.data.status == '200') {
            this.$message.success('验证码发送成功')
            this.msgText = MSGSCUCCESS.replace('${time}', this.msgTime)
            this.msgKey = true
            const time = setInterval(() => {
              this.msgTime--
              this.msgText = MSGSCUCCESS.replace('${time}', this.msgTime)
              if (this.msgTime == 0) {
                this.msgTime = MSGTIME
                this.msgText = MSGINIT
                this.msgKey = false
                clearInterval(time)
              }
            }, 1000)
          } else {
            this.$message.error(response.data.message)
          }
        }).catch(() => {
          this.msgKey = false
        })
      },
      handleDone(){
        this.$refs.form.validate(valid => {
          if (valid) {
            this.$emit('handleDone', this.formData, this.type)
          }
        });
      }
    }
  };
</script>

<style lang="scss" scoped>
  .code-phone-submit {
    display: block !important;
    margin: 40px auto 10px auto !important;
    width: 200px;
    height: 48px;
    font-size: 14px !important;
    text-align: center;
    border-radius: 50px !important;
    border: 0px;
    box-shadow: rgba(152, 22, 244, 0.19) 0px 5px 10px 2px;
  }
</style>

