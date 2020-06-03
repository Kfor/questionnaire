import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    login: false,
    username: ''
  },
  mutations: {
    login(state,username){
      state.login = true;
      state.username = username;
    },
    logout(state){
      state.login = false;
      state.username = '';
    }


  },
  actions: {
  },
  modules: {
  }
})
