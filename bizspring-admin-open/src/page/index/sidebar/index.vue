<template>
  <el-scrollbar class="avue-menu">
      <el-menu unique-opened :default-active="nowTagValue" mode="vertical" :show-timeout="2000" style="border-right: 0px;"
        :collapse="keyCollapse">
        <sidebar-item v-if="menu[topMenuIndex]" :menu="menu[topMenuIndex].children" :screen="screen" first
          :props="website.menu" :collapse="keyCollapse"></sidebar-item>
      </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import sidebarItem from './sidebarItem.vue'
export default {
  name: 'sidebar',
  components: { sidebarItem },
  data() {
    return {}
  },
  created() {
    this.$store.dispatch('GetMenu').then((data) => {
      if (data.length === 0) return
      this.$router.$avueRouter.formatRoutes(data, true)
    })
  },
  computed: {
    ...mapGetters([
      'website',
      'menu',
      'tag',
      'keyCollapse',
      'screen',
      'topMenuIndex',
    ]),
    nowTagValue: function () {
      return this.$router.$avueRouter.getValue(this.$route)
    },
  },

  mounted() { },
  methods: {},
}
</script>
