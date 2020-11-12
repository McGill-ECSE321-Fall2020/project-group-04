import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkSearch from '@/components/ArtworkSearch'
import ArtistSearch from '@/components/ArtistSearch'
import Listing from '@/components/ListingView'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/artworkSearch',
      name: 'ArtworkSearch',
      component: ArtworkSearch
    },
    {
      path: '/artistSearch',
      name: 'ArtistSearch',
      component: ArtistSearch
    },
    {
      path: '/ViewListing',
      name: 'Listing',
      component: Listing
    }
  ]
})
