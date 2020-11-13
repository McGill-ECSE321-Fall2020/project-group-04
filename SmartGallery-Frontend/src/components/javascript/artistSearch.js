import axios from 'axios'
var config = require('../../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
	baseURL: backendUrl,
	headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


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
	name: 'artistsearch',
	data() {
		return {
			artists: [],
			errorArtist: '',
			searchInput: '',
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
		searchArtist: function(searchInput) {
			if (searchInput.length != 0) {
				this.searchArtistValid(searchInput);
	
			}
			else {
				alert("Please enter something into the search box.");
			}
			
		},
		searchArtistValid: function (searchInput) {
			AXIOS.get('/artist/artistSearch/' + searchInput)
				.then(response => {
					this.artists = response.data
				})
				.catch(e => {
					this.errorArtist = e.message
					alert(this.errorArtist);
				})
		}
	}
}

