import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({

	baseURL: backendUrl,
	headers: { 'Access-Control-Allow-Origin': frontendUrl }
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

      artwork: '',
      errorArtwork: '',
      artworkNameInput: '',
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

   methods: {
   
    updateArtwork: function(listingID, artworkName, year, price, style, height, weight, width) {
      AXIOS.put('/listing/updateArtwork'.concat(listingID) + '?artworkName=' + artworkName + '&year=' + year + '&price=' + price +
          '&style=' + style + '&height=' + height + '&width=' + width + '&weight=' + weight )
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