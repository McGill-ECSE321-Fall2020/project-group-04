import axios from 'axios'
  var config = require('../../../config')

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

  var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

  function CustomerDTO(smartGallery, username, password, email, defaultPaymentMethod, creationDate, loggedIn) {
    this.smartGallery = smartGallery;
    this.username = username;
    this.password = password;
    this.email = email;
    this.defaultPaymentMethod = defaultPaymentMethod;
    this.creationDate = creationDate;
    this.loggedIn = loggedIn;
  }

  function ArtistDTO(smartGallery, username, password, email, defaultPaymentMethod, creationDate, loggedIn, isVerified) {
    this.smartGallery = smartGallery;
    this.username = username;
    this.password = password;
    this.email = email;
    this.defaultPaymentMethod = defaultPaymentMethod;
    this.creationDate = creationDate;
    this.loggedIn = loggedIn;
    this.isVerified = isVerified;
  }

  export default {
  name: 'profileinfo',
  // props: ['listingId'],
  data () {
    return {
      profile: '',
      username: '',
      password: '',
      loggedIn: '',
      loginError: ''
      }
  },
  methods: {
    login: function (username, password) {
      if (username == '') {
        var errorMessage = "Username cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert("Please enter your username")
        return
      }
      if (password == '') {
        var errorMessage = "Password cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert("Please enter your password")
        return
      }
      AXIOS.post('/login/?username='.concat(username, '&password=', password))
      .then(response => {
        // this.response = response.data
        // this.loginError =''
        if (response.data) {
          window.location.href = "/#/"
        } else {
          alert("This username and password do not match.")
        }
        // if (this.response != '') {
        //   alert("right")
        //   window.location.href = "/"
        // }
        // else {
        //   alert("wrong")
        //   this.errorLogin = 'Wrong email or password!'
        //   console.log(this.errorlogin)
        // }
      })
      .catch(e => {
        var errorMessage = e
        alert(errorMessage)
        console.log(e)
        this.loginError = errorMessage
      })
      // if(loginError == "Profile doesn't exist") {
      //   alert("This username does not exist")
      // } else if (loginError == "Incorrect password") {
      //   alert(loginError)
      // } else {
      //   alert("Error")
      // }
    },

    register: function() {
      window.location.href = "/#/registration"
    },

    loginWarning: function() {
      alert("You must register or login before using our services")
    }
  }
}