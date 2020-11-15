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
	name: 'addArtist',
	data() {
		return {
			artists: [],
			errorArtist: '',
			artwork: '',
			errorArtwork: '',
			response: []
		}
	},
	created: function () {
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
		addArtist: function(artworkID, artist) {
			AXIOS.put('/artwork/addArtist/'.concat(artworkID) + '/'.concat(artist))
        .then(response => {
          this.artwork = response.data
        })
        .catch(e => {
		  alert("Please choose an exisitng artwork and artist.");
          this.errorArtwork = e
        })}
	}
}