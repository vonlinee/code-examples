import Vue from 'vue'
import App from './App.vue'


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// import { createApp } from 'vue'

Vue.config.productionTip = false


Vue.use(ElementUI);


import Router from './router'


// 引入Vuex
import Vuex from 'vuex'
// 通过插件的方式使用Vuex
Vue.use(Vuex)  
// 创建store实例
const store = new Vuex.Store({
  // 声明状态
  state: {
    count: 0
  },
  // 针对状态可以做的操作
  mutations: {
    increment (state, payload) {
      state.count++
      console.log(payload)
    }
  }
})

new Vue({
  router: Router,
  store: store,
  render: h => h(App),
}).$mount('#app')
