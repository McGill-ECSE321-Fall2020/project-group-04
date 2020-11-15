<template>
<div class='listingInfo'>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png" alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <h1 style="font-size:30px; font-family: fantasy"> Artwork Name : {{ artwork.name }} </h1>
  <h1 style="font-size:30px; font-family: fantasy"> Artist(s) Name (s) : <span v-for="artist in artwork.artists" v-bind:key="artist.username"> {{artist.username}} </span> </h1>
  <img src="../assets/PLACEHOLDER.jpg"><br>
  <h3 style="font-size:25px; font-family: fantasy">Description </h3>
  <div class="description" style="font-size:25px; font-family: fantasy">
    Year Created : {{artwork.year}}<br>
    Price : {{artwork.price}} <br>
    Style : {{artwork.artStyle}} <br>
    Height: {{artwork.height}} <br>
    Width : {{artwork.width}} <br>
    Weight : {{artwork.weight}}<br>
    Sold : {{sold}}<br>
  </div>
  <div class="purchase" style="font-size:25px; font-family: fantasy" v-show = "!newListing.sold">
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
    <button class="button2" type="button" name="button" v-on:click ="createTransaction(selected,delivery)" v-show = "!newListing.sold"> Purchase </button>
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
.listingInfo {
  background-color: #e8f4ff;
  text-align: center;
}
.purchase {
  width: 200;
  height: 200;
}
</style>
