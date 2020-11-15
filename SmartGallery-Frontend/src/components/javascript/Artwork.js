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
      artworks: [],
      artist: '',
      errorArtist: '',
      artwork: '',
      errorArtwork: '',
      artworkNameInput: '',
      yearInput: '',
      priceInput: '',
      artStyle: '',
      heightInput: '',
      weightInput: '',
      widthInput: '',
      artistInput: '',
      galleryInput: '',
      selected: '',
      response: []
    }
  },
  created: function() {
    AXIOS.get('/artist/name/' + this.$route.params.username)
      .then(response => {
        this.artist = response.data
      })
      .catch(e => {
        this.errorArtist = e
      })
    AXIOS.get('/artwork')
      .then(response => {
        this.artworks = response.data
        console.log(response)
      })
      .catch(e => {
        this.errorArtwork = e
      })
  },
  methods: {
    getArtwork: function(artworkID) {
      AXIOS.get('/artwork/'.concat(artworkID))
        .then(response => {
          this.artwork = response.data
        })
        .catch(e => {
          this.errorArtwork = e
        })
    },
    createArtwork: function(artworkName, year, price, style, height, weight, width) {
      AXIOS.post('/artwork/'.concat(artworkName) + '?year=' + year + '&price=' + price + '&style=' + style + '&height=' + height + '&weight=' + weight + '&width=' + width + '&artist=' + this.$route.params.username + '&gallery=testGallery')
        .then(response => {
          this.artwork = response.data
        })
        .catch(e => {
          this.errorArtwork = e
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
    confirmImage : function(artworkID, imageUrl) {
      document.getElementById("inputUrl").style.display = "none"
      var encodedUrl = encodeURIComponent(imageUrl)
      document.getElementById("picture").src = "imageUrl"
      document.getElementById("image").style.display = "block"
      AXIOS.put('/artwork/setImageUrl?artworkID='.concat(artworkID, "&imageUrl=", encodedUrl))
    }
  }
}
