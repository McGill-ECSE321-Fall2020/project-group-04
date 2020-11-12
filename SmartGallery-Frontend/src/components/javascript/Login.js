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
    login: function (username, password) {
      this.loginError = "Hi"
      AXIOS.post('/login/?username='.concat(username, '&password=', password))
      .then(response => {
        this.loggedIn = response.data
      })
      .catch(e => {
        //this.loggedIn = false;
        this.loginError = e.message;
      })
      // if(this.loginError == "Profile doesn't exist") {
      //   alert("This username does not exist")
      // } else if (this.loginError == "Incorrect password") {
      //   alert(this.loginError)
      // } else {
      //   alert(this.loggedIn)
      // }
      alert(this.loggedIn)
    }
  }
}