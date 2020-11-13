<script src="./javascript/artistSearch.js">
</script>

<template>
<body class="artistSearch">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" type="button" name="home"> Home </button>
  <button class="button2" onclick="location.href='/#/artworkSearch'" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" onclick="location.href='/#/artistSearch'" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" onclick="location.href='/#/profile'" type="button" name="viewProfile"> View Profile </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  <span style="font-size:60px; font-family: fantasy"> Search Artist </span>
  <br>
  <br>
  <input v-model="artistNameInput" size="35" placeholder="Artist name">
  <button class="button2" v-on:click="searchArtist(artistNameInput)" name="search"> SEARCH </button>
  <div class="artistsearch">
    <br>
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
    <span style="font-size:30px; font-family: fantasy">{{ artists.length}} result(s):</span>  
    <br>
    <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">	
    <div v-for="artist in artists" :key="artist.username" >
      <br>
      <a style="font-weight: bold;  text-decoration: underline" v-bind:href="'/#/Artist/' + artist.username">{{ artist.username }}</a>
      <br>
      <br>
      <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">	
      </div>
    <br>
    <hr style="height:4px;border-width:0;color:gray;background-color:black">
  </div>
</body>
</template>

<style>
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
  box-shadow: 0 0 3px 3px rgba(238, 238, 0, 1);
}
.artistSearch {
  background-color: #e8f4ff;
    text-align: center;
}
img{
    text-align: center;
    width: 180px;
    height: 105px;
    left:10px;
    top:15px;
}
</style>

