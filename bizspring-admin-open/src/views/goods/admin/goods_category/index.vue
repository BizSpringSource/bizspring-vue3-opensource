<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain :icon="ElIconPlus"   @click="handleAdd">添加
        </el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
      <el-col  :span="6">
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-col>
    </el-row>

    <el-table v-loading="loading" row-key="id" lazy :load="loadChildrens"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="goods_categoryList"
      @selection-change="handleSelectionChange" :header-cell-style="{ 
      background:'#f8f8f9',height:'50px',color:'#606266'}" :rowStyle="{ height:'50px'}">
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-link type="primary" :icon="ElIconEdit" @click="handleUpdate(scope.row)">编辑</el-link>
          <el-link type="primary" :icon="ElIconDelete" @click="handleDelete(scope.row)">删除</el-link>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNumber"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改商品分类对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body :v-loading="loading">
      <el-form ref="form" :model="form" :rules="rules" label-width="270px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" maxlength="50" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="所有上级分类ID">
          <el-tree-select class="item-class" v-model="form.parentId" :data="parentIdOptions" check-strictly
            :render-after-expand="false" :props="{ value: 'id', label: 'name' }"
            placeholder="请选择绑定分类" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="分类图片">
          <imageUpload v-model="form.image" :uploadImgUrl="uploadUrl" v-on:handleImagesUrl="setImagesUrl" />
        </el-form-item>
        <el-form-item label="页面标题" prop="seoTitle">
          <el-input maxlength="30" v-model="form.seoTitle" placeholder="请输入页面标题" />
        </el-form-item>
        <el-form-item label="关联品牌" prop="brandId">
          <el-select class="item-class" v-model="form.brands" multiple filterable allow-create default-first-option
            placeholder="请选择关联品牌">
            <el-option v-for="item in brandIdOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="页面关键词" prop="seoKeywords">
          <el-input v-model="form.seoKeywords" maxlength="30" placeholder="请输入页面关键词" />
        </el-form-item>
        <el-form-item label="页面描述" prop="seoDescription">
          <el-input v-model="form.seoDescription" maxlength="100" placeholder="请输入页面描述" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model.number="form.sort" maxlength="3" placeholder="请输入排序" />
        </el-form-item>
      </el-form>
      <template v-slot:footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {
  Plus as ElIconPlus,
  Edit as ElIconEdit,
  Delete as ElIconDelete,
} from '@element-plus/icons-vue'
import {
  listGoods_category,
  delGoods_category,
  addGoods_category,
  saveGoods_category,
  editGoods_category,
  updateGoods_category,
} from '@/api/goods/admin/goods_category'
import ImageUpload from '@/components/ImageUpload/index.vue'
import { mapGetters } from 'vuex'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品分类表格数据
      goods_categoryList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNumber: 1,
        pageSize: 10,
        sortProperty:'sort',  //排序字段
        sortDirection:'ASC' 	// * 递增  ASC, 递减 DESC
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      parentIdOptions: [],
      brandIdOptions: [],
      uploadUrl: '/upms/file/upload?fileType=image&path=admin-store-store',
      ElIconPlus: shallowRef(ElIconPlus),
      ElIconDelete: shallowRef(ElIconDelete),
      ElIconEdit: shallowRef(ElIconEdit),
    }
  },
  name: 'Goods_category',
  components: {
    ImageUpload,
  },
  created() {
    this.setRules()
    this.getList()
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.permissions['goods:goods_category:add'] ? true : false,
        delBtn: this.permissions['goods:goods_category:del'] ? true : false,
        editBtn: this.permissions['goods:goods_category:edit']
          ? true
          : false,
        viewBtn: this.permissions['goods:goods_category:get']
          ? true
          : false,
      }
    },
  },
  methods: {
    setRules() {
      this.rules = {
        name: [
          {
            required: true,
            message:'名称不能为空',
            trigger: 'blur',
          },
        ],
        treePath: [
          {
            required: true,
            message:'树路径不能为空',
            trigger: 'blur',
          },
        ],
        sort: [
          {
            type: 'number',
            message:'排序只允许输入数字',
            trigger: ['blur', 'change'],
          },
        ],
      }
    },
    /** 查询商品分类列表 */
    getList() {
      this.loading = true
      listGoods_category(this.queryParams)
        .then((response) => {
          this.loading = false
          this.goods_categoryList = response.data.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    loadChildrens(tree, treeNode, resolve) {
      resolve(tree.children)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()      
    },
    // 表单重置
    reset() {
      this.form = {
        name: null,
        id: null,
        parentId: null,
        image: null,
        seoTitle: null,
        seoKeywords: null,
        seoDescription: null,
        sort: null,
        treePath: null,
        brands: [],
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.loading = true;
      this.reset()
      addGoods_category().then((response) => {
        this.loading = false;
        console.log('addGoods_category', response)
        //add  返回数据
        this.parentIdOptions = response.data.data.goodsCategoryTree;
        this.brandIdOptions = response.data.data.brands;
        this.open = true;
        this.title ='添加商品分类';
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset()
      const id = row.id
      editGoods_category(id).then((response) => {
        this.loading = false;
        this.setRules()
        console.log('editGoods_category', response)
        this.parentIdOptions = response.data.data.goodsCategoryTree;
        this.brandIdOptions = response.data.data.brands;
        this.form = response.data.data.data;
        this.open = true;
        this.title ='编辑商品分类';
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.open = false
          this.loading = true
          if (this.form.id != null) {
            updateGoods_category(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  '编辑成功'
                )
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            saveGoods_category(this.form)
              .then((response) => {
                this.loading = false
                this.msgSuccess(
                  '添加成功'
                )
                this.getList()
              })
              .finally(() => {
                this.loading = false
              })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm(
        '是否确认删除商品分类数据?',
        '警告',
        {
          confirmButtonText: '确 定',
          cancelButtonText: '取 消',
          type: 'warning',
        }
      )
        .then(function () {
          return delGoods_category(ids)
        })
        .then(() => {
          this.getList()
          this.msgSuccess(
            '删除成功'
          )
        })
        .catch(() => { })
    },
    setImagesUrl(url) {
      this.form.image = url
    },
  },
}
</script>
<style scoped>
.item-class {
  width: 360px
}
</style>