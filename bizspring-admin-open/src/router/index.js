import { createRouter, createWebHashHistory } from 'vue-router'
import PageRouter from './page/'
import ViewsRouter from './views/'
import AvueRouter from './avue-router'
//18N
// import i18n from '@/lang'
import Store from '@/store/'
//创建路由
const Router = createRouter({
  history: createWebHashHistory(),
  routes: [...PageRouter, ...ViewsRouter]
})
AvueRouter.install({
  store: Store,
  router: Router,
})

Router.$avueRouter.formatRoutes(Store.getters.menuAll, true)

export default Router
