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



