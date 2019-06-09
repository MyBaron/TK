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
      component: Home,
      meta: {
        title: 'ALi菜单'
      }
    }, {
      path: '/order',
      name: 'OrderTable',
      component: OrderTable,
      meta: {
        title: 'ALi订单查询'
      }
    }, {
      path: '/mune',
      name: 'mune',
      component: Mune,
      meta: {
        title: 'ALi菜单管理'
      }
    },
    {
      path: '/mune_edit',
      name: 'mune_edit',
      component: MuneEdit,
      meta: {
        title: 'ALi菜单编辑'
      }
    }

  ]
})
