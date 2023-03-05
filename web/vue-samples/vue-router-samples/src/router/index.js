import Vue from 'vue'
import VueRouter from 'vue-router'

// 组件通过export暴露接口，路由中导入组件
import Login from '../components/LoginView.vue'
import Main from '../components/MainView.vue'
import Register from '../components/RegisterView.vue'
import Hello from '../components/HelloWorld.vue'
// 导入 vue-router 依赖
Vue.use(VueRouter);

export default new VueRouter({
    routes: [
        {
            path: '/login/:id',   // 跳转路径
            name: 'login',    // 名称
            component: Login  // 组件
        },
        {
            path: '/main',
            name: 'main',
            component: Main
        },
        {
            path: '/register',
            name: 'register',
            component: Register
        },
        {
            path: '/hello',
            name: 'hello',
            component: Hello
        }
    ]
});
