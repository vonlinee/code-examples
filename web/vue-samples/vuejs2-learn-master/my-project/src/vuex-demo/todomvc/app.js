// import 'babel-polyfill'
import Vue from 'vue'
import store from './store'
import App from './components/App.vue'

new Vue({
  store, // inject index to all children
  el: '#app',
  render: h => h(App)
})
