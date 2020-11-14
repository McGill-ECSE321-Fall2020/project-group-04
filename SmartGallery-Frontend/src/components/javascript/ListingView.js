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

function TransactionDTO(smartGallery, listing, customer, transactionID,
  paymentMethod, deliveryMethod, paymentDate) {
  this.customer = customer;
  this.smartGallery = smartGallery;
  this.listing = listing;
  this.transactionID = transactionID;
  this.paymentMethod = paymentMethod;
  this.deliveryMethod = deliveryMethod;
  this.paymentDate = paymentDate;
}


export default {
  name: 'listinginfo',
  data() {
    return {
      artists: [],
      artwork: '',
      newListing: '',
      newArtwork: '',
      errorArtwork: '',
      errorListing: '',
      transaction: '',
      errorTransaction: '',
      response: [],
      selected: "Credit Card",
      delivery: "Ship"
    }
  },
  created: function() {
    AXIOS.get('/listing/'.concat(this.$route.params.listingNumber))
      .then(response => {
        this.newListing = response.data
        this.artwork = this.newListing.artwork
      })
      .catch(e => {
        this.errorListing = e
      })
  },
  methods: {
    createTransaction: function(paymentMethod, deliveryMethod, username, listingID) {
      AXIOS.post('/transaction/?paymentMethod=' + paymentMethod + '&deliveryMethod=' +
          deliveryMethod + '&username=' + username + '&listingID=' + listingID)
        .then(response => {
          this.transaction = response.data
        })
        .catch(e => {
          this.errorTransaction = e
        })
    },
  }
}
