import App from './App.vue'
import Vue from 'vue'


Vue.config.productionTip = false

// 新版vue3的创建方式
// var app = createApp(App)
// app.use(router)
// app.mount('#app')

new Vue({
  el: '#app',
  // router,//记得在这里注入引入的router
  components: { App },
  template: '<App/>'
})