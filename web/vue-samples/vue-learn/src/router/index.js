
//注：这句必须要有，虽然在main.js里面已经引入过Vue，但是这里不要这句的话，就直接报错了Vue is not defined
import Vue from 'vue'

import VueRouter from 'vue-router'

import FirstPage from '../components/FirstPage.vue'
import SecondPage from '../components/FirstPage.vue'


// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
  //注：此处容易跟着代码提示一不小心写成components,要注意，控制台报错TypeError: Cannot read property '$createElement' of undefined
  { path: '/first', component: FirstPage },
  { path: '/second', component: SecondPage },
]

// 这种加载vue-router的方式是vue2使用的
Vue.use(VueRouter)

export default new VueRouter({
  mode: 'history',
  //注：此处的方法名，记住这里是routes,不是routers，没有r，要是写成routers，控制台不会报错，就是渲染不出组件来，牢记啊！不然会让人崩溃的
  routes: routes
});


// // 3. 创建路由实例并传递 `routes` 配置
// // 你可以在这里输入更多的配置，但我们在这里
// // 暂时保持简单
// const router = VueRouter.createRouter({
//   // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
//   history: VueRouter.createWebHashHistory(),
//   routes, // `routes: routes` 的缩写
// })


