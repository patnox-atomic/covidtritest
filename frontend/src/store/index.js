import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

// Store functionality
import actions from './actions'
import state from './state'
import mutations from './mutations'
import getters from './getters'
import modules from './modules'

Vue.use(Vuex)

export default new Vuex.Store({
  actions,
  state,
  mutations,
  getters,
  modules,
})
