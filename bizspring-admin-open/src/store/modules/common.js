import {
  setStore,
  getStore,
  removeStore
} from '@/util/store'
import website from '@/config/website'
const common = {
  state: {
    language: getStore({
      name: 'language'
    }) || 'zh-cn',
    isCollapse: false,
    isShade: false,
    isFullScren: false,
    isMenu: true,
    isSearch: false,
    isRefresh: true,
    isLock: getStore({
      name: 'isLock'
    }),
    lockPasswd: getStore({
      name: 'lockPasswd'
    }) || '',
    website: website,
    setting: website.setting,
    likeTopMenuId: undefined,

    topMenuIndex: getStore({
      name: 'topMenuIndex'
    }) || 0,
    screen: -1,
    showMenu: getStore({
      name: 'showMenu'
    }),
    theme: getStore({
      name: 'theme'
    }) || '#409EFF',
    showCollapse: getStore({
      name: 'showCollapse'
    }),
    showTheme: getStore({
      name: 'showTheme'
    }),
    showColor: getStore({
      name: 'showColor'
    }),
  },
  mutations: {
    SET_LANGUAGE: (state, language) => {
      state.language = language
      setStore({
        name: 'language',
        content: state.language
      })
    },
    SET_I18N: (state, i18n) => {
      state.i18n = i18n
      setStore({
        name: 'i18n',
        content: state.i18n
      })
    },
    SET_SHADE: (state, active) => {
      state.isShade = active
    },
    SET_COLLAPSE: (state) => {
      state.isCollapse = !state.isCollapse
    },
    SET_SHOWCOLLAPSE: (state, active) => {
      state.showCollapse = active
      setStore({
        name: 'showCollapse',
        content: state.showCollapse,
      })
    },
    SET_IS_MENU: (state, menu) => {
      state.isMenu = menu
    },
    SET_IS_REFRESH: (state, refresh) => {
      state.isRefresh = refresh
    },
    SET_IS_SEARCH: (state, search) => {
      state.isSearch = search
    },
    SET_FULLSCREN: (state) => {
      state.isFullScren = !state.isFullScren
    },
    SET_LOCK: (state) => {
      state.isLock = true
      setStore({
        name: 'isLock',
        content: state.isLock,
        type: 'session'
      })
    },
    SET_SCREEN: (state, screen) => {
      state.screen = screen
    },
    SET_THEME: (state, color) => {
      state.theme = color
      setStore({
        name: 'theme',
        content: state.theme,
      })
    },
    SET_THEME_NAME: (state, themeName) => {
      state.themeName = themeName
      setStore({
        name: 'themeName',
        content: state.themeName
      })
    },
    SET_TOP_MENU_INDEX: (state, topMenuIndex) => {
      state.topMenuIndex = topMenuIndex
      setStore({
        name: 'topMenuIndex',
        content: state.topMenuIndex,
      })
    },
    SET_LOCK_PASSWD: (state, lockPasswd) => {
      state.lockPasswd = lockPasswd
      setStore({
        name: 'lockPasswd',
        content: state.lockPasswd,
        type: 'session'
      })
    },
    SET_SHOWTAG: (state, active) => {
      state.showTag = active
      setStore({
        name: 'showTag',
        content: state.showTag
      })
    },
    SET_SHOWDEBUG: (state, active) => {
      state.showDebug = active
      setStore({
        name: 'showDebug',
        content: state.showDebug
      })
    },
    SET_SHOWSEARCH: (state, active) => {
      state.showSearch = active
      setStore({
        name: 'showSearch',
        content: state.showSearch
      })
    },
    SET_SHOWMENU: (state, active) => {
      state.showMenu = active
      setStore({
        name: 'showMenu',
        content: state.showMenu
      })
    },
    SET_SHOWLOCK: (state, active) => {
      state.showLock = active
      setStore({
        name: 'showLock',
        content: state.showLock
      })
    },
    CLEAR_LOCK: (state) => {
      state.isLock = false
      state.lockPasswd = ''
      removeStore({
        name: 'lockPasswd',
        type: 'session'
      })
      removeStore({
        name: 'isLock',
        type: 'session'
      })
    },
    LIKE_TOP_MENUID: (state, obj) => {
      if (obj.id === undefined) {
        obj.id = getStore({
          name: 'LIKE_TOP_MENUID'
        })
      }

      state.likeTopMenuId = obj.id
      setStore({
        name: 'LIKE_TOP_MENUID',
        content: state.likeTopMenuId,
        type: 'session'
      })
    },
    REMOVE_LIKE_TOP_MENUID: (state) => {
      state.likeTopMenuId = undefined
      removeStore({
        name: 'LIKE_TOP_MENUID',
        type: 'session'
      })
    },
    SET_SHOWTHEME: (state, active) => {
      state.showTheme = active
      setStore({
        name: 'showTheme',
        content: state.showTheme,
      })
    },
    SET_SHOWCOLOR: (state, active) => {
      state.showColor = active
      setStore({
        name: 'showColor',
        content: state.showColor,
      })
    },
    SET_SHOWFULLSCREN: (state, active) => {
      state.showFullScren = active
      setStore({
        name: 'showFullScren',
        content: state.showFullScren,
      })
    },
  }
}
export default common