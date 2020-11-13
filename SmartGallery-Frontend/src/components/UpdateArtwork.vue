<script src="./javascript/UpdateArtwork.js">
</script>
<template>
<body class="updateArt">

  <div class="header">
    <h1> Update Your Artwork!
    </h1>
	  <p>
    Please fill in all the fields below.
	  <button class="button1">Exit</button>
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

  <button  v-on: click = "updateArtwork(123, yearInput, priceInput, artStyle, heightInput, widthInput, weightInput)" class="updateArtwork"> UPDATE </button>

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


.updateArt{

  color:black;
  text-align: center;
  background-image: url('../assets/circle paint.jpg');
  background-position: center;
  background-position-y: 80px;
  background-repeat: no-repeat;

  font-family: fantasy;
}
</style>
