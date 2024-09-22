<template>
  <div>
    <span v-if="text" @click="handleLock">{{ text }}</span>
    <i v-else class="icon-lock" @click="handleLock"></i>
    <el-dialog v-model="box" title="设置锁屏密码" width="30%" append-to-body>
      <el-form ref="form" :model="form" label-width="80px"   @keyup.enter="handleSetLock">
        <el-form-item label="锁屏密码" prop="passwd" :rules="[{ required: true, message: '锁屏密码不能为空' }]">
          <el-input v-model="form.passwd" placeholder="请输入锁屏密码" >
            <template #append>
              <el-button icon="el-icon-lock" @click="handleSetLock"></el-button>
            </template>
          </el-input>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { validatenull } from '@/util/validate'
import { mapGetters } from 'vuex'

export default {
  name: 'top-lock',
  data() {
    return {
      box: false,
      form: {
        passwd: ''
      }
    }
  },
  computed: {
    ...mapGetters(['lockPasswd'])
  },
  props: {
    text: {
      type: String,
      default: null
    }
  },
  methods: {
    handleSetLock() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.$store.commit('SET_LOCK_PASSWD', this.form.passwd)
          this.handleLock()
        }
      })
    },
    handleLock() {
      if (validatenull(this.lockPasswd)) {
        this.box = true
        return
      }
      this.$store.commit('SET_LOCK')
      setTimeout(() => {
        this.$router.push({ path: '/lock' })
      }, 100)
    }
  }
}
</script>

<style lang="scss" scoped></style>
