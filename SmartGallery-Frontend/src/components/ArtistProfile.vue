<template>
<div>
  <h1>Artist Profile <br> 
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  </h1>
  <div class="userInfo">
    <p id="user">Username: {{ artist.username }} </p>
    <p id="email">Email: {{ artist.email}} </p>
    <p id="dpm">Default payment method: {{ artist.defaultPaymentMethod }}  </p>
    <p id="date">Creation Date: {{ artist.creationDate }} </p>
  </div>
    <button type="button" name="updateProfile" v-show = "!showPassword" v-on:click= "showPassword = !showPassword"> Update password </button>
  <div class = "updatePassword" v-show = "showPassword">
    <input v-model="oldPasswordInput" placeholder="old password">
    <br><br>
    <input v-model="newPasswordInput" placeholder="new password">
    <br><br>
    <button type="button" onclick="updatePassword(oldPasswordInput, newPasswordInput)"> Change </button>
    <br><br>
    <button type="button" v-on:click= "showPassword= !showPassword"> Cancel </button>
  </div>
  <br>
  <br>
  <button type="button" name="updateEmail" v-show = "!showEmail" v-on:click= "showEmail = !showEmail"> Update email </button>
  <div class = "updateEmail"  v-show = "showEmail">
    <input v-model="newEmail" placeholder="new email">
    <br><br>
    <input v-model="passwordInput" placeholder="password">
    <br><br>
    <button type="button" onclick="updateEmail(newEmail,passwordInput)"> Change </button>
    <br><br>
    <button type="button" v-on:click= "showEmail= !showEmail" > Cancel </button>
  </div>
  <br>
  <div class="artwork">
    <h2>Artwork</h2>
    <span v-for="artwork in artist.artworks" v-bind:key="artwork.artworkID">
      Name: {{ artwork.name}}
      <button type="button" name="createListing"> Create Listing </button>
      <button type="button" name="updateListing"> Update Listing </button>
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
