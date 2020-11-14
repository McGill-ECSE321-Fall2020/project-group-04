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
  props: ["artwork"],
  data() {
    return {
      artist: '',
      artwork: '',
      updated: '',
      newListing: '',
      newArtwork: '',
      errorArtwork: '',
      errorArtist: '',
      errorUpdated: '',
      response: [],
    }
  },
  created: function() {
    AXIOS.get('/artist/name/' + 'testartist')
      .then(response => {
        this.artist = response.data
        alert(this.artist.artwork)
      })
      .catch(e => {
        this.errorArtist = e
      })
  },
  methods: {
    updatePassword: function(oldPassword,newPassword){
      AXIOS.post('/password/change/?username='+ artistName + '&oldPassword='+oldPassword+'&newPassword='+newPassword)
      .then(response => {
        this.updated = response.data
      })
      .catch(e => {
        this.errorUpdated = e
      })
    },
    updateEmail: function(email, password){
      AXIOS.post('/email/change/?username='+ artistName +'&password='+password+'&newEmail=' + email)
      .then(response => {
        this.updated = response.data
      })
      .catch(e => {
        this.errorUpdated = e
      })
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
		goToArtworkSearch : function () {
			window.location.href = "/#/artworkSearch/".concat(this.$route.params.username)
		},
		goToArtistSearch : function () {
			window.location.href = "/#/artistSearch/".concat(this.$route.params.username)
		},
		goToProfile : function () {
			AXIOS.get('/customer/'.concat(this.$route.params.username))
			.then(response => {
				if(response.data != null) { //so if it's an artist
					window.location.href = "/#/artistProfile/".concat(this.$route.params.username)
				} else {
					window.location.href = "/#/customerProfile/".concat(this.$route.params.username)
				}
			})
		},
		goToHome : function () {
			window.location.href = "/#/home/".concat(this.$route.params.username)
		}
  }

}
