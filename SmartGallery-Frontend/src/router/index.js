import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Listing from '@/components/ListingView'
import ArtworkSearch from '@/components/ArtworkSearch'
import ArtistProfile from '@/components/ArtistProfile'
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
      path: '/artworkSearch',
      name: 'ArtworkSearch',
      component: ArtworkSearch
    },
    {
      path: '/ArtistProfile',
      name: 'ArtistProfile',
      component: ArtistProfile
    }
  ]
})
