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
function TransactionDTO(smartGallery, listing, customer, transactionID,
  paymentMethod, deliveryMethod, paymentDate) {
  this.customer = customer;
  this.smartGallery = smartGallery;
  this.listing = listing;
  this.transactionID = transactionID;
  this.paymentMethod = paymentMethod;
  this.deliveryMethod = deliveryMethod;
  this.paymentDate = paymentDate;
}
export default {
  data() {
    return {
      showEmail: false,
      showPassword: false,
      showDelete: false,
      oldPasswordInput: '',
      newPasswordInput: '',
      newEmail: '',
      transactions: [],
      errorTransaction: '',
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
    this.checkIfLoggedIn()
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
      AXIOS.get('/transaction/search/username/'+ this.$route.params.username)
        .then(response => {
          this.transactions = response.data
        })
        .catch(e => {
          this.errorTransaction = e
        })
  },
  methods: {
    logout: function() {
      var username = this.$route.params.username
      AXIOS.post('/logout'.concat("?username=", username))
        .then(response => {
          if (response.data) {
            alert("You have been logged out.")
            window.location.href = "/#/"
          }
        })
    },
    deleteCustomer: function() {
    var username = this.$route.params.username
    AXIOS.post('/customer/delete/'.concat(username))
      .then(response => {
        if (response.data) {
          alert("Your account has been deleted.")
          window.location.href = "/#/"
        }
      })
    },
    updatePassword: function(oldPassword, newPassword) {
      AXIOS.post('/password/change/?username=' + this.$route.params.username + '&oldPassword=' + oldPassword + '&newPassword=' + newPassword)
        .then(response => {
          this.updated = response.data
          alert("Password has been successfully updated")
          this.oldPasswordInput = ''
          this.newPasswordInput = ''
        })
        .catch(e => {
          this.errorUpdated = e
        })
    },
    updateEmail: function(email, password) {
      AXIOS.post('/email/change/?username=' + this.$route.params.username + '&password=' + password + '&newEmail=' + email)
        .then(response => {
          alert("Email has been successfully updated")
          this.updated = response.data
          this.newEmail = ''
          this.passwordInput = ''
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
    logout : function () {
			var username = this.$route.params.username
      		AXIOS.post('/logout'.concat("?username=", username))
      		.then(response => {
			if(response.data) {
				alert ("You have been logged out.")
        		window.location.href = "/#/"
			}
	    })
		},
    getListingPageURL : function (listingID) {
			return '/#/ViewListing/'.concat(this.$route.params.username, '/', listingID)
    },
    checkIfLoggedIn: function() {
      var username = this.$route.params.username
      AXIOS.get('/customer/name/'.concat(username))
      .then(response => {
        console.log(response.data.loggedIn)
        var isLoggedIn = response.data.loggedIn
        console.log(isLoggedIn)
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(e => {AXIOS.get('/artist/name/'.concat(username))
        .then(response => {
        var isLoggedIn = response.data.loggedIn
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(e => {
        window.location.href = "/#/"
      }

      )}

      )
    }
  }

}
