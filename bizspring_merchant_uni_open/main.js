import App from './App'
// import store from './store'; //引入vuex
import server from '@/utils/request.js'
// #ifndef VUE3
import Vue from 'vue'

Vue.config.productionTip = false
// Vue.prototype.$store = store //把vuex定义成全局组件

App.mpType = 'app'
const app = new Vue({
	...App
})
app.$mount()


// #endif


// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif

Vue.prototype.$onLaunched = new Promise(resolve => {
	Vue.prototype.$isResolve = resolve
})
