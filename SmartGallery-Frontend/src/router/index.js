import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkSearch from '@/components/ArtworkSearch'
import ArtistSearch from '@/components/ArtistSearch'
import Listing from '@/components/ListingView'
import ArtistProfile from '@/components/ArtistProfile'
import Login from '@/components/Login'
import Registration from '@/components/Registration'
import Artwork from '@/components/Artwork'
import UpdateArtwork from '@/components/UpdateArtwork'
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
    },
    {
      path: '/ArtistProfile',
      name: 'ArtistProfile',
      component: ArtistProfile
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
    },
        {
      path: '/UpdateArtwork',
      name: 'UpdateArtwork',
      component: UpdateArtwork
    }
  ]
})
