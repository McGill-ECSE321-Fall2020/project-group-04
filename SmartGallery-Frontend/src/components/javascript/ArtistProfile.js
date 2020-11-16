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

function ListingDto(gallery, artwork, listedDate, isSold, listingID) {
  this.gallery = gallery;
  this.artwork = artwork;
  this.listedDate = listedDate;
  this.isSold = isSold;
  this.listingID = listingID;
}

function ArtworkDto(artists, gallery, name, year, price, isBeingPromoted, style, height, weight, width, artworkID, imageUrl) {
  this.artists = artists;
  this.gallery = gallery;
  this.name = name;
  this.year = year;
  this.price = price;
  this.isBeingPromoted = isBeingPromoted;
  this.artStyle = style;
  this.height = height;
  this.weight = weight;
  this.width = width;
  this.artworkID = artworkID;
  this.imageUrl = imageUrl;
}

function ArtistDTO(smartGallery, username, password, email, defaultPaymentMethod,
  creationDate, loggedIn, isVerified) {
  this.smartGallery = smartGallery;
  this.username = username;
  this.password = password;
  this.email = email;
  this.defaultPaymentMethod = defaultPaymentMethod;
  this.creationDate = creationDate;
  this.loggedIn = loggedIn;
  this.isVerified = isVerified;
  this.artwork = "";
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
      passwordInput: '',
      artist: '',
      artwork: '',
      updated: '',
      newListing: '',
      newArtwork: '',
      errorArtwork: '',
      errorArtist: '',
      errorUpdated: '',
      errorBrowseHistory: '',
      browseHistory: [],
      response: [],
    }
  },
  created: function() {
    this.checkIfLoggedIn()
    AXIOS.get('/artist/name/' + this.$route.params.username)
      .then(response => {
        this.artist = response.data
      })
      .catch(e => {
        this.errorArtist = e
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
    deleteArtist: function() {
      var username = this.$route.params.username
      AXIOS.post('/artist/delete/'.concat(username))
        .then(response => {
          if (response.data) {
            alert("Your account has been deleted.")
            window.location.href = "/#/"
          }
        })
      },
    goToArtworkSearch: function() {
      window.location.href = "/#/artworkSearch/".concat(this.$route.params.username)
    },
    goToArtistSearch: function() {
      window.location.href = "/#/artistSearch/".concat(this.$route.params.username)
    },
    goToProfile: function() {
      AXIOS.get('/customer/name/'.concat(this.$route.params.username))
        .then(response => {
          window.location.href = "/#/customerProfile/".concat(this.$route.params.username)
        }).catch(e => {
          window.location.href = "/#/artistProfile/".concat(this.$route.params.username)
        })
    },
    goToHome: function() {
      window.location.href = "/#/home/".concat(this.$route.params.username)
    },
    goToCreateListing: function(){
        window.location.href = "/#/createListing/".concat(this.$route.params.username)
    },
    goToCreateArtwork: function(){
      window.location.href = "/#/createartwork/".concat(this.$route.params.username)
    },
    goToAddImage: function(artworkID) {
      alert(artworkID)
      window.location.href = "/#/AddImage/".concat(this.$route.params.username, "/", artworkID)
    },
    getListingPageURL : function (listingID) {
			return '/#/ViewListing/'.concat(this.$route.params.username, '/', listingID)
    },
    goToUpdateArtwork: function() {
        window.location.href = "/#/UpdateArtwork/".concat(this.$route.params.username)
    },
    checkIfLoggedIn: function() {
      var username = this.$route.params.username
      AXIOS.get('/customer/name/'.concat(username))
      .then(response => {
        var isLoggedIn = response.data.isLoggedIn
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(
        AXIOS.get('/artist/name/'.concat(username))
        .then(response => {
        var isLoggedIn = response.data.isLoggedIn
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(
        window.location.href = "/#/"
      )
      )
    }	
  }

}
