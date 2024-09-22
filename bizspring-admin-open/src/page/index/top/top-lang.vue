<template>
  <el-dropdown trigger="click" @command="handleSetLanguage">
    <i class="icon-zhongyingwen"></i>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item :disabled="language === 'zh-cn'" command="zh-cn">中文</el-dropdown-item>
        <el-dropdown-item :disabled="language === 'en'" command="en">English</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'top-lang',
  data() {
    return {
    }
  },
  created() { },
  mounted() { },
  computed: {
    ...mapGetters(['language', 'tag'])
  },
  props: [],
  methods: {
    handleSetLanguage(lang) {
      this.$i18n.locale = lang
      this.$store.commit('SET_LANGUAGE', lang)
      const tag = this.tag
      const title = this.$router.$avueRouter.generateTitle(
        tag.label,
        tag.meta.i18n
      )
      console.log('``````````````````````` handleSetLanguage',this.tag);
      //根据当前的标签也获取label的值动态设置浏览器标题
      this.$router.$avueRouter.setTitle(title)
    }
  }
}
</script>

<style lang="scss" scoped></style>
