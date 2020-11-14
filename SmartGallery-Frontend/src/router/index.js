import Vue from 'vue'
import Router from 'vue-router'
import ArtworkSearch from '@/components/ArtworkSearch'
import ArtistSearch from '@/components/ArtistSearch'
import Listing from '@/components/ListingView'
import ArtistProfile from '@/components/ArtistProfile'
import Login from '@/components/Login'
import Registration from '@/components/Registration'
import Artwork from '@/components/Artwork'
import UpdateArtwork from '@/components/UpdateArtwork'
import Home from '@/components/Home'
import ArtistView from '@/components/ArtistView'
Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'Home',
      component: Home
    },
    // {
    //   path: "/username",
    //   name: 'LoggedIn',
    //   component: "ListingView"
    // },
    {
      path: '/artworkSearch/:username',
      name: 'ArtworkSearch',
      component: ArtworkSearch
    },
    {
      path: '/artistSearch/:username',
      name: 'ArtistSearch',
      component: ArtistSearch
    },
    {
      path: '/ViewListing/:username?:listingNumber',
      name: 'Listing',
      component: Listing,
    },
    {
      path: '/ArtistProfile/username',
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
      path: '/CreateArtwork/:username',
      name: 'Artwork',
      component: Artwork
    },
    {
      path: '/UpdateArtwork/:username',
      name: 'UpdateArtwork',
      component: UpdateArtwork
    },
    {
      path: '/artistView/:username',
      name: 'ArtistView',
      component: ArtistView
    }
  ]
})
