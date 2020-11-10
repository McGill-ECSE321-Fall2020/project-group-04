import axios from 'axios'
  var config = require('../../config')

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

  var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

  export default {
  name: 'listinginfo',
  data () {
    return {
      listing: '',
      errorListing: '',
      response: []
    }
  },
  retrieveListing: function (listingID) {
    AXIOS.get('/listing/'.concat(listingID))
    .then(response +> {
      this.listing = response.data
    })
    .catch(e => {
      this.errorListing = e
    })
  }
}
