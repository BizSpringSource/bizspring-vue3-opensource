<template>
  <div class="avue-contail" :class="{ 'avue--collapse': isCollapse }">
    <div class="avue-layout" :class="{ 'avue-layout--horizontal': isHorizontal }">
      <div v-show="validSidebar" class="avue-sidebar">
        <!-- 左侧导航栏 -->
        <logo />
        <sidebar />
      </div>
      <div class="avue-main">
        <!-- 顶部导航栏 -->
        <top ref="top" />
        <!-- 顶部标签卡 -->
        <tags />
        <transition name="fade-scale">
          <search v-show="isSearch" class="avue-view"></search>
        </transition>
        <!-- 主体视图层 -->
        <div v-show="!isSearch" v-if="isRefresh" id="avue-view" style="background-color:#FFFFFF; padding: 10px;">
          <router-view #="{ Component }">
            <keep-alive :include="$store.getters.tagsKeep">
              <component :is="Component" />
            </keep-alive>
          </router-view>
        </div>
        <div class="avue-footer">
<!--          <p class="copyright">© Powered by www.bizspring.cn</p>-->
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import { validatenull } from '@/util/validate'
import { mapGetters } from 'vuex'
import tags from './tags.vue'
import search from './search.vue'
import logo from './logo.vue'
import top from './top/index.vue'
import sidebar from './sidebar/index.vue'
import { getStore } from '@/util/store'
import admin from '@/util/admin'
import { checkToken } from '@/api/login'

export default {
  name: 'index',
  components: {
    top,
    logo,
    tags,
    search,
    sidebar
  },
  provide() {
    return {
      index: this
    }
  },
  data() {
    return {
      //刷新token锁
      refreshLock: false,
      //刷新token的时间
      refreshTime: ''
    }
  },
  computed: {
    ...mapGetters(['isHorizontal', 'isRefresh', 'isLock', 'isCollapse', 'isSearch', 'menu', 'expires_in']),
    validSidebar() {
      return !((this.$route.meta || {}).menu === false || (this.$route.query || {}).menu === 'false')
    }
  },
  created() {
    //实时检测刷新token
    this.refreshToken()
  },
  mounted() {
    this.init()
  },
  unmounted() {
    clearInterval(this.refreshTime)
    this.disconnect()
  },
  methods: {
    //打开菜单
    openMenu(item = {}) {
      this.$store.dispatch('GetMenu', item.id).then(data => {
        if (data.length !== 0) {
          this.$router.$avueRouter.formatRoutes(data, true)
        }
        //当点击顶部菜单做的事件
        if (!validatenull(item)) {
          let itemActive = {}
          const childItemActive = 0
          //vue-router路由
          if (item.path) {
            itemActive = item
          } else {
            if (this.menu[childItemActive].length === 0) {
              itemActive = this.menu[childItemActive]
            } else {
              itemActive = this.menu[childItemActive].children[childItemActive]
            }
          }
          this.$store.commit('SET_MENUID', item)
          this.$router.push({
            path: itemActive.path
          })
        }

      })
    }, showCollapse() {
      this.$store.commit('SET_COLLAPSE')
    },
    // 屏幕检测
    init() {
      this.$store.commit('SET_SCREEN', admin.getScreen())
      window.onresize = () => {
        setTimeout(() => {
          this.$store.commit('SET_SCREEN', admin.getScreen())
        }, 0)
      }
    },
    refreshToken() {
      this.refreshTime = setInterval(() => {
        checkToken(this.refreshLock, this.$store)
        const token = getStore({
          name: 'access_token',
          debug: true,
        })
        if (validatenull(token)) {
          return
        }
        if (this.expires_in <= 1000 && !this.refreshLock) {
          this.refreshLock = true
          this.$store.dispatch('RefreshToken').catch(() => {
            clearInterval(this.refreshTime)
          })
          this.refreshLock = false
        }
        this.$store.commit('SET_EXPIRES_IN', this.expires_in - 10)
      }, 10000)
    },
    disconnect() {
      if (this.stompClient != null) {
        this.stompClient.disconnect()
        // console.log('Disconnected')
      }
    }
  }
}
</script>
