import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Listing from '@/components/Listing'
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
    }
  ]
})
