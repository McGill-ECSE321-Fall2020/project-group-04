<script src="./javascript/Artwork.js">
</script>

<template>

<body class="createArtwork">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">

<div>
  </div>
  <div class="header">
    <span style="font-size:40px;font-family: fantasy"> Upload Your Artwork! </span>
  </div>
  <div>
    <p>
    <br>
    <button class="button2" v-on:click="goToProfile()">Exit</button>
      <hr style="height:4px;border-width:0;color:gray;background-color:black">
      <span style="font-size:22px;font-family: fantasy"> Please fill in all the fields below: </span>
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

  <button v-on:click="createArtwork(artworkNameInput, yearInput, priceInput, selected, heightInput, weightInput, widthInput)" class="uploadArtwork"> Confirm and select image </button>
  <br>
  <br>

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
.button2
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

body {
  background-color: #e8f4ff;
}
button:hover {
  background-color: #000000;
  color: white
}
.uploadArtwork{
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


.createArtwork {
  color:black;
  text-align: center;
  background-position: center;
  background-repeat: no-repeat;
}

img{
    text-align: center;
    width: 180px;
    height: 105px;
    left:10px;
    top:5px;
}

</style>
