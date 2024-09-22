import { setToken, setRefreshToken } from '@/util/auth'
import { getStore, setStore } from '@/util/store'
import { loginByMobile, loginByUsername, getUserInfo, logout, refreshToken } from '@/api/login'
import { deepClone, encryption } from '@/util'
import { getMenu } from '@/api/upms/menu'
import{securityKey} from '@/config/env.js'
import website from '@/config/website'
import { isURL } from '@/util/validate'

function addPath(ele, first) {
  const propsConfig = website.menu
  const propsDefault = {
    label: propsConfig.label || 'label',
    path: propsConfig.path || 'path',
    icon: propsConfig.icon || 'icon',
    children: propsConfig.children || 'children',
  }
  const isChild =
    ele[propsDefault.children] && ele[propsDefault.children].length !== 0
  if (!isChild && first) {
    ele[propsDefault.path] = ele[propsDefault.path] + '/index'
    return
  }
  if (ele[propsDefault.children] != null) {
    ele[propsDefault.children].forEach((child) => {
      if (!isURL(child[propsDefault.path])) {
        child[propsDefault.path] = `${ele[propsDefault.path]}/${
          child[propsDefault.path] ? child[propsDefault.path] : 'index'
        }`
      }
      addPath(child)
    })
  }
}

const user = {
  state: {
    userInfo: getStore({
      name: 'userInfo'
    }) || {},
    permissions: getStore({
      name: 'permissions'
    }) || [],
    roles: [],
    menu: getStore({
      name: 'menu'
    }) || [],
    menuAll: getStore({ name: 'menuAll' }) || [],
    access_token: getStore({
      name: 'access_token'
    }) || '',
    refresh_token: getStore({
      name: 'refresh_token'
    }) || '',
    expires_in:
      getStore({
        name: 'expires_in',
      }) || ''
  },
  actions: {
    // 根据用户名登录
    LoginByUsername({ commit }, userInfo) {
      const user = encryption({
        data: userInfo,
        key: securityKey,
        param: ['password']
      })
      return new Promise((resolve, reject) => {
        loginByUsername(user.username, user.password, user.code, user.randomStr).then(response => {
          const data = response.data
          commit('SET_ACCESS_TOKEN', data.access_token)
          commit('SET_REFRESH_TOKEN', data.refresh_token)
          commit('SET_EXPIRES_IN', data.expires_in)
          commit('CLEAR_LOCK')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 根据手机号登录
    LoginByPhone({ commit }, smsForm) {
      return new Promise((resolve, reject) => {
        loginByMobile(smsForm).then(response => {
          const data = response.data
          commit('SET_ACCESS_TOKEN', data.access_token)
          commit('SET_REFRESH_TOKEN', data.refresh_token)
          commit('SET_EXPIRES_IN', data.expires_in)
          commit('CLEAR_LOCK')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 刷新token
    RefreshToken({ commit, state }) {
      return new Promise((resolve, reject) => {
        refreshToken(state.refresh_token).then(response => {
          const data = response.data
          commit('SET_ACCESS_TOKEN', data.access_token)
          commit('SET_REFRESH_TOKEN', data.refresh_token)
          commit('SET_EXPIRES_IN', data.expires_in)
          commit('CLEAR_LOCK')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 查询用户信息
    GetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then((res) => {
          const data = res.data.data || {}
          commit('SET_USER_INFO', data.user)
          commit('SET_ROLES', data.roles || [])
          commit('SET_PERMISSIONS', data.permissions || [])
          commit('SET_EXPIRES_IN', data.expires_in)
          commit('SET_I18N',data.user.languageValues||[])
          resolve(data)
        }).catch(() => {
          reject()
        })
      })
    },
    // 登出
    LogOut({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_MENUALL_NULL', [])
          commit('SET_MENU', [])
          commit('SET_PERMISSIONS', [])
          commit('SET_USER_INFO', {})
          commit('SET_ACCESS_TOKEN', '')
          commit('SET_REFRESH_TOKEN', '')
          commit('SET_ROLES', [])
          commit('DEL_ALL_TAG')
          commit('SET_EXPIRES_IN', '')
          commit('CLEAR_LOCK')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 注销session
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_MENU', [])
        commit('SET_MENUALL_NULL', [])
        commit('SET_PERMISSIONS', [])
        commit('SET_USER_INFO', {})
        commit('SET_ACCESS_TOKEN', '')
        commit('SET_REFRESH_TOKEN', '')
        commit('SET_EXPIRES_IN','')
        commit('SET_ROLES', [])
        commit('DEL_ALL_TAG')
        commit('CLEAR_LOCK')
        resolve()
      })
    },
    // 获取系统菜单
    GetMenu({ commit }, obj = {}) {
      // 记录用户点击顶部信息，保证刷新的时候不丢失
      commit('LIKE_TOP_MENUID', obj)
      return new Promise(resolve => {
        getMenu(obj.id).then((res) => {
          const data = res.data.data
          const menu = deepClone(data)
          // menu.forEach(ele => formatPath(ele,true))
          menu.forEach((ele) => {
            addPath(ele)
          })
          commit('SET_MENUALL', menu)
          commit('SET_MENU', menu)
          resolve(menu)
        })
      })
    },
    //顶部菜单
    GetTopMenu() {
      return new Promise(resolve => {
        resolve([])
      })
    }
  },
  mutations: {
    SET_ACCESS_TOKEN: (state, access_token) => {
      state.access_token = access_token
      setToken(access_token)
      setStore({
        name: 'access_token',
        content: state.access_token,
        type: 'session'
      })
    },
    SET_EXPIRES_IN: (state, expires_in) => {
      state.expires_in = expires_in
      setStore({
        name: 'expires_in',
        content: state.expires_in,
        type: 'session',
      })
    },
    SET_REFRESH_TOKEN: (state, rfToken) => {
      state.refresh_token = rfToken
      setRefreshToken(rfToken)
      setStore({
        name: 'refresh_token',
        content: state.refresh_token,
        type: 'session'
      })
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo
      setStore({
        name: 'userInfo',
        content: userInfo,
        type: 'session'
      })
    },
    SET_MENUALL: (state, menuAll) => {
      const menu = state.menuAll
      menuAll.forEach(ele => {
        if (!menu.find(item => item.label === ele.label && item.path === ele.path)) {
          menu.push(ele)
        }
      })
      state.menuAll = menu
      setStore({ name: 'menuAll', content: state.menuAll })
    },
    SET_MENUALL_NULL: (state) => {
      state.menuAll = []
      setStore({ name: 'menuAll', content: state.menuAll })
    },
    SET_MENU: (state, menu) => {
      state.menu = menu
      setStore({ name: 'menu', content: state.menu })
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      const list = {}
      for (let i = 0; i < permissions.length; i++) {
        list[permissions[i]] = true
      }

      state.permissions = list
      setStore({
        name: 'permissions',
        content: list,
        type: 'session'
      })
    }
  }

}
export default user
