/**
 * 全站权限配置
 *
 */
import router from './router/'
import store from './store'
import { validatenull } from '@/util/validate'
import { getToken } from '@/util/auth'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
NProgress.configure({ showSpinner: false })
const lockPage = store.getters.website.lockPage //锁屏页
router.beforeEach((to, from, next) => {
  const meta = to.meta || {}
  if (getToken()) {
    if (store.getters.isLock && to.path !== lockPage) { //如果系统激活锁屏，全部跳转到锁屏页
      next({ path: lockPage })
    } else if (to.path === '/login') { //如果登录成功访问登录页跳转到主页
      next({ path: '/' })
    } else {
      const value = to.path
      const query = to.query
      const label = query.name || to.name
      const meta = to.meta
      if (meta.target) {
        window.open(query.url.replace(/#/g, '&'))
        return
      } else if (meta.isTab !== false && !validatenull(value) && !validatenull(label)) {
        store.commit('ADD_TAG', {
          label: label,
          value: value,
          params: to.params,
          query: to.query,
          meta: to.meta
        })
      }
      next()
    }
  } else {
    //判断是否需要认证，没有登录访问去登录页
    if (meta.isAuth === false) {
      next()
    } else {
      next('/login')
    }
  }
})

router.afterEach(to => {
  NProgress.done()
  let title = to.name
  const i18n = to.meta.i18n
  title = router.$avueRouter.generateTitle(title, i18n)
  //根据当前的标签也获取label的值动态设置浏览器标题
  router.$avueRouter.setTitle(title)
  store.commit('SET_IS_SEARCH', false)
})
