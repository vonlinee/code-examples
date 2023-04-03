import Vue from 'vue'
import App from './App.vue'

// 关闭Vue的生产提示
Vue.config.productionTip = false

import VueRouter from 'vue-router'

Vue.use(VueRouter)

new Vue({
    render: h => h(App),
}).$mount('#app')
