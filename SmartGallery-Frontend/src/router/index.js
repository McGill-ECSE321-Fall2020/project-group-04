import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import ArtworkSearch from '@/components/ArtworkSearch'


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
    }
  ]
})
