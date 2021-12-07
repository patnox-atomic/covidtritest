// https://vuex.vuejs.org/en/mutations.html

export default {
  auth_request (state) {
    state.authStatus = 'loading'
  },
  auth_success (state, { token, user }) {
    state.authStatus = 'success'
    state.token = token
    state.user = user
    //console.log('Setting Token: ' + JSON.stringify(state.token))
    //console.log('Setting User: ' + JSON.stringify(state.user))
  },
  auth_error (state) {
    state.authStatus = 'error'
  },
  logout (state) {
    state.authStatus = ''
    state.token = ''
  },
  setTableList (state, tableList) {
    state.tableList = tableList
  }
}
