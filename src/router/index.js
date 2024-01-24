/**
 * Copyright (C) 2021-2023
 * All rights reserved, Designed By www.bizspring.cn
 * 注意：
 * 本软件为www.bizspring.cn开发研制
 * 如需技术支持，请联系（微信电话同号）：16619915737
 */
import { createRouter, createWebHashHistory } from 'vue-router'
import PageRouter from './page/'
import ViewsRouter from './views/'
import AvueRouter from './avue-router'
import i18n from '@/lang'
import Store from '@/store/'
//创建路由
const Router = createRouter({
  history: createWebHashHistory(),
  routes: [...PageRouter, ...ViewsRouter]
})
AvueRouter.install({
  store: Store,
  router: Router,
  i18n: i18n
})

Router.$avueRouter.formatRoutes(Store.getters.menuAll, true)

export default Router
