import axios from 'axios'
import Router from '../../router'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: {
    'Access-Control-Allow-Origin': frontendUrl
  }
})


function ListingDTO(gallery, artwork, listedDate, isSold, listingID) {
  this.gallery = gallery;
  this.artwork = artwork;
  this.listedDate = listedDate;
  this.isSold = isSold;
  this.listingID = listingID;
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
}


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
  name: 'createListing',
  data() {
    return {
      artwork: [],
      newListing: '',
      errorListing: '',
      errorArtwork: '',
      selectedArtwork: '',
      artist: '',
      listPrice: '',
      response: [],
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
  },

  methods: {
    createListing: function(artworkID, price, gallery) {
      AXIOS.post('/listing/'.concat(artworkID) + '?price=' + price + '&gallery=' +
          gallery)
        .then(response => {
          this.listing = response.data
        })
        .catch(e => {
          this.errorListing = e
        })
    },
  }
}
