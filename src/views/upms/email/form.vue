<template>
  <div class="app-container calendar-list-container">
    <basic-container>
      <avue-form :option="option" :modelValue="form" @submit="handleSubmit">
        <template v-slot:content="scope">
          <BaseEditor v-model="form.content" :height="300" />
        </template>
      </avue-form>
    </basic-container>
  </div>
</template>

<script>
import { sendEmail } from '@/api/upms/email'
import BaseEditor from '@/components/editors/index.vue'

export default {
  name: 'email',
  components: {
    BaseEditor,
  },
  data() {
    return {
      form: {},
      option: {
        column: [
          {
            label: '发送到',
            prop: 'to',
            rules: [
              {
                required: true,
                message: '请输入目标邮箱',
                trigger: 'blur',
              },
              {
                type: 'email',
                message: '请输入正确的邮箱地址',
                trigger: 'blur',
              },
            ],
          },
          {
            label: '主题',
            prop: 'title',
          },
          {
            label: '内容',
            prop: 'content',
            formslot: true,
            span: 24,
          },

        ],
      },
    }
  },
  created() { },
  computed: {},
  methods: {
    handleSubmit(row, done, loading) {
      sendEmail(row)
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
