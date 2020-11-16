<script src="./javascript/CreateListing.js">
</script>
<template>

<body class="createListing">

<div>
   <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png" alt="logo">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  </div>
    <h1>
    <span style="font-family: fantasy"> List an Artwork! </span>
    <br>
    <br>
    <button class="exitButton" v-on:click="goToProfile()">Exit</button>
    <br>
    <br>
     <hr style="height:4px;border-width:0;color:gray;background-color:black">
    </h1>

  <br>
  <div class="artList">
    Select one of your creations:
    <br>
    <select v-model="selectedArtwork">
      <option v-for="artwork in artist.artworks" v-bind:value="{ id: artwork.artworkID, gallery: artwork.gallery.galleryName }" :key="artwork.name"> {{artwork.name}}</option>
    </select>
  </div>
  <br>
  <br>
  <input v-model="listPrice" placeholder="Listing Price ($)">
  <br>
  <br> 
  <br>
  <button v-on:click ="createListing(selectedArtwork.id, listPrice, selectedArtwork.gallery); goToProfile()" class="uploadListing"> Create </button>  
  <br>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <br>
</body>
</template>


<style>
p{
  color:black;
	font-weight: bold;
}
.exitButton
{
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
.uploadListing{
  background-color: #008CBA;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 20px;
  border-radius: 6px;
  border: 2px solid black;
  padding: 8px 40px;
  font-family: fantasy;
}
img{
    text-align: center;
    width: 180px;
    height: 105px;
    left:10px;
    top:15px;
}
body {
  background-color: #e8f4ff;
}
.createListing{
  color:black;
  text-align: center;
}

</style>


