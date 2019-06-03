import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import OrderTable from './views/Order.vue'
import Mune from './views/Mune.vue'
import MuneEdit from './views/MuneEdit.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    }, {
      path: '/order',
      name: 'OrderTable',
      component: OrderTable
    }, {
      path: '/mune',
      name: 'mune',
      component: Mune
    },
    {
      path: '/mune_edit',
      name: 'mune_edit',
      component: MuneEdit
    }

  ]
})
