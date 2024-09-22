import { createApp } from 'vue'
import '@smallwei/avue/lib/index.css'
import 'animate.css/animate.min.css'
import 'styles/common.scss'
import website from '@/config/website.js'
import router from '@/router/index.js'
import store from '@/store/index.js'
import ZhLocale from 'element-plus/es/locale/lang/zh-cn'
import { getScreen, downBlobFile} from '@/util/index.js'
import { baseUrl,codeUrl} from '@/config/env.js'
import dict from '@/util/dict.js'
import '@/permission.js'
import error from '@/error.js'
import {filterForm } from '@/util/util.js'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from '@/router/axios.js'
import Avue from '@smallwei/avue'
import basicBlock from 'components/BasicBlock/main.vue'
import basicContainer from 'components/BasicContainer/main.vue'
import DictTag from '@/components/DictTag/index.vue'
import VueCropper from 'vue-cropper';
import 'vue-cropper/dist/index.css'
import Pagination from 'components/Pagination/index.vue'
import RightToolbar from 'components/RightToolbar/index.vue'
import {parseTime} from '@/utils/index'
import App from './App.vue'
ZhLocale.el.pagination = {
  goto: '前往',
  pageClassifier: '页',
  pagesize: '条/页',
  total: '共{total}条',  // 如上图的分页，可以这样去设置  *注意total的写法：{total}
}
ZhLocale.el.table = {
  emptyText:'没有更多了',
}

window.axios = axios
const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
//注册全局容器
app.component('basicContainer', basicContainer)
app.component('basicBlock', basicBlock)
app.component('DictTag', DictTag)
app.component('Pagination',Pagination)
app.component('RightToolbar',RightToolbar)

//注册全局变量
app.config.globalProperties.website = website
app.config.globalProperties.baseUrl = baseUrl
app.config.globalProperties.codeUrl=codeUrl
//空值过滤器
app.config.globalProperties.filterForm = filterForm
app.config.globalProperties.getScreen = getScreen
app.config.globalProperties.downBlobFile = downBlobFile
app.config.globalProperties.parseTime=parseTime
app.config.globalProperties.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}
app.config.globalProperties.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}
app.config.globalProperties.msgInfo = function (msg) {
  this.$message.info(msg);
}
dict(app)
app.use(error)
app.use(store)
app.use(router)
app.use(Avue, {axios})
app.use(VueCropper)
//控制 element-plus 组件语言
app.use(ElementPlus, { locale: ZhLocale })
app.mount('#app')
