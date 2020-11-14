<template>
<div>
  <h1>Artist Profile <br> 
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistkSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  </h1>
  <div class="userInfo">
    <p id="user">Username: {{ artist.username }} </p>
    <p id="email">Email: {{ artist.email}} </p>
    <p id="dpm">Default payment method: {{ artist.defaultPaymentMethod }}  </p>
    <p id="date">Creation Date: {{ artist.creationDate }} </p>
  </div>

  <div class="artwork">
    <h2>Artwork</h2>
    <span v-for="artwork in artist.artworks" v-bind:key="artwork.artworkID">
      <br>
      Year: {{ artwork.year }}
      <br>
      Style: {{ artwork.artStyle }}
      <br>
      Price: {{ artwork.price }}
    </span>
    <br>
    <br>
  </div>

</div>
</template>

<script src="./javascript/ArtistProfile.js">

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

.artwork{
  padding: 30px;
}
</style>
