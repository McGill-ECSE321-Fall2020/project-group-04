<script src="./javascript/artworkSearch.js">
</script>


<template>
<body class="artworkSearch">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistkSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <span style="font-size:60px; font-family: fantasy"> Search Artwork </span>
  <br>
  <br>
  <input v-model="artworkNameInput" size="35" placeholder="Artwork name">
  <button class="button2" v-on:click="searchArtwork(artworkNameInput, minPriceInput, maxPriceInput, artStyleInput)" name="search"> SEARCH </button>
  <br>
  <br>
  <span style="font-size:25px; font-family: fantasy"> Filters:  </span>
  <br>
  <br>
  <span style="font-size:18px; font-family: fantasy"> Price  </span>
  <input class="search1" v-model="minPriceInput" size="13" placeholder="Min price">
  <input class="search2" v-model="maxPriceInput" size="13" placeholder="Max price">
  <br>
  <br>
  <span class="search3" style="font-size:18px; font-family: fantasy"> Art Style  </span>
  <input v-model="artStyleInput" size="20" placeholder="Art style">
  <br>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <div id="artworksearch">
    <br>
    <span style="font-size:30px; font-family: fantasy">{{ listings.length}} result(s):</span>
    <br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
    <div v-for="listing in listings" :key="listing.name" >
            <a style="font-weight: bold; text-decoration: underline;" v-bind:href="'/#/ViewListing/' + listing.listingID">{{ listing.artwork.name }}</a>
            <br>
            Artist(s):
            <span v-for="artist in listing.artwork.artists"  :key="artist.username" >
            <span style="font-style: italic;"> {{ artist.username }}, </span>
            </span>
            <br>
            Year Created:
            <span style="font-style: italic;"> {{ listing.artwork.year }} </span>
            <br>
            Style:
            <span style="font-style: italic;">  {{ listing.artwork.artStyle }} </span>
            <br>
            Price:
            <span style="font-style: italic;"> {{ listing.artwork.price }} $ </span>
            <br>
            <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
    </div>
    <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  </div>
</body>
</template>

<style>
.header {
  font-size: 16px;
}
.button2 {
  font-family: fantasy;
  background-color: #008CBA;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  border-radius: 6px;
  border: 2px solid black;
  padding: 8px 40px
}
button:hover {
  box-shadow: 0 0 3px 3px rgba(238, 238, 0, 1);
}
.artworkSearch {
  background-color: #e8f4ff;
    text-align: center;
}
img{
    text-align: center;
    width: 180px;
    height: 105px;
    left:10px;
    top:5px;
}
.search1 {
  margin-right: 2px;
  margin-left: 7px;
}
.search2 {
  margin-right: 2px;
  margin-left: 5px;
}
.search3 {
  margin-right: 7px;
}
</style>
