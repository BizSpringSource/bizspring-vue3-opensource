<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="108px">
      <el-col :span="1.5">
        <el-button type="primary" plain    @click="handleESInit" >搜索引擎ElasticSearch初始化
        </el-button>
      </el-col>
    </el-form>
  </div>
</template>

<script>
import {Edit as ElIconEdit} from '@element-plus/icons-vue'

import {
  init_search_data,
} from '@/api/goods/admin/goods'

import {mapGetters} from 'vuex'
import {shallowRef} from 'vue'

export default {
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Payment',
  components: {},
  created() {
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['upms:payment:add'] ? true : false,
        delBtn: this.permissions['upms:payment:del'] ? true : false,
        editBtn: this.permissions['upms:payment:edit'] ? true : false,
        viewBtn: this.permissions['upms:payment:get'] ? true : false,
      }
    },
  },
  methods: {
    handleESInit(){
      init_search_data().then((response) => {
        this.msgSuccess(
            '操作成功'
        )
      })
    }
  },
}
</script>
