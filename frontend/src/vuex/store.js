import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersistence from 'vuex-persist'
import * as types from './types'

Vue.use(Vuex)

const vuexLocal = new VuexPersistence({
  storage: window.localStorage
})

export default new Vuex.Store({
  state: {
    user: '',
    token: '',
    title: ''
  },
  mutations: {
    [types.LOGIN]: (state, data) => {
      state.token = data.token
      state.user = data.user
    },
    [types.LOGOUT]: (state) => {
      state.token = ''
      state.user = ''
    },
    [types.TITLE]: (state, data) => {
      state.title = data
    }
  },
  actions: {

  },
  plugins: [vuexLocal.plugin]
})
