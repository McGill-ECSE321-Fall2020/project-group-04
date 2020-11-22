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

function ArtworkDTO(artists, gallery, name, year, price, isBeingPromoted, style, height, weight, width, artworkID) {
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

export default {
  name: 'createArtwork',
  data() {
    return {
      addArtist: false,
      artwork: '',
      artist: '',
			artists: [],
      errorArtist: '',
      errorArtwork: '',
      selectedArtStyle: '',
      selectedListing: '',
      artworkNameInput: '',
      addedArtistInput: '',
      yearInput: '',
      priceInput: '',
      artStyle: '',
      heightInput: '',
      weightInput: '',
      widthInput: '',
      selected: '',
      response: []
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
			AXIOS.get('/artist')
	      .then(response => {
	        this.artists = response.data
	        console.log(artists)
	      })
	      .catch(e => {
	        this.errorArtist = e
	      })
  },

  methods: {
    updateArtwork: function(artwork, artworkName, year, price, style, height, width, weight) {

      AXIOS.put('/listing/updateArtwork/'.concat(artwork.listing.listingID) + '?artworkName=' + artworkName + '&year=' + year + '&price=' + price +
          '&style=' + style + '&height=' + height + '&width=' + width + '&weight=' + weight)
        .then(response => {
          this.artwork = response.data
        })
        .catch(e => {
          alert("Failure. Please list the artwork before updating it or enter valid fields.");
          this.errorArtwork = e
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
		addArtists: function(artworkID, artist) {
      AXIOS.put('/artwork/addArtist/'.concat(artworkID) + '/'.concat(artist))
        .then(response => {
		  this.artwork = response.data
		  alert("Artist succesfully added.")
        })
        .catch(e => {
          alert("Please choose an exisitng artwork and artist.");
          this.errorArtwork = e
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
