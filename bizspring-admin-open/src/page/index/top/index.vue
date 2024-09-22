<template>
  <div class="avue-top">
    <div class="top-bar__left">
      <div v-if="setting.collapse && !isHorizontal" class="avue-breadcrumb"
        :class="[{ 'avue-breadcrumb--active': isCollapse }]">
        <i class="icon-navicon" @click="setCollapse"></i>
      </div>
    </div>
    <div class="top-bar__title">
      <div class="top-bar__item--show" style="height: 100%; min-width: 600px">
        <top-menu></top-menu>
      </div>


    </div>
    <div class="top-bar__right">
      <div class="top-bar__item" style="display: flex;align-items: center;">
      </div>

      <el-tooltip effect="dark" content="主题色" placement="bottom">
        <div class="top-bar__item">
          <top-color></top-color>
        </div>
      </el-tooltip>
      <el-tooltip effect="dark" content="锁屏" placement="bottom">
        <div v-if="setting.lock" class="top-bar__item">
          <top-lock></top-lock>
        </div>
      </el-tooltip>
      <el-tooltip effect="dark"  :content="isFullScren?'退出全屏':'全屏'" placement="bottom">
        <div v-if="setting.fullscren" class="top-bar__item">
          <top-full></top-full>
        </div>
      </el-tooltip>
      <el-tooltip effect="dark" content="日志" placement="bottom">
        <div v-if="setting.debug" class="top-bar__item">
          <top-logs></top-logs>
        </div>
      </el-tooltip>
      <div class="top-user">
        <img class="top-bar__img" :src="userInfo.avatar ? baseUrl + userInfo.avatar : '/img/head.png'">
        <el-dropdown>
          <span class="el-dropdown-link">
            <span class="current-user"> {{ userInfo.username }} </span>
            <el-icon>
              <ArrowDownBold />
            </el-icon>
          </span>
          <template #dropdown >
            <el-dropdown-menu>
              <el-dropdown-item>
                <router-link to="/wel/index">首页</router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link to="/info/index">个人信息</router-link>
              </el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>


      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapState } from 'vuex'
import topLock from './top-lock.vue'
import topMenu from './top-menu.vue'
import topLogs from './top-logs.vue'
import topFull from './top-full.vue'
import topColor from './top-color.vue'
import topSetting from './top-setting.vue'

export default {
  name: 'top',
  components: {
    topLock,
    topMenu,
    topLogs,
    topFull,
    topColor,
    topSetting
  }, data() {
    return {
      langName: '',
    }
  },
  computed: {
    ...mapState({
      showColor: (state) => state.common.showColor,
      showTheme: (state) => state.common.showTheme,
      showLock: (state) => state.common.showLock,
      showFullScren: (state) => state.common.showFullScren,
      showCollapse: (state) => state.common.showCollapse,
      showSearch: (state) => state.common.showSearch,
      showMenu: (state) => state.common.showMenu,
    }),
    ...mapGetters([
      'setting',
      'userInfo',
      'tagWel',
      'tagList',
      'isCollapse',
      'tag',
      'logsLen',
      'logsFlag',
      'isHorizontal',
      'language',
      'i18n',
      'isFullScren'
    ])
  },
  mounted() {
    if (this.language === 'zh-cn') {
      this.langName = '中文';
    } else {
      this.langName = 'English';
    }
  },
  methods: {
    handleScreen() {
      fullscreenToggel()
    },
    setCollapse() {
      this.$store.commit('SET_COLLAPSE')
    },
    logout() {
      this.$confirm('退出登录', '提示', {
        confirmButtonText: '确 定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          this.$router.push({ path: '/login' })
        })
      })
    },

  }
}
</script>

<style lang="scss" scoped></style>
