<template>
  <div v-if="setting.tag" class="avue-tags" @click="contextmenuFlag = false">
    <!-- tag盒子 -->
    <div v-if="contextmenuFlag" class="avue-tags__contentmenu"
      :style="{ left: contentmenuX + 'px', top: contentmenuY + 'px' }">
      <div class="item" @click="closeOthersTags">关闭其它</div>
      <div class="item" @click="closeAllTags">关闭所有</div>
    </div>
    <div class="avue-tags__box" :class="{ 'avue-tags__box--close': !website.isFirstPage }">
      <el-tabs v-model="active" type="card" :closable="tagLen !== 1" @contextmenu="handleContextmenu" @tab-click="openTag"
        @edit="menuTag">
        <el-tab-pane v-for="item in tagList" :key="item.value" :label="generateTitle(item)" :name="item.value">
          <template #label>
            <span>
              {{ generateTitle(item) }}
              <i v-if="active == item.value" class="el-icon-refresh" :class="{ 'turn': refresh }"
                @click="handleRefresh"></i>
            </span>
          </template>
        </el-tab-pane>
      </el-tabs>
      <el-dropdown class="avue-tags__menu">
        <el-button type="primary">
          更多
          <i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="openSearch">搜索</el-dropdown-item>
            <el-dropdown-item @click="closeOthersTags">关闭其它</el-dropdown-item>
            <el-dropdown-item @click="closeAllTags">关闭所有</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import website from '@/config/website'
export default {
  name: 'tags',
  data() {
    return {
      refresh: false,
      active: '',
      contentmenuX: '',
      contentmenuY: '',
      contextmenuFlag: false
    }
  },
  watch: {
    tag: {
      handler(val) {
        this.active = val.value
      },
      immediate: true
    },
    contextmenuFlag() {
      window.addEventListener('mousedown', this.watchContextmenu)
    }
  },
  computed: {
    ...mapGetters(['tagWel', 'tagList', 'tag', 'setting']),
    tagLen() {
      return this.tagList.length || 0
    }
  },
  methods: {
    openSearch() {
      this.$store.commit('SET_IS_SEARCH', true)
    },
    handleRefresh() {
      this.refresh = true
      this.$store.commit('SET_IS_REFRESH', false)
      setTimeout(() => {
        this.$store.commit('SET_IS_REFRESH', true)
      }, 100)
      setTimeout(() => {
        this.refresh = false
      }, 500)
    },
    generateTitle(item) {
      return this.$router.$avueRouter.generateTitle(item.label, item.meta.i18n)
    },
    watchContextmenu(event) {
      if (!this.$el.contains(event.target) || event.button !== 0) {
        this.contextmenuFlag = false
      }
      window.removeEventListener('mousedown', this.watchContextmenu)
    },
    handleContextmenu(event) {
      let target = event.target
      let flag = false
      if (target.className.indexOf('el-tabs__item') > -1) flag = true
      else if (target.parentNode.className.indexOf('el-tabs__item') > -1) {
        target = target.parentNode
        flag = true
      }
      if (flag) {
        event.preventDefault()
        event.stopPropagation()
        this.contentmenuX = event.clientX
        this.contentmenuY = event.clientY
        this.tagName = target.getAttribute('aria-controls').slice(5)
        this.contextmenuFlag = true
      }
    },
    menuTag(value, action) {
      if (action === 'remove') {
        let openTag // 最终要打开的页面
        const { tag, key } = this.findTag(value)
        const tagWel = website.fistPage
        if (tag.value == tagWel.value)
          return;
        if (tag.value === this.tag.value) {
          openTag = this.tagList[key === 0 ? key : key - 1] //如果关闭本标签让前推一个
        } else {
          openTag = this.tag
          this.openTag(tag)
        }
        this.$store.commit('DEL_TAG', tag)
        this.openTag(openTag)
      }
    },
    openTag(item) {
      if (item.props) item = item.props
      let tag
      if (item.name) {
        tag = this.findTag(item.name).tag
      } else {
        tag = item
      }
      this.$router.push({
        path: tag.value,
        query: tag.query
      })
    },
    findTag(value) {
      let tag, key
      this.tagList.map((item, index) => {
        if (item.value === value) {
          tag = item
          key = index
        }
      })
      return { tag: tag, key: key }
    },
    // 因需清除每个keep-alive页面的缓存，需一个一个的激活tag到前台做删除
    activeTag(tagList) {
      tagList.forEach(item => {
        this.openTag(item)
        this.$store.commit('DEL_TAG', item)
      })
    },
    closeOthersTags() {
      this.contextmenuFlag = false
      const tagList = this.tagList.filter(item =>
        item.value !== this.tag.value && item.value !== this.tagWel.value
      )
      this.activeTag(tagList)
      this.$router.push({
        path: this.tagWel.value,
        query: this.tagWel.query
      })
    },
    closeAllTags() {
      this.contextmenuFlag = false
      this.activeTag(this.tagList)
      this.$router.push({
        path: this.tagWel.value,
        query: this.tagWel.query
      })
    }
  }
}
</script>


