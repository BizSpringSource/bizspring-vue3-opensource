import website from '@/config/website'
const modules =
  import.meta.glob('../**/**/*.vue')
function isURL(s) {
  return /^http[s]?:\/\/.*/.test(s)
}
function randomColor() {
  return '#' + Math.random().toString(16).substr(2, 6).toUpperCase()
}

const RouterPlugin = function () {
  this.$router = null
  this.$store = null
}
RouterPlugin.install = function (option = {}) {
  this.$router = option.router
  this.$store = option.store
  //18N
  // const i18n = option.i18n.global
  this.$router.$avueRouter = {
    safe: this,
    // 设置标题
    setTitle: (title) => {
      document.title = title
    },
    closeTag: (value) => {
      let tag = value || this.$store.getters.tag
      if (typeof value === 'string') {
        tag = this.$store.getters.tagList.filter(ele => ele.value === value)[0]
      }
      this.$store.commit('DEL_TAG', tag)
    },
    generateTitle: (title, key) => {
      return title
    },
     // 设置路由值
     getValue: function (route) {
      let value = ''
      if (route.query.src) {
        value = route.query.src
      } else {
        value = route.path
      }
      return value
    },
     // 处理路由
     getPath: function (params) {
      let { src } = params
      let result = src || '/'
      if (src.includes('http') || src.includes('https')) {
        result = `/myiframe/urlPath?${objToform(params)}`
      }
      return result
    },
      // 正则处理路由
      vaildPath: function (list, path) {
        let result = false
        list.forEach((ele) => {
          if (new RegExp('^' + ele + '.*', 'g').test(path)) {
            result = true
          }
        })
        return result
      },
    //动态路由
    formatRoutes: function (aMenu = [], first) {
      const aRouter = []
      const propsDefault = website.menu
      if (aMenu && aMenu.length === 0) return
      for (let i = 0; i < aMenu.length; i++) {
        const oMenu = aMenu[i]
        const path = oMenu[propsDefault.path],
          component = oMenu.component,
          name = oMenu[propsDefault.label],
          icon = oMenu[propsDefault.icon],
          children = oMenu[propsDefault.children],
          query = oMenu[propsDefault.query],
          meta ={  keepAlive: Number(oMenu['keepAlive']) === 0,}

        if (oMenu.keepAlive) {
          meta.keepAlive = oMenu.keepAlive

        }
        const isChild = !!(children && children.length !== 0)
        const oRouter = {
          path: path,
          component: (() => {
            // 判断是否为首路由
            if (first) {
              return modules[option.store.getters.isMacOs ? '../page/index/layout.vue' : '../page/index/index.vue']
              // 判断是否为多层路由
            } else if (isChild && !first) {
              return modules['../page/index/layout.vue']
              // 判断是否为最终的页面视图
            } else {
              const result = modules[`../${component}.vue`]
              if (result) {
                result().then(mod => {
                  mod.default.name = path
                })
              }
              return result
            }
          })(),
          name,
          icon,
          meta,
          query,
          redirect: (() => {
            if (!isChild && first) return `${path}`
            else return ''
          })(),
          // 处理是否为一级路由
          children: !isChild ? (() => {
            if (first) {
              oMenu[propsDefault.path] = `${path}`
              const result = modules[`../${component}.vue`]
              if (result) {
                result().then(mod => {
                  mod.default.name = path
                })
              }
              return [{
                component: result,
                icon: icon,
                name: name,
                meta: meta,
                query: query,
                path: ''
              }]
            }
            return []
          })() : (() => {
            return this.formatRoutes(children, false)
          })()
        }
        if (!isURL(path)) aRouter.push(oRouter)
      }
      if (first) {
        aRouter.forEach((ele) => this.safe.$router.addRoute(ele))
      } else {
        return aRouter
      }

    }
  }
}
export default RouterPlugin
