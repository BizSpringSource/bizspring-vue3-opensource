<template>
  <div class="menu-wrapper">
    <template v-for="item in menu">
      <el-menu-item v-if="validatenull(item[childrenKey]) && vaildRoles(item)" :index="item[pathKey]" @click="open(item)"
        :key="item[labelKey]" :class="{ 'is-active': vaildAvtive(item) }">
        <i :class="item[iconKey]"></i>
        <template #title>
         <span :alt="item[pathKey]">{{ item[labelKey] }}</span>
        </template>
      </el-menu-item>
      <el-sub-menu v-else-if="!validatenull(item[childrenKey]) && vaildRoles(item)" :index="item[pathKey]">
        <template #title>
          <i :class="item[iconKey]"></i>
         <span v-if="!collapse" >{{ item[labelKey] }}</span>
        </template>
        <template v-for="(child, cindex) in item[childrenKey]">
          <el-menu-item :index="child[pathKey], cindex" @click="open(child)" :class="{ 'is-active': vaildAvtive(child) }"
            v-if="validatenull(child[childrenKey])" :key="child[labelKey]">
            <i :class="child[iconKey]"></i>
           <span slot="title">{{ child[labelKey] }}</span>
          </el-menu-item>
          <sidebar-item v-else :menu="[child]" :key="cindex" :props="props" :screen="screen"
            :collapse="collapse"></sidebar-item>
        </template>
      </el-sub-menu>
    </template>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { validatenull } from '@/util/validate'
import config from './config'
export default {
  name: 'sidebarItem',
  data() {
    return {
      config: config,
    }
  },
  props: {
    menu: {
      type: Array,
    },
    screen: {
      type: Number,
    },
    first: {
      type: Boolean,
      default: false,
    },
    props: {
      type: Object,
      default: () => {
        return {}
      },
    },
    collapse: {
      type: Boolean,
    },
  },
  created() {
  },
  mounted() {
  },
  computed: {
    ...mapGetters(['roles']),
    labelKey() {
      return this.props.label || this.config.propsDefault.label
    },
    pathKey() {
      return this.props.path || this.config.propsDefault.path
    },
    iconKey() {
      return this.props.icon || this.config.propsDefault.icon
    },
    childrenKey() {
      return this.props.children || this.config.propsDefault.children
    },
    nowTagValue() {
      return this.$router.$avueRouter.getValue(this.$route)
    },
  },
  methods: {
    vaildAvtive(item) {
      const groupFlag = (item['group'] || []).some((ele) =>
        this.$route.path.includes(ele)
      )
      return this.nowTagValue === item[this.pathKey] || groupFlag
    },
    vaildRoles(item) {
      item.meta = item.meta || {}
      return item.meta.roles ? item.meta.roles.includes(this.roles) : true
    },
    validatenull(val) {

      return validatenull(val)
    },
    open(item) {
      if (this.screen <= 1) {
        this.$store.commit('SET_COLLAPSE')
      }
      this.$router.$avueRouter.group = item.group
      this.$router.push({
        path: this.$router.$avueRouter.getPath({
          name:item['i18nCode'],
          src: item[this.pathKey],
        }),
        query: item.query,
      })
    },
  },
}
</script>
