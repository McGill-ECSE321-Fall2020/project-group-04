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
      artists: [],
      newArtist: '',
      newCustomer: '',
      username: '',
      password: '',
      email: '',
      errorCustomer: '',
      errorArtist: '',
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
      if (username == '') {
        var errorMessage = "Username cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert(errorMessage)
        return
      }
      if (email == '') {
        var errorMessage = "Email cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert(errorMessage)
        return
      }
      if (password == '') {
        var errorMessage = "Password cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert(errorMessage)
        return
      }
      if (accountType != "Customer" && accountType != "Artist") {
        var errorMessage = "Please choose an account type"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert(errorMessage)
        return
      }
      if (paymentType != "Credit" && paymentType != "Paypal") {
        var errorMessage = "Please choose a payment method"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert(errorMessage)
        return
      }
      if(accountType == "Customer") {
        this.createCustomerProfile(username, password, email, paymentType)
      } else {
        this.createArtistProfile(username, password, email, paymentType)
      }
    },

    createCustomerProfile: function (username, password, email, paymentType) {
      AXIOS.post('/customer/'.concat(username, '/', password, '/', email, '/', paymentType, '?smartGalleryID=123' ))
      .then(response => {
          alert("success")
          this.customers.push(response.data)
          this.errorCustomer = ''
          this.newCustomer = ''
          window.location.href = "/#/home".concat(username)
      	})
      .catch(e => {
        var errorMessage = e.message
        alert(errorMessage)
        console.log(errorMessage)
        this.errorCustomer = errorMessage
      })
    },
    createArtistProfile: function (username, password, email, paymentType) {
      AXIOS.post('/artist/'.concat(username, '?password=', password, "&email=", email, "&defaultPaymentMethod=", paymentType, "&smartGalleryID=123"))
      .then(response => {
        alert ("success")
        this.artists.push(response.data)
        this.errorArtist = ''
        this.newArtist = ''
        window.location.href = "/#/home".concat(username)
      })
      .catch(e => {
        alert ("incorrect")
        var errorMessage = e.message
        alert(errorMessage)
        console.log(errorMessage)
        this.errorArtist = errorMessage
      })
    },

    backToLogin: function() {
      window.location.href = "/#/"
    },

    loginWarning: function() {
      alert("You must register or login before using our services")
    },
  }
}
