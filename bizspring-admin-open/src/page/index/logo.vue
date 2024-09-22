<template>
  <div class="avue-logo">
    <transition name="fade">
      <span v-if="getScreen(isCollapse)" key="0" class="avue-logo_subtitle">
        <img class="top-bar__img" :src="userInfo.avatar ? baseUrl + userInfo.avatar : '/img/head.png'">
      </span>
    </transition>
    <transition-group name="fade">
      <template v-if="getScreen(!isCollapse)">
        <span key="1" class="avue-logo_title"> 
          <img class="top-bar__img"
            :src="userInfo.avatar ? baseUrl + userInfo.avatar : '/img/head.png'">{{ website.indexTitle }} </span>
      </template>
    </transition-group>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'logo',
  computed: {
    ...mapGetters(['isCollapse', 'userInfo'])
  }
}
</script>
