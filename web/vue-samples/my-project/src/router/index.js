
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

// 导入参与路由的组件
import VueTest from '../components/VuexTest'
import School from '../components/School'
import HelloWorld from '../components/HelloWorld'

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'helloworld',
      component: HelloWorld
    },
    {
      path: '/test',
      name: 'test',
      component: VueTest
    },
    {
      path: '/school',
      name: 'school',
      component: School
    },
    {
      path: '/hello',
      name: 'hello',
      component: HelloWorld
    }
  ]
});








