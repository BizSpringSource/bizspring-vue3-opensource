<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <avue-form :option="option" v-model="form" @submit="handleSubmit">
      </avue-form>
    </basic-container>
  </div>
</template>

<script>
import { sendSms } from '@/api/upms/sms'

export default {
  name: 'sms',
  data() {
    return {
      form: {},
      option: {
        column: [
          {
            label: '签名名称',
            prop: 'signName',
            type: 'select',
            rules: [
              {
                required: true,
                message: '请选择签名名称',
                trigger: 'blur',
              },
            ],
            dicData: [
              {
                label: 'bizspring商城',
                value: 'bizspring商城',
              },
            ],
          },
          {
            label: '模板',
            prop: 'templateCode',
            type: 'select',
            rules: [
              {
                required: true,
                message: '请选择模板ID',
                trigger: 'blur',
              },
            ],
            dicData: [
              {
                label: '注册验证码',
                value: 'SMS_183247308',
              },
            ],
          },
          {
            label: '手机号码',
            prop: 'phoneNumbers',
            rules: [
              {
                required: true,
                message: '请输入手机号码',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '模板实际值',
            prop: 'templateParam',
            type: 'textarea',
            tip: '短信模板变量对应的实际值，JSON格式',
            value: '{"code":"66987"}',
          },
        ],
      },
    }
  },
  created() { },
  computed: {},
  methods: {
    handleSubmit(row, done, loading) {
      sendSms(row)
        .then((data) => {
          this.$message({
            showClose: true,
            message: '发送成功',
            type: 'success',
          })
          done()
        })
        .catch(() => {
          done()
        })
    },
  },
}
</script>
