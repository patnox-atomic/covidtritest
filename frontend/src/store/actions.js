// https://vuex.vuejs.org/en/actions.html
import axios from 'axios'
const Querystring = require('querystring');
import jwt_decode from 'jwt-decode';

// The login action passes vuex commit helper that we will use to trigger mutations.
// NB: here we are sending login as form data not as JSON
export default {
  parseJwt (token) {
      var base64Url = token.split('.')[1];
      var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

      return JSON.parse(jsonPayload);
  },
  login ({ commit }, userData) {
    return new Promise((resolve, reject) => {
      commit('auth_request')
      let body = Querystring['stringify']({
        username: userData.username,
        password: userData.password
      })
      
      const config = {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }
      let url = '/login';
      console.log('URL is: ' + JSON.stringify(url))
      axios.post(url, body, config)
        .then(response => {
          const accessToken = response.data.accesstoken
          //const user = response.data.username
          //const user = "test"
          //const userId = response.data.user_id
          const refreshToken = response.data.refreshtoken
          //const scope = response.data.scope
          // console.log('Success! We got auth: ' + JSON.stringify(response))
          //We decode the token to get the user and scope
          var decodedToken = jwt_decode(accessToken);
          // console.log('Token Content: ' + JSON.stringify(decodedToken))
          const user = decodedToken.sub;
          const scope = decodedToken.roles;
          // console.log('Token User: ' + JSON.stringify(user))
          // console.log('Token Scope: ' + JSON.stringify(scope))
          // storing jwt in localStorage. https cookie is safer place to store
          localStorage.clear()
          localStorage.setItem('token', accessToken)
          localStorage.setItem('user', user)
          //localStorage.setItem('user_id', userId)
          localStorage.setItem('refresh_token', refreshToken)
          localStorage.setItem('scope', scope)
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken
          // check if we can init auto refresh here
          // autoRefreshToken
          // mutation to change state properties to the values passed along
          const token = accessToken
          commit('auth_success', { token, user })
          resolve(response)
        })
        .catch(err => {
          console.log('Login Error: ')
          commit('auth_error')
          localStorage.removeItem('token')
          reject(err)
        })
    })
  },
  logout ({ commit }) {
    return new Promise((resolve, reject) => {
      commit('logout')
      localStorage.removeItem('token')
      localStorage.clear()
      console.log('MAJOR: Logout and clear everything')
      delete axios.defaults.headers.common['Authorization']
      resolve()
    })
  },
  refreshtoken ({ commit }) {
    axios.post('/refresh', { refresh_token: localStorage.getItem('refresh_token') })
      .then(response => {
        const token = response.data.access_token
        const refreshToken = response.data.refresh_token
        localStorage.setItem('token', token)
        localStorage.setItem('refresh_token', refreshToken)
        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
        commit('auth_success', { token })
      })
      .catch(error => {
        console.log('refresh token error')
        commit('logout')
        localStorage.removeItem('token')
        localStorage.clear()
        delete axios.defaults.headers.common['Authorization']
        console.log(error)
        //this.$router.push('/')
      })
  },
  getTableList ({ commit }, tableName) {
    this.$http.get(`/${tableName}`)
      .then(response => {
        console.log(response)
        let tableList = response.data.Keys
        commit('setTableList', { tableList })
      })
      .catch(error => console.log(error))
  },
  updateTableItem ({ commit }, tableData) {
    return new Promise((resolve, reject) => {
      let httpmethod = tableData.method
      axios({ url: `/${tableData.endpoint}`, method: httpmethod, data: tableData.tableItem })
        .then(response => {
          resolve(response)
        })
        .catch(error => {
          console.log(error)
          reject(error)
        })
    })
  },
  // Refresh the token after every 20 minutes
  autoRefreshToken ({ commit }) {
    console.log('Starting autorefresh of token: ')
    return new Promise((resolve, reject) => {
      setInterval(function () {
        // code goes here that will be run every 20 minutes.
        axios.post('/refresh', { refresh_token: localStorage.getItem('refresh_token') })
          .then(response => {
            const token = response.data.access_token
            const refreshToken = response.data.refresh_token
            localStorage.setItem('token', token)
            localStorage.setItem('refresh_token', refreshToken)
            axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
            commit('auth_success', { token })
            resolve(response)
            console.log('One Round of autorefresh of token: ' + response)
          })
          .catch(error => {
            console.log('Autorefresh token error: ')
            commit('logout')
            localStorage.removeItem('token')
            localStorage.clear()
            console.log(error)
            reject(error)
            delete axios.defaults.headers.common['Authorization']
            //this.$router.push('/')
          })
      }, 1200000)
    }
    )
  }
  // End Refresh token

}
