import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from '../components/Dashboard.vue'
import Intro from '../components/Intro.vue'
import Patient from '../components/Patient.vue'
import Triage from '../components/Triage.vue'
import Queue from '../components/Queue.vue'
import User from '../components/User.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import store from '../store'
import Meta from 'vue-meta'

Vue.use(VueRouter)
Vue.use(Meta)

const routes = [
  {
    path: '/',
    meta: {
      name: 'Dashboard',
      requiresAuth: true
    },
    component: Dashboard,
    children: [
      {
        path: '',
        meta: {
          name: 'Intro',
          requiresAuth: true
        },
        component: Intro
      },
      {
        path: 'patient',
        meta: {
          name: 'Patient',
          requiresAuth: true
        },
        component: Patient
      },
      {
        path: 'triage',
        meta: {
          name: 'Triage',
          requiresAuth: true
        },
        component: Triage
      },
      {
        path: 'queue',
        meta: {
          name: 'Queue',
          requiresAuth: true
        },
        component: Queue
      },
      {
        path: 'user',
        meta: {
          name: 'User',
          requiresAuth: true
        },
        component: User
      },
    ]
  },
  {
    path: '/login',
    meta: {
      name: 'Login',
      requiresAuth: false
    },
    component: Login
  },
  {
    path: '/register',
    meta: {
      name: 'Register',
      requiresAuth: false
    },
    component: Register
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// Route guard checks to see if you are logged in, if not reroutes to login
// to is where you are going, matched.some is to find which routes have requiresAuth
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) 
  {
    if (store.getters.authorized)
    {
      next()
      return
    }
    next('/login')
  } else {
    next()
  }
})

export default router
