<script src="./javascript/Artwork.js">
</script>
<template>
    <div>
    <div class="header">
      <h1> Upload Your Artwork!
      </h1>
    </div>
    <br>
    <div>
      Please fill in all the fields below.
      </div>
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
 <div class = "artStyle">
   Select Art Style
    <br>
    <select v-model="selected">
    <option >realist</option>
    <option>renaissance</option>
    <option>surrealist</option>
    <option>impressionist</option>
    </select>
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
   <input v-model="artistInput" placeholder="Enter your username">
   <br>
   <br>
   <input v-model="galleryInput" placeholder="Gallery">
   <br>
   <br>
   <br>
  <button v-on:click="createArtwork(artworkNameInput, yearInput, priceInput, artStyle, heightInput, weightInput, widthInput, artistInput, galleryInput)"  name="uploadArtwork"> UPLOAD </button>
    


  </div>
</template>

<style>
.header {
  text-align: center;
}


</style>
