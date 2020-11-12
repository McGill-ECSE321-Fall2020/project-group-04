<script src="./javascript/artworkSearch.js">
</script>

<template>
    <div>
    <hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <div class="header">
      <h1>SmartGallery - Artwork Search
      <button type="button" name="home"> HOME </button>
      <button onclick="location.href='/#/artistSearch'" type="button" name="artistSearch"> SEARCH ARTIST </button>
      <button type="button" name="viewProfile"> VIEW PROFILE </button>
      </h1>
    </div>
    <hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <br>
    <input v-model="artworkNameInput" placeholder="Artwork name">
  <br>
  <br>
  Filters: 
  <br>
    Price: <input v-model="minPriceInput" placeholder="Min price">
  <input v-model="maxPriceInput" placeholder="Max price">
  <br><br>
    Art Style: <input v-model="artStyleInput" placeholder="Art style">
  <br>
  <br>
  <button v-on:click="searchArtwork(artworkNameInput, minPriceInput, maxPriceInput, artStyleInput)" name="search"> SEARCH </button>
  <hr style="height:2px;border-width:0;color:gray;background-color:gray">	

   <div id="artworksearch">
    <br>
    <h2>{{listings.length}} result(s):</h2>
    <br>
    <li v-for="listing in listings" :key="listing.name" >
            {{ listing.artwork.name }}
            <!-- <br> -->
            <!-- Artists: -->
            <!-- <li v-for="artist in listing.artwork.artists" :key="artist.username" > -->
              <!-- {{ artist.username }},  -->
              <!-- </li> -->
            <br>
            Year: {{ listing.artwork.year }}
            <br>
            Style: {{ listing.artwork.artStyle }}
            <br>
            Price: {{ listing.artwork.price }} $
            <br>
            <br>
    </li>
    <br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
  </div>

  </div>
</template>