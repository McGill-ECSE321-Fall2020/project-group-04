import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Listing from '@/components/ListingView'
import Login from '@/components/Login'
import Registration from '@/components/Registration'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/ViewListing',
      name: 'Listing',
      component: Listing
    },
        {
      path: '/Login',
      name: 'Login',
      component: Login
    },
        {
      path: '/Registration',
      name: 'Registration',
      component: Registration
    },
  ]
})
