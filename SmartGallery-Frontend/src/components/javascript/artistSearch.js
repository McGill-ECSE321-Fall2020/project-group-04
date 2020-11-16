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
			artistNameInput: '',
			response: []
		}
	},
	created: function () {
	this.checkIfLoggedIn()
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
		searchArtist: function (searchInput) {
			if (searchInput.length != 0) {
				if (!searchInput.replace(/\s/g, '').length) {
					alert("The search cannot be empty")
					this.artists = []

				}
				else {
					this.searchArtistValid(searchInput);
				}
			}
			else {
				alert("The search cannot be empty")
				this.artists = []
			}

		},
		searchArtistValid: function (searchInput) {
			AXIOS.get('/artist/artistSearch/' + searchInput)
				.then(response => {
					this.artists = response.data
					if (this.artists.length == 0) {
						alert("No artworks matched your search input")
					}
				})
				.catch(e => {
					this.errorArtist = e.message
					alert(this.errorArtist);
				})
		},
		logout: function () {
			var username = this.$route.params.username
			AXIOS.post('/logout'.concat("?username=", username))
				.then(response => {
					if (response.data) {
						alert("You have been logged out.")
						window.location.href = "/#/"
					}
				})
		},
		goToArtworkSearch: function () {
			window.location.href = "/#/artworkSearch/".concat(this.$route.params.username)
		},
		goToArtistSearch: function () {
			window.location.href = "/#/artistSearch/".concat(this.$route.params.username)
		},
		goToProfile: function () {
			AXIOS.get('/customer/name/'.concat(this.$route.params.username))
				.then(response => {
					window.location.href = "/#/customerProfile/".concat(this.$route.params.username)
				}).catch(e => {
					window.location.href = "/#/artistProfile/".concat(this.$route.params.username)
				})
		},
		goToHome: function () {
			window.location.href = "/#/home/".concat(this.$route.params.username)
		},
		getArtistPageURL: function (artistUsername) {
			return '/#/artistView/'.concat(this.$route.params.username, '/', artistUsername)
		},
		checkIfLoggedIn: function() {
      var username = this.$route.params.username
      AXIOS.get('/customer/name/'.concat(username))
      .then(response => {
        console.log(response.data.loggedIn)
        var isLoggedIn = response.data.loggedIn
        console.log(isLoggedIn)
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(e => {AXIOS.get('/artist/name/'.concat(username))
        .then(response => {
        var isLoggedIn = response.data.isLoggedIn
        if (!isLoggedIn) {
          window.location.href = "/#/"
        }
      })
      .catch(e => {
        window.location.href = "/#/"
      }

      )}

      )
    }	
	}
}
