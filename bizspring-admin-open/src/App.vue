<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script>
export default {
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
