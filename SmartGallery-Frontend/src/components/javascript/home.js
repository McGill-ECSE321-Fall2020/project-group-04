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
		AXIOS.get('/artwork/getPromoted')
			.then(response => {
				this.listings = response.data
				console.log(listings)
			})
			.catch(e => {
				this.errorListing = e
			})
	},
	methods: {

	}
}
