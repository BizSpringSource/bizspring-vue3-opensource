<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script>
import { messages } from '@/lang/'
export default {
  computed: {
    locale() {
      const languageType = this.$store.getters.language
      return messages[languageType]
    }
  }
} 
</script>

<style>
html,
body,
#app {
  width: 100%;
  height: 100%;
}
</style>
