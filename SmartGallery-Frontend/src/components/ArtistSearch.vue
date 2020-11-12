<script src="./javascript/artistSearch.js">
</script>

<template>
    <div>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <div class="header">
            <h1><span style="font-size:25px">SmartGallery - Artist Search </span>
      <button type="button" name="home"> HOME </button>
	  <button onclick="location.href='/#/artworkSearch'" type="button" name="artworkSearch"> SEARCH ARTWORK </button>
      <button type="button" name="viewProfile"> VIEW PROFILE </button>
      </h1>
    </div>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <br>
    <input v-model="artistNameInput" size="35" placeholder="Artist name">
  <button v-on:click="searchArtist(artistNameInput)" name="search"> SEARCH </button>
   <div class="artistsearch">
    <br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">
    <span style="font-size:16x">{{ artists.length}} result(s):</span>  
    <br>
	<hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:gray">	
    <ul v-for="artist in artists" :key="artist.username" >
			<br>
            <!-- {{ artist.username }} -->
			<a href="/#/artworkSearch">{{ artist.username }}</a>
            <br>
			<br>
			<hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:gray">	

    </ul>
	<br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">
  </div>

  </div>
</template>

<style>
.header {
  font-size: 16px;
}
button {
  background-color: #008CBA;
  border: none;
  color: white;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 6px;
  border: 2px solid black;
  padding: 8px 40px
}
body {
  background-color: #e8f4ff;
}
</style>

