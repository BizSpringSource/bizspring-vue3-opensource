<template>
  <span class="setting">
    <div class="setting__shade" :class="{ 'setting__shade--show': isShade }" @click="close"></div>
    <el-icon class="setting__icon"><el-icon-more @click="open" /></el-icon>
    <div class="setting__content" :class="{ 'setting__content--show': box }">
      <div class="setting__header">系统信息</div>
      <div class="setting__body setting__about">
        <p>BizSpring电商平台</p>
        <p>当前版本：{{ version }}</p>
        <a href="http://www.maowei.co/" target="_black">
          <el-button type="primary"> 查看详情 </el-button>
        </a>
      </div>
      <div class="setting__header">
        设置
        <small>(滑动鼠标下面还有更多设置)</small>
      </div>
      <el-scrollbar>
        <div class="setting__body setting__form">
          <el-switch v-model="light"   active-text="中文"    inactive-text="English" />
        </div>
      </el-scrollbar>
    </div>
  </span>
</template>

<script>
import {Sunny, Moon} from '@element-plus/icons-vue'
import { mapState, mapGetters } from 'vuex'
import { validatenull } from '@/util/validate'
import { option, list } from '@/const/setting/'
import config from '../../../../package.json'
import { shallowRef } from 'vue'

export default {
  data() {
    return {
      box: false,
      form: {},
      list: list,
      option: option(this),
      version: config.version,
      Sunny:shallowRef(Sunny),
      Moon:shallowRef(Moon),
      light:true
    }
  },
  computed: {
    ...mapGetters(['isShade']),
    ...mapState({
      showTag: (state) => state.common.showTag,
      showDebug: (state) => state.common.showDebug,
      showLock: (state) => state.common.showLock,
      showColor: (state) => state.common.showColor,
      showFullScren: (state) => state.common.showFullScren,
      showCollapse: (state) => state.common.showCollapse,
      showSearch: (state) => state.common.showSearch,
      showMenu: (state) => state.common.showMenu,
      showTheme: (state) => state.common.showTheme,
    }),
  },
  created() {
    setTimeout(() => {
      this.init()
    }, 0)
  },
  methods: {
    close() {
      this.box = false
      this.$store.commit('SET_SHADE', false)
    },
    set(key) {
      const ele = this.find(key)
      this.$store.commit(ele.commit, eval(this.form[ele.key]))
    },
    find(key) {
      return this.list.filter((ele) => ele.key === key)[0]
    },
    init() {
      this.list.forEach((ele) => {
        this.form[ele.key] = validatenull(this[ele.key])
          ? 'true'
          : this[ele.key] + ''
        this.set(ele.key)
      })
    },
    open() {
      this.box = true
      this.$store.commit('SET_SHADE', true)
    },
  },
}
</script>

<style lang="scss" scoped>
.setting {
  margin-left: 10px;

  &__icon {
    transform: rotate(90deg);
  }

  &__header {
    height: 42px;
    line-height: 42px;
    padding: 0 15px;
    border-bottom: 1px solid #f6f6f6;
    color: #333;
    border-radius: 2px 2px 0 0;
    font-size: 14px;

    small {
      margin-left: 8px;
      color: #999;
    }
  }

  &__body {
    padding: 10px 15px;
    line-height: 24px;
  }

  &__about {
    font-size: 14px;
    line-height: 30px;
  }

  &__shade {
    position: fixed;
    display: none;
    width: 100%;
    height: 100%;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.3);
    z-index: 2048;

    &--show {
      display: block;
    }
  }

  &__form {
    width: 230px;
    margin: 0 auto;
  }

  &__content {
    box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.1);
    transition: all 0.3s;
    position: fixed;
    width: 320px;
    height: 100%;
    right: -450px;
    top: 0;
    z-index: 2048;
    background-color: #fff;

    &--show {
      right: 0;
    }
  }
}
</style>
