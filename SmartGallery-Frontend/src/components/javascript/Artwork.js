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
  name: 'listinginfo',
  props: ['listingId'],
  data () {
    return {
      artworks: [],
      artwork: '',
      errorArtwork: '',
  
      response: [],
      }
  },
  created: function (){
    AXIOS.get('/artwork')
      .then(response => {
        this.artworks = response.data
        console.log(response)
      })
      .catch(e => {
        this.errorArtwork = e
      })
  },
  methods: {
    getArtwork: function (artworkID) {
      AXIOS.get('/artwork/'.concat(artworkID))
      .then(response => {
        this.artworks = [response.data]
        this.artwork = this.artworks.artwork
      })
      .catch(e => {
        this.errorArtwork = e
      })
    },
    createArtwork: function (artworkName, year, price, style, height, weight, width, artist, gallery){
      AXIOS.post('/artist/' .concat(artworkName) + '?year='+ year + '&price=' + price 
      +'&style='+style + '&height=' + height  + '&weight=' + weight  + '&width=' + width +  + '&artist=' + artist  + '&gallery=' + gallery)
      .then(response => {
        this.artwork= response.data
      })
      .catch(e => {
        this.errorArtwork = e
      })
    }
  }
}
  export default {
  name: 'listinginfo',
  props: ['listingId'],
  data () {
    return {
     artwork: [],
	  errorArtwork: '',
      newArtwork: '',
      errorNewArtwork: '',
     
      response: [],
   
      }
  },
  created: function (){
    AXIOS.get('/artwork')
      .then(response => {
        this.artwork = response.data
        console.log(response)
      })
      .catch(e => {
        this.errorArtwork = e
      })
  },
  methods: {
    getArtwork: function (artworkID) {
      AXIOS.get('/artwork/'.concat(artworkID))
      .then(response => {
        this.artwork = [response.data]
        this.artwork = this.listings.artwork
      })
      .catch(e => {
        this.errorListing = e
      })
    },
    createTransaction: function (paymentMethod, deliveryMethod, username, listingID){
      AXIOS.post('/transaction/?paymentMethod='+ paymentMethod + '&deliveryMethod='
      +deliveryMethod+'&username='+username + '&listingID=' + listingID)
      .then(response => {
        this.transaction = response.data
      })
      .catch(e => {
        this.errorTransaction = e
      })
    }
  }
}


