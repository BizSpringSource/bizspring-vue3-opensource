<template>
  <router-view #="{ Component }">
    <keep-alive :include="this.$store.getters.tagsKeep">
      <component :is="Component" />
    </keep-alive>
  </router-view>
</template>


