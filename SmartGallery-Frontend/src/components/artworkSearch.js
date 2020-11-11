import axios from 'axios'
var config = require('../../config')

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

export default {
	name: 'artworksearch',
	data() {
		return {
			listings: [],
			errorListing: '',
			response: []
		}
	},
	created: function () {
		AXIOS.get('/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}')
			.then(response => {
				this.listings = response.data
				console.log(response)
			})
			.catch(e => {
				this.errorListing = e
			})
	},
	methods: {
		getListing: function (listingID) {
			AXIOS.get('/listing/'.concat(listingID))
				.then(response => {
					this.listings = [response.data]
					this.artwork = this.listings.artwork
				})
				.catch(e => {
					this.errorListing = e
				})
		}

	}
}
"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}", "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/{style}/" 

"/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}", "/listing/artworkSearch/{searchInput}/{minPrice}/{maxPrice}/" 

"/listing/artworkSearch/{searchInput}/{style}", "/listing/artworkSearch/{searchInput}/{style}/"

"/listing/artworkSearch/{searchInput}", "/listing/artworkSearch/{searchInput}/" 

