<script src="./javascript/artistView.js">
</script>

<template>
<body class="artistView">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <span style="font-size:50px; font-family: fantasy"> {{ artist.username }} </span>

  <br>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <div id="artistview">
    <br>
	<span style="font-size:35px; font-family: fantasy"> {{ artist.username }}'s listed artworks:</span>
	<br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">
    <div v-for="artwork in artworkss" :key="artwork.artworkID" >
          <a style="font-weight: bold; text-decoration: underline;" v-bind:href="getListingPageURL(artwork.listing.listingID)">{{ artwork.name }}</a>
          <br>
          Year Created:
          <span style="font-style: italic;"> {{artwork.year }} </span>
          <br>
          Style:
          <span style="font-style: italic;">  {{ artwork.artStyle }} </span>
          <br>
          Price:
          <span style="font-style: italic;"> {{ artwork.price }} $ </span>
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
  background-color: #000000;
  color: white
}
.artistView {
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
body {
  background-color: #e8f4ff;
}
.search2 {
  margin-right: 2px;
  margin-left: 5px;
}
.search3 {
  margin-right: 7px;
}
</style>
