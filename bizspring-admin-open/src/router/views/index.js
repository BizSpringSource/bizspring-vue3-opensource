import Layout from '@/page/index/index.vue'
export default [
  {
  path: '/wel',
  component: () => import('@/page/index/index.vue'),
  redirect: '/wel/index',
  children: [{
    path: 'index',
    name: '首页',
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
    // name: '个人信息',
    name:'个人信息',
    component: () =>
      import(/* webpackChunkName: "page" */ '@/views/upms/user/info.vue')
  }]
}]
