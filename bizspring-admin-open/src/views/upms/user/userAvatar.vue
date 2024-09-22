<template>
  <div>
    <div class="user-info-head" @click="editCropper()">
      <img
        v-bind:src="user.avatar"
        title="点击上传头像"
        class="img-circle img-lg"
      />
    </div>

    <el-dialog
      :title="title"
      v-model="open"
      width="800px"
      append-to-body
      @opened="modalOpened"
      @close="closeDialog()"
    >
      <el-row>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{ height: '350px' }">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload
            action="#"
            :http-request="requestUpload"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button  >
              选择
              <el-icon class="el-icon--right"><el-icon-upload /></el-icon>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{ span: 1, offset: 2 }" :md="2">
          <el-button
            :icon="ElIconPlus"
             
            @click="changeScale(1)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            :icon="ElIconMinus"
             
            @click="changeScale(-1)"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            :icon="ElIconRefreshLeft"
             
            @click="rotateLeft()"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 1, offset: 1 }" :md="2">
          <el-button
            :icon="ElIconRefreshRight"
             
            @click="rotateRight()"
          ></el-button>
        </el-col>
        <el-col :lg="{ span: 2, offset: 6 }" :md="2">
          <el-button type="primary"   @click="uploadImg()"
            >确定</el-button
          >
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {
  Upload as ElIconUpload,
  Plus as ElIconPlus,
  Minus as ElIconMinus,
  RefreshLeft as ElIconRefreshLeft,
  RefreshRight as ElIconRefreshRight,
} from '@element-plus/icons-vue'
import store from '@/store'
import { VueCropper } from 'vue-cropper'
import { uploadAvatar } from '@/api/upms/user'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 是否显示cropper
      visible: false,
      // 弹出层标题
      title: '修改头像',
      options: {
        img: store.getters.avatar, //裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 200, // 默认生成截图框宽度
        autoCropHeight: 200, // 默认生成截图框高度
        fixedBox: true, // 固定截图框大小 不允许改变
      },
      previews: {},
      ElIconPlus:shallowRef(ElIconPlus),
      ElIconMinus:shallowRef(ElIconMinus),
      ElIconRefreshLeft:shallowRef(ElIconRefreshLeft),
      ElIconRefreshRight:shallowRef(ElIconRefreshRight),
    }
  },
  components: {
    VueCropper,
    ElIconUpload,
  },
  props: {
    user: {
      type: Object,
    },
  },
  created() {
    console.log('father --> user:', this.user)
  },
  methods: {
    // 编辑头像
    editCropper() {
      this.open = true
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft()
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight()
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1
      this.$refs.cropper.changeScale(num)
    },
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf('image/') == -1) {
        this.msgError('文件格式错误，请上传图片类型,如：JPG，PNG文件')
      } else {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.options.img = reader.result
        }
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob((data) => {
        console.log('imgdata:', data)
        let formData = new FormData()
        formData.append('file', data)
        uploadAvatar(formData).then((response) => {
          console.log('uploadAvatarResault:', response)
          this.open = false
          this.options.img = response.data.link
          this.msgSuccess('更新成功')
          this.$emit('setAvatar', response.data.link)
          this.visible = false
        })
      })
    },
    // 实时预览
    realTime(data) {
      this.previews = data
    },
    // 关闭窗口
    closeDialog() {
      this.options.img = store.getters.avatar
      this.visible = false
    },
  },
  emits: ['setAvatar'],
}
</script>

<style lang="scss" scoped>
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}
.user-info-head:hover:after {
  content: '+';
  text-align: center;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}
.img-circle {
  border-radius: 50%;
}
.img-lg {
  width: 120px;
  height: 120px;
}
</style>
