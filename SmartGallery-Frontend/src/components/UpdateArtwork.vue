<script src="./javascript/UpdateArtwork.js">
</script>
<template>
<body>
<div>
  <div class="header">
    <h1> Update Your Artwork!
    </h1>
  </div>
  <br>
  <div>
	  <p>
    Please fill in all the fields below.
	  </p>
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

  <button  v-on: click = "updateArtwork(123, yearInput, priceInput, artStyle, heightInput, widthInput, weightInput)" name="updateArtwork"> UPDATE </button>
</div>
</body>
</template>

<style>

p {
	color:red;
	font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	font-style: italic;
	font-weight: bold;
}
body {
  background-image: url('../assets/collage.jpg');
  color:gold;
  text-align: center;
  background-color: rgba(255,255,255,0.4);
  background-blend-mode: lighten;
 
  font-family: fantasy;
}
</style>
