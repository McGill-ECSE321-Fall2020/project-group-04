<script src="./javascript/artworkSearch.js">
</script>

<template>
    <div>
    <div class="header">
      <h1>SmartGallery - Artwork Search
      <button type="button" name="home"> HOME </button>
      <button type="button" name="artistSearch"> SEARCH ARTIST </button>
      <button type="button" name="viewProfile"> VIEW PROFILE </button>
      </h1>
    </div>
    <br>
    <input v-model="artworkNameInput" placeholder="Artwork name">
  <br>
  <br>
  Filters: 
  <input v-model="minPriceInput" placeholder="Min price">
  <input v-model="maxPriceInput" placeholder="Max price">
  <input v-model="artStyleInput" placeholder="Art Style">
  <br>
  <br>
  <button v-on:click="searchArtwork(artworkNameInput, minPriceInput, maxPriceInput, artStyleInput)" name="search"> SEARCH </button>
    <!-- <button v-on:click="searchArtworkNoFilter(artworkNameInput)" name="search"> SEARCH </button> -->


   <div id="artworksearch">
    <br>
    <h2>Results:</h2>
    <br>
    <li v-for="listing in listings" :key="listing.name" >
            {{ listing.artwork.name }}
            <br>
            Price: {{ listing.artwork.price }} $
    </li>
  </div>

  </div>
</template>

<style>
.header {
  text-align: left;
}

.header {
  text-align: left;
}

.header {
  text-align: left;
}

.header {
  text-align: left;
}

.header {
  text-align: left;
}


</style>



