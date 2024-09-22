import { createStore } from 'vuex'
import user from './modules/user'
import common from './modules/common'
import tags from './modules/tags'
import logs from './modules/logs'
import getters from './getters'
const store = createStore({
  modules: {
    user,
    common,
    logs,
    tags
  },
  getters
})

export default store
