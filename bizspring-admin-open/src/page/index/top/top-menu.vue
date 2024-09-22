<template>
  <div class="top-menu">
    <el-menu :default-active="topMenuIndex + ''" style="border-bottom: none" mode="horizontal" :active-text-color="theme">
      <template v-for="(item, index) in menu" :key="index">
        <el-menu-item :index="index + ''" @click="openMenu(index)">
          <template v-slot:title>
            <i :class="item.icon"></i>
           <span>{{ item.label }}</span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'top-menu',
  data() {
    return {
    }
  },
  created() { 
    this.getUserInfo();
  },
  computed: {
    ...mapGetters(['tagCurrent', 'menu', 'theme', 'topMenuIndex']),
  },
  methods: {
    openMenu(index) {
      this.$store.commit('SET_TOP_MENU_INDEX', index)
    },
    getUserInfo() {
      // 更新sessionStore 权限信息
      this.$store.dispatch('GetUserInfo')
    }
  },
}
</script>
