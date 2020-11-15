<script src="./javascript/UpdateArtwork.js">
</script>

<template>
<body class="updateArt">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">

  <div class="header">
    <h1> Update Your Artwork!
    </h1>
	  <p>
    Please fill in all the fields below.
	  <button class="button1" v-on:click="goToProfile()">Exit</button>
	  </p>
	<br>
	<br>
	<br>
	<br>
  </div>
  <br>
  <div>

  </div>
  <br>
  Select a listing to update
  <br>

  <select v-model="selectedListing">
      <option v-for="artwork in artist.artworks" v-bind:value="{ listingid: artwork.listing.listingID, artworkid: artwork.artworkID}" :key="artwork.name"> {{artwork.name}}</option>
    </select>
	<br>
	<br>
  <input v-model="artworkNameInput" placeholder="Artwork name">
  <br>
  <br>
  <input v-model="yearInput" placeholder="Year (YYYY)">
  <br>
  	<div>
  <img src="../assets/sglogo.png" alt="logo">
  </div>
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
  <button class="button2" type="button" v-show = "!addArtist" v-on:click= "addArtist = !addArtist"> Add Artist?</button>
  <br>
    <div class = "uaddArtist" v-show = "addArtist">
    <input v-model="addedArtistInput" placeholder="Enter username">
    <br><br>
    <button class="button2" type="button" v-on:click="addArtists(selectedListing.artworkid, addedArtistInput)"> Add</button>
    <br><br>
    <button class="button2" type="button" v-on:click= "addArtist= !addArtist"> Cancel </button>
  </div>
  <br>
  <input v-model="heightInput" placeholder="Height">
  <br>
  <br>
  <input v-model="weightInput" placeholder="Weight">
  <br>
  <br>
  <input v-model="widthInput" placeholder="Width">
  <br>
  <br>



  <button  v-on:click="updateArtwork(selectedListing.listingid, artworkNameInput, yearInput, priceInput, selectedArtStyle, heightInput, widthInput, weightInput); goToProfile()" class="updateArtwork"> UPDATE </button>

</body>
</template>

<style>

p {
	color:black;
	font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-style: italic;
	font-weight: bold;
}

img{
   width: 250px;
    height: 200px;
    position: absolute;
    left:0;
    top:0;
}

.button1
{
  border-radius: 20px;
  position: absolute;
  top: 20%;
  right: 30%;
  background-color: black;
  color: white;

}

.updateArtwork{

    border-radius: 50px;
  background-color: black;
  color: white;
}

button:hover {
  background-color: #000000;
  color: white
}
.updateArt{

  color:black;
  text-align: center;
  background-image: url('../assets/circle paint.jpg');
  background-position: center;
  background-position-y: 180px;
  background-repeat: no-repeat;

  font-family: fantasy;
}
</style>
