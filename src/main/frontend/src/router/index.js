import Vue from 'vue'
import Router from 'vue-router'
import store from '../vuex/store'
import * as types from '../vuex/types'
import Login from '@/components/Login.vue'
import NotFound from '@/components/404.vue'
import Home from '@/components/Home.vue'
import Main from '@/components/Main.vue'
import Table from '@/components/nav1/Table.vue'
import Form from '@/components/nav1/Form.vue'
import user from '@/components/nav1/user.vue'

Vue.use(Router)

const routes = [
  {
    path: '/login',
    component: Login,
    name: '',
    hidden: true
  },
  {
    path: '/404',
    component: NotFound,
    name: '',
    hidden: true
  },
  {
    path: '/',
    component: Home,
    name: 'Home',
    iconCls: 'el-icon-message', // 图标样式class
    children: [
      { path: '/main', component: Main, name: '主页', hidden: true },
      { path: '/table', component: Table, name: 'Table' },
      { path: '/form', component: Form, name: 'Form' },
      { path: '/user', component: user, name: '列表' }
    ]
  },
  {
    path: '*',
    hidden: true,
    redirect: { path: '/404' }
  }
]

// if (localStorage.getItem('token')) {
//   store.commit(types.LOGIN, localStorage.getItem('token'))
// }

const router = new Router({
  routes
})

router.beforeEach((to, from, next) => {
  // NProgress.start()
  if (to.path === '/login') {
    // sessionStorage.removeItem('user')
    store.commit(types.LOGOUT)
  }
  // let user = JSON.parse(sessionStorage.getItem('user'))
  let user = store.state.user
  if (!user && to.path !== '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

export default router