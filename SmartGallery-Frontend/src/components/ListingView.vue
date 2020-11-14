<template>
<div id='listingInfo'>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  <img src="../assets/sglogo.png"  alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistkSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  
  <h1> Artwork Name : {{ artwork.name }}</h1>
  <h1> Artist(s) Name (s) :  <span v-for="artist in artwork.artists" v-bind:key="artist.username"> {{artist.username}} </span> </h1>
  <img src="../assets/PLACEHOLDER.jpg"><br>
  <h3>Description </h3>
  <div class="description">
    Year Created : {{artwork.year}}<br>
    Price : {{artwork.price}} <br>
    Style : {{artwork.artStyle}} <br>
    Height: {{artwork.height}} <br>
    Width : {{artwork.width}} <br>
    Weight : {{artwork.weight}}<br>
    Sold : {{newListing.isSold}}<br>
  </div>
  <div class="purchase">
    Select payment method
    <br><br>
    <select v-model="selected">
      <option>Credit Card</option>
      <option>Paypal</option>
    </select>
    <br><br>
    Select Delivery method
    <br><br>
    <select v-model="delivery">
      <option>Ship</option>
      <option>Pickup</option>
    </select>
    <br><br>
    <button type="button" name="button" v-on: click = "createTransaction(selected,delivery,username,listingID)" > Purchase </button>
  </div>
</div>
</template>


<script src="./javascript/ListingView">
</script>


<style>
.description {
  width: 30%;
  max-height: auto;
  min-width: 550px;
  margin: auto;
  margin-top: 15px;
  padding: 15px;
  text-align: left;
}

.purchase {
  width: 200;
  height: 200;
}
</style>
