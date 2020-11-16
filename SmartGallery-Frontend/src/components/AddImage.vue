<template>
<div>
  <h1>Artist Profile <br>
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="goToCreateArtwork()" type="button" name="uploadArtwork"> Upload Artwork </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  </h1>

  <div id="inputUrl" style="display:block">
    <h3> Enter your image url:
      <input
        type="text"
        v-model="imageUrl"
        placeholder="Image URL"> 
      <button class="button2" v-on:click="confirmImage(imageUrl)" type="button" name="confirmImage"> Confirm </button>
    </h3>
  </div>

  <div id="image" style="display:none">
    <img src="" alt="Your artwork" id="picture" textalign:center> 
  </div> 

</div>
</template>

<script src="./javascript/AddImage.js">

</script>

<style lang="css" scoped>
#user{
  padding: 10px;
}
#email{
  padding: 10px;
}
#dpm{
  padding: 10px;
}
#date{
  padding: 10px;
}
body {
  background-color: #e8f4ff;
}
.artwork{
  padding: 30px;
}
</style>
