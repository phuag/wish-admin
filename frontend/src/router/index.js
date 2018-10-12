import Vue from 'vue'
import Router from 'vue-router'
import store from '../vuex/store'

// import Login from '@/components/Login.vue'
// import NotFound from '@/components/404.vue'
import Home from '@/components/Home.vue'
// import Main from '@/components/Main.vue'
// import SysUser from '@/components/nav1/SysUser.vue'
// import Staff from '@/components/nav1/Staff.vue'
// import Form from '@/components/nav1/Form.vue'
// import User from '@/components/nav1/user.vue'
Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/components/Login.vue'),
    name: '',
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/components/404.vue'),
    name: '',
    hidden: true
  },
  {
    path: '/',
    component: Home,
    redirect: '/main',
    name: '首页',
    hidden: true,
    children: [
      { path: 'main', component: () => import('@/components/Main.vue'), name: '个人设置' }
    ]
  }
]

export const asyncRouterMap = [
  {
    path: '/sys',
    component: Home,
    redirect: '/sys/sysUser',
    name: '用户和机构',
    iconCls: 'el-icon-message', // 图标样式class
    children: [
      { path: 'sysUser', component: () => import('@/components/nav1/SysUser.vue'), name: '用户管理', menu: 'sysUser' },
      { path: 'staff', component: () => import('@/components/nav1/Staff.vue'), name: 'Staff', menu: 'staff' },
      { path: 'form', component: () => import('@/components/nav1/Form.vue'), name: 'Form', menu: 'form' },
      { path: 'user', component: () => import('@/components/nav1/user.vue'), name: '列表', menu: 'user' }
    ]
  },
  {
    path: '*',
    hidden: true,
    redirect: { path: '/404' }
  }
]

let routesOpenToPublic = ['/login', '/404']

const router = new Router({
  mode: 'history', // 后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

router.beforeEach((to, from, next) => {
  let user = store.getters.user
  console.log('addRoutesTag:', Vue.prototype.addRoutersTag)
  if (user && user !== '') {
    if (to.path === '/login') {
      next({ path: '/' }) // 有session 想跳到login，转到主页
    } else if (Vue.prototype.addRoutersTag === false) {
      store.dispatch('GetInfo').then(() => {
        next({ ...to })
      })
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
