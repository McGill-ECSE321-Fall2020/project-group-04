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

export default {
  name: 'artworksearch',
  data() {
    return {
      listings: [],
      errorListing: '',
      response: []
    }
  },
  created: function() {
    // Show all listings when you first open page
  },
  methods: {

    searchArtworkAllFilters: function(searchInput, minPrice, maxPrice, style) {
      AXIOS.get('/listing/artworkSearch/' + searchInput + minPrice + maxPrice + style)
        .then(response => {
          this.listings = [response.data]
        })
        .catch(e => {
          this.errorListing = e
        })
    },
    searchArtworkPriceFilter: function(searchInput, minPrice, maxPrice) {
      AXIOS.get('/listing/artworkSearch/' + searchInput + minPrice + maxPrice)
        .then(response => {
          this.listings = [response.data]
        })
        .catch(e => {
          this.errorListing = e
        })
    },
    searchArtworkArtStyleFilter: function(searchInput, style) {
      AXIOS.get('/listing/artworkSearch/' + searchInput + style)
        .then(response => {
          this.listings = [response.data]
        })
        .catch(e => {
          this.errorListing = e
        })
    },
    searchArtworkNoFilter: function(searchInput) {
      AXIOS.get('/listing/artworkSearch/' + searchInput)
        .then(response => {
          this.listings = [response.data]
        })
        .catch(e => {
          this.errorListing = e
        })
    }
  }
}
