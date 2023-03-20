import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        num: 100,
        userinfo: {
            name: '张三',
            age: 30
        },
    },
    mutations: {
        setNum: function (state, new_value) {
            state.num = new_value;
        }
    },
    getters: {
        getAge(state) {
            return state.userinfo.age;
        }
    },
    actions: {
        // context对象会自动传入，它与store实例具有相同的方法和属性
        changeNum: function (context, payload) {
            // 1. 发异步请求, 请求数据
            // 2. commit调用mutation来修改/保存数据
            context.commit('setNum', payload)
        }
    }
})
