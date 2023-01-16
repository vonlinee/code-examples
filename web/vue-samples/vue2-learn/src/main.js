import Vue from 'vue'
import App from './App.vue'

import {createStore} from 'vuex'
// 创建一个新的 store 实例
const store = createStore({
    state() {
        return {
            count: 0
        }
    }, mutations: {
        increment(state) {
            state.count++
        }
    }
})

// 关闭Vue的生产提示
Vue.config.productionTip = false

import VueRouter from 'vue-router'

Vue.use(VueRouter)
Vue.use(store)

new Vue({
    render: h => h(App),
}).$mount('#app')
