<script src="./javascript/CreateListing.js">
</script>
<template>

<body class="createListing">

<div>
  <img src="../assets/sglogo.png" alt="logo">
  </div>
    <h1> List an Artwork!
    </h1>
 
 
  <div>
    <p>
    Please fill in all the fields below.
    <button class="exitButton">Exit</button>
    </p>
  </div>
  <br>
  <br>
  <div class="artList">
    Select on of your creations:
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
  <br>
  <button v-on:click = "createListing(selectedArtwork.id, listPrice, selectedArtwork.gallery)" class="uploadListing"> LIST </button>
</body>
</template>


<style>
p{
  color:black;
	font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-style: italic;
	font-weight: bold;
}
.exitButton
{
  border-radius: 20px;
  position: absolute;
  right: 30%;
  background-color: black;
  color: white;
  
}
button:hover {
  background-color: #000000;
  color: white
}
.uploadListing{
    border-radius: 50px;
  background-color: black;
  color: white;
}
img{
   width: 250px;
    height: 200px;
    position: absolute;
    left:0;
    top:0;
}

.createListing{
  color:black;
  text-align: center;

  font-family: fantasy;
}
</style>


