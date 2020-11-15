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

function ListingDTO(gallery, artwork, listedDate, sold, listingID) {
  this.gallery = gallery;
  this.artwork = artwork;
  this.listedDate = listedDate;
  this.sold = sold;
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
      sold: '',
      comission:'',
      selected: "Credit",
      delivery: "Shipping"
    }
  },
  created: function() {
    AXIOS.get('/listing/'.concat(this.$route.params.listingNumber))
      .then(response => {
        this.newListing = response.data
        this.artwork = this.newListing.artwork
        if (this.newListing.sold) {
          this.sold = "Sold"
        } else {
          this.sold = "Available"
        }
        AXIOS.put('/customer/addToBrowseHistory/'.concat(this.$route.params.username, '/', this.artwork.artworkID))
      })
      .catch(e => {
        this.errorListing = e
      })
  },
  methods: {
    createTransaction: function(paymentMethod, deliveryMethod) {
      AXIOS.post('/transaction/?paymentMethod=' + paymentMethod + '&deliveryMethod=' +
          deliveryMethod + '&username=' + this.$route.params.username + '&listingID=' + this.$route.params.listingNumber)
        .then(response => {
          alert("You have been billed " + this.newListing.artwork.price + "$ The gallery received " + this.newListing.gallery.commissionPercentage/100*this.newListing.artwork.price + "$")
          this.transaction = response.data
          this.sold ="Sold"

          this.setImage()
        })
        .catch(e => {
          this.errorTransaction = e
        })
    },
    logout: function() {
      var username = this.$route.params.username
      AXIOS.post('/logout'.concat("?username=", username))
        .then(response => {
          if (response.data) {
            alert("You have been logged out.")
            window.location.href = "/#/"
          }
        })
    },
    goToArtworkSearch: function() {
      window.location.href = "/#/artworkSearch/".concat(this.$route.params.username)
    },
    goToArtistSearch: function() {
      window.location.href = "/#/artistSearch/".concat(this.$route.params.username)
    },
    goToProfile: function() {
      AXIOS.get('/customer/name/'.concat(this.$route.params.username))
        .then(response => {
          window.location.href = "/#/customerProfile/".concat(this.$route.params.username)
        }).catch(e => {
          window.location.href = "/#/artistProfile/".concat(this.$route.params.username)
        })
    },
    goToHome: function() {
      window.location.href = "/#/home/".concat(this.$route.params.username)
    },
    addToBrowseHistory: function (artID) {
      AXIOS.get('/customer/name/testcustomer/'.concat(this.$route.params.username))
        .then(response => {
          if (response != null) {
            AXIOS.put('/customer/addToBrowseHistory/'.concat(this.$route.params.username, '/', artID))
          }
        })
    },
    setImage : function() {
      alert("Setting image")
      //var url = AXIOS.get('/artwork/'.concat(artworkID))
    }
  }
}
