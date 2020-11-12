import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkSearch from '@/components/ArtworkSearch'
import Listing from '@/components/ListingView'
import Login from '@/components/Login'
import Registration from '@/components/Registration'
import Artwork from '@/components/Artwork'

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
        {
      path: '/CreateArtwork',
      name: 'Artwork',
      component: Artwork
    }
  ]
})
