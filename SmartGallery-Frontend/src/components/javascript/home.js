import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({

	baseURL: backendUrl,
	headers: { 'Access-Control-Allow-Origin': frontendUrl }
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
	this.listing = "";
}

function ArtistDTO(smartGallery, username, password, email, defaultPaymentMethod, creationDate, loggedIn, isVerified) {
	this.smartGallery = smartGallery;
	this.username = username;
	this.password = password;
	this.email = email;
	this.defaultPaymentMethod = defaultPaymentMethod;
	this.creationDate = creationDate;
	this.loggedIn = loggedIn;
	this.isVerified = isVerified;
}

export default {
	name: 'home',
	data() {
		return {
			artworks: [],
			errorArtwork: '',
			response: []
		}
	},
	created: function () {
		AXIOS.get('/artwork/getPromoted')
			.then(response => {
				this.artworks = response.data
				console.log(listings)
			})
			.catch(e => {
				this.errorArtwork = e
			})
	},
	methods: {

	}
}
