<script src="./javascript/CustomerProfile.js">
</script>

<template>
<body class="customer" style="font-size:25px; font-family: fantasy background-color: #e8f4ff; text-align: center;">
  <hr style="height:4px;border-width:0;color:gray;background-color:black">
  <img src="../assets/sglogo.png" alt="logo">
  <button class="button2" v-on:click="goToHome()" type="button" name="home"> Home </button>
  <button class="button2" v-on:click="goToArtworkSearch()" type="button" name="artworkSearch"> Search Artwork </button>
  <button class="button2" v-on:click="goToArtistSearch()" type="button" name="artistSearch"> Search Artist </button>
  <button class="button2" v-on:click="goToProfile()" type="button" name="viewProfile"> View Profile </button>
  <button class="button2" v-on:click="logout()" type="button" name="logout"> Logout </button>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">

  <span style="font-size:60px; font-family: fantasy"> Customer Profile </span>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	 
  <br>
  <div class="userInfo" style="font-size:25px; font-weight: bold">
    <p id="user">Username: {{ customer.username}} </p>
    <br>
    <p id="email">Email: {{ customer.email}} </p>
    <br>
    <p id="dpm">Default payment method: {{ customer.defaultPaymentMethod }} </p>
    <br>
    <p id="date">Creation Date: {{ customer.creationDate }} </p>
  </div>
  <br>
  <button class="button2" type="button" name="updateProfile" v-show="!showPassword" v-on:click="showPassword = !showPassword"> Update password </button>
  <div class="updatePassword" v-show="showPassword">
    <input v-model="oldPasswordInput" placeholder="old password">
    <br><br>
    <input v-model="newPasswordInput" placeholder="new password">
    <br><br>
    <button class="button2" type="button" v-on:click="updatePassword(oldPasswordInput, newPasswordInput)"> Change </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showPassword= !showPassword"> Cancel </button>
  </div>
  <br>
  <br>
  <button class="button2" type="button" name="updateEmail" v-show="!showEmail" v-on:click="showEmail = !showEmail"> Update email </button>
  <div class="updateEmail" v-show="showEmail">
    <input v-model="newEmail" placeholder="new email">
    <br><br>
    <input v-model="passwordInput" placeholder="password">
    <br><br>
    <button class="button2" type="button" v-on:click="updateEmail(newEmail,passwordInput)"> Change </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showEmail= !showEmail"> Cancel </button>
  </div>
  <br>
  <br>
  <button class="button2" type="button" name="deleteCustomer" v-show="!showDelete" v-on:click="showDelete = !showDelete"> Delete Account </button>
  <br>
  <div class="deleteCustomer" v-show="showDelete">
    <hr style="height:4px;border-width:0;color:gray;background-color:black">	
    <br>
    <p>Are you sure you want to delete your account?</p>
    <br>
    <button class="button2" type="button" v-on:click="deleteCustomer()"> Yes </button>
    <br><br>
    <button class="button2" type="button" v-on:click="showDelete= !showDelete"> No </button>
  </div>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  <br>
  <span style="font-size:30px; font-family: fantasy"> BROWSE HISTORY: </span>
  <br>
  <br>
  <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">	
  <div v-for="artwork in browseHistory" v-bind:key="artwork.artworkID">
      <a style="font-weight: bold; text-decoration: underline;" v-bind:href="getListingPageURL(artwork.listing.listingID)">{{ artwork.name }}</a>
      <br>
      Artist(s):
      <span v-for="artist in artwork.artists"  :key="artist.username" >
      <span style="font-style: italic;"> {{ artist.username }}, </span>
      </span>
      <br>
      Year Created:
      <span style="font-style: italic;"> {{ artwork.year }} </span>
      <br>
      Style:
      <span style="font-style: italic;">  {{ artwork.artStyle }} </span>
      <br>
      Price:
      <span style="font-style: italic;"> {{ artwork.price }} $ </span>
      <br>
      <hr style="width:1000px;height:2px;border-width:0;color:gray;background-color:black">	
  </div>
  <br>
  <hr style="height:4px;border-width:0;color:gray;background-color:black">	
  <br>
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
  background-color: #000000;
  color: white
}
body {
  background-color: #e8f4ff;
}
img {
  text-align: center;
  width: 180px;
  height: 105px;
}

.customer {
  color: black;
  text-align: center;
  background-color: #e8f4ff;
}
</style>
