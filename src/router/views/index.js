/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import Layout from '@/page/index/index.vue'
export default [
  {
  path: '/wel',
  component: () => import('@/page/index/index.vue'),
  redirect: '/wel/index',
  children: [{
    path: 'index',
    name: '首页',
    meta: {
      i18n: 'dashboard'
    },
    component: () =>
      import(/* webpackChunkName: "views" */ '@/page/wel.vue')
  }]
}, {
  path: '/iframe',
  component:Layout,
  redirect: '/iframe',
  children: [{
    path: '',
    name: '',
    component: () =>
      import(/* webpackChunkName: "views" */ '@/components/Iframe/main.vue')
  }]
}, {
  path: '/info',
  component: Layout,
  redirect: '/info/index',
  children: [{
    path: 'index',
    name: '个人信息',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/upms/user/info.vue')
  }]
}]
