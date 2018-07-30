import Vue from 'vue'
import Router from 'vue-router'
import store from './vuex/store'

import Login from '@/components/Login.vue'
import NotFound from '@/components/404.vue'
import Home from '@/components/Home.vue'
import Main from '@/components/Main.vue'
import SysUser from '@/components/nav1/SysUser.vue'
import Staff from '@/components/nav1/Staff.vue'
import Form from '@/components/nav1/Form.vue'
import User from '@/components/nav1/user.vue'

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
    name: '用户和机构',
    iconCls: 'el-icon-message', // 图标样式class
    children: [
      { path: '/main', component: Main, name: '个人设置', hidden: 'true' },
      { path: '/sysUser', component: SysUser, name: '用户管理' },
      { path: '/staff', component: Staff, name: 'Staff' },
      { path: '/form', component: Form, name: 'Form' },
      { path: '/user', component: User, name: '列表' }
    ]
  },
  {
    path: '*',
    hidden: true,
    redirect: { path: '/404' }
  }
]

let routesOpenToPublic = ['/login', '/authredirect', '/reset', '/sendpwd']

const router = new Router({
  routes
})
router.beforeEach((to, from, next) => {
  let user = store.state.user
  if (user && user !== '') {
    if (to.path === '/login') {
      next({ path: '/' }) // 有session 想跳到login，转到主页
    } else {
      next() // 有session 想跳到非login的页面，正常跳转
    }
  } else {
    // console.log('already logged in...');
    if (routesOpenToPublic.indexOf(to.path) !== -1) {
      next() // 无session 想跳到公开页面，正常跳转
    } else {
      next('/login') // 无session 想跳到非公开页面，转到登录
    }
  }

  // NProgress.start()
  // if (to.path === '/login') {
  //   // sessionStorage.removeItem('user')
  //   store.commit(types.LOGOUT)
  // }
  // // let user = JSON.parse(sessionStorage.getItem('user'))
  // let user = store.state.user
  // if (!user && to.path !== '/login') {
  //   next({ path: '/login' })
  // } else {
  //   next()
  // }
})

export default router
