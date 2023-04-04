import Vue from 'vue'
import App from './App.vue'

// 关闭Vue的生产提示
Vue.config.productionTip = false


import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

import VueRouter from 'vue-router'

Vue.use(ElementUI)
Vue.use(VueRouter)

new Vue({
    render: h => h(App),
}).$mount('#app')
