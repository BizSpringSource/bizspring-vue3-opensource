<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="128px"  >
      <el-form-item label="编号" prop="sn">
        <el-input maxlength="32" v-model="queryParams.sn" placeholder="请输入编号"
          clearable @keyup.enter="handleQuery"  style="width: 194px;" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input maxlength="50" v-model="queryParams.name"
          placeholder="请输入名称" clearable @keyup.enter="handleQuery" style="width: 194px;" />
      </el-form-item>
      <el-form-item label="商品分类"  prop="goodsCategoryId">
        <el-tree-select v-model="queryParams.goodsCategoryId"   :data="goodsCategoryTree" check-strictly
          :render-after-expand="false" :props="{ value: 'id', label: 'name' }"
          placeholder="商品分类" clearable/>
      </el-form-item>
      <el-form-item label="店铺" prop="storeId">
        <el-autocomplete v-model="storeSearch" :fetch-suggestions="querySearch" placeholder="请搜索"
          @select="handleSelect" @change="handleSelect"  :trigger-on-focus="false"  style="width: 194px;"></el-autocomplete>
      </el-form-item>
      <el-form-item label="关键词" prop="keyword">
        <el-input maxlength="30" v-model="queryParams.keyword"
          placeholder="请输入关键词" clearable @keyup.enter="handleQuery"  style="width: 194px;"/>
      </el-form-item>
      <el-form-item label="品牌" prop="brandId">
        <el-select v-model="queryParams.brandId" placeholder="请选择品牌"
          clearable>
          <el-option v-for="item in brands" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="ElIconSearch" @click="handleQuery">
          搜索
        </el-button>
        <el-button :icon="ElIconRefresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="goodsList"  :header-cell-style="{
      background: '#f8f8f9', height: '50px', color: '#606266'
    }" :rowStyle="{ height: '50px' }">
      <el-table-column label="编号" align="center" prop="sn" />
      <el-table-column label="名称" align="center" prop="name"  show-overflow-tooltip/>
      <el-table-column label="商品分类" align="center" prop="goodsCategoryName" />
      <el-table-column label="销售价" align="center" prop="price" />
      <el-table-column label="店铺" align="center" prop="storeName" />
      <el-table-column label="创建日期" align="center" prop="createdTime" width="180">
        <template v-slot="scope">
          <span>{{
            parseTime(scope.row.createdTime, '{y}-{m}-{d} {h}:{i}:{s}')
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import {
  Search as ElIconSearch,
  Refresh as ElIconRefresh,
  Edit as ElIconEdit,
  Delete as ElIconDelete,
} from '@element-plus/icons-vue'
import {
  listGoods,
  delGoods,
} from '@/api/goods/admin/goods'
import { storeSelect } from '@/api/store/admin/store'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品表格数据
      goodsList: [],
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        sn: null,
        name: null,
        goodsCategoryId: null,
        storeId: null,
        createdTime: null,
        keyword: null,
        brandId: null,
      },
      goodsCategoryTree: [],
      brands: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      storeSearch: '',
      ElIconSearch: shallowRef(ElIconSearch),
      ElIconRefresh: shallowRef(ElIconRefresh),
      ElIconDelete: shallowRef(ElIconDelete),
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Goods',
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['goods:goods:add'] ? true : false,
        delBtn: this.permissions['goods:goods:del'] ? true : false,
        editBtn: this.permissions['goods:goods:edit'] ? true : false,
        viewBtn: this.permissions['goods:goods:get'] ? true : false,
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {}
    },
    /** 查询商品列表 */
    getList() {
      this.loading = true
      listGoods(this.queryParams).then((response) => {
        this.loading = false
        this.goodsList = response.data.data.data.content
        this.goodsCategoryTree = response.data.data.goodsCategoryTree
        this.brands = response.data.data.brands
        this.total = response.data.data.data.total
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNumber = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      if (this.$refs['queryForm']) {
        this.$refs['queryForm'].resetFields()
      }
      this.storeSearch = null
      this.handleQuery()
    },
    handleSelect(item) {
      console.log("item:",item);
      this.queryParams.storeId=item.id;
      this.storeSearch=item.value;
    },
    // 多选框选中数据
    querySearch(queryString, callback) {
      var list = [{}]
      if (queryString && queryString.length > 0) {
        let queryParams = {
          keyword: queryString,
          count: 999,
        }
        storeSelect({
          keyword: queryString,
          count: 999,
        }).then((response) => {
          var list = response.data.data
          // 如果list.length等于0，则代表没有匹配到结果。手动给list添加一条提示信息
          if (list.length == '0') {
            list.push({
              id: '-1',
              value: '无匹配结果',
            })
            // 调用 callback 返回建议列表的数据
            callback(list)
          } else {
            list = list.map((item) => {
              return {
                value: `${item.name}`,
                id: `${item.id}`,
              }
            })
            // 调用 callback 返回建议列表的数据
            callback(list)
          }
        })
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除商品数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delGoods(ids)
        })
        .then(() => {
          this.getList()
          this.msgSuccess(
            '删除成功'
          )
        })
        .catch(() => { })
    },
  },
}
</script>

<style scoped>
.el-form-item {
  margin-bottom: 5px;
}

.el-form-item__content {
  line-height: 32px;
  position: relative;
  font-size: 14px;
}


:deep(.el-select .el-input) {
  width: 195px;
  display: flex;
}


.el-input__inner {
  -webkit-appearance: none;
  appearance: none;
  background-color: #fff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 32px;
  line-height: 32px;
  outline: 0;
  padding: 0 15px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;

}
</style>
