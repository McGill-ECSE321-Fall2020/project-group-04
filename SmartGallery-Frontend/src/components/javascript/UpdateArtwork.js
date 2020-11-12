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
    }
  }
}