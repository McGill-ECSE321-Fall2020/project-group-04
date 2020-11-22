<script src="./javascript/UpdateArtwork.js">
</script>

<template>
<body class="updateArt">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png"  alt="logo">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">

  <div class="header">
    <span style="font-size:40px; font-family: fantasy"> Update Your Artwork! </span>
	  <p>
    <br>
    <button v-on:click="goToProfile()">Exit</button>
    <br>
    <br>
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <br>
      <span style="font-size:22px;font-family: fantasy"> Please fill in all the fields below: </span>
	 </p>
  </div>

  <br>
  Select a listing to update
  <br>

  <select v-model="selectedListing">
      <option v-for="artwork in artist.artworks" v-bind:value="{artwork: artwork}" :key="artwork.name"> {{artwork.name}}</option>
    </select>
	<br>
	<br>
  <input v-model="artworkNameInput" placeholder="Artwork name">
  <br>
  <br>
  <input v-model="yearInput" placeholder="Year (YYYY)">
  <br>
  <br>
  <input v-model="priceInput" placeholder="Valued Price ($)">
  <br>
  <br>
  <div class="artStyle">
    Select Art Style
    <br>
    <select v-model="selectedArtStyle">
      <option>realist</option>
      <option>renaissance</option>
      <option>surrealist</option>
      <option>impressionist</option>
    </select>
  </div>

    <br>
  <input v-model="heightInput" placeholder="Height (cm)">
  <br>
  <br>
  <input v-model="weightInput" placeholder="Weight (kg)">
  <br>
  <br>
  <input v-model="widthInput" placeholder="Width (cm)">
  <br>

  <br>
  <br>
  <button type="button" v-show = "!addArtist" v-on:click= "addArtist = !addArtist"> Add Artist?</button>
  <br>
    <div class = "uaddArtist" v-show = "addArtist">
    <input v-model="addedArtistInput" placeholder="Enter username">
    <br><br>
    <button type="button" v-on:click="addArtists(selectedListing.artworkid, addedArtistInput)"> Add</button>
    <br><br>
    <button type="button" v-on:click= "addArtist= !addArtist"> Cancel </button>
  </div>
  <br>
  <button  v-on:click="updateArtwork(selectedListing.artwork, artworkNameInput, yearInput, priceInput, selectedArtStyle, heightInput, widthInput, weightInput); goToProfile()" > UPDATE </button>
  <br>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <br>
  <br>
</body>
</template>

<style>

p {
	color:black;
	font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-style: italic;
	font-weight: bold;
}

body {
  background-color: #e8f4ff;
}
button
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
img{
    text-align: center;
    width: 180px;
    height: 105px;
    left:10px;
    top:15px;
}

button:hover {
  background-color: #000000;
  color: white
}
.updateArt{
  color:black;
  text-align: center;
}
</style>
