import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './registerServiceWorker'
import router from './router'
import store from './store'

Vue.config.productionTip = false
Vue.prototype.servername = 'http://localhost:8080'
Vue.use(ElementUI)

axios.defaults.baseURL = 'http://localhost:8080'

Vue.use(VueAxios, axios)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
