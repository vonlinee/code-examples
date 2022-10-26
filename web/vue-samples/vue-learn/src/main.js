import App from './App.vue'
import Vue from 'vue'
import router from './router'    //import后面的router只能写成router,且首字母大写都不行，不然在下面new Vue里面注入的时候控制台会报错Cannot read property 'matched' of undefined，为什么会这样，目前我也不太清楚，还望大佬指点
Vue.config.productionTip = false

// 新版vue3的创建方式
// var app = createApp(App)
// app.use(router)
// app.mount('#app')

new Vue({
  el: '#app',
  router,//记得在这里注入引入的router
  components: { App },
  template: '<App/>'
})