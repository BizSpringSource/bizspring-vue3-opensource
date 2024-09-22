
const getters = {
  tag: state => state.tags.tag,
  website: state => state.common.website,
  language: state => state.common.language,
  i18n: state => state.common.i18n,
  setting: state => state.common.setting,
  userInfo: state => state.user.userInfo,
  theme: (state) => state.common.theme,
  themeName: state => state.common.themeName,
  isMacOs: (state, getters) => getters.themeName === 'mac-os',
  isRefresh: state => state.common.isRefresh,
  isSearch: state => state.common.isSearch,
  isShade: state => state.common.isShade,
  isHorizontal: state => state.common.setting.sidebar === 'horizontal',
  screen: (state) => state.common.screen,
  isLock: state => state.common.isLock,
  isFullScren: state => state.common.isFullScren,
  isMenu: state => state.common.isMenu,
  lockPasswd: state => state.common.lockPasswd,
  tagList: state => state.tags.tagList,
  keyCollapse: (state, getters) =>
  getters.screen > 1 ? getters.isCollapse : false,
  isCollapse: state => state.common.isCollapse,
  tagsKeep: (state, getters) => {
    return getters.tagList.filter(ele => {
      return ele.meta.keepAlive
    }).map(ele => ele.value)
  },
  tagWel: state => state.tags.tagWel,
  access_token: state => state.user.access_token,
  refresh_token: state => state.user.refresh_token,
  expires_in: (state) => state.user.expires_in,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,
  menuId: state => state.user.menuId,
  menu: state => state.user.menu,
  menuAll: state => state.user.menuAll,
  logsList: state => state.logs.logsList,
  logsLen: state => state.logs.logsList.length || 0,
  logsFlag: (state, getters) => getters.logsLen === 0,
  topMenuIndex: (state) => state.common.topMenuIndex

}
export default getters
