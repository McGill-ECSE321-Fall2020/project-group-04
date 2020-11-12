<script src="./javascript/artistSearch.js">
</script>

<template>
    <div>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <div class="header">
      <h1>SmartGallery - Artist Search
      <button type="button" name="home"> HOME </button>
	  <button onclick="location.href='/#/artworkSearch'" type="button" name="artworkSearch"> SEARCH ARTWORK </button>
      <button type="button" name="viewProfile"> VIEW PROFILE </button>
      </h1>
    </div>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">	
    <br>
    <input v-model="artistNameInput" placeholder="Artist name">
  <button v-on:click="searchArtist(artistNameInput)" name="search"> SEARCH </button>
   <div class="artistsearch">
    <br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">
    <h2>{{ artists.length }} result(s):</h2>

    <br>
    <li v-for="artist in artists" :key="artist.username" >
            {{ artist.username }}
            <br>
			<br>
    </li>
	<br>
	<hr style="height:2px;border-width:0;color:gray;background-color:gray">
  </div>

  </div>
</template>



