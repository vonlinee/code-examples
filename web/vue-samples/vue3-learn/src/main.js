import { createApp } from 'vue'
import App from './App.vue'

let app = createApp(App)
//为vue3项目特别更新的版本
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

app.use(ElementPlus)

app.mount('#app')
