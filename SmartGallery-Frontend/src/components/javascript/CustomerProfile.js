import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': frontendUrl
  }
})

function CustomerDTO(smartGallery, username, password, email, defaultPaymentMethod,
  creationDate, loggedIn) {
  this.smartGallery = smartGallery;
  this.username = username;
  this.password = password;
  this.email = email;
  this.defaultPaymentMethod = defaultPaymentMethod;
  this.creationDate = creationDate;
  this.loggedIn = loggedIn;
}

export default {
  data() {
    return {
      showEmail: false,
      showPassword: false,
      oldPasswordInput: '',
      newPasswordInput: '',
      newEmail: '',
      passwordInput: '',
      customer: '',
      updated: '',
      errorCustomer: '',
      errorUpdated: '',
      errorBrowseHistory: '',
      browseHistory: [],
      response: [],
    }
  },

  created: function() {
    AXIOS.get('/customer/name/' + this.$route.params.username)
      .then(response => {
       this.customer = response.data
      })
      .catch(e => {
       this.errorCustomer = e
      })
    AXIOS.get('/customer/viewBrowsingHistory/'.concat(this.$route.params.username))
      .then(response => {
        this.browseHistory = response.data
      })
      .catch(e => {
        this.errorBrowseHistory = e
      })
  },
  methods: {
    updatePassword: function(oldPassword,newPassword){
      AXIOS.post('/password/change/?username='+ customerName + '&oldPassword='+oldPassword+'&newPassword='+newPassword)
      .then(response => {
        this.updated = response.data
      })
      .catch(e => {
        this.errorUpdated = e
      })
    },
    updateEmail: function(email, password){
      AXIOS.post('/email/change/?username='+ customerName +'&password='+password+'&newEmail=' + email)
      .then(response => {
        this.updated = response.data
      })
      .catch(e => {
        this.errorUpdated = e
      })
    },
		goToArtworkSearch : function () {
			window.location.href = "/#/artworkSearch/".concat(this.$route.params.username)
		},
		goToArtistSearch : function () {
			window.location.href = "/#/artistSearch/".concat(this.$route.params.username)
		},
		goToProfile : function () {
			AXIOS.get('/customer/name/'.concat(this.$route.params.username))
			.then(response => {
				window.location.href = "/#/customerProfile/".concat(this.$route.params.username)
			}).catch(e => {
				window.location.href = "/#/artistProfile/".concat(this.$route.params.username)
			})
		},
		goToHome : function () {
			window.location.href = "/#/home/".concat(this.$route.params.username)
    },
    getListingPageURL : function (listingID) {
			return '/#/ViewListing/'.concat(this.$route.params.username, '/', listingID)
		}
  }

}
