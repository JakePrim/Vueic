import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 引入element ui
import ElementUI from 'element-ui'
// 引入element主题文件
import 'element-ui/lib/theme-chalk/index.css'
// 将element注册到Vue中进行使用
Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
