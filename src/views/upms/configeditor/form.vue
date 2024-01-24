<template>
  <div class="execution">
    <basic-container>
      <el-card class="box-card">
        <template v-slot:header>
          <div class="clearfix">
            <span>文本编辑器配置</span>
          </div>
        </template>
        <avue-form ref="form" :modelValue="form" :option="tableOption" @submit="handleUpdate">
        </avue-form>
      </el-card>
    </basic-container>
  </div>
</template>

<script>
import {
  getPage,
  getObj,
  addObj,
  putObj,
  delObj,
  getObj2,
} from '@/api/upms/configeditor'
import { tableOption } from '@/const/crud/upms/configeditor'
import { mapGetters } from 'vuex'

export default {
  name: 'configeditor',
  data() {
    return {
      form: {},
      tableOption: tableOption,
    }
  },
  created() {
    this.handleGet()
  },
  mounted: function () { },
  computed: {
    ...mapGetters(['permissions']),
  },
  methods: {
    handleGet: function () {
      getObj2().then((response) => {
        this.form = response.data.data ? response.data.data : {}
      })
    },
    /**
     * @title 数据更新
     * @param row 为当前的数据
     * @param index 为当前更新数据的行数
     * @param done 为表单关闭函数
     *
     **/
    handleUpdate(form, done) {
      putObj(this.form)
        .then((response) => {
          done()
          this.form = response.data.data
          this.setRules()
          this.$message({
            showClose: true,
            message: '修改成功',
            type: 'success',
          })
        })
        .catch(() => {
          done()
        })
    },
  },
}
</script>
