import axios from 'axios'
import { Message } from 'element-ui'
import * as types from '../vuex/types'
import store from '../vuex/store'
import * as base64 from '../common/js/base64'
import router from '../router'

axios.defaults.timeout = 5000

let base = ''
// let wishBase = 'localhost:8086'

axios.interceptors.request.use(
  config => {
    if (store.state.user.token) {
      config.headers.Authorization = `Basic ${store.state.user.token}`
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

axios.interceptors.response.use(
  response => {
    // if the response has a text and a type property, it is a message to be shown
    if (response.data.text && response.data.type && response.status !== 200) {
      Message({
        message: response.data.text,
        type: response.data.type,
        duration: 5 * 1000
      })
    }

    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          store.commit(types.LOGOUT)
          router.push({ path: '/login' })
          break
        case 403:
          router.push({ path: '/login' })
          break
        case 404:
          router.push({path: '/404'})
          break
        default:
          store.commit(types.LOGOUT)
          router.push({ path: '/login' })
      }
    } else {
      store.commit(types.LOGOUT)
      router.push({ path: '/login' })
    }
    return Promise.reject(error)
  }
)

// export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data) }
export const requestLogin = params => {
  // httpHeaders.Authorization = 'Basic ' + base64.encode(params.username + ':' + params.password)
  axios.defaults.headers.common['Authorization'] = 'Basic ' + base64.encode(params.username + ':' + params.password)
  return axios.post(`${base}/api/sysUser/me`).then(res => res.data)
}

export const requestLogout = () => {
  delete axios.defaults.headers.common['Authorization']
  return axios.post(`${base}/api/sysUser/logout`)
}

export const getUserMenu = () => { return axios.post(`${base}/api/sysUser/myMenu`) }

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }) }

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }) }

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }) }

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }) }

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }) }

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }) }

export const getStaffListPage = params => { return axios.get(`${base}/api/staff`, { params: params }) }

export const removeStaff = params => { return axios.delete(`${base}/api/staff/` + params.id) }

export const batchRemoveStaff = params => { return axios.delete(`${base}/api/staff/batchremove`, { params: params }) }

export const editStaff = params => { return axios.put(`${base}/api/staff/` + params.id, params) }

export const addStaff = params => { return axios.post(`${base}/api/staff/`, params) }

export const getSysUserListPage = params => { return axios.get(`${base}/api/sysUser`, { params: params }) }

export const removeSysUser = params => { return axios.delete(`${base}/api/sysUser/` + params.id) }

export const batchRemoveSysUser = params => { return axios.delete(`${base}/api/sysUser/batchremove`, { params: params }) }

export const editSysUser = params => { return axios.put(`${base}/api/sysUser/` + params.id, params) }

export const addSysUser = params => { return axios.post(`${base}/api/sysUser/`, params) }

export const checkLoginName = params => { return axios.get(`${base}/api/sysUser/checkLoginName`, { params: params }) }

export const modifyPassword = params => { return axios.get(`${base}/api/sysUser/modifyPwd`, { params: params }) }

export const getOfficeList = params => { return axios.get(`${base}/api/sysOffice`, { params: params }) }
