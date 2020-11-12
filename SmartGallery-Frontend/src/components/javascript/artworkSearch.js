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
	name: 'artworksearch',
	data() {
		return {
			listings: [],
			errorListing: '',
			artworkNameInput: '',
			minPriceInput: '',
			maxPriceInput: '',
			artStyleInput: '',
			response: []
		}
	},
	created: function () {
		AXIOS.get('/listing')
			.then(response => {
				this.listings = response.data
				console.log(listings)
			})
			.catch(e => {
				this.errorArtist = e
			})
	},
	methods: {
		searchArtwork: function (searchInput, minPrice, maxPrice, style) {
			if (searchInput.length != 0) {
				if (minPrice.length == 0 || maxPrice.length == 0) {
					if (style.length == 0) {
						this.searchArtworkNoFilter(searchInput);
					}
					else {
						this.searchArtworkArtStyleFilter(searchInput, style);
					}
				}
				else {
					if (style.length == 0) {
						this.searchArtworkPriceFilter(searchInput, minPrice, maxPrice);
					}
					else {
						this.searchArtworkAllFilters(searchInput, minPrice, maxPrice, style);
					}
				}
			}
			else {
				alert("Please enter something into the search box.");
			}

		},
		searchArtworkAllFilters: function (searchInput, minPrice, maxPrice, style) {
			AXIOS.get('/listing/artworkSearch/' + searchInput + '/' + minPrice + '/' + maxPrice + '/' + style)
				.then(response => {
					this.listings = response.data
				})
				.catch(e => {
					this.errorListing = e
				})
		},
		searchArtworkPriceFilter: function (searchInput, minPrice, maxPrice) {
			AXIOS.get('/listing/artworkSearch/' + searchInput + '/' + minPrice + '/' + maxPrice)
				.then(response => {
					this.listings = response.data
				})
				.catch(e => {
					this.errorListing = e
				})
		},
		searchArtworkArtStyleFilter: function (searchInput, style) {
			AXIOS.get('/listing/artworkSearch/' + searchInput + '/' + style)
				.then(response => {
					this.listings = response.data
				})
				.catch(e => {
					this.errorListing = e
				})
		},
		searchArtworkNoFilter: function (searchInput) {
			AXIOS.get('/listing/artworkSearch/' + searchInput)
				.then(response => {
					this.listings = response.data
				})
				.catch(e => {
					this.errorListing = e
				})
		}
	}
}