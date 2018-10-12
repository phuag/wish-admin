import store from '@/vuex/store'

export function hasPermission (permission) {
  let myPermissions = store.getters.permissions
  return myPermissions.indexOf(permission) > -1
}
