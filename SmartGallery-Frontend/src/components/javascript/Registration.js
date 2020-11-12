import axios from 'axios'
  var config = require('../../../config')

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

  var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

  export default {
  name: 'profileinfo',
  // props: ['listingId'],
  data () {
    return {
      profile: '',
      username: '',
      password: '',
      email: '',
      createError: ''
      }
  },
  created: function (){
    // AXIOS.get('/login')
    //   .then(response => {
    //     this.listings = response.data
    //     console.log(response)
    //   })
    //   .catch(e => {
    //     this.errorListing = e
    //   })
  },
  methods: {
    createCustomerProfile: function (username, password, email, paymentType) {
      AXIOS.get('/customer/'.concat(username, '/', password, '/', email, '/', paymentType, "?smartGalleryID=", 123))
      .then()
      .catch(e => {
        this.loginError = e.message;
      })
    },
    createArtistProfile: function (username, password, email, paymentType) {
      AXIOS.get('/artist/'.concat(username, '&password=', password, "&email=", email, "&defaultPaymentMethod=", paymentType, "&smartGalleryID=", 123))
      .then()
      .catch(e => {
        this.loginError = e.message;
      })
    },

    createProfile: function(accountType, username, password, email, paymentType) {
        if(accountType == Customer) {
          this.createCustomerProfile(username, password, email, paymentType)
        } else {
          this.createArtistProfile(username, password, email, paymentType)
        }
    }
  }
}
