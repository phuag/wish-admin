import axios from 'axios'
import * as types from '../vuex/types'
import store from '../vuex/store'

axios.defaults.timeout = 5000

let base = ''

axios.interceptors.request.use(
  config => {
    if (store.state.token) {
      config.headers.Authorization = `Basic ${store.state.token}`
    }
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          store.commit(types.LOGOUT)
          this.$router.push({ path: '/login' })
          break
        default:

      }
    }
  }
)

export const requestLogin = params => { return axios.post(`${base}/login`, params).then(res => res.data) }

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }) }

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }) }

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }) }

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }) }

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }) }

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }) }
