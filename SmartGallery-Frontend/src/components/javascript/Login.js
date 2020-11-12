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
      loginError: ''
      }
  },
  methods: {
    login: function (username, password) {
      if (username == '') {
        var errorMessage = "Username cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert("username")
        return
      }
      if (password == '') {
        var errorMessage = "Password cannot be empty"
        console.log(errorMessage)
        this.loginError = errorMessage
        alert("password")
        return
      }
      AXIOS.post('/login/?username='.concat(username, '&password=', password))
      .then(response => {
        this.reponse = response.data
        this.loginError =''
      })
      .catch(e => {
        alert ("Fail")
        var errorMessage = e.response
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
    }
  }
}
