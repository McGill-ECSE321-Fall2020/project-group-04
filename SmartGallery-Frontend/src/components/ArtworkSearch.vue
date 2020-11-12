<script src="./javascript/artworkSearch.js">
</script>

<template>
    <div>

    <hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <div class="header">
      <h1><span style="font-size:25px">SmartGallery - Artwork Search</span>
      <button type="button" name="home"> Home </button>
      <button onclick="location.href='/#/artistSearch'" type="button" name="artistSearch"> Search Artist </button>
      <button type="button" name="viewProfile"> View Profile </button>
      </h1>
    </div>
    <hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <br>
    <input v-model="artworkNameInput" size="35" placeholder="Artwork name">
  <br>
  <br>
  <span style="font-size:16x">Filters:</span>  
  <br>
    Price <input v-model="minPriceInput" size="13" placeholder="Min price">
  <input v-model="maxPriceInput" size="13" placeholder="Max price">
    Art Style <input v-model="artStyleInput" size="20" placeholder="Art style">
  <br>
  <br>
  <button v-on:click="searchArtwork(artworkNameInput, minPriceInput, maxPriceInput, artStyleInput)" name="search"> SEARCH </button>
  <hr style="height:2px;border-width:0;color:gray;background-color:gray">	

   <div id="artworksearch">
    <br>
    <span style="font-size:16x">{{ listings.length}} result(s):</span>  
    <br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:gray">	
    <ul v-for="listing in listings" :key="listing.name" >
            <br>
            {{ listing.artwork.name }}
            <br>
            Artist(s):
            <span v-for="artist in listing.artwork.artists" :key="artist.username" > 
              {{ artist.username }},
            </span>
            <br>
            Year: {{ listing.artwork.year }}
            <br>
            Style: {{ listing.artwork.artStyle }}
            <br>
            Price: {{ listing.artwork.price }} $
            <br>
            <br>
            <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:gray">	

    </ul>
    <br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
  </div>

  </div>
</template>

<style>
.header {
  font-size: 16px;
}
button {
  background-color: #008CBA;
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 6px;
  border: 2px solid black;
  padding: 8px 40px
}
body {
  background-color: #e8f4ff;
}
</style>
