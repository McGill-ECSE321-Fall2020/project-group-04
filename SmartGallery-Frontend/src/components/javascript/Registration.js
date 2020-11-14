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
      customers: [],
      newCustomer: '',
      username: '',
      password: '',
      email: '',
      errorCustomer: '',
      response: []
      }
  },
  created: function (){
    AXIOS.get('/customer')
    .then(response => {
      this.customers = response.data
    })
    .catch(e => {
      this.errorCustomer = e
    })
  },
  methods: {
    createProfile: function(accountType, username, password, email, paymentType) {
      if(accountType == "Customer") {
        alert("hi")
        this.createCustomerProfile(username, password, email, paymentType)
      } else {
        alert("hello")
        this.createArtistProfile(username, password, email, paymentType)
      }
    },

    createCustomerProfile: function (username, password, email, paymentType) {
      AXIOS.post('/customer/'.concat(username, '/', password, '/', email, '/', paymentType, "?smartGalleryID=", 123))
      .then(response => {
          this.customers.push(response.data)
          this.errorCustomer = ''
          this.newCustomer = ''
          alert ("success")
      	})
      .catch(e => {
        alert ("incorrect")
        var errorMessage = e.response.data.message
        console.log(errorMessage)
        this.errorCustomer = errorMessage
      })
    },
    createArtistProfile: function (username, password, email, paymentType) {
      AXIOS.post('/artist/'.concat(username, '&password=', password, "&email=", email, "&defaultPaymentMethod=", paymentType, "&smartGalleryID=", 123))
      .then()
      .catch(e => {
        this.loginError = e.message;
      })
    },

    backToLogin: function() {
      window.location.href = "/#/login"
    }
  }
}
