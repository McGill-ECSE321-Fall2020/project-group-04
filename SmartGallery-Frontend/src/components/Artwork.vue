<script src="./javascript/Artwork.js">

</script>
<template>
<body class="createArtwork">

<div>
  <img src="../assets/sglogo.png" alt="logo">
  </div>
  <div class="header">
    <h1> Upload Your Artwork!
    </h1>
  </div>
 
  <div>
    <p>
    Please fill in all the fields below.
    <button class="button1">Exit</button>
    </p>
  </div>
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
    <select v-model="selected">
      <option>realist</option>
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
  <button v-on: click = "createArtwork(artworkNameInput, yearInput, priceInput, artStyle, heightInput, weightInput, widthInput, artistInput, galleryInput)" class="uploadArtwork"> UPLOAD </button>

</body>
</template>

<style>

p{
  color:black;
	font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-style: italic;
	font-weight: bold;
}

.button1
{
  border-radius: 20px;
  position: absolute;
  right: 30%;
  background-color: black;
  color: white;
  
}

.uploadArtwork{

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
.createArtwork {

  color:black;
  text-align: center;
  background-image: url('../assets/circle paint.jpg');
  background-position: center;
  background-repeat: no-repeat;
  font-family: fantasy;
}
</style>
