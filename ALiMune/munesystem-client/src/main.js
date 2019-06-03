import Vue from 'vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';

Vue.config.productionTip = false
// 弹框的初始 z-index（默认值：2000）
// size 用于改变组件的默认尺寸
Vue.use(ElementUI)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
