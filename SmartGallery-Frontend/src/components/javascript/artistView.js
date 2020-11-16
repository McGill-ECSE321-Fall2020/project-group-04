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

function ArtistDTO(smartGallery, username, password, email, defaultPaymentMethod, creationDate, loggedIn, isVerified) {
  this.smartGallery = smartGallery;
  this.username = username;
  this.password = password;
  this.email = email;
  this.defaultPaymentMethod = defaultPaymentMethod;
  this.creationDate = creationDate;
  this.loggedIn = loggedIn;
  this.isVerified = isVerified;
	this.artworks = [];
}

function ListingDto(gallery, artwork, listedDate, isSold, listingID) {
  this.gallery = gallery;
  this.artwork = artwork;
  this.listedDate = listedDate;
  this.isSold = isSold;
  this.listingID = listingID;
}

function ArtworkDto(artists, gallery, name, year, price, isBeingPromoted, style, height, weight, width, artworkID) {
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
  this.listing = "";

}

export default {
  name: 'artistview',
  data() {
    return {
      artist: '',
      availableListings: [],
      errorArtist: '',
			artworkss: [],
      response: []
    }
  },
  created: function() {
	  this.checkIfLoggedIn()
    AXIOS.get('artist/name/'.concat(this.$route.params.artistUsername)) // artist/name/testartist to test
      .then(response => {
        this.artist = response.data
        for (var i = 0; i < this.artist.artworks.length; i++) {
          if (this.artist.artworks[i].listing != null && !(this.artist.artworks[i].listing.sold)) {
            this.artworkss[i] = this.artist.artworks[i]
          }
        }
        this.availableListings = this.artworkss
      })
      .catch(e => {
        this.errorArtist = e
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
    getListingPageURL: function(listingID) {
      return '/#/ViewListing/'.concat(this.$route.params.username, '/', listingID)
	},
  checkIfLoggedIn: function() {
    var username = this.$route.params.username
    AXIOS.get('/customer/name/'.concat(username))
      .then(response => {
        this.customer = response.data
        if (!this.customer.loggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(
        AXIOS.get('/customer/name/'.concat(username))
          .then(response => {
            this.customer = response.data
            if (!this.customer.loggedIn) {
              window.location.href = "/#/"
            }
          })
      )
  }
  }
}
